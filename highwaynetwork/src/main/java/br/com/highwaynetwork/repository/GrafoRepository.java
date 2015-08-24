package br.com.highwaynetwork.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RestResource;

import br.com.highwaynetwork.model.Grafo;


public interface GrafoRepository extends CrudRepository<Grafo, Long> {

}
