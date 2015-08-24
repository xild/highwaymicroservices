package br.com.highwaynetwork.builders;

import java.util.function.Consumer;
import java.util.stream.Stream;

import br.com.highwaynetwork.model.Grafo;

public class GrafoBuilder {
    
    @FunctionalInterface
    public interface GrafoSetter extends Consumer<Grafo> {}

    public static Grafo build(GrafoSetter... grafoSetters) {
      final Grafo grafo = new Grafo();

      Stream.of(grafoSetters).forEach(
          s -> s.accept(grafo)
      );

      return grafo;
    }

}
