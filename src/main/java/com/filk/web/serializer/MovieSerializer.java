package com.filk.web.serializer;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import com.filk.entity.Movie;

import java.io.IOException;

public class MovieSerializer extends StdSerializer<Movie> {
    public MovieSerializer() {
        this(null);
    }

    public MovieSerializer(Class<Movie> t) {
        super(t);
    }

    @Override
    public void serialize(Movie movie, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
        jsonGenerator.writeStartObject();
        jsonGenerator.writeNumberField("id", movie.getId());
        jsonGenerator.writeStringField("nameRussian", movie.getNameRussian());
        jsonGenerator.writeStringField("nameNative", movie.getNameNative());
        jsonGenerator.writeStringField("yearOfRelease", movie.getYearOfRelease());
        jsonGenerator.writeNumberField("rating", movie.getRating());
        jsonGenerator.writeNumberField("price", movie.getPrice());
        jsonGenerator.writeStringField("picturePath", movie.getPicturePath());
        jsonGenerator.writeEndObject();
    }
}