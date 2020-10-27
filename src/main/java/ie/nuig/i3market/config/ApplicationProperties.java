package ie.nuig.i3market.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * Properties specific to I 3 Market.
 * <p>
 * Properties are configured in the {@code application.yml} file.
 * See {@link io.github.jhipster.config.JHipsterProperties} for a good example.
 */
@ConfigurationProperties(prefix = "application", ignoreUnknownFields = false)
public class ApplicationProperties {

    public ApplicationProperties() {
    }

    VirtuosoProperties virtuosoProperties= new VirtuosoProperties();

    public VirtuosoProperties getVirtuosoProperties() {
        return virtuosoProperties;
    }

    public static  class VirtuosoProperties{
        private String url;
        private String query_interface;
        private String update_interface;
        private String username;
        private String password;
        private String graph;


        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public String getQuery_interface() {
            return query_interface;
        }

        public void setQuery_interface(String query_interface) {
            this.query_interface = query_interface;
        }

        public String getUpdate_interface() {
            return update_interface;
        }

        public void setUpdate_interface(String update_interface) {
            this.update_interface = update_interface;
        }

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }

        public String getGraph() {
            return graph;
        }

        public void setGraph(String graph) {
            this.graph = graph;
        }
    }

}
