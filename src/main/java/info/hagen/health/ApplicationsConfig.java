package info.hagen.health;

import info.hagen.health.domain.Application;
import info.hagen.health.domain.Server;
import org.apache.commons.lang3.Validate;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;

/**
 * Configuration class for applications.
 *
 * Use Spring Boot to populate classes in domain model directly.
 */
@Component
@ConfigurationProperties(prefix = "health", ignoreUnknownFields = false)
public class ApplicationsConfig {

    List<Application> applications = new ArrayList<>();

    public List<Application> getApplications() {
        return applications;
    }

    public void setApplications(List<Application> applications) {
        this.applications = applications;
    }


    /**
     * Validate configuration. At least one application with one attached server must have been configured.
     */
    @PostConstruct
    public void validateConfig() {
        Validate.notEmpty(applications, "No health applications defined");
        applications.forEach(this::validateApplication);
    }

    private void validateApplication(Application application) {
        Validate.notBlank(application.getApplicationName(), "Application name cannot be blank");
        Validate.notEmpty(application.getServers(), "No servers defined for application '" + application.getApplicationName() + '\'');
        application.getServers().forEach(this::validateServer);
    }

    private void validateServer(Server server) {
        Validate.notBlank(server.getServerName(), "Server name cannot be blank");
        Validate.notBlank(server.getHealthUrl(), "Server health URL cannot be blank for server '" + server.getServerName() + '\'');
    }



}
