package com.john.www.e_libraryapp;

import java.io.Serializable;

public class BookModel implements Serializable {

    private String imageUrl, imagename, pdfUrl, typeofbook;

    public BookModel() {

    }

    public String getTypeofbook() {
        return typeofbook;
    }

    public void setTypeofbook(String typeofbook) {
        this.typeofbook = typeofbook;
    }

    public String getPdfUrl() {
        return pdfUrl;
    }

    public void setPdfUrl(String pdfUrl) {
        this.pdfUrl = pdfUrl;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getImagename() {
        return imagename;
    }

    public void setImagename(String imagename) {
        this.imagename = imagename;
    }
}
