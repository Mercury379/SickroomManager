package com.hospital.hospitalsym.dto;

import com.hospital.hospitalsym.entity.Doctor;
import com.hospital.hospitalsym.entity.Patient;

public class SystemDoctorDto {
    private String msg;
    private Doctor doctor;

    public Doctor getDoctor() {
        return doctor;
    }

    public void setDoctor(Doctor doctor) {
        this.doctor = doctor;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
