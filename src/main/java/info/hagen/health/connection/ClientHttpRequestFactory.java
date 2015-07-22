package info.hagen.health.connection;

import org.springframework.http.client.SimpleClientHttpRequestFactory;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import java.io.IOException;
import java.net.HttpURLConnection;

/**
 * Factory for new HTTP connection where you can set custom host name verifier.
 */
public class ClientHttpRequestFactory extends SimpleClientHttpRequestFactory {

        private final HostnameVerifier verifier;

        public ClientHttpRequestFactory(HostnameVerifier verifier) {
           this.verifier = verifier;
        }

        @Override
        protected void prepareConnection(HttpURLConnection connection, String httpMethod) throws IOException {
           if (connection instanceof HttpsURLConnection && verifier != null) {
              ((HttpsURLConnection) connection).setHostnameVerifier(verifier);
           }
           super.prepareConnection(connection, httpMethod);
        }

}
