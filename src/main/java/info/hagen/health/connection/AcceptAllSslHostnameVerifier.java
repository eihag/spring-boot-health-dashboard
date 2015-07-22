package info.hagen.health.connection;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLSession;

/**
 * Ignores all SSL certificate errors 
 */
public class AcceptAllSslHostnameVerifier implements HostnameVerifier {

    @Override
    public boolean verify(String hostname, SSLSession session) {
               return true;
    }

}
