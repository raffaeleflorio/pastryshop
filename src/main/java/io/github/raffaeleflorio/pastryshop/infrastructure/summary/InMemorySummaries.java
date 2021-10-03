package io.github.raffaeleflorio.pastryshop.infrastructure.summary;

import io.github.raffaeleflorio.pastryshop.domain.summary.JsonSummary;
import io.github.raffaeleflorio.pastryshop.domain.summary.Summaries;
import io.github.raffaeleflorio.pastryshop.domain.summary.Summary;
import jakarta.json.Json;
import jakarta.json.JsonArray;
import jakarta.json.JsonArrayBuilder;
import jakarta.json.JsonObject;

import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.function.BiFunction;

/**
 * A volatile {@link Summaries} implementation that uses the pastry name as id
 *
 * @author Raffaele Florio (raffaeleflorio@protonmail.com)
 * @since 1.0.0
 */
public final class InMemorySummaries implements Summaries {
  /**
   * Builds volatile summaries
   *
   * @since 1.0.0
   */
  public InMemorySummaries() {
    this(new ConcurrentHashMap<>(), JsonSummary::new);
  }

  /**
   * Builds volatile summaries
   *
   * @param map       The backed map
   * @param summaryFn The function to map summary
   * @since 1.0.0
   */
  InMemorySummaries(
    final ConcurrentMap<CharSequence, JsonObject> map,
    final BiFunction<JsonObject, Summaries, Summary> summaryFn
  ) {
    this.map = map;
    this.summaryFn = summaryFn;
  }

  @Override
  public void add(final JsonObject description) {
    map.put(description.getString("name"), description);
  }

  @Override
  public void remove(final CharSequence id) {
    map.remove(id);
  }

  @Override
  public Optional<Summary> summary(final CharSequence id) {
    return Optional.ofNullable(map.get(id)).map(description -> summaryFn.apply(description, this));
  }

  @Override
  public JsonArray description() {
    return map.values().stream()
      .reduce(Json.createArrayBuilder(), JsonArrayBuilder::add, JsonArrayBuilder::addAll)
      .build();
  }

  private final ConcurrentMap<CharSequence, JsonObject> map;
  private final BiFunction<JsonObject, Summaries, Summary> summaryFn;
}
