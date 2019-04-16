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
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.data.MongoItemWriter;
import org.springframework.batch.item.file.builder.FlatFileItemReaderBuilder;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.FileSystemResource;
import org.springframework.data.mongodb.core.MongoTemplate;
import ru.otus.domain.Book;

@EnableBatchProcessing
@Configuration

public class BatchConfig {

    @Autowired
    public JobBuilderFactory jobBuilderFactory;
    @Autowired
    public StepBuilderFactory stepBuilderFactory;
    private Logger log = LoggerFactory.getLogger(BatchConfig.class);

    @Bean
    public ItemReader<Book> bookReader() {
        /*return new MongoItemReaderBuilder<Person>()
                .name("mongoPersonReader")
                .template(mongoTemplate)
                .targetType(Person.class)
                .jsonQuery("{}")
                .sorts(new HashMap<>())
                .build();*/
        return new FlatFileItemReaderBuilder<Book>()
                .name("bookItemReader")
                .resource(new FileSystemResource("books.csv"))
                .delimited()
                .names(new String[]{"title", "author", "genre"})
                .fieldSetMapper(new BeanWrapperFieldSetMapper<Book>() {{
                    setTargetType(Book.class);
                }})
                .build();
    }

    @Bean
    public ItemWriter<Book> bookWriter(@Autowired MongoTemplate mongoTemplate) {
        MongoItemWriter<Book> writer = new MongoItemWriter<>();
        try {
            writer.setTemplate(mongoTemplate);
        } catch (Exception e) {
            log.error(e.toString());
        }
        writer.setCollection("library");
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
    public Job importBookJob(Step step1) {
        return jobBuilderFactory.get("importBookJob")
                .incrementer(new RunIdIncrementer())
                .flow(step1)
                .end()
                .listener(new JobExecutionListener() {
                    @Override
                    public void beforeJob(JobExecution jobExecution) {

                        log.info("Начало job {}", jobExecution.getJobConfigurationName());
                    }

                    @Override
                    public void afterJob(JobExecution jobExecution) {
                        log.info("Конец job {}", jobExecution.getJobConfigurationName());
                    }
                })
                .build();
    }


    @Bean
    public Step step1(ItemWriter writer, ItemReader reader, ItemProcessor itemProcessor) {
        return stepBuilderFactory.get("step1")
                .chunk(5)
                .reader(reader)
                .processor(itemProcessor)
                .writer(writer)

//                .taskExecutor(new SimpleAsyncTaskExecutor())
                .build();


    }
}
