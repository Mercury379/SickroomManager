package com.hospital.hospitalsym.entity;

public class LiveInfo {
    String LNo;
    String PNo;
    String PName;
    String DNo;
    String DName;
    String Rno;
    String Bno;
    String InTime;
    String OutTime;

    public LiveInfo(String LNo,String PNo, String PName, String DNo, String DName, String rno, String bno, String inTime) {
        this.LNo=LNo;
        this.PNo = PNo;
        this.PName = PName;
        this.DNo = DNo;
        this.DName = DName;
        Rno = rno;
        Bno = bno;
        InTime = inTime;
    }

    public String getOutTime() {
        return OutTime;
    }

    public void setOutTime(String outTime) {
        OutTime = outTime;
    }

    public String getLNo() {
        return LNo;
    }

    public void setLNo(String LNo) {
        this.LNo = LNo;
    }

    public String getPNo() {
        return PNo;
    }

    public void setPNo(String PNo) {
        this.PNo = PNo;
    }

    public String getPName() {
        return PName;
    }

    public void setPName(String PName) {
        this.PName = PName;
    }

    public String getDNo() {
        return DNo;
    }

    public void setDNo(String DNo) {
        this.DNo = DNo;
    }

    public String getDName() {
        return DName;
    }

    public void setDName(String DName) {
        this.DName = DName;
    }

    public String getRno() {
        return Rno;
    }

    public void setRno(String rno) {
        Rno = rno;
    }

    public String getBno() {
        return Bno;
    }

    public void setBno(String bno) {
        Bno = bno;
    }

    public String getInTime() {
        return InTime;
    }

    public void setInTime(String inTime) {
        InTime = inTime;
    }
}
