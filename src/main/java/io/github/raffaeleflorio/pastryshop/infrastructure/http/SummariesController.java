package io.github.raffaeleflorio.pastryshop.infrastructure.http;

import io.github.raffaeleflorio.pastryshop.domain.pastry.JsonSummary;
import io.github.raffaeleflorio.pastryshop.domain.pastry.Summaries;
import jakarta.json.Json;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.io.StringReader;

@RestController
@RequestMapping("/summaries")
public class SummariesController {
  public SummariesController(final Summaries summaries) {
    this.summaries = summaries;
  }

  @PostMapping(consumes = "application/json", produces = "application/json")
  public String summary(@RequestBody final String description) {
    var summary = new JsonSummary(
      Json.createReader(new StringReader(description)).readObject(),
      summaries
    );
    summary.track();
    return summary.description().toString();
  }

  @DeleteMapping("/{name}")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public void remove(@PathVariable final String name) {
    summaries.remove(name);
  }

  private final Summaries summaries;
}
