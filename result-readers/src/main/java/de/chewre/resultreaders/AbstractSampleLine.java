package de.chewre.resultreaders;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class AbstractSampleLine {

    protected final List<String> keys = new ArrayList<>();
    protected final List<Object> values = new ArrayList<>();

    
    public Map<String, Object> toAssocMap() {
        Map<String, Object> map = new HashMap<>();

        for (int i = 0; i < keys.size(); i++) {
            map.put(keys.get(i), values.get(i));
        }

        return map;
    }
}
