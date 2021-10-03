package io.github.raffaeleflorio.pastryshop.domain.pastry;

import jakarta.json.Json;
import jakarta.json.JsonObject;

import java.time.LocalDate;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.Map;
import java.util.function.Supplier;

/**
 * An already sold {@link Pastry} with expiration date
 *
 * @author Raffaele Florio (raffaeleflorio@protonmail.com)
 * @since 1.0.0
 */
public final class PastryWithExpiration implements Pastry {
  /**
   * Builds a pastry which expires in four days from now with system timezone
   *
   * @param origin The pastry to decorate
   * @since 1.0.0
   */
  public PastryWithExpiration(final Pastry origin) {
    this(origin, ZoneId.systemDefault());
  }

  /**
   * Builds a pastry which expires in four days from now
   *
   * @param origin The pastry to decorate
   * @param zoneId The timezone
   * @since 1.0.0
   */
  public PastryWithExpiration(final Pastry origin, final ZoneId zoneId) {
    this(origin, LocalDate.now(zoneId), () -> LocalDate.now(zoneId));
  }

  /**
   * Builds a pastry that expires in four days
   *
   * @param origin           The pastry to decorate
   * @param manufacturedDate The expiration date
   * @param now              The supplier of now
   * @since 1.0.0
   */
  public PastryWithExpiration(final Pastry origin, final LocalDate manufacturedDate, final Supplier<LocalDate> now) {
    this(origin, manufacturedDate, now, 3L);
  }

  /**
   * Builds a pastry
   *
   * @param origin           The pastry to decorate
   * @param manufacturedDate The expiration date
   * @param now              The supplier of now
   * @param expireDays       The days after expiration
   * @since 1.0.0
   */
  public PastryWithExpiration(
    final Pastry origin,
    final LocalDate manufacturedDate,
    final Supplier<LocalDate> now,
    final Long expireDays
  ) {
    this.origin = origin;
    this.manufacturedDate = manufacturedDate;
    this.now = now;
    this.expireDays = expireDays;
  }

  @Override
  public void sell(final Number quantity) {
    assertNotExpired();
    origin.sell(quantity);
  }

  private void assertNotExpired() {
    if (expired()) {
      throw new IllegalStateException("Expired pastry");
    }
  }

  @Override
  public JsonObject description() {
    return Json.createObjectBuilder(origin.description())
      .add("expiration", expirationDate().toString())
      .add("price", price().doubleValue())
      .build();
  }

  private LocalDate expirationDate() {
    return manufacturedDate.plusDays(expireDays);
  }

  @Override
  public Number price() {
    assertNotExpired();
    var priceMap = Map.<Long, Supplier<Number>>of(
      0L, origin::price,
      1L, () -> origin.price().doubleValue() * 0.8,
      2L, () -> origin.price().doubleValue() * 0.2);
    return priceMap.get(soldDays()).get();
  }

  private Long soldDays() {
    return manufacturedDate.until(now.get(), ChronoUnit.DAYS);
  }

  @Override
  public Boolean expired() {
    return soldDays() >= expireDays;
  }

  private final Pastry origin;
  private final LocalDate manufacturedDate;
  private final Supplier<LocalDate> now;
  private final Long expireDays;
}
