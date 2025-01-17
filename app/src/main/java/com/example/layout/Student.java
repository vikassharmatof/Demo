package com.example.layout;

import androidx.room.Entity;
import androidx.room.PrimaryKey;


@Entity(tableName = "student_details")
public class Student {
    @PrimaryKey(autoGenerate = true)
    private int id;
    private String name;
    private int rollNumber;

    // Getters and Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getRollNumber() {
        return rollNumber;
    }

    public void setRollNumber(int rollNumber) {
        this.rollNumber = rollNumber;
    }

}
