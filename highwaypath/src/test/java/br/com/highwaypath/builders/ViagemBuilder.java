package br.com.highwaypath.builders;

import java.util.function.Consumer;
import java.util.stream.Stream;

import br.com.highwaypath.builders.GrafoBuilder.GrafoSetter;
import br.com.highwaypath.model.Grafo;
import br.com.highwaypath.model.Viagem;

public class ViagemBuilder {
    
    @FunctionalInterface
    public interface ViagemSetter extends Consumer<Viagem> {}

    public static Viagem build(ViagemSetter... viagemSetters) {
      final Viagem viagem = new Viagem();

      Stream.of(viagemSetters).forEach(
          s -> s.accept(viagem)
      );

      return viagem;
    }

}
