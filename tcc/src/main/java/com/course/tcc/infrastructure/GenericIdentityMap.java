package com.course.tcc.infrastructure;

import java.util.HashMap;
import java.util.Map;

public class GenericIdentityMap<T> {
    private final Map<Long, T> cache = new HashMap<>();

    public T get(Long id) { return cache.get(id); }
    public void add(Long id, T obj) { cache.put(id, obj); }
    public boolean contains(Long id) { return cache.containsKey(id); }
}
