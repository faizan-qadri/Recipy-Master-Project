package com.example.recipy;

public class Fav_Model {

    private String documentId;
    private String image;
    public String index;

    public Fav_Model() {
    }

    public Fav_Model(String documentId, String image, String index) {
        this.documentId = documentId;
        this.image = image;
        this.index = index;
    }

    public String getDocumentId() {
        return documentId;
    }

    public void setDocumentId(String documentId) {
        this.documentId = documentId;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getIndex() {
        return index;
    }

    public void setIndex(String index) {
        this.index = index;
    }
}
