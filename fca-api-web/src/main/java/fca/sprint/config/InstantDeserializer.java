package fca.sprint.config;



import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;

import org.apache.commons.lang3.StringUtils;

import java.io.IOException;
import java.time.Instant;

public class InstantDeserializer extends JsonDeserializer<Instant> {
  @Override
  public Instant deserialize(JsonParser arg0, DeserializationContext arg1) throws IOException, JsonProcessingException {
    if (StringUtils.isEmpty(arg0.getText())) return null;
    return Instant.parse(arg0.getText());
  }
}

