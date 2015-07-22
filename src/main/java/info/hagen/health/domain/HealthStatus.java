package info.hagen.health.domain;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import info.hagen.health.json.HealthStatusJsonSerializer;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 * The health status of a component, e.g a server or a health indicator.
 */
@JsonSerialize(using = HealthStatusJsonSerializer.class)
public class HealthStatus {

    private static final String UP_STATUS_STRING = "UP";
    private static final String DOWN_STATUS_STRING = "DOWN";
    private static final String UNKNOWN_STATUS_STRING = "UNKNOWN";

    public static final HealthStatus UNKNOWN = new HealthStatus(UNKNOWN_STATUS_STRING);
    public static final HealthStatus UP = new HealthStatus(UP_STATUS_STRING);
    public static final HealthStatus DOWN = new HealthStatus(DOWN_STATUS_STRING);


    private final String status;

    public HealthStatus(String status) {
        this.status = status;
    }

    public boolean isUp() {
        return UP_STATUS_STRING.equalsIgnoreCase(status);
    }


    public String getStatus() {
        return status;
    }

    @Override
    public boolean equals(Object obj) {
        return EqualsBuilder.reflectionEquals(this, obj);
    }

    @Override
    public int hashCode() {
        return HashCodeBuilder.reflectionHashCode(this);
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }
}

