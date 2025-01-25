package com.hospital.hospitalsym.service;

import com.hospital.hospitalsym.dto.SystemDoctorDto;
import com.hospital.hospitalsym.dto.SystemReceptionDto;
import com.hospital.hospitalsym.entity.BedInfo;
import com.hospital.hospitalsym.entity.LiveCert;
import com.hospital.hospitalsym.entity.TreatInfo;

import java.util.List;

public interface SystemReceptionService {
    public SystemReceptionDto loginReception(String patientid, String password);
    public List<BedInfo> listBlankBed();
    public List<BedInfo> searchBlankBed(String department,String rno,String time);
    public List<LiveCert> listLiveCertificate();
    public List<LiveCert> searchLiveCertificate(String lno,String time,String department);
    public List<LiveCert> listCanOut();
    public List<LiveCert> searchCanOut(String lno,String intime,String department);
    public List<BedInfo> listRoom();
    public List<BedInfo> searchRoom(String Department,String floor);
    public void roomOut(String lno);
    public List<BedInfo> searchRoom(String department);
    public List<BedInfo> searchBed(String rno);
    public void insertLive(String lno,String rno,String bno);
    public void updateRoom(String rno,String standard,String department,String bnum);
    public List<TreatInfo> listLiveRecord(String status);
    public List<TreatInfo> searchLiveRecord(String name,String status);
}
