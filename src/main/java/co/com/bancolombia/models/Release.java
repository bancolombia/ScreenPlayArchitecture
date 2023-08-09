package co.com.bancolombia.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.time.OffsetDateTime;

@Getter
@Setter
public class Release {
    @JsonProperty("tag_name")
    private String tagName;

    @JsonProperty("published_at")
    private OffsetDateTime publishedAt;
}
