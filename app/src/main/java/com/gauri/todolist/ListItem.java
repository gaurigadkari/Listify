package com.gauri.todolist;


import java.io.Serializable;

public class ListItem implements Serializable{
    String taskName;
    boolean isChecked;
    int priority;

    public ListItem (String taskName, int priority) {
        this.taskName = taskName;
        this.isChecked = false;
        this.priority = priority;
    }

    void setChecked(boolean val){
        this.isChecked = val;
    }

}
