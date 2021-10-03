package io.github.raffaeleflorio.pastryshop.domain.summary;

import jakarta.json.Json;
import jakarta.json.JsonObject;

/**
 * A pastry summary, like "Torta caprese with dark chocalate, butter etc..."
 *
 * @author Raffaele Florio (raffaeleflorio@protonmail.com)
 * @since 1.0.0
 */
public interface Summary {
  /**
   * Tracks itself
   *
   * @since 1.0.0
   */
  void track();

  /**
   * Builds the description
   *
   * @return The description
   * @since 1.0.0
   */
  JsonObject description();

  /**
   * A {@link Summary} useful for testing
   *
   * @author Raffaele Florio (raffaeleflorio@protonmail.com)
   * @since 1.0.0
   */
  final class Fake implements Summary {
    /**
     * Builds a fake unable to track and with an empty description
     *
     * @since 1.0.0
     */
    public Fake() {
      this(() -> {
        throw new IllegalStateException("Unable to track a fake");
      });
    }

    /**
     * Builds fake with an empty description
     *
     * @param trackFn The track function
     * @since 1.0.0
     */
    public Fake(final Runnable trackFn) {
      this(trackFn, Json.createObjectBuilder().build());
    }

    /**
     * Builds a fake
     *
     * @param trackFn     The track function
     * @param description The description
     * @since 1.0.0
     */
    public Fake(final Runnable trackFn, final JsonObject description) {
      this.trackFn = trackFn;
      this.description = description;
    }

    @Override
    public void track() {
      trackFn.run();
    }

    @Override
    public JsonObject description() {
      return description;
    }

    private final Runnable trackFn;
    private final JsonObject description;
  }
}
