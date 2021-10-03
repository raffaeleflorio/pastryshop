package io.github.raffaeleflorio.pastryshop.domain.pastry;

import jakarta.json.Json;
import jakarta.json.JsonObject;

import java.time.LocalDate;
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
   * Builds the expiration date
   *
   * @return The expiration date
   * @since 1.0.0
   */
  LocalDate expiration();

  final class Fake implements Pastry {
    /**
     * Builds a fake
     *
     * @since 1.0.0
     */
    public Fake() {
      this(
        x -> {
          throw new IllegalStateException("Unable to sell a fake");
        },
        Json.createObjectBuilder().build(),
        0,
        LocalDate.EPOCH
      );
    }

    /**
     * Builds a fake
     *
     * @param sellFn      The sell function
     * @param description The description
     * @param price       The price
     * @param expiration  The expiration
     * @since 1.0.0
     */
    public Fake(
      final Consumer<Number> sellFn,
      final JsonObject description,
      final Number price,
      final LocalDate expiration
    ) {
      this.sellFn = sellFn;
      this.description = description;
      this.price = price;
      this.expiration = expiration;
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

    @Override
    public LocalDate expiration() {
      return expiration;
    }

    private final Consumer<Number> sellFn;
    private final JsonObject description;
    private final Number price;
    private final LocalDate expiration;
  }
}
