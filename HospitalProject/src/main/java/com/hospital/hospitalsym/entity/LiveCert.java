package com.hospital.hospitalsym.entity;

import com.hospital.hospitalsym.dao.DataProcess;

import java.sql.ResultSet;
import java.sql.SQLException;

public class LiveCert {
    String lno;
    String dno;
    String pno;
    String time;
    String preintime;
    String tno;
    String preouttime;
    String canout;
    String dname;
    String department;
    String status;
    String intime;
    String name;
    String rno;
    String bno;

    public LiveCert(String lno, String dno, String pno, String time, String preintime, String tno) {
        this.lno = lno;
        this.dno = dno;
        this.pno = pno;
        this.time = time;
        this.preintime = preintime;
        this.tno = tno;
        this.name= DataProcess.searchPatient(this.pno).getName();
        ResultSet resultSet = DataProcess.searchLiveRecord(lno);
        try {
            while(resultSet.next()) {
                this.rno = resultSet.getString("RNo");
                this.bno = resultSet.getString("BNo");
            }
        }catch (SQLException e){
            throw new RuntimeException(e);
        }
    }

    public String getRno() {
        return rno;
    }

    public void setRno(String rno) {
        this.rno = rno;
    }

    public String getBno() {
        return bno;
    }

    public void setBno(String bno) {
        this.bno = bno;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
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

    public String getIntime() {
        return intime;
    }

    public void setIntime(String intime) {
        this.intime = intime;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getLno() {
        return lno;
    }

    public void setLno(String lno) {
        this.lno = lno;
    }

    public String getDno() {
        return dno;
    }

    public void setDno(String dno) {
        this.dno = dno;
    }

    public String getPno() {
        return pno;
    }

    public void setPno(String pno) {
        this.pno = pno;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getPreintime() {
        return preintime;
    }

    public void setPreintime(String preintime) {
        this.preintime = preintime;
    }

    public String getTno() {
        return tno;
    }

    public void setTno(String tno) {
        this.tno = tno;
    }

    public String getPreouttime() {
        return preouttime;
    }

    public void setPreouttime(String preoutime) {
        this.preouttime = preoutime;
    }

    public String getCanout() {
        return canout;
    }

    public void setCanout(String canout) {
        this.canout = canout;
    }
}
