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
    var price = 13.37;
    assertThat(
      new PastryOnSale(new Pastry.Fake(price)).price(),
      equalTo(price)
    );
  }

  @Test
  void testEightyPercentPriceSecondDay() {
    var price = 13.37 * 0.8;
    assertThat(
      new PastryOnSale(
        new Pastry.Fake(13.37),
        LocalDate.EPOCH,
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
        new Pastry.Fake(13.37),
        LocalDate.EPOCH,
        () -> LocalDate.EPOCH.plusDays(2)
      ).price(),
      equalTo(price)
    );
  }

  @Test
  void testPriceExceptionAtFourthDay() {
    assertThat(
      () -> new PastryOnSale(
        new Pastry.Fake(1234),
        LocalDate.EPOCH,
        () -> LocalDate.EPOCH.plusDays(3)
      ).price(),
      throwsWithMessage(IllegalStateException.class, "Expired pastry")
    );
  }

  @Test
  void testDescriptionAtThirdDay() {
    var description = Json.createObjectBuilder()
      .add("expiration", LocalDate.EPOCH.plusDays(3).toString())
      .add("price", 42 * 0.2)
      .build();
    assertThat(
      new PastryOnSale(
        new Pastry.Fake(
          Json.createObjectBuilder().add("price", 42).build(),
          42
        ),
        LocalDate.EPOCH,
        () -> LocalDate.EPOCH.plusDays(2)
      ).description(),
      equalTo(description)
    );
  }

  @Test
  void testSellAtThirdDay() {
    assertThat(
      () -> new PastryOnSale(
        new Pastry.Fake(),
        LocalDate.EPOCH,
        () -> LocalDate.EPOCH.plusDays(2)
      ).sell(123),
      throwsWithMessage(IllegalStateException.class, "Unable to sell a fake")
    );
  }

  @Test
  void testSellExceptionAtFourthDay() {
    assertThat(
      () -> new PastryOnSale(
        new Pastry.Fake(),
        LocalDate.EPOCH,
        () -> LocalDate.EPOCH.plusDays(3)
      ).sell(9),
      throwsWithMessage(IllegalStateException.class, "Expired pastry")
    );
  }
}
