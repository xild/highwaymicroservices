package br.com.highwaypath.builders;

import java.util.function.Consumer;
import java.util.stream.Stream;

import br.com.highwaypath.model.Vertice;

public class VerticeBuilder {
    
    @FunctionalInterface
    public interface VerticeSetter extends Consumer<Vertice> {}

    public static Vertice build(VerticeSetter... VerticeSetters) {
      final Vertice vertice = new Vertice();

      Stream.of(VerticeSetters).forEach(
          v -> v.accept(vertice)
      );

      return vertice;
    }

}
