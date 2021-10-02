package io.github.raffaeleflorio.pastryshop.infrastructure.pastry;

import io.github.raffaeleflorio.pastryshop.domain.pastry.Summary;
import jakarta.json.Json;
import jakarta.json.JsonArray;
import jakarta.json.JsonObject;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

class InMemorySummariesTest {
  @Test
  void testNonexistentSummary() {
    assertThat(
      new InMemorySummaries().summary("any nonexistent pastry name"),
      equalTo(Optional.empty())
    );
  }

  @Test
  void testAdding() {
    var description = Json.createObjectBuilder()
      .add("name", "a name that identify the summary")
      .build();
    assertThat(
      new AddedSummary(new InMemorySummaries(), description).take().description(),
      equalTo(description)
    );
  }

  private final static class AddedSummary {
    private AddedSummary(final InMemorySummaries summaries, final JsonObject description) {
      this.summaries = summaries;
      this.description = description;
    }

    public Summary take() {
      summaries.add(description);
      return summaries.summary(description.getString("name")).orElseThrow();
    }

    private final InMemorySummaries summaries;
    private final JsonObject description;
  }

  @Test
  void testRemoving() {
    var description = Json.createObjectBuilder()
      .add("name", "a name")
      .build();
    assertThat(
      new RemovedSummary(new InMemorySummaries(), description).take(),
      equalTo(Optional.empty())
    );
  }

  private final static class RemovedSummary {
    private RemovedSummary(final InMemorySummaries summaries, final JsonObject description) {
      this.summaries = summaries;
      this.description = description;
    }

    public Optional<Summary> take() {
      summaries.add(description);
      summaries.remove(description.getString("name"));
      return summaries.summary(description.getString("name"));
    }

    private final InMemorySummaries summaries;
    private final JsonObject description;
  }

  @Test
  void testDescription() {
    var description = Json.createArrayBuilder()
      .add(Json.createObjectBuilder().add("name", "pastry one"))
      .add(Json.createObjectBuilder().add("name", "pastry two"))
      .build();
    assertThat(
      new DescriptionAfterAdding(new InMemorySummaries(), description).take(),
      equalTo(description)
    );
  }

  private final static class DescriptionAfterAdding {
    private DescriptionAfterAdding(final InMemorySummaries summaries, final JsonArray description) {
      this.summaries = summaries;
      this.description = description;
    }

    public JsonArray take() {
      description.forEach(v -> summaries.add(v.asJsonObject()));
      return summaries.description();
    }

    private final InMemorySummaries summaries;
    private final JsonArray description;
  }
}
