package br.com.highwaypath.services;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import org.neo4j.graphalgo.GraphAlgoFactory;
import org.neo4j.graphalgo.PathFinder;
import org.neo4j.graphdb.Direction;
import org.neo4j.graphdb.Node;
import org.neo4j.graphdb.Path;
import org.neo4j.graphdb.PathExpanders;
import org.neo4j.graphdb.Relationship;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.neo4j.support.Neo4jTemplate;
import org.springframework.data.neo4j.template.Neo4jOperations;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import br.com.highwaypath.exception.RelationshipInvalidException;
import br.com.highwaypath.model.Grafo;
import br.com.highwaypath.model.PathResult;
import br.com.highwaypath.model.ProjectRelationshipTypes;
import br.com.highwaypath.model.Vertice;
import br.com.highwaypath.model.Viagem;
import br.com.highwaypath.model.neo4j.DistanciaRelationship;
import br.com.highwaypath.model.neo4j.LocalNode;
import br.com.highwaypath.repository.LocalRepository;

@Service
@Transactional
public class PathServices {
    
    @Autowired
    private Neo4jTemplate template;
    @Autowired
    private Neo4jOperations neo4jop;
    @Autowired
    private LocalRepository localRepository;
    
    
    public int criarNodes(Grafo grafo){
        int quantidade = 0;
      
        for(Vertice v: grafo.getVertices()){
            LocalNode localOrigem = new LocalNode();
            LocalNode localDestino = new LocalNode();
            localOrigem.setLocalId(grafo.getNome()+"_"+v.getOrigem());
            LocalNode origem = salvarNode(localOrigem);
            
            localDestino.setLocalId(grafo.getNome()+"_"+v.getDestino());
            LocalNode destino = salvarNode(localDestino);
            
            adicionaRelacao(origem, destino, v.getDistancia());
            quantidade++;
        }
        
        return quantidade;
        
    }
    
    private LocalNode salvarNode(LocalNode localNode){
        LocalNode local = localRepository.findByLocalId(localNode.getLocalId());
        if(local == null){
            return localRepository.save(localNode);
        }
        
        return local;
    }
    
    @Transactional
    private void adicionaRelacao(LocalNode origem, LocalNode destino, double distancia){
        DistanciaRelationship distanciaRel = new DistanciaRelationship();
        distanciaRel.setLocalOrigem(origem);
        distanciaRel.setLocalDestino(destino);
        distanciaRel.setDistancia(distancia);
        
        DistanciaRelationship distanciaRelationship = null;
        distanciaRelationship = template.save(distanciaRel, ProjectRelationshipTypes.FROM_TO);
        
        if(distanciaRelationship == null){
            throw new RelationshipInvalidException();
        }
        
    }
    
    @Transactional
    public PathResult buscaMelhorCaminho(Viagem viagem) {
        LocalNode origem = localRepository.findByLocalId(viagem.getMapa()+"_"+viagem.getOrigem()); 
        LocalNode destino = localRepository.findByLocalId(viagem.getMapa()+"_"+viagem.getDestino());
        
        PathFinder<Path> finder = GraphAlgoFactory.shortestPath(PathExpanders.forTypeAndDirection(ProjectRelationshipTypes.FROM_TO, Direction.OUTGOING), 50);
        
        Node origemNode = template.getGraphDatabase().getNodeById(origem.getId());
        Node destinoNode = template.getGraphDatabase().getNodeById(destino.getId());
        
        Path findSinglePath = finder.findSinglePath(origemNode,  destinoNode);
        PathResult pathResult = new PathResult();
        List<String> rotas = new ArrayList<String>();
        
        for (Node node : findSinglePath.nodes()) {
            LocalNode convert = neo4jop.convert(node, LocalNode.class);
            rotas.add(convert.getLocalId().split("_")[1]);
        }
            
        for (Relationship relationship : findSinglePath.relationships()) {
            DistanciaRelationship convert = neo4jop.convert(relationship, DistanciaRelationship.class);
            pathResult.setDistancia(pathResult.getDistancia() + convert.getDistancia());
        }
        
        pathResult.setLocais(rotas);
        pathResult.setCusto(calcularCusto(viagem.getAutonomia(), viagem.getValorLitro(), pathResult.getDistancia()));
        
        return pathResult;
    
    }

    private BigDecimal calcularCusto(double autonomia, BigDecimal valorLitro,
            double distancia) {
        BigDecimal dist = new BigDecimal(distancia);
        BigDecimal auto = new BigDecimal(autonomia);
        BigDecimal resultado = (valorLitro.multiply(dist)).divide(auto);
        return resultado;
    }
}
