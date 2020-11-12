package com.example.spendcelebsmoney.LocalDatabase;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

//Creating the Entity and specifying its' name
@Entity(tableName = "users")
public class User {

    //Declaring the attributes
    @PrimaryKey(autoGenerate = true)
    private int id;
    private String myName;
    private String myDescription;
    private String myPhoto;
    private int myWorth;


    //Constructor
    public User(String myName, String myDescription, String myPhoto, int myWorth) {
        this.myName = myName;
        this.myDescription = myDescription;
        this.myPhoto = myPhoto;
        this.myWorth = myWorth;
    }

    //Getter and setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getMyName() {
        return myName;
    }

    public void setMyName(String myName) {
        this.myName = myName;
    }

    public String getMyDescription() {
        return myDescription;
    }

    public void setMyDescription(String myDescription) {
        this.myDescription = myDescription;
    }

    public String getMyPhoto() {
        return myPhoto;
    }

    public void setMyPhoto(String myPhoto) {
        this.myPhoto = myPhoto;
    }

    public int getMyWorth() {
        return myWorth;
    }

    public void setMyWorth(int myWorth) {
        this.myWorth = myWorth;
    }
}
