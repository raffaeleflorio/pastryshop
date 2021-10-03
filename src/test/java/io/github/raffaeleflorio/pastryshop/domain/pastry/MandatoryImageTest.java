package io.github.raffaeleflorio.pastryshop.domain.pastry;

import jakarta.json.Json;
import org.junit.jupiter.api.Test;

import static io.github.raffaeleflorio.pastryshop.hamcrest.IsThrowedWithMessage.throwsWithMessage;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

class MandatoryImageTest {
  @Test
  void testMissingPriceException() {
    var descriptionWithoutImage = Json.createObjectBuilder().add("x", "y").build();
    assertThat(
      () -> new MandatoryImage(new Summaries.Fake()).add(descriptionWithoutImage),
      throwsWithMessage(IllegalStateException.class, "Missing image characteristic")
    );
  }

  @Test
  void testRemovingPassThru() {
    assertThat(
      () -> new MandatoryImage(new Summaries.Fake()).remove("any"),
      throwsWithMessage(IllegalStateException.class, "Unable to remove from a fake")
    );
  }

  @Test
  void testSummaryPassThru() {
    assertThat(
      () -> new MandatoryImage(new Summaries.Fake()).summary("any"),
      throwsWithMessage(IllegalStateException.class, "Unable to build a summary from a fake")
    );
  }

  @Test
  void testDescriptionPassThru() {
    var description = Json.createArrayBuilder().build();
    assertThat(
      new MandatoryImage(new Summaries.Fake()).description(),
      equalTo(description)
    );
  }
}
