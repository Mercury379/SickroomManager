package com.hospital.hospitalsym.service;

import com.hospital.hospitalsym.dto.SystemPatientDto;
import com.hospital.hospitalsym.entity.*;

import java.util.List;

public interface SystemPatientService {
    public SystemPatientDto loginPatient(String patientid);
    public boolean inseratPatient(String name,String sex,String age,String address,String phonenum);

    public List<TreatInfo> listTreatRecord();
    public List<TreatInfo> searchTreatRecord(String department,String starttime,String endtime);
    public List<PayInfo> listBill();
    public List<PayInfo> searchBill(String Status,String Rno);
    public List<LiveCert> listLiveCert();
    public List<LiveCert> searchLiveCert(String Status);
    public List<LiveCert> listCanOut();
    public List<LiveCert> searchCanOut(String Status);
    public List<TreatInfo> listRegister();
    public List<TreatInfo> searchRegister(String Status,String department);

    public void setPatient(Patient searchPatient);
    public void payInvoice(String ino);
    public void deleteRegister(String tno);
    public List<Doctor> listAllDepartment(String department);
    public void insertRegister(String dno);
    public void insertRegister(String dno,String time);
}
