package com.qdu.applet.dao;

import java.util.ArrayList;

public class Course {
    private String name;
    private String teacher;
    private String credit;
    private String assess;
    private ArrayList<CourseItem> timeAndPlace;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTeacher() {
        return teacher;
    }

    public void setTeacher(String teacher) {
        this.teacher = teacher;
    }

    public String getCredit() {
        return credit;
    }

    public void setCredit(String credit) {
        this.credit = credit;
    }

    public String getAssess() {
        return assess;
    }

    public void setAssess(String assess) {
        this.assess = assess;
    }

    public ArrayList<CourseItem> getTimeAndPlace() {
        return timeAndPlace;
    }

    public void setTimeAndPlace(ArrayList<CourseItem> timeAndPlace) {
        this.timeAndPlace = timeAndPlace;
    }
}
