package io.github.raffaeleflorio.pastryshop.infrastructure.showcase;

import io.github.raffaeleflorio.pastryshop.domain.pastry.Pastry;
import io.github.raffaeleflorio.pastryshop.domain.showcase.Showcase;
import jakarta.json.Json;
import jakarta.json.JsonArray;
import jakarta.json.JsonArrayBuilder;
import jakarta.json.JsonObject;

import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.function.Function;
import java.util.function.Predicate;

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
  public InMemoryShowcase(final Function<JsonObject, Pastry> pastryFn) {
    this(pastryFn, new ConcurrentHashMap<>());
  }

  /**
   * Builds a showcase
   *
   * @param pastryFn The function to build pastry on sale
   * @param map      The backed map
   * @since 1.0.0
   */
  InMemoryShowcase(
    final Function<JsonObject, Pastry> pastryFn,
    final ConcurrentMap<UUID, JsonObject> map
  ) {
    this.pastryFn = pastryFn;
    this.map = map;
  }

  @Override
  public void add(final JsonObject description, final Number quantity) {
    map.put(
      UUID.randomUUID(),
      Json.createObjectBuilder(description).add("quantity", quantity.doubleValue()).build()
    );
  }

  @Override
  public JsonArray description() {
    return map.values().stream()
      .map(pastryFn)
      .filter(Predicate.not(Pastry::expired))
      .map(Pastry::description)
      .reduce(Json.createArrayBuilder(), JsonArrayBuilder::add, JsonArrayBuilder::addAll)
      .build();
  }

  private final Function<JsonObject, Pastry> pastryFn;
  private final ConcurrentMap<UUID, JsonObject> map;
}
