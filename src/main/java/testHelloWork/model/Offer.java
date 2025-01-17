package testHelloWork.model;

import org.bson.types.ObjectId;

public class Offer {
    private ObjectId id;
    private String reference;
    private String description;
    private String frenchDate;

    public Offer() {}

    public Offer(String reference, String description, String frenchDate) {
        this.reference = reference;
        this.description = description;
        this.frenchDate = frenchDate;
    }

    // Getters et Setters
    public ObjectId getId() {
        return id;
    }

    public void setId(ObjectId id) {
        this.id = id;
    }

    public String getReference() {
        return reference;
    }

    public void setReference(String reference) {
        this.reference = reference;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getFrenchDate() {
        return frenchDate;
    }

    public void setFrenchDate(String frenchDate) {
        this.frenchDate = frenchDate;
    }
}
