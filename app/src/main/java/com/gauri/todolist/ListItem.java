package com.gauri.todolist;


import java.io.Serializable;
import java.util.Date;



public class ListItem implements Serializable{
    String taskName;
    int isChecked;
    int priority;
    int id;
    String imageURL;
    String date;



    public ListItem (String taskName, int priority) {
        this.taskName = taskName;
        this.isChecked = 0;
        this.priority = priority;
        this.imageURL = "";

    }

    public ListItem (String taskName, int priority, String imagePath) {
        this.taskName = taskName;
        this.isChecked = 0;
        this.priority = priority;
        this.imageURL = imagePath;

    }

    /*public ListItem (String taskName, int priority, String imagePath, int dateDay, String dateMonth, int dateYear) {
        this.taskName = taskName;
        this.isChecked = 0;
        this.priority = priority;
        this.imageURL = imagePath;
        this.dateDay = today.getDay();
        this.dateMonth = today.getMonth();
        this.dateYear = today.getYear();

    }*/
}
