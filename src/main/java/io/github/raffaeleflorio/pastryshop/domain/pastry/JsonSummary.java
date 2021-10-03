package io.github.raffaeleflorio.pastryshop.domain.pastry;

import jakarta.json.JsonObject;

/**
 * A {@link Summary} that animates a {@link JsonObject}
 *
 * @author Raffaele Florio (raffaeleflorio@protonmail.com)
 * @since 1.0.0
 */
public final class JsonSummary implements Summary {
  /**
   * Builds a summary
   *
   * @param origin    The json to animate
   * @param summaries The summaries where to track
   * @since 1.0.0
   */
  public JsonSummary(final JsonObject origin, final Summaries summaries) {
    this.origin = origin;
    this.summaries = summaries;
  }

  @Override
  public void track() {
    summaries.add(origin);
  }

  @Override
  public JsonObject description() {
    return origin;
  }

  private final JsonObject origin;
  private final Summaries summaries;
}
