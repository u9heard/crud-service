package org.example.parsers;

import org.example.exceptions.PathParsingException;
import org.example.interfaces.PathParser;

public class LongPathParser implements PathParser<Long> {
    public LongPathParser() {
    }

    @Override
    public Long parsePath(String path, int position) {
        String [] peaces = path.split("/");

        try {
            if(peaces.length > position && position >= 0){
                    Long result = Long.parseLong(peaces[position]);
                    return result;
            }
            else{
                throw new PathParsingException("Unable to parse path:", path);
            }
        } catch (NumberFormatException e) {
            throw new PathParsingException("Unable to parse path:", path);
        }
    }
}
