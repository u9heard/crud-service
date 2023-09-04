package org.example.parsers;

import org.example.exceptions.parsers.PathParseException;
import org.example.interfaces.PathParser;

import java.util.Arrays;

public class LongPathParser implements PathParser<Long> {
    public LongPathParser() {
    }

    @Override
    public Long parsePath(String path, int position) {
        if(path == null){
            throw new PathParseException("Unable to parse empty path");
        }
        String [] peaces = path.split("/");
        System.out.println(path.isEmpty());
        try {
            if(peaces.length > position && position >= 0){
                    Long result = Long.parseLong(peaces[position]);
                    return result;
            }
            else{
                throw new PathParseException("Unable to parse path:", path);
            }
        } catch (NumberFormatException e) {
            throw new PathParseException("Unable to parse path:", path);
        }
    }
}
