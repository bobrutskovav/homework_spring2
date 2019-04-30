package ru.otus.actuators;

import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.ReactiveHealthIndicator;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component
public class SimpleHealthIndicator implements ReactiveHealthIndicator {
    @Override
    public Mono<Health> health() {
        return Mono.just(Health.status("Its All Good!")
                .up()
                .withDetail("Library Status", "Open")
                .build());
    }
}
