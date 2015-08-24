package br.com.highwaynetwork.v1.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import br.com.highwaynetwork.model.Grafo;
import br.com.highwaynetwork.properties.ServicesSettings;
import br.com.highwaynetwork.services.MalhaViariaServices;



/**
 * @author luis vieira
 * Classe expõe o serviço de cadastro de malha viária
 */
@RestController
@RequestMapping(value = "v1/malhaviaria")
public class MalhaViariaController {
    
    @Autowired
    @LoadBalanced //LoadBalance do CEP-SERVICES controlado pelo microservices-suport.
    protected RestTemplate restTemplate;
    @Autowired
    private ServicesSettings servicesEureka;
	
	private final Logger logger = LoggerFactory.getLogger(MalhaViariaController.class);
	
	@Autowired
	private MalhaViariaServices services; 
	
    @RequestMapping(method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.CREATED)
    public Grafo create(@RequestBody @Validated  Grafo grafo) {
        Grafo grafoReturn = services.salvar(grafo);
        if(grafoReturn != null){
            return restTemplate.postForObject(servicesEureka.getHighwaypath()+"/v1/buscaRota", grafo, Grafo.class);
        }
        return grafoReturn;
    }
	
	
}
