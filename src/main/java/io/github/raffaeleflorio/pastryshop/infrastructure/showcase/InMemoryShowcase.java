package io.github.raffaeleflorio.pastryshop.infrastructure.showcase;

import io.github.raffaeleflorio.pastryshop.domain.pastry.Pastry;
import io.github.raffaeleflorio.pastryshop.domain.showcase.Showcase;
import jakarta.json.Json;
import jakarta.json.JsonArray;
import jakarta.json.JsonArrayBuilder;
import jakarta.json.JsonObject;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.function.Function;
import java.util.function.Predicate;

// TODO: test and refactoring
/**
 * Volatile {@link Showcase} implementation
 *
 * @author Raffaele Florio (raffaeleflorio@protonmail.com)
 * @since 1.0.0
 */
public final class InMemoryShowcase implements Showcase {
  /**
   * Builds a showcase
   *
   * @param pastryFn The function to build pastry on sale
   * @since 1.0.0
   */
  public InMemoryShowcase(final Function<Pastry, Pastry> pastryFn) {
    this(pastryFn, new ConcurrentHashMap<>());
  }

  /**
   * Builds a showcase
   *
   * @param pastryFn The function to build pastry
   * @param map      The backed map
   * @since 1.0.0
   */
  InMemoryShowcase(
    final Function<Pastry, Pastry> pastryFn,
    final ConcurrentMap<UUID, Map.Entry<JsonObject, Number>> map
  ) {
    this.pastryFn = pastryFn;
    this.map = map;
  }

  @Override
  public void add(final JsonObject description, final Number quantity) {
    map.put(UUID.randomUUID(), Map.entry(description, quantity));
  }

  @Override
  public JsonArray description() {
    return map.values().stream()
      .map(InMemoryPastry::new)
      .map(pastryFn)
      .filter(Predicate.not(Pastry::expired))
      .map(Pastry::description)
      .reduce(Json.createArrayBuilder(), JsonArrayBuilder::add, JsonArrayBuilder::addAll)
      .build();
  }

  private final Function<Pastry, Pastry> pastryFn;
  private final ConcurrentMap<UUID, Map.Entry<JsonObject, Number>> map;

  private final static class InMemoryPastry implements Pastry {
    private InMemoryPastry(final Map.Entry<JsonObject, Number> entry) {
      this.entry = entry;
    }

    @Override
    public void sell(final Number quantity) {

    }

    @Override
    public JsonObject description() {
      return Json.createObjectBuilder(entry.getKey())
        .add("quantity", entry.getValue().doubleValue())
        .build();
    }

    @Override
    public Number price() {
      return entry.getKey().getJsonNumber("price").numberValue();
    }

    @Override
    public Boolean expired() {
      return null;
    }

    private final Map.Entry<JsonObject, Number> entry;
  }
}
