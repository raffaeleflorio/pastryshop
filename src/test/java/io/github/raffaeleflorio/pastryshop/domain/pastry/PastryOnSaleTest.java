package io.github.raffaeleflorio.pastryshop.domain.pastry;

import jakarta.json.Json;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static io.github.raffaeleflorio.pastryshop.hamcrest.IsThrowedWithMessage.throwsWithMessage;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

class PastryOnSaleTest {
  @Test
  void testFullPriceFirstDay() {
    var price = 1337;
    assertThat(
      new PastryOnSale(
        Json.createObjectBuilder()
          .add("price", price)
          .add("manufactured", LocalDate.EPOCH.toString())
          .build(),
        () -> LocalDate.EPOCH
      ).price(),
      equalTo(price)
    );
  }

  @Test
  void testEightyPercentPriceSecondDay() {
    var price = 13.37 * 0.8;
    assertThat(
      new PastryOnSale(
        Json.createObjectBuilder()
          .add("price", 13.37)
          .add("manufactured", LocalDate.EPOCH.toString())
          .build(),
        () -> LocalDate.EPOCH.plusDays(1)
      ).price(),
      equalTo(price)
    );
  }

  @Test
  void testTwentyPercentPriceThirdDay() {
    var price = 13.37 * 0.2;
    assertThat(
      new PastryOnSale(
        Json.createObjectBuilder()
          .add("price", 13.37)
          .add("manufactured", LocalDate.EPOCH.toString())
          .build(),
        () -> LocalDate.EPOCH.plusDays(2)
      ).price(),
      equalTo(price)
    );
  }

  @Test
  void testPriceExceptionAtFourthDay() {
    assertThat(
      () -> new PastryOnSale(
        Json.createObjectBuilder()
          .add("price", 13.37)
          .add("manufactured", LocalDate.EPOCH.toString())
          .build(),
        () -> LocalDate.EPOCH.plusDays(3)
      ).price(),
      throwsWithMessage(IllegalStateException.class, "Expired pastry")
    );
  }

  @Test
  void testDescriptionAtThirdDay() {
    var description = Json.createObjectBuilder()
      .add("manufactured", LocalDate.EPOCH.toString())
      .add("expiration", LocalDate.EPOCH.plusDays(3).toString())
      .add("price", 42 * 0.2)
      .build();
    assertThat(
      new PastryOnSale(
        Json.createObjectBuilder()
          .add("price", 42)
          .add("manufactured", LocalDate.EPOCH.toString())
          .build(),
        () -> LocalDate.EPOCH.plusDays(2)
      ).description(),
      equalTo(description)
    );
  }

  @Test
  void testSellException() {
    assertThat(
      () -> new PastryOnSale(Json.createObjectBuilder().build()).sell(9),
      throwsWithMessage(IllegalStateException.class, "Pastry already on sale")
    );
  }

  @Test
  void testExpiredAtFourthDayToTrue() {
    assertThat(
      new PastryOnSale(
        Json.createObjectBuilder()
          .add("manufactured", LocalDate.EPOCH.toString())
          .build(),
        () -> LocalDate.EPOCH.plusDays(3)
      ).expired(),
      equalTo(true)
    );
  }

  @Test
  void testExpiredAtThirdDayToFalse() {
    assertThat(
      new PastryOnSale(
        Json.createObjectBuilder()
          .add("manufactured", LocalDate.EPOCH.toString())
          .build(),
        () -> LocalDate.EPOCH.plusDays(2)
      ).expired(),
      equalTo(false)
    );
  }

  @Test
  void testExpiredAtSecondDayToFalse() {
    assertThat(
      new PastryOnSale(
        Json.createObjectBuilder()
          .add("manufactured", LocalDate.EPOCH.toString())
          .build(),
        () -> LocalDate.EPOCH.plusDays(1)
      ).expired(),
      equalTo(false)
    );
  }
}
