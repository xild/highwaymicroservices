package br.com.highwaynetwork.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@Entity
@Table(name="TB_VERTICE")
public class Vertice {
    
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Long id;
    
    @NotNull(message = "error.validation.grafo.vertice.destino")
    private String origem;
    @NotNull(message = "error.validation.grafo.vertice.destino")
    private String destino;
    @NotNull(message = "error.validation.grafo.vertice.distancia")
    private Double distancia;
    
    @ManyToOne
    @JoinColumn(name = "grafo_id", insertable = false, updatable = false)
    private Grafo grafo;
    
    public String getOrigem() {
        return origem;
    }
    public void setOrigem(String origem) {
        this.origem = origem;
    }
    public String getDestino() {
        return destino;
    }
    public void setDestino(String destino) {
        this.destino = destino;
    }
    public Double getDistancia() {
        return distancia;
    }
    public void setDistancia(Double distancia) {
        this.distancia = distancia;
    }
    public Grafo getGrafo() {
        return grafo;
    }
    public void setGrafo(Grafo grafo) {
        this.grafo = grafo;
    }
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }

}
