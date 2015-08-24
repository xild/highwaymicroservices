package br.com.highwaynetwork.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.highwaynetwork.model.Grafo;
import br.com.highwaynetwork.repository.GrafoRepository;

@Service
public class MalhaViariaServices {

    @Autowired
    private GrafoRepository repository;
    
    public Grafo salvar(Grafo grafo){
       return repository.save(grafo);
    }
    
}
