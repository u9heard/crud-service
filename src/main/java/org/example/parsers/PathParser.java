package org.example.parsers;

import java.util.List;

public class PathParser {

    private String path;

    public PathParser(String path){
        this.path = path;
    }

    public Long parseLong(int position){
        if(this.path == null){
            return null;
        }

        String [] peaces = path.split("/");

        if(peaces.length >= position && position >= 0){
            try {
                Long result = Long.parseLong(peaces[position]);
                return result;
            } catch (NumberFormatException e) {
                e.printStackTrace();
                return null;
            }
        }
        return null;
    }
}
