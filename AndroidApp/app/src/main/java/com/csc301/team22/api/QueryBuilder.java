package com.csc301.team22.api;

import java.util.HashMap;
import java.util.Map;

public class QueryBuilder {
    private Map<String, String> queries = new HashMap<>();

    public QueryBuilder add(String key, Object value) {
        if (value != null) {
            this.queries.put(key, value.toString());
        }
        return this;
    }

    public Map<String, String> build() {
        return queries;
    }
}
