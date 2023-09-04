package org.example.parsers;

import org.example.exceptions.parsers.PathParseException;
import org.example.interfaces.PathParser;

public class StringPathParser implements PathParser<String> {
    @Override
    public String parsePath(String path, int position) {
        if(path == null){
            throw new PathParseException("Unable to parse empty path");
        }

        String [] peaces = path.split("/");
        if(peaces.length > position && position >= 0){
            String result = peaces[position];
            return result;
        }
        else{
            throw new PathParseException("Unable to parse path:", path);
        }
    }
}
