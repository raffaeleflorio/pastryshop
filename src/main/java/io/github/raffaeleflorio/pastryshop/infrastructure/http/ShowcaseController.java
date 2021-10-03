package io.github.raffaeleflorio.pastryshop.infrastructure.http;

import io.github.raffaeleflorio.pastryshop.domain.pastry.PastryFromSummaries;
import io.github.raffaeleflorio.pastryshop.domain.showcase.Showcase;
import io.github.raffaeleflorio.pastryshop.domain.summary.Summaries;
import jakarta.json.Json;
import org.springframework.web.bind.annotation.*;

import java.io.StringReader;

@RestController
@RequestMapping("/showcase")
public class ShowcaseController {
  public ShowcaseController(final Showcase showcase, final Summaries summaries) {
    this.showcase = showcase;
    this.summaries = summaries;
  }

  @GetMapping(produces = "application/json")
  public String description() {
    return showcase.description().toString();
  }

  @PostMapping(consumes = "application/json", produces = "application/json")
  public String showcase(@RequestBody final String request) {
    var body = Json.createReader(new StringReader(request)).readObject();
    new PastryFromSummaries(
      body.getString("name"),
      summaries,
      showcase
    ).sell(body.getInt("quantity"));
    return description();
  }

  private final Showcase showcase;
  private final Summaries summaries;
}
