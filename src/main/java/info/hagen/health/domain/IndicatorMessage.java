package info.hagen.health.domain;


/**
 * Health indicators may contain simple key / value messages.
 */
public class IndicatorMessage {

    private final String key;
    private final String value;


    public IndicatorMessage(String key, String value) {
        this.key = key;
        this.value = value;
    }

    public String getKey() {
        return key;
    }

    public String getValue() {
        return value;
    }
}
