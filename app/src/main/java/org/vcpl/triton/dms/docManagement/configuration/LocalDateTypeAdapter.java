package org.vcpl.triton.dms.docManagement.configuration;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;

public class LocalDateTypeAdapter extends TypeAdapter<LocalDate> {

    private final DateTimeFormatter formatter = DateTimeFormatter.ISO_LOCAL_DATE;



    @Override
    public void write(JsonWriter out, LocalDate value) throws IOException {
        if (value == null) {
            out.nullValue();
        } else {
            out.value(formatter.format(value));
        }
    }

    @Override
    public LocalDate read(JsonReader in) throws IOException {
        if (in.peek() == null) {
            in.nextNull();
            return null;
        } else {
            String dateStr = in.nextString();
            return LocalDate.parse(dateStr, formatter);
        }
    }

//    private final DateTimeFormatter formatter = DateTimeFormatter.ISO_LOCAL_DATE;
//
//    @Override
//    public void write(JsonWriter jsonWriter, LocalDate localDate) throws IOException {
////        jsonWriter.value(localDate.getYear());
//        if (localDate == null) {
//            jsonWriter.nullValue();
//        } else {
//            jsonWriter.value(formatter.format(localDate));
//        }
//    }
//
//    @Override
//    public LocalDate read(JsonReader jsonReader) throws IOException {
//        if (jsonReader.peek() == null) {
//            jsonReader.nextNull();
//            return null;
//        } else {
//            String dateStr = jsonReader.nextString();
//            return LocalDate.parse(dateStr, formatter);
//        }
//
////        String dateString = jsonReader.nextString();
////        return LocalDate.parse(dateString);
//    }
}