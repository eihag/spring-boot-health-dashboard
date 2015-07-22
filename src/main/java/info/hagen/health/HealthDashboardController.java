package info.hagen.health;

import info.hagen.health.domain.Application;
import info.hagen.health.json.JsonOutputMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * Expose JSON endpoint with list of  application health status.
 */
@Controller
public class HealthDashboardController {

    @Autowired
    ApplicationsConfig applicationsConfig;


    private final static String STATUS_UP = "UP";
    private final static String STATUS_DOWN = "DOWN";

    JsonOutputMapper mapper = new JsonOutputMapper();

    @RequestMapping("/healthstatus")
    public HttpEntity<String> getAllHealthStatus() {
        return new ResponseEntity<>(mapper.mapToJson(applicationsConfig.getApplications()), null, HttpStatus.OK);


    }

    @RequestMapping("/healthstatus/{applicationNo}")
    public HttpEntity<String> getHealthStatus(@PathVariable @NotNull Integer applicationNo) {

        List<Application> applications = applicationsConfig.getApplications();

        if (applications == null) {
            return new ResponseEntity<>("No applications configured", null, HttpStatus.INTERNAL_SERVER_ERROR);
        }


        if (applicationNo >= applications.size()) {
            return new ResponseEntity<>("Application not configured", null, HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>(mapper.mapToJson(applications.get(applicationNo)), null, HttpStatus.OK);
    }


}
