package com.buu.se.s55160026.mywebdata_json;

import java.io.Serializable;

/**
 * Created by thamrongs on 2/10/15 AD.
 */
public class Student implements Serializable {
    private int id;
    private String name;
    private String[] subject = null;
    private String[] grade = null;

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

    public String[] getSubject() {
        return subject;
    }

    public void setSubject(String[] subject) {
        this.subject = subject;
    }

    public String[] getGrade() {
        return grade;
    }

    public void setGrade(String[] grade) {
        this.grade = grade;
    }

    @Override
    public String toString() {
        return this.id + ": " + this.name;
    }

}