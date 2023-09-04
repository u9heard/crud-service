package org.example.parsers;

import jakarta.servlet.http.HttpServletRequest;
import org.example.exceptions.parsers.BodyParseException;

import java.io.BufferedReader;

public class RequestBodyParser {
    public static String readBody(HttpServletRequest req){
        StringBuilder requestBody = new StringBuilder();
        try (BufferedReader reader = req.getReader()) {
            String line;
            while ((line = reader.readLine()) != null) {
                requestBody.append(line);
            }
        } catch (Exception e) {
            throw new BodyParseException("Can't parse request body");
        }

        return requestBody.toString();
    }
}
