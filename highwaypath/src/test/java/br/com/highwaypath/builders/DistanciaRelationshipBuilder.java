package br.com.highwaypath.builders;

import java.util.function.Consumer;
import java.util.stream.Stream;

import br.com.highwaypath.model.neo4j.DistanciaRelationship;

public class DistanciaRelationshipBuilder {
    
    @FunctionalInterface
    public interface DistanciaRelationshipSetter extends Consumer<DistanciaRelationship> {}

    public static DistanciaRelationship build(DistanciaRelationshipSetter... relSetters) {
      final DistanciaRelationship rel = new DistanciaRelationship();

      Stream.of(relSetters).forEach(
          s -> s.accept(rel)
      );

      return rel;
    }

}
