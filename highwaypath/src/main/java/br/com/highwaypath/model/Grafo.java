package br.com.highwaypath.model;

import java.util.List;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.NotEmpty;

public class Grafo {
    
    private Long id;
    @NotNull(message = "error.validation.grafo.nome")
    @NotBlank(message = "error.validation.grafo.nome")
    private String nome;
    
    
    @NotEmpty
    private List<Vertice> vertices;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public List<Vertice> getVertices() {
        return vertices;
    }

    public void setVertices(List<Vertice> vertices) {
        this.vertices = vertices;
    }
    
}
