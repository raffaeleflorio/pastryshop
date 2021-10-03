package io.github.raffaeleflorio.pastryshop.domain.pastry;

import jakarta.json.Json;
import org.junit.jupiter.api.Test;

import static io.github.raffaeleflorio.pastryshop.hamcrest.IsThrowedWithMessage.throwsWithMessage;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

class MandatoryCharacteristicTest {
  @Test
  void testMissingCharacteristicException() {
    var expected = new IllegalStateException("OPS");
    assertThat(
      () -> new MandatoryCharacteristic(
        new Summaries.Fake(),
        "expected characteristic",
        expected
      ).add(Json.createObjectBuilder().build()),
      throwsWithMessage(expected.getClass(), expected.getMessage())
    );
  }

  @Test
  void testAddingWithExpectedCharacteristic() {
    assertThat(
      () -> new MandatoryCharacteristic(
        new Summaries.Fake(),
        "price",
        new IllegalStateException("OPS")
      ).add(Json.createObjectBuilder().add("price", 123).build()),
      throwsWithMessage(IllegalStateException.class, "Unable to add to a fake")
    );
  }

  @Test
  void testRemovingPassThru() {
    assertThat(
      () -> new MandatoryCharacteristic(new Summaries.Fake(), "any", new IllegalStateException("OPS"))
        .remove("any"),
      throwsWithMessage(IllegalStateException.class, "Unable to remove from a fake")
    );
  }

  @Test
  void testSummaryPassThru() {
    assertThat(
      () -> new MandatoryCharacteristic(new Summaries.Fake(), "any", new IllegalStateException("OPS"))
        .summary("any"),
      throwsWithMessage(IllegalStateException.class, "Unable to build a summary from a fake")
    );
  }

  @Test
  void testDescriptionPassThru() {
    var description = Json.createArrayBuilder().build();
    assertThat(
      new MandatoryCharacteristic(new Summaries.Fake(), "any", new IllegalStateException("OPS"))
        .description(),
      equalTo(description)
    );
  }
}
