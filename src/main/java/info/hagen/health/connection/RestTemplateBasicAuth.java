package info.hagen.health.connection;


import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.nio.charset.Charset;

/**
 * Uses Spring REST Template but adds Basic Authentication and option to disable SSL verification.
 */
public class RestTemplateBasicAuth {

    private final HttpHeaders httpHeaders;

    private RestTemplate restTemplate;


    public RestTemplateBasicAuth(final String username, final String password, boolean sslVerificationDisabled) {
        httpHeaders = createBasicAuthHeaders(username, password);
        initRestTemplate(sslVerificationDisabled);
    }


    public ResponseEntity<String> getRestResource(String url) {
        return restTemplate.exchange(url, HttpMethod.GET, new HttpEntity<>(httpHeaders), String.class);
    }

    private void initRestTemplate(boolean sslVerificationDisabled) {
        ClientHttpRequestFactory factory;
        if (sslVerificationDisabled) {
            factory = new ClientHttpRequestFactory(new AcceptAllSslHostnameVerifier());
            SslUtil.turnOffJavaX509SslChecks();
        } else {
            factory = new ClientHttpRequestFactory(null);
            SslUtil.turnOnJavaX509SslChecks();
        }
        restTemplate = new RestTemplate(factory);
    }


    public static HttpHeaders createBasicAuthHeaders(final String username, final String password) {
        HttpHeaders headers = new HttpHeaders();

        if (username != null && password != null) {
            headers.set("Authorization", encodeCredentials(username, password));
        }

        headers.add("Accept", "*/*");
        return headers;
    }

    public static String encodeCredentials(final String username, final String password) {
        String auth = username + ":" + password;
        byte[] encodedAuth = Base64.encodeBase64(auth.getBytes(Charset.forName("US-ASCII")));
        String authHeader = "Basic " + new String(encodedAuth);
        return authHeader;
    }


}
