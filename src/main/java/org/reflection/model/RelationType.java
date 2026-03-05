package org.reflection.model;

public enum RelationType {
    EXTENDS,
    IMPLEMENTS,
    ASSOCIATION,
    DEPENDENCY;

    public boolean equals(RelationType other) {
        return other == this;
    }
}
