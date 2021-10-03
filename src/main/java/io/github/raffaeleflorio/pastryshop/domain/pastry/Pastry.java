package io.github.raffaeleflorio.pastryshop.domain.pastry;

import jakarta.json.JsonObject;

import java.time.LocalDate;

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
    @Override
    public void sell(final Number quantity) {

    }

    @Override
    public JsonObject description() {
      return null;
    }

    @Override
    public Number price() {
      return null;
    }

    @Override
    public LocalDate expiration() {
      return null;
    }
  }
}
