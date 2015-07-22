package info.hagen.health.json;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import info.hagen.health.domain.HealthStatus;
import info.hagen.health.domain.Indicator;
import info.hagen.health.domain.IndicatorMessage;
import info.hagen.health.domain.Server;
import org.slf4j.Logger;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Parse Spring Boot Health JSON message
 */
public class JsonHealthParser {

    private static final String JSON_STATUS_NODE = "status";
    private static final Logger LOG = org.slf4j.LoggerFactory.getLogger(JsonHealthParser.class);
    private ObjectMapper mapper = new ObjectMapper();

    public void parseServerHealth(Server server, String serverResponse) {
        try {
            JsonNode serverResponseNode = mapper.readTree(serverResponse);

            HealthStatus serverHealthStatus = parseHealthStatus(serverResponseNode);
            if (serverHealthStatus == null) {
                LOG.warn("Could not parse health status from server response " + server + ". Response: " + serverResponse);
                server.setExceptionErrorState("NoHealthStatus", "Could not parse health status from server response");
                return;
            }

            server.setHealthStatus(serverHealthStatus);
            server.setIndicators(parseIndicators(serverResponseNode));


        } catch (NullPointerException e) {
            LOG.warn("No response from server " + server);
            server.setExceptionErrorState("EmptyResponse", "No response from server");
        } catch (IOException e) {
            LOG.warn("Error parsing response from server " + server + ". Response: " + serverResponse + " Exc: " + e.getMessage());
            server.setExceptionErrorState("ParseError", "Error parsing response");
        }
    }


    private List<Indicator> parseIndicators(JsonNode jsonNode) {
        List<Indicator> indicators = new ArrayList<>();
        jsonNode.fields().forEachRemaining(jsonElement -> parseIndicator(jsonElement, indicators));
        return indicators;
    }


    private void parseIndicator(Map.Entry<String, JsonNode> indicatorElement, List<Indicator> indicators) {
        if (isStatusNode(indicatorElement.getKey())) {
            return;
        }

        HealthStatus healthStatus = parseHealthStatus(indicatorElement.getValue());
        if (healthStatus == null) {
            return;
        }

        List<IndicatorMessage> messages = parseIndicatorMessages(indicatorElement.getValue());
        indicators.add(new Indicator(indicatorElement.getKey(), healthStatus, messages));
    }

    private List<IndicatorMessage> parseIndicatorMessages(JsonNode jsonNode) {
        List<IndicatorMessage> indicatorMessages = new ArrayList<>();
        jsonNode.fields().forEachRemaining(jsonElement -> parseIndicatorMessage(jsonElement, indicatorMessages));
        return indicatorMessages;

    }

    private void parseIndicatorMessage(Map.Entry<String, JsonNode> indicatorElement, List<IndicatorMessage> indicatorMessages) {
        if (isStatusNode(indicatorElement)) {
            return;
        }

        String key = indicatorElement.getKey();
        String value = indicatorElement.getValue().asText();
        indicatorMessages.add(new IndicatorMessage(key, value));
    }


    private HealthStatus parseHealthStatus(JsonNode jsonNode) {
        JsonNode statusNode = jsonNode.get(JSON_STATUS_NODE);

        if (statusNode != null) {
            return new HealthStatus(statusNode.asText());
        }
        return null;
    }


    private boolean isStatusNode(String key) {
        return JSON_STATUS_NODE.equals(key);
    }

    private boolean isStatusNode(Map.Entry<String, JsonNode> element) {
        return isStatusNode(element.getKey());
    }


}
