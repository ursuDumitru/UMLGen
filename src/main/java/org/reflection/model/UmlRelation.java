package org.reflection.model;

import java.util.Objects;

/** Responsibilities: - Relationship between classes/interfaces. */
public class UmlRelation {
    private String fromFqcn;
    private String toFqcn;
    private RelationType type;
    private String label; // @Override for ex.

    public UmlRelation(String fromFqcn, String toFqcn, RelationType type) {
        this.fromFqcn = fromFqcn;
        this.toFqcn = toFqcn;
        this.type = type;
    }

    public UmlRelation(String fromFqcn, String toFqcn, RelationType type, String label) {
        this.fromFqcn = fromFqcn;
        this.toFqcn = toFqcn;
        this.type = type;
        this.label = label;
    }

    public String getFromFqcn() {
        return fromFqcn;
    }

    public void setFromFqcn(String fromFqcn) {
        this.fromFqcn = fromFqcn;
    }

    public String getToFqcn() {
        return toFqcn;
    }

    public void setToFqcn(String toFqcn) {
        this.toFqcn = toFqcn;
    }

    public RelationType getType() {
        return type;
    }

    public void setType(RelationType type) {
        this.type = type;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    @Override
    public boolean equals(Object other) {
        if (other == null || getClass() != other.getClass()) return false;
        UmlRelation otherRelation = (UmlRelation) other;
        return Objects.equals(this.fromFqcn, otherRelation.getFromFqcn())
                && Objects.equals(this.toFqcn, otherRelation.getToFqcn())
                && this.type == otherRelation.getType()
                && Objects.equals(this.label, otherRelation.getLabel());
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.fromFqcn, this.toFqcn, this.type, this.label);
    }

    @Override
    public String toString() {
        return "UmlRelation: "
                + "[\n\t fromFqcn: "
                + this.fromFqcn
                + ",\n\t toFqcn: "
                + this.toFqcn
                + ",\n\t type: "
                + this.type
                + ",\n\t label: "
                + this.label
                + "\n]";
    }
}
