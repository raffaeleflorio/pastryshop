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

  final class Fake implements Pastry {
    /**
     * Builds a fake
     *
     * @since 1.0.0
     */
    public Fake() {
      this(0);
    }

    /**
     * Builds a fake with price
     *
     * @param price The price
     * @since 1.0.0
     */
    public Fake(final Number price) {
      this(Json.createObjectBuilder().build(), price);
    }

    /**
     * Builds a fake
     *
     * @param description The description
     * @param price       The price
     * @since 1.0.0
     */
    public Fake(final JsonObject description, final Number price) {
      this(
        x -> {
          throw new IllegalStateException("Unable to sell a fake");
        },
        description,
        price
      );
    }

    /**
     * Builds a fake
     *
     * @param sellFn      The sell function
     * @param description The description
     * @param price       The price
     * @since 1.0.0
     */
    public Fake(final Consumer<Number> sellFn, final JsonObject description, final Number price) {
      this.sellFn = sellFn;
      this.description = description;
      this.price = price;
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
      return price;
    }

    private final Consumer<Number> sellFn;
    private final JsonObject description;
    private final Number price;
  }
}
