package com.hospital.hospitalsym.service.impl;

import com.hospital.hospitalsym.dao.DataProcess;
import com.hospital.hospitalsym.entity.Doctor;
import com.hospital.hospitalsym.service.SystemAdminService;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class SystemAdminServiceImpl implements SystemAdminService {
    @Override
    public boolean loginAdmin(String id, String password) {
        if(id.equals("00000000")&&DataProcess.searchPassword(id,password)){
            return true;
        }else{
            return false;
        }
    }

    @Override
    public List<Doctor> listDoctor() {
        List<Doctor> list=new ArrayList<>();
        ResultSet resultSet=DataProcess.listDoctor();
        try {
            while (resultSet.next()) {
                String dno=resultSet.getString("DNo");
                String name=resultSet.getString("Name");
                String sex=resultSet.getString("Sex");
                int age=resultSet.getInt("Age");
                String title=resultSet.getString("Title");
                String phonenum=resultSet.getString("PhoneNum");
                String department=resultSet.getString("Department");
                if(!dno.equals("00000000")) {
                    Doctor doctor = new Doctor(dno, name, sex, age, title, phonenum, department);
                    list.add(doctor);
                }
            }
        }catch (SQLException e){
            throw new RuntimeException(e);
        }
        return list;
    }

    @Override
    public void updateDoctor(String dno, String name, String sex, String age, String title, String phonenum, String department) {
        DataProcess.changeDoctor(dno,name,sex,age,title,phonenum,department);
    }

    @Override
    public void deleteDoctor(String dno) {
        DataProcess.deleteDoctor(dno);
    }
    public void insertDoctor(String name,String sex,String age,String title,String phonenum,String department){
        DataProcess.insertDoctor(name,sex,age,title,phonenum,department);
    }

    @Override
    public List<Doctor> searchDoctor(String Name,String Department) {
        List<Doctor> list=new ArrayList<>();
        ResultSet resultSet=DataProcess.listDoctor();
        try {
            if(Name.equals("")&&Department.equals("")){
                return listDoctor();
            } else if (Name.equals("")&&!Department.equals("")) {
                while (resultSet.next()) {
                    String dno=resultSet.getString("DNo");
                    String name=resultSet.getString("Name");
                    String sex=resultSet.getString("Sex");
                    int age=resultSet.getInt("Age");
                    String title=resultSet.getString("Title");
                    String phonenum=resultSet.getString("PhoneNum");
                    String department=resultSet.getString("Department");
                    if(!dno.equals("00000000")&&department.equals(Department)) {
                        Doctor doctor = new Doctor(dno, name, sex, age, title, phonenum, department);
                        list.add(doctor);
                    }
                }
            } else if (!Name.equals("")&&Department.equals("")) {
                while (resultSet.next()) {
                    String dno=resultSet.getString("DNo");
                    String name=resultSet.getString("Name");
                    String sex=resultSet.getString("Sex");
                    int age=resultSet.getInt("Age");
                    String title=resultSet.getString("Title");
                    String phonenum=resultSet.getString("PhoneNum");
                    String department=resultSet.getString("Department");
                    if(!dno.equals("00000000")&&name.contains(Name)) {
                        Doctor doctor = new Doctor(dno, name, sex, age, title, phonenum, department);
                        list.add(doctor);
                    }
                }
            }else{
                while (resultSet.next()) {
                    String dno=resultSet.getString("DNo");
                    String name=resultSet.getString("Name");
                    String sex=resultSet.getString("Sex");
                    int age=resultSet.getInt("Age");
                    String title=resultSet.getString("Title");
                    String phonenum=resultSet.getString("PhoneNum");
                    String department=resultSet.getString("Department");
                    if(!dno.equals("00000000")&&name.contains(Name)&&department.equals(Department)) {
                        Doctor doctor = new Doctor(dno, name, sex, age, title, phonenum, department);
                        list.add(doctor);
                    }
                }
            }
        }catch (SQLException e){
            throw new RuntimeException(e);
        }
        return list;
    }

    @Override
    public List<Doctor> listLoginInfo() {
        ResultSet resultSet = DataProcess.listLoginInfo();
        List<Doctor> list = new ArrayList<>();
        try {
            while (resultSet.next()) {
                String dno = resultSet.getString("DNo");
                String name = resultSet.getString("Name");
                String sex = resultSet.getString("Sex");
                int age = resultSet.getInt("Age");
                String title = resultSet.getString("Title");
                String phonenum = resultSet.getString("PhoneNum");
                String department = resultSet.getString("Department");
                if (!dno.equals("00000000")) {
                    Doctor doctor = new Doctor(dno, name, sex, age, title, phonenum, department);
                    doctor.setPassword(resultSet.getString("password"));
                    list.add(doctor);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return list;
    }

    @Override
    public List<Doctor> searchLoginInfo(String Name, String Department) {
        ResultSet resultSet = DataProcess.listLoginInfo();
        List<Doctor> list = new ArrayList<>();
        try {
            if(Name.equals("")&&Department.equals("")){
                return listLoginInfo();
            } else if (Name.equals("")&&!Department.equals("")) {
                while (resultSet.next()) {
                    String dno = resultSet.getString("DNo");
                    String name = resultSet.getString("Name");
                    String sex = resultSet.getString("Sex");
                    int age = resultSet.getInt("Age");
                    String title = resultSet.getString("Title");
                    String phonenum = resultSet.getString("PhoneNum");
                    String department = resultSet.getString("Department");
                    if (!dno.equals("00000000")&&Department.equals(department)) {
                        Doctor doctor = new Doctor(dno, name, sex, age, title, phonenum, department);
                        doctor.setPassword(resultSet.getString("password"));
                        list.add(doctor);
                    }
                }
            }else if(!Name.equals("")&&Department.equals("")){
                while (resultSet.next()) {
                    String dno = resultSet.getString("DNo");
                    String name = resultSet.getString("Name");
                    String sex = resultSet.getString("Sex");
                    int age = resultSet.getInt("Age");
                    String title = resultSet.getString("Title");
                    String phonenum = resultSet.getString("PhoneNum");
                    String department = resultSet.getString("Department");
                    if (!dno.equals("00000000")&&name.contains(Name)) {
                        Doctor doctor = new Doctor(dno, name, sex, age, title, phonenum, department);
                        doctor.setPassword(resultSet.getString("password"));
                        list.add(doctor);
                    }
                }
            }else{
                while (resultSet.next()) {
                    String dno = resultSet.getString("DNo");
                    String name = resultSet.getString("Name");
                    String sex = resultSet.getString("Sex");
                    int age = resultSet.getInt("Age");
                    String title = resultSet.getString("Title");
                    String phonenum = resultSet.getString("PhoneNum");
                    String department = resultSet.getString("Department");
                    if (!dno.equals("00000000")&&name.contains(Name)&&Department.equals(department)) {
                        Doctor doctor = new Doctor(dno, name, sex, age, title, phonenum, department);
                        doctor.setPassword(resultSet.getString("password"));
                        list.add(doctor);
                    }
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return list;
    }
}
