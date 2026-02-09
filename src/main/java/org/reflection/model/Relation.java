package org.reflection.model;

import javax.management.relation.RelationType;

/**
 * Responsibilities:
 * - Relationship between classes/interfaces.
 * */
public class Relation {

    public enum RelationType { EXTENDS, IMPLEMENTS, ASSOCIATION, DEPENDENCY }

    String fromFqcn;
    String toFqcn;
    RelationType type;   // EXTENDS, IMPLEMENTS, ASSOCIATION, DEPENDENCY...
    String label;        // optional (can be null)
}