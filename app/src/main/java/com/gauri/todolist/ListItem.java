package com.gauri.todolist;


public class ListItem {
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
