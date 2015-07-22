package info.hagen.health.json;

import info.hagen.health.domain.Application;
import info.hagen.health.domain.Server;
import org.junit.Before;
import org.junit.Test;
import org.skyscreamer.jsonassert.JSONAssert;
import org.skyscreamer.jsonassert.JSONCompareMode;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

/**
 * Based on a given health status collected from server, verify JSON output matches expected output.
 */
public class JsonOutputMapperTest {


    JsonHealthParser parser = new JsonHealthParser();
    JsonOutputMapper mapper = new JsonOutputMapper();

    Server server;
    Application application;

    @Before
    public void setup() {
        server = new Server("DummyServer", "dummyUrl", null, null);

        List<Server> serverList = new ArrayList<>();
        serverList.add(server);

        application = new Application();
        application.setApplicationName("Dummy Application");
        application.setServers(serverList);
    }

    @Test
    public void testMapToJsonUp() throws Exception {
        String parseThis = readFile("health-up.json");
        parser.parseServerHealth(server, parseThis);

        String response = mapper.mapToJson(application);
        System.out.println(response);
        String expectedJson = readFile("output-application-up.json");


        JSONAssert.assertEquals(expectedJson, response, JSONCompareMode.STRICT);

    }

    @Test
    public void testMapToJsonDown() throws Exception {
        String parseThis = readFile("health-down.json");
        parser.parseServerHealth(server, parseThis);

        String response = mapper.mapToJson(application);
        System.out.println(response);
        String expectedJson = readFile("output-application-down.json");
        JSONAssert.assertEquals(expectedJson, response, JSONCompareMode.STRICT);
    }

    private String readFile(String filename) throws Exception {
        return new String(Files.readAllBytes(Paths.get(ClassLoader.getSystemResource(filename).toURI())));
    }

}
