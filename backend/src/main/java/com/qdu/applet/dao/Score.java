package com.qdu.applet.dao;

public class Score implements Comparable<Score> {
    private String year;
    private String term;
    private String course;
    private String score;
    private String assess;
    private String credit;

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getTerm() {
        return term;
    }

    public void setTerm(String term) {
        this.term = term;
    }

    public String getCourse() {
        return course;
    }

    public void setCourse(String course) {
        this.course = course;
    }

    public String getScore() {
        return score;
    }

    public void setScore(String score) {
        this.score = score;
    }

    public String getAssess() {
        return assess;
    }

    public void setAssess(String assess) {
        this.assess = assess;
    }

    public String getCredit() {
        return credit;
    }

    public void setCredit(String credit) {
        this.credit = credit;
    }

    public int compareTo(Score score2) {
        int year1 = Integer.valueOf(this.year);
        int year2 = Integer.valueOf(score2.year);
        if (year1 < year2) {
            return 1;
        } else if (year1 < year2) {
            return 0;
        } else {
            return -1;
        }
    }
}
