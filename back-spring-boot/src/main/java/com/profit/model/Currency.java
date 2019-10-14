package com.profit.model;

import java.util.Optional;

public enum Currency {
    EUR,
    RUB;

    public static
    Optional<Currency> get(String name) {
        try {
            return Optional.of(valueOf(name));
        } catch (IllegalArgumentException ex) {
            return Optional.empty();
        }
    }
}
