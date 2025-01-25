package com.hospital.hospitalsym.service;

import com.hospital.hospitalsym.entity.Doctor;

import java.util.List;

public interface SystemAdminService {
    public boolean loginAdmin(String id, String password);
    public List<Doctor> listDoctor();
    public List<Doctor> searchDoctor(String name,String department);
    public void updateDoctor(String dno,String name,String sex,String age,String title,String phonenum,String department);
    public void deleteDoctor(String dno);
    public void insertDoctor(String name,String sex,String age,String title,String phonenum,String department);
    public List<Doctor> listLoginInfo();
    public List<Doctor> searchLoginInfo(String name,String department);
}
