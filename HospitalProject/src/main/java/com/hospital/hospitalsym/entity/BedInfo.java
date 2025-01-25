package com.hospital.hospitalsym.entity;

public class BedInfo {
    String RNo;
    String BNo;
    String Standard;
    String Department;
    String Status;
    String BedNum;

    public BedInfo(String RNo, String BNo, String standard, String department,String status) {
        this.RNo = RNo;
        this.BNo = BNo;
        this.Standard = standard;
        this.Department = department;
        this.Status=status;
    }

    public String getBedNum() {
        return BedNum;
    }

    public void setBedNum(String bedNum) {
        BedNum = bedNum;
    }

    public String getRNo() {
        return RNo;
    }

    public void setRNo(String RNo) {
        this.RNo = RNo;
    }

    public String getBNo() {
        return BNo;
    }

    public void setBNo(String BNo) {
        this.BNo = BNo;
    }

    public String getStandard() {
        return Standard;
    }

    public void setStandard(String standard) {
        this.Standard = standard;
    }

    public String getDepartment() {
        return Department;
    }

    public void setDepartment(String department) {
        this.Department = department;
    }

    public String getStatus() {
        return Status;
    }

    public void setStatus(String status) {
        Status = status;
    }
}
