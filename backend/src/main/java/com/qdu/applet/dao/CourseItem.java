package com.qdu.applet.dao;

import java.util.ArrayList;

public class CourseItem {
    private ArrayList<Integer> weeks;
    private Integer weekday;
    private Integer start;
    private Integer end;
    private String place;

    public ArrayList<Integer> getWeeks() {
        return weeks;
    }

    public void setWeeks(ArrayList<Integer> weeks) {
        this.weeks = weeks;
    }

    public Integer getWeekday() {
        return weekday;
    }

    public void setWeekday(Integer weekday) {
        this.weekday = weekday;
    }

    public Integer getStart() {
        return start;
    }

    public void setStart(Integer start) {
        this.start = start;
    }

    public Integer getEnd() {
        return end;
    }

    public void setEnd(Integer end) {
        this.end = end;
    }

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }
}
