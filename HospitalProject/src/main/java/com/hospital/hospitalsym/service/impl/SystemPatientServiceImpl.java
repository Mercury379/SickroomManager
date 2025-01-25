package com.hospital.hospitalsym.service.impl;

import com.hospital.hospitalsym.StartDB;
import com.hospital.hospitalsym.dto.SystemPatientDto;
import com.hospital.hospitalsym.entity.*;
import com.hospital.hospitalsym.service.SystemPatientService;
import com.hospital.hospitalsym.dao.*;

import java.awt.*;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class SystemPatientServiceImpl implements SystemPatientService {
    static Patient patient;
    @Override
    public SystemPatientDto loginPatient(String patientid) {
        SystemPatientDto systemPatientDto=new SystemPatientDto();
        StartDB startDB=new StartDB();
        Patient patient=DataProcess.searchPatient(patientid);
        if(patient==null){
            systemPatientDto.setMsg("病案号不存在");
        }else{
            systemPatientDto.setMsg("登陆成功");
            this.patient=patient;
            systemPatientDto.setPatient(patient);
        }
        return systemPatientDto;
    }
    public void setPatient(Patient patient1){
        patient=patient1;
    }

    @Override
    public List<TreatInfo> listTreatRecord() {
        ResultSet resultSet=DataProcess.searchTreatRecord(patient.getPno());
        List<TreatInfo> list=new ArrayList<>();
        try{
            while(resultSet.next()) {
                String pno = resultSet.getString("PNo");
                String dno = resultSet.getString("DNo");
                String time = resultSet.getString("Time");
                String Case = resultSet.getString("Case");
                String diagnosis = resultSet.getString("Diagnosis");
                String treat = resultSet.getString("Treat");
                String tno = resultSet.getString("TNo");
                TreatInfo treatInfo = new TreatInfo(pno, time, Case, diagnosis, treat, dno, tno);
                Doctor doctor=DataProcess.searchDoctor(dno);
                treatInfo.setDname(doctor.getName());
                treatInfo.setDepartment(doctor.getDepartment());
                treatInfo.setTitle(doctor.getTitle());
                list.add(treatInfo);
            }
        }catch (SQLException e){
            throw new RuntimeException(e);
        }
        return list;
    }
    @Override
    public List<TreatInfo> searchTreatRecord(String department, String starttime, String endtime) {
        ResultSet resultSet=DataProcess.searchTreatRecord(patient.getPno());
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        List<TreatInfo> list=new ArrayList<>();
        try {
            if (starttime.equals("") && department.equals("")) {
                return listTreatRecord();
            } else if (!department.equals("")&&starttime.equals("")) {
                while (resultSet.next()) {
                    String pno = resultSet.getString("PNo");
                    String dno = resultSet.getString("DNo");
                    String time = resultSet.getString("Time");
                    String Case = resultSet.getString("Case");
                    String diagnosis = resultSet.getString("Diagnosis");
                    String treat = resultSet.getString("Treat");
                    String tno = resultSet.getString("TNo");
                    TreatInfo treatInfo = new TreatInfo(pno, time, Case, diagnosis, treat, dno, tno);
                    Doctor doctor = DataProcess.searchDoctor(dno);
                    treatInfo.setDname(doctor.getName());
                    treatInfo.setDepartment(doctor.getDepartment());
                    treatInfo.setTitle(doctor.getTitle());
                    if(doctor.getDepartment().equals(department)){
                        list.add(treatInfo);
                    }
                }
            } else if (!starttime.equals("")&&department.equals("")) {
                Date date1 = dateFormat.parse(starttime);
                Date date2=dateFormat.parse(endtime);
                while (resultSet.next()) {
                    String pno = resultSet.getString("PNo");
                    String dno = resultSet.getString("DNo");
                    String time = resultSet.getString("Time");
                    long time1=resultSet.getTimestamp("Time").getTime();
                    String Case = resultSet.getString("Case");
                    String diagnosis = resultSet.getString("Diagnosis");
                    String treat = resultSet.getString("Treat");
                    String tno = resultSet.getString("TNo");
                    TreatInfo treatInfo = new TreatInfo(pno, time, Case, diagnosis, treat, dno, tno);
                    Doctor doctor = DataProcess.searchDoctor(dno);
                    treatInfo.setDname(doctor.getName());
                    treatInfo.setDepartment(doctor.getDepartment());
                    treatInfo.setTitle(doctor.getTitle());
                    if(date1.getTime()<time1&&time1<date2.getTime()){
                        list.add(treatInfo);
                    }
                }
            }else{
                Date date1 = dateFormat.parse(starttime);
                Date date2=dateFormat.parse(endtime);
                while (resultSet.next()) {
                    String pno = resultSet.getString("PNo");
                    String dno = resultSet.getString("DNo");
                    String time = resultSet.getString("Time");
                    long time1=resultSet.getTimestamp("Time").getTime();
                    String Case = resultSet.getString("Case");
                    String diagnosis = resultSet.getString("Diagnosis");
                    String treat = resultSet.getString("Treat");
                    String tno = resultSet.getString("TNo");
                    TreatInfo treatInfo = new TreatInfo(pno, time, Case, diagnosis, treat, dno, tno);
                    Doctor doctor = DataProcess.searchDoctor(dno);
                    treatInfo.setDname(doctor.getName());
                    treatInfo.setDepartment(doctor.getDepartment());
                    treatInfo.setTitle(doctor.getTitle());
                    if(date1.getTime()<time1&&time1<date2.getTime()&&doctor.getDepartment().equals(department)){
                        list.add(treatInfo);
                    }
                }
            }
        }catch (SQLException e){
            throw new RuntimeException(e);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
        return list;
    }
    @Override
    public List<PayInfo> listBill() {
        ResultSet resultSet=DataProcess.searchBill(patient.getPno());
        List<PayInfo> list=new ArrayList<>();
        try {
            while (resultSet.next()) {
                String ino=resultSet.getString("INo");
                String money=resultSet.getString("Money");
                String paytime=resultSet.getString("PayTime");
                String time=resultSet.getString("Time");
                String paystatus=resultSet.getString("Status");
                String lno=resultSet.getString("LNo");
                PayInfo payInfo=new PayInfo(ino,money,paystatus,paytime,time,lno);
                payInfo.setRno(resultSet.getString("RNo"));
                payInfo.setBno(resultSet.getString("BNo"));
                payInfo.setIntime(resultSet.getString("InTime"));
                payInfo.setOuttime(resultSet.getString("OutTime"));
                payInfo.setName(patient.getName());
                payInfo.setPno(patient.getPno());
                payInfo.setStandard(resultSet.getString("Standard"));
                payInfo.setDays();
                list.add(payInfo);
            }
        }catch (SQLException e){
            throw new RuntimeException(e);
        }
        return list;
    }

    @Override
    public List<PayInfo> searchBill(String Status, String Rno) {
        ResultSet resultSet=DataProcess.searchBill(patient.getPno());
        List<PayInfo> list=new ArrayList<>();
        try {
            if (Status.equals("") && Rno.equals("")) {
                return listBill();
            } else if (Status.equals("") && !Rno.equals("")) {
                while (resultSet.next()) {
                    String ino = resultSet.getString("INo");
                    String money = resultSet.getString("Money");
                    String paytime = resultSet.getString("PayTime");
                    String time = resultSet.getString("Time");
                    String paystatus = resultSet.getString("Status");
                    String lno = resultSet.getString("LNo");
                    PayInfo payInfo = new PayInfo(ino, money, paystatus, paytime, time, lno);
                    payInfo.setRno(resultSet.getString("RNo"));
                    payInfo.setBno(resultSet.getString("BNo"));
                    payInfo.setIntime(resultSet.getString("InTime"));
                    payInfo.setOuttime(resultSet.getString("OutTime"));
                    payInfo.setDays();
                    payInfo.setName(patient.getName());
                    payInfo.setPno(patient.getPno());
                    payInfo.setStandard(resultSet.getString("Standard"));
                    if (payInfo.getRno().equals(Rno)) {
                        list.add(payInfo);
                    }
                }
            } else if (!Status.equals("") && Rno.equals("")) {
                while (resultSet.next()) {
                    String ino = resultSet.getString("INo");
                    String money = resultSet.getString("Money");
                    String paytime = resultSet.getString("PayTime");
                    String time = resultSet.getString("Time");
                    String paystatus = resultSet.getString("Status");
                    String lno = resultSet.getString("LNo");
                    PayInfo payInfo = new PayInfo(ino, money, paystatus, paytime, time, lno);
                    payInfo.setRno(resultSet.getString("RNo"));
                    payInfo.setBno(resultSet.getString("BNo"));
                    payInfo.setIntime(resultSet.getString("InTime"));
                    payInfo.setOuttime(resultSet.getString("OutTime"));
                    payInfo.setDays();
                    payInfo.setName(patient.getName());
                    payInfo.setPno(patient.getPno());
                    payInfo.setStandard(resultSet.getString("Standard"));
                    if (payInfo.getStatus().equals(Status)) {
                        list.add(payInfo);
                    }
                }
            }else{
                while (resultSet.next()) {
                    String ino = resultSet.getString("INo");
                    String money = resultSet.getString("Money");
                    String paytime = resultSet.getString("PayTime");
                    String time = resultSet.getString("Time");
                    String paystatus = resultSet.getString("Status");
                    String lno = resultSet.getString("LNo");
                    PayInfo payInfo = new PayInfo(ino, money, paystatus, paytime, time, lno);
                    payInfo.setRno(resultSet.getString("RNo"));
                    payInfo.setBno(resultSet.getString("BNo"));
                    payInfo.setIntime(resultSet.getString("InTime"));
                    payInfo.setOuttime(resultSet.getString("OutTime"));
                    payInfo.setDays();
                    payInfo.setName(patient.getName());
                    payInfo.setPno(patient.getPno());
                    payInfo.setStandard(resultSet.getString("Standard"));
                    if (payInfo.getStatus().equals(Status)&&payInfo.getRno().equals(Rno)) {
                        list.add(payInfo);
                    }
                }
            }
        }catch (SQLException e){
            throw new RuntimeException(e);
        }
        return list;
    }

    @Override
    public List<LiveCert> listLiveCert() {
        ResultSet resultSet=DataProcess.searchLiveCertificateNotLive();
        List<LiveCert> list=new ArrayList<>();
        try{
            while (resultSet.next()){
                String lno=resultSet.getString("LNo");
                String dno=resultSet.getString("DNo");
                String pno=resultSet.getString("PNo");
                String pretime=resultSet.getString("PreInTime");
                String time=resultSet.getString("Time");
                String tno=resultSet.getString("TNo");
                if(pno.equals(patient.getPno())){
                    LiveCert liveCert=new LiveCert(lno,dno,pno,time,pretime,tno);
                    liveCert.setStatus("未使用");
                    Doctor doctor=DataProcess.searchDoctor(dno);
                    liveCert.setDname(doctor.getName());
                    liveCert.setDepartment(doctor.getDepartment());
                    list.add(liveCert);
                }
            }
            ResultSet resultSet1=DataProcess.searchLiveCertificate();
            while (resultSet1.next()){
                String lno=resultSet1.getString("LNo");
                String dno=resultSet1.getString("DNo");
                String pno=resultSet1.getString("PNo");
                String pretime=resultSet1.getString("PreInTime");
                String time=resultSet1.getString("Time");
                String tno=resultSet1.getString("TNo");
                if(pno.equals(patient.getPno())){
                    LiveCert liveCert=new LiveCert(lno,dno,pno,time,pretime,tno);
                    liveCert.setStatus("已使用");
                    Doctor doctor=DataProcess.searchDoctor(dno);
                    liveCert.setDname(doctor.getName());
                    liveCert.setDepartment(doctor.getDepartment());
                    list.add(liveCert);
                }
            }
        }catch (SQLException e){
            throw new RuntimeException(e);
        }
        return list;
    }

    @Override
    public List<LiveCert> searchLiveCert(String Status) {
        List<LiveCert> list=new ArrayList<>();
        try {
            if (Status.equals("")) {
                return listLiveCert();
            } else {
                if (Status.equals("未使用")) {
                    ResultSet resultSet=DataProcess.searchLiveCertificateNotLive();
                    while (resultSet.next()) {
                        String lno = resultSet.getString("LNo");
                        String dno = resultSet.getString("DNo");
                        String pno = resultSet.getString("PNo");
                        String pretime = resultSet.getString("PreInTime");
                        String time = resultSet.getString("Time");
                        String tno = resultSet.getString("TNo");
                        if (pno.equals(patient.getPno())) {
                            LiveCert liveCert = new LiveCert(lno, dno, pno, time, pretime, tno);
                            liveCert.setStatus("未使用");
                            Doctor doctor = DataProcess.searchDoctor(dno);
                            liveCert.setDname(doctor.getName());
                            liveCert.setDepartment(doctor.getDepartment());
                            list.add(liveCert);
                        }
                    }
                } else {
                    ResultSet resultSet1=DataProcess.searchLiveCertificate();
                    while (resultSet1.next()){
                        String lno=resultSet1.getString("LNo");
                        String dno=resultSet1.getString("DNo");
                        String pno=resultSet1.getString("PNo");
                        String pretime=resultSet1.getString("PreInTime");
                        String time=resultSet1.getString("Time");
                        String tno=resultSet1.getString("TNo");
                        if(pno.equals(patient.getPno())){
                            LiveCert liveCert=new LiveCert(lno,dno,pno,time,pretime,tno);
                            liveCert.setStatus("已使用");
                            Doctor doctor=DataProcess.searchDoctor(dno);
                            liveCert.setDname(doctor.getName());
                            liveCert.setDepartment(doctor.getDepartment());
                            list.add(liveCert);
                        }
                    }
                }
            }
        }catch (SQLException e){
            throw new RuntimeException(e);
        }
        return list;
    }

    @Override
    public List<LiveCert> listCanOut() {
        ResultSet resultSet=DataProcess.searchLiveInfomation("1");
        List<LiveCert> list=new ArrayList<>();
        try {
            while (resultSet.next()) {
                String lno=resultSet.getString("LNo");
                String dno=resultSet.getString("DNo");
                String pno=resultSet.getString("PNo");
                String pretime=resultSet.getString("PreInTime");
                String time=resultSet.getString("Time");
                String tno=resultSet.getString("TNo");
                String outtime=resultSet.getString("OutTime");
                if(pno.equals(patient.getPno()) && outtime == null){
                    LiveCert liveCert=new LiveCert(lno,dno,pno,time,pretime,tno);
                    liveCert.setStatus("未使用");
                    Doctor doctor=DataProcess.searchDoctor(dno);
                    liveCert.setDname(doctor.getName());
                    liveCert.setDepartment(doctor.getDepartment());
                    String preouttime=resultSet.getString("PreOutTime");
                    String intime=resultSet.getString("InTime");
                    liveCert.setIntime(intime);
                    liveCert.setPreouttime(preouttime);
                    list.add(liveCert);
                } else if (pno.equals(patient.getPno())) {
                    LiveCert liveCert=new LiveCert(lno,dno,pno,time,pretime,tno);
                    liveCert.setStatus("已使用");
                    Doctor doctor=DataProcess.searchDoctor(dno);
                    liveCert.setDname(doctor.getName());
                    liveCert.setDepartment(doctor.getDepartment());
                    String preouttime=resultSet.getString("PreOutTime");
                    String intime=resultSet.getString("InTime");
                    liveCert.setIntime(intime);
                    liveCert.setPreouttime(preouttime);
                    list.add(liveCert);
                }
            }
        }catch (SQLException e){
            throw new RuntimeException(e);
        }
        return list;
    }

    @Override
    public List<LiveCert> searchCanOut(String Status) {
        ResultSet resultSet=DataProcess.searchLiveInfomation("1");
        List<LiveCert> list=new ArrayList<>();
        try {
            if(Status.equals("")){
                return listCanOut();
            } else if (Status.equals("未使用")) {
                while (resultSet.next()) {
                    String lno=resultSet.getString("LNo");
                    String dno=resultSet.getString("DNo");
                    String pno=resultSet.getString("PNo");
                    String pretime=resultSet.getString("PreInTime");
                    String time=resultSet.getString("Time");
                    String tno=resultSet.getString("TNo");
                    String outtime=resultSet.getString("OutTime");
                    if(pno.equals(patient.getPno()) && outtime == null){
                        LiveCert liveCert=new LiveCert(lno,dno,pno,time,pretime,tno);
                        liveCert.setStatus("未使用");
                        Doctor doctor=DataProcess.searchDoctor(dno);
                        liveCert.setDname(doctor.getName());
                        liveCert.setDepartment(doctor.getDepartment());
                        String preouttime=resultSet.getString("PreOutTime");
                        String intime=resultSet.getString("InTime");
                        liveCert.setIntime(intime);
                        liveCert.setPreouttime(preouttime);
                        list.add(liveCert);
                    }
                }
            }else {
                while (resultSet.next()) {
                    String lno=resultSet.getString("LNo");
                    String dno=resultSet.getString("DNo");
                    String pno=resultSet.getString("PNo");
                    String pretime=resultSet.getString("PreInTime");
                    String time=resultSet.getString("Time");
                    String tno=resultSet.getString("TNo");
                    String outtime=resultSet.getString("OutTime");
                    if(pno.equals(patient.getPno()) && outtime != null){
                        LiveCert liveCert=new LiveCert(lno,dno,pno,time,pretime,tno);
                        liveCert.setStatus("已使用");
                        Doctor doctor=DataProcess.searchDoctor(dno);
                        liveCert.setDname(doctor.getName());
                        liveCert.setDepartment(doctor.getDepartment());
                        String preouttime=resultSet.getString("PreOutTime");
                        String intime=resultSet.getString("InTime");
                        liveCert.setIntime(intime);
                        liveCert.setPreouttime(preouttime);
                        list.add(liveCert);
                    }
                }
            }
        }catch (SQLException e){
            throw new RuntimeException(e);
        }
        return list;
    }

    @Override
    public List<TreatInfo> listRegister() {
        ResultSet resultSet=DataProcess.searchRegister(patient.getPno());
        List<TreatInfo> list=new ArrayList<>();
        try {
            while (resultSet.next()) {
                String dno=resultSet.getString("DNo");
                String treattime=resultSet.getString("Time");
                String Case=resultSet.getString("case");
                String diagnosis=resultSet.getString("diagnosis");
                String treat= resultSet.getString("treat");
                String tno=resultSet.getString("TNo");
                TreatInfo treatInfo=new TreatInfo(patient.getPno(), treattime,Case,diagnosis,treat,dno,tno);
                Doctor doctor=DataProcess.searchDoctor(dno);
                treatInfo.setDname(doctor.getName());
                treatInfo.setDepartment(doctor.getDepartment());
                treatInfo.setRegistermoney("10");
                if(Case==null) {
                    treatInfo.setStatus("未就诊");
                }else {
                    treatInfo.setStatus("已就诊");
                }
                list.add(treatInfo);
            }
        }catch (SQLException e){
            throw new RuntimeException(e);
        }
        return list;
    }

    @Override
    public List<TreatInfo> searchRegister(String Status, String department) {
        ResultSet resultSet=DataProcess.searchRegister(patient.getPno());
        List<TreatInfo> list=new ArrayList<>();
        try {
            if (Status.equals("") && department.equals("")) {
                return listRegister();
            } else if (!Status.equals("") && department.equals("")) {
                while (resultSet.next()) {
                    String dno = resultSet.getString("DNo");
                    String treattime = resultSet.getString("Time");
                    String Case = resultSet.getString("case");
                    String diagnosis = resultSet.getString("diagnosis");
                    String treat = resultSet.getString("treat");
                    String tno = resultSet.getString("TNo");
                    TreatInfo treatInfo = new TreatInfo(patient.getPno(), treattime, Case, diagnosis, treat, dno, tno);
                    Doctor doctor = DataProcess.searchDoctor(dno);
                    treatInfo.setDname(doctor.getName());
                    treatInfo.setDepartment(doctor.getDepartment());
                    treatInfo.setRegistermoney("10");
                    if (Case == null) {
                        treatInfo.setStatus("未就诊");
                    } else {
                        treatInfo.setStatus("已就诊");
                    }
                    if (treatInfo.getStatus().equals(Status)) {
                        list.add(treatInfo);
                    }
                }
            } else if (Status.equals("") && !department.equals("")) {
                while (resultSet.next()) {
                    String dno = resultSet.getString("DNo");
                    String treattime = resultSet.getString("Time");
                    String Case = resultSet.getString("case");
                    String diagnosis = resultSet.getString("diagnosis");
                    String treat = resultSet.getString("treat");
                    String tno = resultSet.getString("TNo");
                    TreatInfo treatInfo = new TreatInfo(patient.getPno(), treattime, Case, diagnosis, treat, dno, tno);
                    Doctor doctor = DataProcess.searchDoctor(dno);
                    treatInfo.setDname(doctor.getName());
                    treatInfo.setDepartment(doctor.getDepartment());
                    treatInfo.setRegistermoney("10");
                    if (Case == null) {
                        treatInfo.setStatus("未就诊");
                    } else {
                        treatInfo.setStatus("已就诊");
                    }
                    if (treatInfo.getDepartment().equals(department)) {
                        list.add(treatInfo);
                    }
                }
            } else {
                while (resultSet.next()) {
                    String dno = resultSet.getString("DNo");
                    String treattime = resultSet.getString("Time");
                    String Case = resultSet.getString("case");
                    String diagnosis = resultSet.getString("diagnosis");
                    String treat = resultSet.getString("treat");
                    String tno = resultSet.getString("TNo");
                    TreatInfo treatInfo = new TreatInfo(patient.getPno(), treattime, Case, diagnosis, treat, dno, tno);
                    Doctor doctor = DataProcess.searchDoctor(dno);
                    treatInfo.setDname(doctor.getName());
                    treatInfo.setDepartment(doctor.getDepartment());
                    treatInfo.setRegistermoney("10");
                    if (Case == null) {
                        treatInfo.setStatus("未就诊");
                    } else {
                        treatInfo.setStatus("已就诊");
                    }
                    if (treatInfo.getStatus().equals(Status) && treatInfo.getDepartment().equals(department)) {
                        list.add(treatInfo);
                    }
                }
            }
        }catch (SQLException e){
            throw new RuntimeException(e);
        }
        return list;
    }

    @Override
    public boolean inseratPatient(String name, String sex, String age, String address, String phonenum) {
        return DataProcess.newPatient(name,sex,Integer.valueOf(age),address,phonenum);
    }

    @Override
    public void payInvoice(String ino) {
        DataProcess.changePayStatus(ino,"1");
    }

    @Override
    public void deleteRegister(String tno) {
        DataProcess.deleteRegister(tno);
    }

    @Override
    public List<Doctor> listAllDepartment(String department) {
        ResultSet resultSet=DataProcess.listAllDepartment(department);
        List<Doctor> list=new ArrayList<>();
        try {
            while (resultSet.next()) {
                list.add(new Doctor(resultSet.getString("DNo"),resultSet.getString("Name"),"",0,"","",department));
            }
        }catch (SQLException e){
            throw new RuntimeException(e);
        }
        return list;
    }

    @Override
    public void insertRegister(String dno) {
        DataProcess.insertTreatRecord(dno,patient.getPno());
    }

    @Override
    public void insertRegister(String dno, String time) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date= null;
        try {
            date = dateFormat.parse(time);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
        DataProcess.insertTreatRecord(dno,patient.getPno(),new Timestamp(date.getTime()));
    }
}
