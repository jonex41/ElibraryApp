package com.john.www.e_libraryapp;

import android.widget.TextView;

import java.io.Serializable;

public class ReviewModel implements Serializable {

    private String nameofbook, name, dateofcollection;


    public ReviewModel() {
    }

    public String getNameofbook() {
        return nameofbook;
    }

    public void setNameofbook(String nameofbook) {
        this.nameofbook = nameofbook;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDateofcollection() {
        return dateofcollection;
    }

    public void setDateofcollection(String dateofcollection) {
        this.dateofcollection = dateofcollection;
    }
}
