package info.hagen.health.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.ArrayList;
import java.util.List;

/**
 * The configuration and health status of an application server.
 */
public class Server {

    private String serverName;
    private String healthUrl;

    private String username;
    private String password;

    // defaults to false
    private boolean sslVerificationDisabled;


    private HealthStatus healthStatus;
    private List<Indicator> indicators;

    public Server() {
    }

    public Server(String serverName, String healthUrl, String username, String password) {
        this.serverName = serverName;
        this.healthUrl = healthUrl;
        this.username = username;
        this.password = password;
    }

    public String getServerName() {
        return serverName;
    }


    public void setServerName(String serverName) {
        this.serverName = serverName;
    }

    @JsonIgnore
    public String getHealthUrl() {
        return healthUrl;
    }

    public void setHealthUrl(String healthUrl) {
        this.healthUrl = healthUrl;
    }

    @JsonIgnore
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @JsonIgnore
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @JsonIgnore
    public boolean isSslVerificationDisabled() {
        return sslVerificationDisabled;
    }

    public void setSslVerificationDisabled(boolean sslVerificationDisabled) {
        this.sslVerificationDisabled = sslVerificationDisabled;
    }

    public void setHealthStatus(HealthStatus healthStatus) {
        this.healthStatus = healthStatus;
    }


    public HealthStatus getHealthStatus() {
        if (healthStatus == null) {
            return HealthStatus.UNKNOWN;
        }
        return healthStatus;
    }

    public List<Indicator> getIndicators() {
        if (indicators == null || indicators.size() == 0) {
            return null;
        }

        return indicators;
    }

    public void setIndicators(List<Indicator> indicators) {
        this.indicators = indicators;
    }


    @Override
    public String toString() {
        return "Server{" +
                "serverName='" + serverName + '\'' +
                ", healthUrl='" + healthUrl + "'}";
    }


    /**
     * Set server in "DOWN" state and put exception in JSON output as message.
     */
    public void setExceptionErrorState(Exception exception) {
        setExceptionErrorState(exception.getClass().getSimpleName(), exception.getMessage());
    }

    /**
     * Set server in "DOWN" state and put key/value in JSON output as message.
     */
    public void setExceptionErrorState(String errorKey, String errorMessage) {
        IndicatorMessage indicatorMessage = new IndicatorMessage(errorKey, errorMessage);
        List<IndicatorMessage> indicatorMessages = new ArrayList<>();
        indicatorMessages.add(indicatorMessage);

        Indicator indicator = new Indicator("Error", HealthStatus.DOWN, indicatorMessages);

        List<Indicator> indicators = new ArrayList<>();
        indicators.add(indicator);

        setIndicators(indicators);
        setHealthStatus(HealthStatus.DOWN);
    }

}




