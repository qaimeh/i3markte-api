package ie.nuig.i3market.config.databases;


import ie.nuig.i3market.config.ApplicationProperties;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.protocol.BasicHttpContext;
import org.apache.http.protocol.HttpContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;


@Configuration
public class VirtuosoConfiguration {

    private final ApplicationProperties applicationProperties;

    @Autowired
    VirtuosoConfiguration(ApplicationProperties applicationProperties1) {
        this.applicationProperties = applicationProperties1;
    }


    public HttpContext getVirtuosoContext() {

        HttpContext httpContext=null;
       try {
           httpContext = new BasicHttpContext();
           CredentialsProvider provider = new BasicCredentialsProvider();
           provider.setCredentials(new AuthScope(AuthScope.ANY_HOST,
               AuthScope.ANY_PORT), new UsernamePasswordCredentials(applicationProperties.getVirtuosoProperties().getUsername(), applicationProperties.getVirtuosoProperties().getPassword()));
           httpContext.setAttribute(HttpClientContext.CREDS_PROVIDER, provider);
       }catch(Exception e){
           e.printStackTrace();
       }
       return httpContext;

    }

}
