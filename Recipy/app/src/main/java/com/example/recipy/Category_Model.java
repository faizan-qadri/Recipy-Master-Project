package com.example.recipy;

public class Category_Model {

    private String image;
    private String documentId;

    public Category_Model() {
    }

    public Category_Model(String image, String documentId) {
        this.image = image;
        this.documentId = documentId;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getDocumentId() {
        return documentId;
    }

    public void setDocumentId(String documentId) {
        this.documentId = documentId;
    }
}
