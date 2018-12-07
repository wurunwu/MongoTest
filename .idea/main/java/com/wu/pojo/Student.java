package com.wu.pojo;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Student {

    private Integer sid;
    private String sname;
    private String pwd;

    public Student() {
    }

    public Student(String sname, String pwd) {
        this.sname = sname;
        this.pwd = pwd;
    }

    public Student(Integer sid, String sname, String pwd) {
        this.sid = sid;
        this.sname = sname;
        this.pwd = pwd;
    }

    @Override
    public String toString() {
        return "Student{" +
                "sid=" + sid +
                ", sname='" + sname + '\'' +
                ", pwd='" + pwd + '\'' +
                '}';
    }
}
