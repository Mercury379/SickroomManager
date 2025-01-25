package com.hospital.hospitalsym.entity;

import com.hospital.hospitalsym.dao.DataProcess;

import javax.naming.Name;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;

public class TreatInfo {
    String pno;
    String dno;
    String name;
    String treattime;
    String sex;
    String age;
    String address;
    String phonenumber;
    String Case;
    String diagnosis;
    String treat;
    String tno;
    String status;
    String dname;
    String department;
    String title;
    String registermoney;
    public TreatInfo(String pno,String treattime,String Case,String diagnosis,String treat,String dno,String tno) {
        this.tno=tno;
        this.dno=dno;
        this.pno = pno;
        this.treattime=treattime;
        this.Case=Case;
        this.diagnosis=diagnosis;
        this.treat=treat;
        this.name= DataProcess.searchPatient(this.pno).getName();
    }
//    public TreatInfo(String pno,String name,String treattime,String sex,String age,String address,String phonenumber,String Case,String diagnosis,String treat,String dno,String tno) {
//        this.tno=tno;
//        this.dno=dno;
//        this.pno = pno;
//        this.age=age;
//        this.name=name;
//        this.sex=sex;
//        this.phonenumber=phonenumber;
//        this.address=address;
//        this.treattime=treattime;
//        this.Case=Case;
//        this.diagnosis=diagnosis;
//        this.treat=treat;
//    }


    public String getRegistermoney() {
        return registermoney;
    }

    public void setRegistermoney(String registermoney) {
        this.registermoney = registermoney;
    }

    public String getDname() {
        return dname;
    }

    public void setDname(String dname) {
        this.dname = dname;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getDno() {
        return dno;
    }

    public void setDno(String dno) {
        this.dno = dno;
    }

    public String getTno() {
        return tno;
    }

    public void setTno(String tno) {
        this.tno = tno;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getTreattime() {
        return treattime;
    }

    public void setTreattime(String treattime) {
        this.treattime = treattime;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPno() {
        return pno;
    }

    public void setPno(String pno) {
        this.pno = pno;
    }

    public String getPhonenumber() {
        return phonenumber;
    }

    public void setPhonenumber(String phonenumber) {
        this.phonenumber = phonenumber;
    }

    public String getCase() {
        return Case;
    }

    public void setCase(String aCase) {
        Case = aCase;
    }

    public String getDiagnosis() {
        return diagnosis;
    }

    public void setDiagnosis(String diagnosis) {
        this.diagnosis = diagnosis;
    }

    public String getTreat() {
        return treat;
    }

    public void setTreat(String treat) {
        this.treat = treat;
    }
    public LiveInfo LiveInfo(){
        ResultSet resultSet= DataProcess.searchPatientLiveInfo(this.tno);
        try {
            LiveInfo liveInfo=null;
            while(resultSet.next()) {
                String Lno = resultSet.getString("LNo");
                String Rno = resultSet.getString("RNo");
                String Bno = resultSet.getString("BNo");
                String intime = resultSet.getString("InTime");
                String outtime = resultSet.getString("OutTime");
                liveInfo = new LiveInfo(Lno, Rno, this.name, this.dno, this.dname, Rno, Bno, intime);
                liveInfo.setOutTime(outtime);
            }
            return liveInfo;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public String getNextLno(){
        return DataProcess.getNextLno();
    }
}
