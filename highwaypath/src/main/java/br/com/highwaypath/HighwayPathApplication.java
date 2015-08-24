package br.com.highwaypath;

import org.neo4j.graphdb.GraphDatabaseService;
import org.neo4j.graphdb.factory.GraphDatabaseFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.data.neo4j.config.EnableNeo4jRepositories;
import org.springframework.data.neo4j.config.Neo4jConfiguration;
import org.springframework.data.neo4j.rest.SpringRestGraphDatabase;
@EnableNeo4jRepositories
@SpringBootApplication
public class HighwayPathApplication extends Neo4jConfiguration {
    /**
     * Roda a aplicação como standalone
     * 
     * @param args
     * Program arguments - ignored.
     */
    public static void main(String[] args) {
        SpringApplication.run(HighwayPathApplication.class, args);
    }
    
    
    @Bean(name = "messageSource")
    public ReloadableResourceBundleMessageSource messageSource() {
      ReloadableResourceBundleMessageSource messageBundle = new ReloadableResourceBundleMessageSource();
      messageBundle.setBasename("classpath:messages");
      messageBundle.setDefaultEncoding("UTF-8");
      return messageBundle;
    }
    
    
    public HighwayPathApplication() {
        setBasePackage("br.com.highwaypath");
    }

    @Bean(destroyMethod = "shutdown")
    public GraphDatabaseService graphDatabaseService() {
        /**local test
         * need to set env vars
         * */
        String login = System.getenv("NEO4J_LOGIN");
        String pwd = System.getenv("NEO4J_PASSWORD");
        String url = System.getenv("NEO4J_REST_URL");

        return new SpringRestGraphDatabase(url, login, pwd ) ;
//        return new GraphDatabaseFactory().newEmbeddedDatabase("target/highway.db");
    }
}
