package info.hagen.health.domain;

import java.util.List;

/**
 * The response from a health indicator. See http://docs.spring.io/spring-boot/docs/current/reference/html/production-ready-endpoints.html#production-ready-health
 *
 */
public class Indicator {

    private final String indicatorName;


    private final HealthStatus healthStatus;

    private final List<IndicatorMessage> messages;

    public Indicator(String indicatorName, HealthStatus healthStatus, List<IndicatorMessage> messages) {
        this.indicatorName = indicatorName;
        this.healthStatus = healthStatus;
        this.messages = messages;
    }

    public String getIndicatorName() {
        return indicatorName;
    }


    public HealthStatus getHealthStatus() {
        return healthStatus;
    }

    public List<IndicatorMessage> getMessages() {
        return messages;
    }
}
