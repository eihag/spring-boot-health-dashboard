package info.hagen.health.sample;

import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.stereotype.Component;

import java.util.Random;

/**
 * Example of sample Spring Boot Health Indicator with custom properties.
 */
@Component
public class SampleCustomHealthIndicator implements HealthIndicator {

    @Override
    public Health health() {
        boolean isOk = amIfeelingHappy();
        if (!isOk) {
            return Health.down().withDetail("Custom Error Code", 123).build();
        }
        return Health.up().withDetail("Custom key", "Custom value").withDetail("other Key", "value2").build();
    }

    /**
     * Indicator does not always feel well.
     */
    private boolean amIfeelingHappy() {
        Random r = new Random();
        return r.nextBoolean();

    }
}
