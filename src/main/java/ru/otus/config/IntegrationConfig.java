package ru.otus.config;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.channel.RendezvousChannel;
import org.springframework.integration.dsl.MessageChannels;

@Configuration
public class IntegrationConfig {


    @Bean
    public RendezvousChannel newBookChannel() {
        return MessageChannels.rendezvous("newBookChannel").get();
    }

    @Bean
    public RendezvousChannel monoVoidChannel() {
        return MessageChannels.rendezvous("monoVoidChannel").get();
    }

}
