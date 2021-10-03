package io.github.raffaeleflorio.pastryshop.domain.pastry;

import jakarta.json.Json;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static io.github.raffaeleflorio.pastryshop.hamcrest.IsThrowedWithMessage.throwsWithMessage;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

class PastryTest {
  @Nested
  class FakeTest {
    @Test
    void testDefaultSellException() {
      assertThat(
        () -> new Pastry.Fake().sell(123),
        throwsWithMessage(IllegalStateException.class, "Unable to sell a fake")
      );
    }

    @Test
    void testDefaultDescriptionToEmptyJsonObject() {
      var description = Json.createObjectBuilder().build();
      assertThat(
        new Pastry.Fake().description(),
        equalTo(description)
      );
    }

    @Test
    void testDefaultExpiredToTrue() {
      assertThat(
        new Pastry.Fake().expired(),
        equalTo(true)
      );
    }

    @Test
    void testDefaultPriceToZero() {
      var price = 0;
      assertThat(
        new Pastry.Fake().price(),
        equalTo(price)
      );
    }
  }
}
