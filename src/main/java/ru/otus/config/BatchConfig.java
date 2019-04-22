package ru.otus.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobExecutionListener;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.data.MongoItemWriter;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.builder.FlatFileItemReaderBuilder;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.FileSystemResource;
import org.springframework.data.mongodb.core.MongoTemplate;
import ru.otus.dao.BookRepository;
import ru.otus.domain.Book;

@EnableBatchProcessing
@Configuration

public class BatchConfig {

    @Autowired
    BookRepository bookRepository;


    @Autowired
    public JobBuilderFactory jobBuilderFactory;
    @Autowired
    public StepBuilderFactory stepBuilderFactory;
    private Logger log = LoggerFactory.getLogger(BatchConfig.class);

    @Bean
    public FlatFileItemReader<Book> fileBookReader() {

        return new FlatFileItemReaderBuilder<Book>()
                .name("fileBookReader")
                .resource(new FileSystemResource("books.csv"))
                .delimited()
                .names(new String[]{"title", "author", "genre"})
                .fieldSetMapper(new BeanWrapperFieldSetMapper<Book>() {{
                    setTargetType(Book.class);
                }})
                .build();
    }

    @Bean
    public MongoItemWriter<Book> mongoBookWriter(@Autowired MongoTemplate mongoTemplate) {
        MongoItemWriter<Book> writer = new MongoItemWriter<>();
        try {
            writer.setTemplate(mongoTemplate);
        } catch (Exception e) {
            log.error(e.toString());
        }
        writer.setCollection("book");
        return writer;
    }


    @Bean
    public ItemProcessor processor() {
        return o -> {
            Book book = (Book) o;
            log.info("Process Book with Title {}", book.getTitle());
            return o;
        };
    }

    @Bean
    public Job importBookJob(Step loadDataToMongo) {
        return jobBuilderFactory.get("importBookJob")
                .incrementer(new RunIdIncrementer())
                .flow(loadDataToMongo)
                .end()
                .listener(new JobExecutionListener() {


                    @Override
                    public void beforeJob(JobExecution jobExecution) {

                        log.info("Начало job {}", jobExecution.getJobInstance().getJobName());
                    }

                    @Override
                    public void afterJob(JobExecution jobExecution) {
                        log.info("Конец job {}", jobExecution.getJobInstance().getJobName());
                        bookRepository.findAll().map(book -> {
                            log.info("Загружена книга {}", book.getTitle());
                            return book;
                        }).subscribe();
                    }
                })
                .build();
    }


    @Bean
    public Step loadDataToMongo(MongoItemWriter writer, FlatFileItemReader reader, ItemProcessor itemProcessor) {
        return stepBuilderFactory.get("loadDataToMongo")
                .chunk(5)
                .reader(reader)
                .processor(itemProcessor)
                .writer(writer)

//                .taskExecutor(new SimpleAsyncTaskExecutor())
                .build();


    }
}
