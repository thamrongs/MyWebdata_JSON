package com.buu.se.s55160026.mywebdata_json;

import java.io.Serializable;

/**
 * Created by thamrongs on 2/10/15 AD.
 */
public class Grade  implements Serializable {
    private String subject = "";
    private String grade = "";

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getGrade() {
        return grade;
    }

    public void setGrade(String grade) {
        this.grade = grade;
    }

    @Override
    public String toString() {
        return this.subject + "     " + this.grade;
    }
}
