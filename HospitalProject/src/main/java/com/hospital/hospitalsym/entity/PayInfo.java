package com.hospital.hospitalsym.entity;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class PayInfo {
    String ino;
    String money;
    String status;
    String paytime;
    String time;
    String lno;
    String rno;
    String bno;
    String intime;
    String outtime;
    String days;
    String name;
    String standard;
    String pno;

    public PayInfo(String ino, String money, String status, String paytime, String time, String lno) {
        this.ino = ino;
        this.money = money;
        //this.status = status;
        if(status.equals("0")){
            this.status="未支付";
        }else {
            this.status="已支付";
        }
        this.paytime = paytime;
        this.time = time;
        this.lno = lno;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDays() {
        return days;
    }

    public String getStandard() {
        return standard;
    }

    public void setStandard(String standard) {
        this.standard = standard;
    }

    public String getPno() {
        return pno;
    }

    public void setPno(String pno) {
        this.pno = pno;
    }

    public void setDays() {
        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date Outtime=dateFormat.parse(outtime);
            Date Intime=dateFormat.parse(intime);
            this.days = String.valueOf((Outtime.getTime()-Intime.getTime())/(24 * 60 * 60 * 1000)+1);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }

    public String getIno() {
        return ino;
    }

    public void setIno(String ino) {
        this.ino = ino;
    }

    public String getMoney() {
        return money;
    }

    public void setMoney(String money) {
        this.money = money;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getPaytime() {
        return paytime;
    }

    public void setPaytime(String paytime) {
        this.paytime = paytime;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getLno() {
        return lno;
    }

    public void setLno(String lno) {
        this.lno = lno;
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

    public String getIntime() {
        return intime;
    }

    public void setIntime(String intime) {
        this.intime = intime;
    }

    public String getOuttime() {
        return outtime;
    }

    public void setOuttime(String outtime) {
        this.outtime = outtime;
    }
}
