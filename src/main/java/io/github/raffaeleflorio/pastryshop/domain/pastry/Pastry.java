package io.github.raffaeleflorio.pastryshop.domain.pastry;

import jakarta.json.Json;
import jakarta.json.JsonObject;

import java.util.function.Consumer;

/**
 * A produced pastry
 *
 * @author Raffaele Florio (raffaeleflorio@protonmail.com)
 * @since 1.0.0
 */
public interface Pastry {
  /**
   * Sells itself
   *
   * @param quantity The quantity
   * @since 1.0.0
   */
  void sell(Number quantity);

  /**
   * Builds the description
   *
   * @return The description
   * @since 1.0.0
   */
  JsonObject description();

  /**
   * Builds the price
   *
   * @return The price
   * @since 1.0.0
   */
  Number price();

  /**
   * Builds true if expired
   *
   * @return True if expired
   * @since 1.0.0
   */
  Boolean expired();

  final class Fake implements Pastry {
    /**
     * Builds a fake
     *
     * @since 1.0.0
     */
    public Fake() {
      this(Json.createObjectBuilder().add("price", 0).build());
    }

    /**
     * Builds a fake
     *
     * @param description The description
     * @since 1.0.0
     */
    public Fake(final JsonObject description) {
      this(description, true);
    }

    /**
     * Builds a fake
     *
     * @param description The description
     * @param expired     True if expired
     * @since 1.0.0
     */
    public Fake(final JsonObject description, final Boolean expired) {
      this(
        description,
        expired,
        x -> {
          throw new IllegalStateException("Unable to sell a fake");
        }
      );
    }

    /**
     * Builds a fake
     *
     * @param sellFn      The sell function
     * @param description The description
     * @param expired     True if expired
     * @since 1.0.0
     */
    public Fake(final JsonObject description, final Boolean expired, final Consumer<Number> sellFn) {
      this.description = description;
      this.expired = expired;
      this.sellFn = sellFn;
    }

    @Override
    public void sell(final Number quantity) {
      sellFn.accept(quantity);
    }

    @Override
    public JsonObject description() {
      return description;
    }

    @Override
    public Number price() {
      return description().getJsonNumber("price").numberValue();
    }

    @Override
    public Boolean expired() {
      return expired;
    }

    private final JsonObject description;
    private final Boolean expired;
    private final Consumer<Number> sellFn;
  }
}
