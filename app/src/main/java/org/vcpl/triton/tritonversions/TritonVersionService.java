package org.vcpl.triton.tritonversions;

import org.json.JSONObject;
import org.springframework.stereotype.Service;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;


@Service
public class TritonVersionService {

    public String getFile() throws IOException {

        String text = new String(Files.readAllBytes(Paths.get("src\\main\\resources\\TritonVersions")), StandardCharsets.UTF_8);
        JSONObject json = new JSONObject();
        json.put("content",text);
        return json.toString();
    }
}
