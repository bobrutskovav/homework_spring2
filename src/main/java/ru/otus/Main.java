package ru.otus;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.repository.config.EnableReactiveMongoRepositories;

@EnableReactiveMongoRepositories
@SpringBootApplication
public class Main {

    public static void main(String[] args) {


        //ToDo Возможно стоит представить Комметарии по- старому
        SpringApplication.run(Main.class);

    }

}
