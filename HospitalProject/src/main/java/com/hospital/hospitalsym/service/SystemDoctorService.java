package com.hospital.hospitalsym.service;

import com.hospital.hospitalsym.dto.SystemDoctorDto;
import com.hospital.hospitalsym.dto.SystemPatientDto;
import com.hospital.hospitalsym.entity.BedInfo;
import com.hospital.hospitalsym.entity.LiveInfo;
import com.hospital.hospitalsym.entity.TreatInfo;

import java.sql.Timestamp;
import java.util.List;

public interface SystemDoctorService {
    public SystemDoctorDto loginDoctor(String patientid, String password);
    public List<TreatInfo> listPatient(String status);
    public List<TreatInfo> searchPatient(String patientid,String treatdate,String status);
    public List<BedInfo> listBlankBed();
    public List<BedInfo> searchBlankBed(String time,String rno);
    public List<TreatInfo> listNotCertPatient();
    public List<TreatInfo> searchNotCertPatient(String name);
    public List<LiveInfo> listLiveCanOut();
    public List<LiveInfo> searchLiveCanOut(String name,String id);
    public List<TreatInfo> listTreatPatient();
    public List<TreatInfo> searchTreatPatient(String name,String id);
    public boolean insertLiveCertificate(String preintime,String tno);
    public boolean insertCanOut(String lno,String preouttime);
    public boolean updateTreatRecord(String tno,String Case,String diagosis,String treat);
}
