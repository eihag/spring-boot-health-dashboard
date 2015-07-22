package info.hagen.health.json;

import info.hagen.health.domain.HealthStatus;
import info.hagen.health.domain.Server;
import org.junit.Before;
import org.junit.Test;

import java.nio.file.Files;
import java.nio.file.Paths;

import static org.junit.Assert.*;

public class JsonHealthParserTest {

    JsonHealthParser parser;
    Server server;


    @Before
    public void init() {
        parser = new JsonHealthParser();
        server = new Server("DummyServer", "dummyUrl", null, null);
    }


    @Test
    public void testNoResponse() {
        parser.parseServerHealth(server, null);
        assertEquals(HealthStatus.DOWN, server.getHealthStatus());
    }

    @Test
    public void testUnparseableResponse() throws Exception {
        String serverResponse = readFile("unparseable.json");
        parser.parseServerHealth(server, serverResponse);
        assertEquals(HealthStatus.DOWN, server.getHealthStatus());
    }


    @Test
    public void testUpResponse() throws Exception {
        String serverResponse = readFile("health-up.json");
        parser.parseServerHealth(server, serverResponse);

        assertTrue(server.getHealthStatus().isUp());
        assertEquals(2, server.getIndicators().size());

        server.getIndicators().forEach(h -> assertTrue(h.getHealthStatus().isUp()));

        assertEquals(2, server.getIndicators().get(0).getMessages().size());
        assertEquals(2, server.getIndicators().get(1).getMessages().size());


    }


    @Test
    public void testDownResponse() throws Exception {
        String serverResponse = readFile("health-down.json");
        parser.parseServerHealth(server, serverResponse);


        assertFalse(server.getHealthStatus().isUp());
        assertEquals(2, server.getIndicators().size());

        assertFalse(server.getIndicators().get(0).getHealthStatus().isUp());
        assertTrue(server.getIndicators().get(1).getHealthStatus().isUp());

        assertEquals(1, server.getIndicators().get(0).getMessages().size());
        assertEquals(2, server.getIndicators().get(1).getMessages().size());


    }


    private String readFile(String filename) throws Exception {
        return new String(Files.readAllBytes(Paths.get(ClassLoader.getSystemResource(filename).toURI())));
    }


}