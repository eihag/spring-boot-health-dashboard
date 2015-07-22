package info.hagen.health.domain;

import java.util.List;

/**
 * Application being monitored by the health dashboard.
 */
public class Application {

    private String applicationName;

    private List<Server> servers;

    public Application() {
    }

    public void setApplicationName(String applicationName) {
        this.applicationName = applicationName;
    }


    public String getApplicationName() {
        return applicationName;
    }

    /**
     * Return aggregate health status based on health of app servers.
     */
    public HealthStatus getHealthStatus() {
        boolean foundUnknown = false;
        boolean foundUp = false;
        boolean foundDown = false;

        for (Server server : servers) {
            if (HealthStatus.UNKNOWN.equals(server.getHealthStatus())) {
                foundUnknown = true;
            } else if (server.getHealthStatus().isUp()) {
                foundUp = true;
            } else {
                foundDown = true;
            }
        }

        if (foundDown && !foundUp) {
            return HealthStatus.DOWN;
        }

        if (foundUnknown || foundDown) {
            return HealthStatus.UNKNOWN;
        }

        // Only found "UP" servers
        return HealthStatus.UP;
    }


    public void setServers(List<Server> servers) {
        this.servers = servers;
    }

    public List<Server> getServers() {
        return servers;
    }
}
