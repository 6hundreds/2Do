package com.six_hundreds.todo.model;

/**
 * Created by six_hundreds on 30.12.15.
 */
public class ModelTask implements Item {

    private String title;
    private Long date;

    public ModelTask() {

    }

    public ModelTask(String title, Long date) {
        this.title = title;
        this.date = date;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Long getDate() {
        return date;
    }

    public void setDate(Long date) {
        this.date = date;
    }

    @Override
    public boolean isTask() {
        return true;
    }
}
