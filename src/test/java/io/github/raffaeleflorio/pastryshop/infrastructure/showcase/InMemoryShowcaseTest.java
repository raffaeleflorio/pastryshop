package io.github.raffaeleflorio.pastryshop.infrastructure.showcase;

import io.github.raffaeleflorio.pastryshop.domain.pastry.Pastry;
import jakarta.json.Json;
import jakarta.json.JsonArray;
import org.junit.jupiter.api.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

class InMemoryShowcaseTest {
  @Test
  void testDescriptionAfterAdding() {
    var description = Json.createArrayBuilder()
      .add(Json.createObjectBuilder()
        .add("quantity", 3.0)
        .add("name", "a name")
      )
      .build();
    assertThat(
      new DescriptionAfterAdding(
        new InMemoryShowcase(pastryDescription -> new Pastry.Fake(pastryDescription, false)),
        description
      ).take(),
      equalTo(description)
    );
  }

  private final static class DescriptionAfterAdding {
    private DescriptionAfterAdding(final InMemoryShowcase showcase, final JsonArray description) {
      this.showcase = showcase;
      this.description = description;
    }

    public JsonArray take() {
      description.forEach(v -> showcase.add(
          v.asJsonObject(),
          v.asJsonObject().getJsonNumber("quantity").numberValue()
        )
      );
      return showcase.description();
    }

    private final InMemoryShowcase showcase;
    private final JsonArray description;
  }
}
