package com.gauri.todolist;


import java.io.Serializable;

public class ListItem implements Serializable{
    String taskName;
    int isChecked;
    int priority;
    int id;
    String imageURL;

    public ListItem (String taskName, int priority) {
        this.taskName = taskName;
        this.isChecked = 0;
        this.priority = priority;
        this.imageURL = "";

    }
}
