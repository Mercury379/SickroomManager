package com.hospital.hospitalsym.dto;

import com.hospital.hospitalsym.entity.Doctor;

public class SystemReceptionDto {
    private String msg;
    private Doctor doctor;

    public Doctor getReception() {
        return doctor;
    }

    public void setReception(Doctor doctor) {
        this.doctor = doctor;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
