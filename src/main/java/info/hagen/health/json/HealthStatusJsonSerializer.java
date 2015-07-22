package info.hagen.health.json;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import info.hagen.health.domain.HealthStatus;

import java.io.IOException;

public class HealthStatusJsonSerializer extends JsonSerializer<HealthStatus> {

    @Override
    public void serialize(HealthStatus healthStatus, JsonGenerator jsonGenerator, SerializerProvider provider) throws IOException {
        jsonGenerator.writeString(healthStatus.getStatus());
    }


}
