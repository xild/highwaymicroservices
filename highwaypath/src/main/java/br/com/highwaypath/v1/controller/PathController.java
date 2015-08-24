package br.com.highwaypath.v1.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import br.com.highwaypath.model.Grafo;
import br.com.highwaypath.model.PathResult;
import br.com.highwaypath.model.Viagem;
import br.com.highwaypath.services.PathServices;



/**
 * @author luis vieira
 * Classe expõe o serviço de busca de caminho mais rápido.
 */
@RestController
@RequestMapping(value = "v1/buscaRota")
public class PathController {
	
	private final Logger logger = LoggerFactory.getLogger(PathController.class);
	
	@Autowired
	private PathServices services;
	
    @RequestMapping(method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.CREATED)
    public String create(@RequestBody @Validated  Grafo grafo) {
        int criarNodes = services.criarNodes(grafo);
        return "Foram criados "+criarNodes+" rotas";
    }
    
    @RequestMapping(method = RequestMethod.GET)
    public PathResult buscaRota(@Validated Viagem viagem){
        return services.buscaMelhorCaminho(viagem);
    }
	
	
}
