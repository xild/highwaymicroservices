package br.com.highwaypath.model.neo4j;

import org.springframework.data.neo4j.annotation.EndNode;
import org.springframework.data.neo4j.annotation.GraphId;
import org.springframework.data.neo4j.annotation.RelationshipEntity;
import org.springframework.data.neo4j.annotation.StartNode;

@RelationshipEntity
public class DistanciaRelationship {
    
    @GraphId
    private Long id;
    
    @StartNode
    private LocalNode localOrigem;
    @EndNode
    private LocalNode localDestino;
    
    private double distancia;

    public LocalNode getLocalOrigem() {
        return localOrigem;
    }

    public void setLocalOrigem(LocalNode localOrigem) {
        this.localOrigem = localOrigem;
    }

    public LocalNode getLocalDestino() {
        return localDestino;
    }

    public void setLocalDestino(LocalNode localDestino) {
        this.localDestino = localDestino;
    }

    public double getDistancia() {
        return distancia;
    }

    public void setDistancia(double distancia) {
        this.distancia = distancia;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
    
}
