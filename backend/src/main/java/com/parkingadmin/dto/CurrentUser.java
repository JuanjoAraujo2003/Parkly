package com.parkingadmin.dto;

import java.util.Arrays;
import java.util.stream.Collectors;

// initials son la primera letra de las dos primeras palabras del nombre ("Juan Delgado" -> "JD").
public record CurrentUser(String name, String role, String initials) {

    public static CurrentUser of(String name, String role) {
        String initials = Arrays.stream(name.trim().split("\\s+"))
                .limit(2)
                .map(word -> word.substring(0, 1).toUpperCase())
                .collect(Collectors.joining());
        return new CurrentUser(name, role, initials);
    }
}
