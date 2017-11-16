package com.jisort.ttsiva;

/**
 * Created by jjsikini on 11/16/17.
 */

public class DataClass {

    public String getStart_time() {
        return start_time;
    }

    public void setStart_time(String start_time) {
        this.start_time = start_time;
    }

    public String getEnd_time() {
        return end_time;
    }

    public void setEnd_time(String end_time) {
        this.end_time = end_time;
    }

    public String getUnit_name() {
        return unit_name;
    }

    public void setUnit_name(String unit_name) {
        this.unit_name = unit_name;
    }

    public String getLecturer_name() {
        return lecturer_name;
    }

    public void setLecturer_name(String lecturer_name) {
        this.lecturer_name = lecturer_name;
    }

    public String getRoomnumber() {
        return roomnumber;
    }

    public void setRoomnumber(String roomnumber) {
        this.roomnumber = roomnumber;
    }

    public String getCourse() {
        return course;
    }

    public void setCourse(String course) {
        this.course = course;
    }

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public String roomnumber;
    public String course;
    public String start_time;
    public String end_time;
    public String unit_name;
    public String lecturer_name;




    public String day;
    public DataClass(String roomnumber, String course, String start_time, String end_time, String unit_name, String lecturer_name,String day)
    {
        this.roomnumber = roomnumber;
        this.course = course;
        this.start_time = start_time;
        this.end_time = end_time;
        this.unit_name = unit_name;
        this.day = day;
        this.lecturer_name = lecturer_name;
    }



}
