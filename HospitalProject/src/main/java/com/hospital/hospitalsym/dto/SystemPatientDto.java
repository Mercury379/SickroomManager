package com.hospital.hospitalsym.dto;

import com.hospital.hospitalsym.entity.Patient;

public class SystemPatientDto {
    private String msg;
    private Patient patient;

    public Patient getPatient() {
        return patient;
    }

    public void setPatient(Patient patient) {
        this.patient = patient;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
