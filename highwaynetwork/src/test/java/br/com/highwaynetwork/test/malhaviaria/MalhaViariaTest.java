package br.com.highwaynetwork.test.malhaviaria;

import static com.jayway.restassured.RestAssured.given;
import static org.hamcrest.Matchers.containsString;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.doReturn;

import java.util.Arrays;

import org.apache.http.HttpStatus;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.IntegrationTest;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.web.client.RestTemplate;

import br.com.highwaynetwork.HighwayApplication;
import br.com.highwaynetwork.builders.GrafoBuilder;
import br.com.highwaynetwork.builders.VerticeBuilder;
import br.com.highwaynetwork.exception.TipoMensagemEnum;
import br.com.highwaynetwork.model.Grafo;
import br.com.highwaynetwork.model.Vertice;

import com.jayway.restassured.RestAssured;
import com.jayway.restassured.http.ContentType;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = HighwayApplication.class)
@WebAppConfiguration
@IntegrationTest("server.port:0") //procura a primeira porta disponível para startar o server
public class MalhaViariaTest {

    private static final String MALHA_VIARIA = "/v1/malhaviaria";
    
    @Value("${local.server.port}") //porta que foi startado o servidor
    int port;
    
    @Mock
    private RestTemplate template;
    
    
    private Vertice v1, v2, v3;
    private Grafo g1, g2, g3;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        RestAssured.port = port;
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

        //na hora do build o highwaypath não está no ar
       Mockito.doReturn(g1)
        .when(template).postForObject(anyString(), 
                any(), any());
        
        
    }
    
    @Test
    public void validaInputGrafo_sucesso(){
        //TODO implementar método de teste
        //mockando restTemplate
        
    }
    
    @Test
    public void validaInputGrafo_falhaArgumentoInvalido(){
       Vertice v1 = VerticeBuilder.build(
                   v -> v.setOrigem("D"),
                   v -> v.setDestino("E"),
                   v -> v.setDistancia(30.00));
        
        
       Grafo g1 = GrafoBuilder.build(
                g -> g.setId(1L),
                g -> g.setNome(""),
                g -> g.setVertices(Arrays.asList(v1)));

        
        given()
            .body(g1)
            .contentType(ContentType.JSON)
        .when()
            .post(MALHA_VIARIA)
        .then()
            .statusCode(HttpStatus.SC_BAD_REQUEST)  
            .body("tipo", containsString(TipoMensagemEnum.ERROR.toString()));
    }
    
}
