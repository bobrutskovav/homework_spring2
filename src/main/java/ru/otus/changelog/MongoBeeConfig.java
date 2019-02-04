package ru.otus.changelog;


import com.github.mongobee.Mongobee;
import com.mongodb.MongoClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

@Configuration
public class MongoBeeConfig {


    @Autowired
    private MongoClient mongoClient;


    @Bean
    public Mongobee mongobee(Environment environment) {
        Mongobee runner = new Mongobee(mongoClient);
        runner.setDbName("library");
        runner.setChangeLogsScanPackage(MongoChangeLog.class.getPackage().getName());
        runner.setSpringEnvironment(environment);
        return runner;
    }
}
