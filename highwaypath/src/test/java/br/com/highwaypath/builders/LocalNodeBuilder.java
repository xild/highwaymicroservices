package br.com.highwaypath.builders;

import java.util.function.Consumer;
import java.util.stream.Stream;

import br.com.highwaypath.model.neo4j.LocalNode;

public class LocalNodeBuilder {
    
    @FunctionalInterface
    public interface LocalNodeSetter extends Consumer<LocalNode> {}

    public static LocalNode build(LocalNodeSetter... localNodeSetters) {
      final LocalNode localNode = new LocalNode();

      Stream.of(localNodeSetters).forEach(
          s -> s.accept(localNode)
      );

      return localNode;
    }

}
