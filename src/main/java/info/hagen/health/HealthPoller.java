package info.hagen.health;

import info.hagen.health.connection.RestTemplateBasicAuth;
import info.hagen.health.domain.Application;
import info.hagen.health.domain.Server;
import info.hagen.health.json.JsonHealthParser;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClientException;

/**
 * Poll a Spring Boot application and retrieve health status
 */
@Component
public class HealthPoller {

    private static final int POLLING_INTERVAL_MS = 10000;

    private static Logger log = org.slf4j.LoggerFactory.getLogger(HealthPoller.class);


    @Autowired
    ApplicationsConfig applicationsConfig;

    private JsonHealthParser jsonParser = new JsonHealthParser();


    @Scheduled(fixedRate = POLLING_INTERVAL_MS )
    public void pollApplications() {
        applicationsConfig.getApplications().forEach(this::pollApplication);
    }

    private void pollApplication(Application application) {
        application.getServers().forEach(this::pollServer);

    }

    private void pollServer(Server server) {
        log.debug("Polling " + server);
        RestTemplateBasicAuth restTemplate = new RestTemplateBasicAuth(server.getUsername(), server.getPassword(), server.isSslVerificationDisabled());
        ResponseEntity<String> response = null;
        try {
            response = restTemplate.getRestResource(server.getHealthUrl());
            jsonParser.parseServerHealth(server, response.getBody());

        } catch (RestClientException e) {
            log.warn("Error polling server " + server + ", error: " + e.getMessage());
            server.setExceptionErrorState(e);
        }
    }


}
