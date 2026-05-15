package com.ideaforge.platform.shared.infrastructure.persistence.jpa.configuration.strategy;

import org.hibernate.boot.model.naming.Identifier;
import org.hibernate.boot.model.naming.PhysicalNamingStrategyStandardImpl;
import org.hibernate.engine.jdbc.env.spi.JdbcEnvironment;

public class SnakeCaseWithPluralizedTablePhysicalNamingStrategy extends PhysicalNamingStrategyStandardImpl {
    @Override
    public Identifier toPhysicalTableName(Identifier name, JdbcEnvironment context) {
        String snake = toSnakeCase(name.getText());
        return Identifier.toIdentifier(pluralize(snake));
    }

    @Override
    public Identifier toPhysicalColumnName(Identifier name, JdbcEnvironment context) {
        return Identifier.toIdentifier(toSnakeCase(name.getText()));
    }

    private String toSnakeCase(String text) {
        if (text == null) return null;
        return text.replaceAll("([a-z])([A-Z])", "$1_$2")
                .replaceAll("([A-Z])([A-Z][a-z])", "$1_$2")
                .toLowerCase();
    }

    private String pluralize(String text) {
        if (text == null || text.isBlank() || text.endsWith("s")) return text;
        if (text.endsWith("y")) return text.substring(0, text.length() - 1) + "ies";
        return text + "s";
    }
}
