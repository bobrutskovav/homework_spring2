package ru.otus;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@EnableMongoRepositories
@SpringBootApplication
public class Main {

    public static void main(String[] args) {
        //Todo Добавить отображение комментариев / добавление комментариев
        //Todo Добавить стили (Bootstrap?)
        //ToDo Добавить тесты MockMvc
        SpringApplication.run(Main.class);

    }

}
