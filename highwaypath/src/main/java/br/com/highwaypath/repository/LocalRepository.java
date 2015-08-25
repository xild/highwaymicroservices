package br.com.highwaypath.repository;

import java.util.HashMap;
import java.util.Iterator;

import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import br.com.highwaypath.model.neo4j.LocalNode;

/**
 * @author Luis
 * @since 28.08.2015
 */

public interface LocalRepository extends  CrudRepository<LocalNode, String> {
    LocalNode findByLocalId(@Param("0") String localId);
}

