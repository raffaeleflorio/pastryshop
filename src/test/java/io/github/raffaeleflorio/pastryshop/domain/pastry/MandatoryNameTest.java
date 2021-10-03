package io.github.raffaeleflorio.pastryshop.domain.pastry;

import jakarta.json.Json;
import org.junit.jupiter.api.Test;

import static io.github.raffaeleflorio.pastryshop.hamcrest.IsThrowedWithMessage.throwsWithMessage;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

class MandatoryNameTest {
  @Test
  void testMissingNameException() {
    var descriptionWithoutName = Json.createObjectBuilder().add("x", "y").build();
    assertThat(
      () -> new MandatoryName(new Summaries.Fake()).add(descriptionWithoutName),
      throwsWithMessage(IllegalStateException.class, "Missing name characteristic")
    );
  }

  @Test
  void testRemovingPassThru() {
    assertThat(
      () -> new MandatoryName(new Summaries.Fake()).remove("any"),
      throwsWithMessage(IllegalStateException.class, "Unable to remove from a fake")
    );
  }

  @Test
  void testSummaryPassThru() {
    assertThat(
      () -> new MandatoryName(new Summaries.Fake()).summary("any"),
      throwsWithMessage(IllegalStateException.class, "Unable to build a summary from a fake")
    );
  }

  @Test
  void testDescriptionPassThru() {
    var description = Json.createArrayBuilder().build();
    assertThat(
      new MandatoryName(new Summaries.Fake()).description(),
      equalTo(description)
    );
  }
}
