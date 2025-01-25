package com.hospital.hospitalsym.entity;

public class Patient {
    private String pno;
    private String name;
    private String sex;
    private int age;
    private String address;
    private String phonenum;
    public Patient(String pno, String name, String sex, int age, String address, String phonenum){
        this.pno=pno;
        this.name=name;
        this.sex=sex;
        this.age=age;
        this.address=address;
        this.phonenum=phonenum;
    }

    public String getPno() {
        return pno;
    }

    public void setPno(String pno) {
        this.pno = pno;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhonenum() {
        return phonenum;
    }

    public void setPhonenum(String phonenum) {
        this.phonenum = phonenum;
    }
}
