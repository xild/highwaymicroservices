package br.com.highwaypath.test.viagem;

import static com.jayway.restassured.RestAssured.given;
import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.hasItems;

import java.math.BigDecimal;
import java.util.Arrays;

import org.apache.http.HttpStatus;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.neo4j.graphdb.GraphDatabaseService;
import org.neo4j.graphdb.Node;
import org.neo4j.graphdb.factory.GraphDatabaseFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.IntegrationTest;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.data.neo4j.support.Neo4jTemplate;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.web.context.WebApplicationContext;
import br.com.highwaypath.HighwayPathApplication;
import br.com.highwaypath.builders.DistanciaRelationshipBuilder;
import br.com.highwaypath.builders.GrafoBuilder;
import br.com.highwaypath.builders.LocalNodeBuilder;
import br.com.highwaypath.builders.VerticeBuilder;
import br.com.highwaypath.builders.ViagemBuilder;
import br.com.highwaypath.model.Grafo;
import br.com.highwaypath.model.ProjectRelationshipTypes;
import br.com.highwaypath.model.Vertice;
import br.com.highwaypath.model.Viagem;
import br.com.highwaypath.model.neo4j.DistanciaRelationship;
import br.com.highwaypath.model.neo4j.LocalNode;
import br.com.highwaypath.repository.LocalRepository;
import br.com.highwaypath.services.PathServices;
import com.jayway.restassured.RestAssured;
import com.jayway.restassured.http.ContentType;
import static org.hamcrest.Matchers.*;



@SpringApplicationConfiguration(classes = HighwayPathApplication.class)
@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@IntegrationTest("server.port:0") //procura a primeira porta disponível para startar o server
public class PathControllerTest{

    private static final String BUSCA_ROTA = "/v1/buscaRota";
    
    @Value("${local.server.port}") //porta que foi startado o servidor
    int port;
    
    @Autowired
    private PathServices services;
    
    @Autowired
    private LocalRepository localRepository;
    
    @Autowired
    private Neo4jTemplate template;
    
    @Autowired
    private WebApplicationContext context;
    
    @Bean(destroyMethod = "shutdown")
    public GraphDatabaseService graphDatabaseService() {
        return new GraphDatabaseFactory().newEmbeddedDatabase("target/s2.db");
    }
    



    private Vertice v1, v2, v3;
    private Grafo g1, g2, g3;
    private LocalNode l1, l2, l3, l4, l5;
    private DistanciaRelationship d1, d2, d3, d4, d5, d6;
    private Viagem viagem;
    
    @Before
    public void setUp() {
        RestAssured.port = port;
        buildGrafo();
        buildLocalNode();
        buildDistanciaRelationship();
        
        viagem = ViagemBuilder.build(
                v -> v.setMapa("SV"),
                v -> v.setOrigem("A"),
                v -> v.setDestino("D"),
                v -> v.setAutonomia(10),
                v -> v.setValorLitro(new BigDecimal(2.50)));
    }

    private void buildGrafo() {
        v1 = VerticeBuilder.build(
                v -> v.setOrigem("A"),
                v -> v.setDestino("B"),
                v -> v.setDistancia(15.00));
        
        v2 = VerticeBuilder.build(
                v -> v.setOrigem("C"),
                v -> v.setDestino("D"),
                v -> v.setDistancia(20.00));
        
        v3 = VerticeBuilder.build(
                v -> v.setOrigem("D"),
                v -> v.setDestino("E"),
                v -> v.setDistancia(30.00));
        
        
        g1 = GrafoBuilder.build(
                g -> g.setId(1L),
                g -> g.setNome("SP"),
                g -> g.setVertices(Arrays.asList(v1, v2, v3)));
    }
    
    private void buildLocalNode() {
        l1 = localRepository.save(LocalNodeBuilder.build(
                l -> l.setLocalId("SV_A")));
        l2 = localRepository.save(LocalNodeBuilder.build(
                l -> l.setLocalId("SV_B")));
        l3 = localRepository.save(LocalNodeBuilder.build(
                l -> l.setLocalId("SV_C")));    
        l4 = localRepository.save(LocalNodeBuilder.build(
                l -> l.setLocalId("SV_D")));    
        l5 = localRepository.save(LocalNodeBuilder.build(
                l -> l.setLocalId("SV_E")));    
    }
    
    private void buildDistanciaRelationship(){
        d1 = template.save(DistanciaRelationshipBuilder.build(
                d -> d.setLocalOrigem(l1),
                d -> d.setLocalDestino(l2),
                d -> d.setDistancia(10)), ProjectRelationshipTypes.FROM_TO);
        
        d2 = template.save(DistanciaRelationshipBuilder.build(
                d -> d.setLocalOrigem(l2),
                d -> d.setLocalDestino(l4),
                d -> d.setDistancia(15)), ProjectRelationshipTypes.FROM_TO);
        
        d3 = template.save(DistanciaRelationshipBuilder.build(
                d -> d.setLocalOrigem(l1),
                d -> d.setLocalDestino(l3),
                d -> d.setDistancia(20)), ProjectRelationshipTypes.FROM_TO);
        
        d4 = template.save(DistanciaRelationshipBuilder.build(
                d -> d.setLocalOrigem(l3),
                d -> d.setLocalDestino(l4),
                d -> d.setDistancia(30)), ProjectRelationshipTypes.FROM_TO);
        
        
        d5 = template.save(DistanciaRelationshipBuilder.build(
                d -> d.setLocalOrigem(l2),
                d -> d.setLocalDestino(l5),
                d -> d.setDistancia(50)), ProjectRelationshipTypes.FROM_TO);
        
        d6 = template.save(DistanciaRelationshipBuilder.build(
                d -> d.setLocalOrigem(l4),
                d -> d.setLocalDestino(l5),
                d -> d.setDistancia(30)), ProjectRelationshipTypes.FROM_TO);
        
    }
    
    @Test
    public void criarNodes_sucesso(){
        given()
            .body(g1)
            .contentType(ContentType.JSON)
        .when()
            .post(BUSCA_ROTA)
        .then()
            .statusCode(HttpStatus.SC_CREATED)
            .body(containsString("3"));
    }
    
    
    @Test
    public void buscaMelhorRota_sucesso(){
        given()
            .parameters("mapa", viagem.getMapa(), "origem", viagem.getOrigem(), 
                    "destino", viagem.getDestino(), "autonomia", viagem.getAutonomia(), "valorLitro", viagem.getValorLitro())
            .contentType(ContentType.JSON)
        .when()
            .get(BUSCA_ROTA)
         .then()
            .statusCode(HttpStatus.SC_OK)
            .body("custo", is(12.50f))
            .body("distancia", is(50.0f))
            .body("locais", hasItems("A","C","D")); 
        
    }
    
}
