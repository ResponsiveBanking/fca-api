package fca.sprint.config;



import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import org.apache.commons.lang3.StringUtils;

import java.io.IOException;
import java.time.Instant;
import java.time.LocalDateTime;

public class InstantSerializer extends JsonSerializer<Instant> {
  @Override
  public void serialize(Instant arg0, JsonGenerator arg1, SerializerProvider arg2) throws IOException, JsonProcessingException {
    arg1.writeString(arg0.toString());
  }
}

