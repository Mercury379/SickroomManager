package com.hospital.hospitalsym.service.impl;

import com.hospital.hospitalsym.StartDB;
import com.hospital.hospitalsym.dao.DataProcess;
import com.hospital.hospitalsym.dto.SystemDoctorDto;
import com.hospital.hospitalsym.entity.*;
import com.hospital.hospitalsym.service.SystemDoctorService;

import java.sql.Timestamp;
import java.text.*;

import java.sql.SQLException;
import java.time.temporal.Temporal;
import java.util.*;

import java.sql.ResultSet;

public class SystemDoctorServiceImpl implements SystemDoctorService {
    static Doctor doctor;
    public SystemDoctorDto loginDoctor(String doctorid, String password) {
        SystemDoctorDto systemDoctorDto=new SystemDoctorDto();
        StartDB startDB=new StartDB();
        if(DataProcess.searchDoctorLogin(doctorid)&&DataProcess.searchPassword(doctorid,password)){
            systemDoctorDto.setMsg("登陆成功");
            doctor=DataProcess.searchDoctor(doctorid);
            systemDoctorDto.setDoctor(doctor);
        }else if(!DataProcess.searchDoctorLogin(doctorid)){
            systemDoctorDto.setMsg("医生不存在");
            doctor=null;
        }else {
            systemDoctorDto.setMsg("密码输入错误");
            doctor=null;
        }
        return systemDoctorDto;
    }
    public List<TreatInfo> listPatient(String status){
        ResultSet resultSet=null;
        List<TreatInfo> list=new ArrayList<>();
        if(doctor!=null) {
            resultSet = DataProcess.searchDoctorTreatRecord(doctor.getDno());
            try{
                while(resultSet.next()){
                    String pno=resultSet.getString("PNo");
                    String treattime=resultSet.getString("Time");
                    String Case=resultSet.getString("case");
                    String diagnosis=resultSet.getString("diagnosis");
                    String treat= resultSet.getString("treat");
                    String tno=resultSet.getString("TNo");
                    TreatInfo treatInfo=new TreatInfo(pno,treattime,Case,diagnosis,treat,doctor.getDno(),tno);
                    treatInfo.setName(resultSet.getString("Name"));
                    treatInfo.setSex(resultSet.getString("PSex"));
                    treatInfo.setAge(resultSet.getString("PAge"));
                    treatInfo.setAddress(resultSet.getString("Address"));
                    treatInfo.setPhonenumber(resultSet.getString("Pphonenum"));
                    String temp=LiveStatus(tno);
                    if(temp.equals(status) || status.equals("")) {
                        treatInfo.setStatus(temp);
                        list.add(treatInfo);
                    }
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
        return list;
    }
    public String LiveStatus(String tno){
        ResultSet resultSet1=DataProcess.listDoctorLiveCertificate(doctor.getDno());
        String status="无需住院";
        try {
            while (resultSet1.next()) {
                String Tno=resultSet1.getString("TNo");
                String preouttime=resultSet1.getString("PreOutTime");
                String canout=resultSet1.getString("CanOut");
                String intime=resultSet1.getString("InTime");
                String outtime=resultSet1.getString("OutTime");
                if(tno.equals(Tno)){
                    if(intime==null){
                        status="还未住院";
                    } else if (preouttime==null) {
                        status="正在住院";
                    }else if(outtime==null){
                        status="还未出院";
                    }else{
                        status="已经出院";
                    }
                    break;
                }
            }
        }catch (SQLException e){
            throw new RuntimeException(e);
        }
        return status;
    }
    @Override
    public List<TreatInfo> searchPatient(String patientid,String treatdate,String status) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        List<TreatInfo> list=new ArrayList<>();
        try {
            if(treatdate.equals("")&&patientid.equals("")){
                return listPatient(status);
            }else if(!treatdate.equals("")&&patientid.equals("")){
                Date date = dateFormat.parse(treatdate);
                Timestamp time = new Timestamp(date.getTime());
                ResultSet resultSet=DataProcess.searchTimeTreat(time,doctor.getDno());
                while(resultSet.next()){
                    String pno=resultSet.getString("PNo");
                    String treattime=resultSet.getString("Time");
                    String Case=resultSet.getString("case");
                    String diagnosis=resultSet.getString("diagnosis");
                    String treat= resultSet.getString("treat");
                    String tno=resultSet.getString("TNo");
                    TreatInfo treatInfo=new TreatInfo(pno,treattime,Case,diagnosis,treat,doctor.getDno(),tno);
                    treatInfo.setName(resultSet.getString("Name"));
                    treatInfo.setSex(resultSet.getString("PSex"));
                    treatInfo.setAge(resultSet.getString("PAge"));
                    treatInfo.setAddress(resultSet.getString("Address"));
                    treatInfo.setPhonenumber(resultSet.getString("Pphonenum"));
                    if(Case!=null) {
                        String temp=LiveStatus(tno);
                        if(temp.equals(status) || status.equals("")) {
                            treatInfo.setStatus(temp);
                            list.add(treatInfo);
                        }
                    }
                }
                return list;
            }else if(treatdate.equals("")&&!patientid.equals("")){
                ResultSet resultSet=DataProcess.searchPatientTreat(patientid,doctor.getDno());
                while(resultSet.next()){
                    String pno=resultSet.getString("PNo");
                    String treattime=resultSet.getString("Time");
                    String Case=resultSet.getString("case");
                    String diagnosis=resultSet.getString("diagnosis");
                    String treat= resultSet.getString("treat");
                    String tno=resultSet.getString("TNo");
                    TreatInfo treatInfo=new TreatInfo(pno,treattime,Case,diagnosis,treat,doctor.getDno(),tno);
                    treatInfo.setName(resultSet.getString("Name"));
                    treatInfo.setSex(resultSet.getString("PSex"));
                    treatInfo.setAge(resultSet.getString("PAge"));
                    treatInfo.setAddress(resultSet.getString("Address"));
                    treatInfo.setPhonenumber(resultSet.getString("Pphonenum"));
                    if(Case!=null) {
                        //TreatInfo treatInfo = new TreatInfo(pno, name, treattime, sex, age, address, phonenumber, Case, diagnosis, treat, doctor.getDno(), tno);
                        String temp=LiveStatus(tno);
                        if(temp.equals(status) || status.equals("")) {
                            treatInfo.setStatus(temp);
                            list.add(treatInfo);
                        }
                    }
                }
                return list;
            }else{
                Date date = dateFormat.parse(treatdate);
                Timestamp time = new Timestamp(date.getTime());
                ResultSet resultSet=DataProcess.searchTimePatientTreat(time,patientid, doctor.getDno());
                while(resultSet.next()){
                    String pno=resultSet.getString("PNo");
                    String treattime=resultSet.getString("Time");
                    String Case=resultSet.getString("case");
                    String diagnosis=resultSet.getString("diagnosis");
                    String treat= resultSet.getString("treat");
                    String tno=resultSet.getString("TNo");
                    TreatInfo treatInfo=new TreatInfo(pno,treattime,Case,diagnosis,treat,doctor.getDno(),tno);
                    treatInfo.setName(resultSet.getString("Name"));
                    treatInfo.setSex(resultSet.getString("PSex"));
                    treatInfo.setAge(resultSet.getString("PAge"));
                    treatInfo.setAddress(resultSet.getString("Address"));
                    treatInfo.setPhonenumber(resultSet.getString("Pphonenum"));
                    if(Case!=null) {
                        //TreatInfo treatInfo = new TreatInfo(pno, name, treattime, sex, age, address, phonenumber, Case, diagnosis, treat, doctor.getDno(), tno);
                        String temp=LiveStatus(tno);
                        if(temp.equals(status) || status.equals("")) {
                            treatInfo.setStatus(temp);
                            list.add(treatInfo);
                        }
                    }
                }
                return list;
            }
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }catch (SQLException e){
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<BedInfo> listBlankBed() {
        try{
            ResultSet resultSet=DataProcess.searchVancantbed(doctor.getDepartment());
            List<BedInfo> list=new ArrayList<>();
            while(resultSet.next()){
                String rno=resultSet.getString("RNo");
                String bno=resultSet.getString("BNo");
                String department=resultSet.getString("Department");
                Double standard=resultSet.getDouble("Standard");
                list.add(new BedInfo(rno,bno,standard.toString(),department,"空闲"));
            }
            return list;
        }catch (SQLException e){
            throw new RuntimeException(e);
        }
    }

//    resultSet1=searchVancantBed(doctor.getDepartment()); //查询该医生科室下的的所有当前使用状态为0的空病床
//    resultSet2=searchUsingBed(doctor.getDepartment()); //查询该医生科室下使用状态为1的正在被使用的空病床
//    List<BedInfo> list=new ArrayList<>();
//    while(resultSet1.next()){ //遍历当前空病床
//        list.add(new BedInfo(resultSet1, "空闲"));
//    }
//    while(resultSet2.next())
//
//    { //遍历当前被使用病床
//        if (searchTime.equals("")) {
//            list.add(new BedInfo(resultSet1, "空闲"));
//        } else {
//            preOutTimeSet = searchLiveInfo(doctor.getDepartment());
//            while (preOutTimeSet.next()) {
//                if (resultSet1.RNo.equals(preOutTimeSet.RNo)
//                        && resultSet1.BNo.equals(preOutTimeSet.BNo)
//                        && preOutTimeSet.getTimeStamp("PreOutTime") > searchTime
//                        && preOutTimeSet.getTimeStamp("OutTime") == null) {
//                    list.add(new BedInfo(resultSet1, "预计空闲"));
//                }
//            }
//        }
//    }
    @Override
    public List<BedInfo> searchBlankBed(String time,String Rno) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        try{
            if(time.equals("") && Rno.equals("")){
                return listBlankBed();
            }else if(!time.equals("")&&Rno.equals("")){
                Date date = dateFormat.parse(time);
                Timestamp timestamp = new Timestamp(date.getTime());
                ResultSet resultSet=DataProcess.searchVancantbed(timestamp);
                List<BedInfo> list = new ArrayList<>();
                while (resultSet.next()) {
                    String rno = resultSet.getString("RNo");
                    String bno = resultSet.getString("BNo");
                    ResultSet resultSet1 = DataProcess.searchBedInformation(rno);
                    resultSet1.next();
                    String department = resultSet1.getString("Department");
                    Double standard = resultSet1.getDouble("Standard");
                    if (department.equals(doctor.getDepartment())) {
                        ResultSet resultSet2=DataProcess.searchPreVancantbed(timestamp);
                        int flag=0;
                        while(resultSet2.next()){
                            if(resultSet2.getString("RNo").equals(rno)&&resultSet2.getString("BNo").equals(bno)){
                                list.add(new BedInfo(rno, bno, standard.toString(), department,"预计空闲"));
                                flag=1;
                                break;
                            }
                        }
                        if(flag==0) {
                            list.add(new BedInfo(rno, bno, standard.toString(), department,"空闲"));
                        }
                    }
                }
                return list;
            }else if(time.equals("")&&!Rno.equals("")){
                ResultSet resultSet=DataProcess.searchVancantbed(doctor.getDepartment());
                List<BedInfo> list=new ArrayList<>();
                while(resultSet.next()){
                    String rno=resultSet.getString("RNo");
                    String bno=resultSet.getString("BNo");
                    String department=resultSet.getString("Department");
                    Double standard=resultSet.getDouble("Standard");
                    if(Rno.equals(rno)) {
                        list.add(new BedInfo(rno, bno, standard.toString(), department, "空闲"));
                    }
                }
                return list;
            }else{
                Date date = dateFormat.parse(time);
                Timestamp timestamp = new Timestamp(date.getTime());
                ResultSet resultSet=DataProcess.searchVancantbed(timestamp);
                List<BedInfo> list = new ArrayList<>();
                while (resultSet.next()) {
                    String rno = resultSet.getString("RNo");
                    String bno = resultSet.getString("BNo");
                    ResultSet resultSet1 = DataProcess.searchBedInformation(rno);
                    resultSet1.next();
                    String department = resultSet1.getString("Department");
                    Double standard = resultSet1.getDouble("Standard");
                    if (department.equals(doctor.getDepartment())&&rno.equals(Rno)) {
                        ResultSet resultSet2=DataProcess.searchPreVancantbed(timestamp);
                        int flag=0;
                        while(resultSet2.next()){
                            if(resultSet2.getString("RNo").equals(rno)&&resultSet2.getString("BNo").equals(bno)){
                                list.add(new BedInfo(rno, bno, standard.toString(), department,"预计空闲"));
                                flag=1;
                                break;
                            }
                        }
                        if(flag==0) {
                            list.add(new BedInfo(rno, bno, standard.toString(), department,"空闲"));
                        }
                    }
                }
                return list;
            }
        }catch (SQLException e){
            throw new RuntimeException(e);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<TreatInfo> listNotCertPatient() {
        try{
            ResultSet resultSet=DataProcess.searchDoctorTreatLive(doctor.getDno());
            List<TreatInfo> list=new ArrayList<>();
            while(resultSet.next()){
                String pno=resultSet.getString("PNo");
                String treattime=resultSet.getString("Time");
                String Case=resultSet.getString("case");
                String diagnosis=resultSet.getString("diagnosis");
                String treat= resultSet.getString("treat");
                String tno=resultSet.getString("TNo");
                TreatInfo treatInfo=new TreatInfo(pno,treattime,Case,diagnosis,treat,doctor.getDno(),tno);
                treatInfo.setName(resultSet.getString("Name"));
                treatInfo.setSex(resultSet.getString("PSex"));
                treatInfo.setAge(resultSet.getString("PAge"));
                treatInfo.setAddress(resultSet.getString("Address"));
                treatInfo.setPhonenumber(resultSet.getString("Pphonenum"));
                list.add(treatInfo);
            }
            return list;
        }catch (SQLException e){
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<TreatInfo> searchNotCertPatient(String Name) {
        try {
            if(Name.equals("")){
                return listNotCertPatient();
            }else {
                ResultSet resultSet = DataProcess.searchDoctorTreatLive(doctor.getDno(), Name);
                List<TreatInfo> list = new ArrayList<>();
                while (resultSet.next()) {
                    String pno=resultSet.getString("PNo");
                    String treattime=resultSet.getString("Time");
                    String Case=resultSet.getString("case");
                    String diagnosis=resultSet.getString("diagnosis");
                    String treat= resultSet.getString("treat");
                    String tno=resultSet.getString("TNo");
                    TreatInfo treatInfo=new TreatInfo(pno,treattime,Case,diagnosis,treat,doctor.getDno(),tno);
                    treatInfo.setName(resultSet.getString("Name"));
                    treatInfo.setSex(resultSet.getString("PSex"));
                    treatInfo.setAge(resultSet.getString("PAge"));
                    treatInfo.setAddress(resultSet.getString("Address"));
                    treatInfo.setPhonenumber(resultSet.getString("Pphonenum"));
                    list.add(treatInfo);
                }
                return list;
            }
        }catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<LiveInfo> listLiveCanOut() {
        try{
            ResultSet resultSet=DataProcess.searchLiveCertificateLive("0");
            List<LiveInfo> list=new ArrayList<>();
            while(resultSet.next()){
                String lno=resultSet.getString("LNo");
                String pno=resultSet.getString("PNo");
                String pname=resultSet.getString("Name");
                String dno=resultSet.getString("DNo");
                String dname=resultSet.getString("DName");
                String rno=resultSet.getString("RNo");
                String bno=resultSet.getString("BNo");
                String intime=resultSet.getString("InTime");
                if(doctor.getDno().equals(dno)) {
                    list.add(new LiveInfo(lno, pno, pname, doctor.getDno(), dname, rno, bno, intime));
                }
            }
            return list;
        }catch (SQLException e){
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<LiveInfo> searchLiveCanOut(String name, String id) {
        try{
            if(name.equals("")&&id.equals("")){
                return listLiveCanOut();
            } else if (name.equals("")&&!id.equals("")) {
                ResultSet resultSet=DataProcess.searchLiveCertificateLive("0");
                List<LiveInfo> list=new ArrayList<>();
                while(resultSet.next()){
                    String lno=resultSet.getString("LNo");
                    String pno=resultSet.getString("PNo");
                    String pname=resultSet.getString("Name");
                    String dno=resultSet.getString("DNo");
                    String dname=resultSet.getString("DName");
                    String rno=resultSet.getString("RNo");
                    String bno=resultSet.getString("BNo");
                    String intime=resultSet.getString("InTime");
                    if(doctor.getDno().equals(dno)&&id.equals(lno)) {
                        list.add(new LiveInfo(lno, pno, pname, doctor.getDno(), dname, rno, bno, intime));
                    }
                }
                return list;
            }else if(!name.equals("")&&id.equals("")){
                ResultSet resultSet=DataProcess.searchLiveCertificateLive("0",name);
                List<LiveInfo> list=new ArrayList<>();
                while(resultSet.next()){
                    String lno=resultSet.getString("LNo");
                    String pno=resultSet.getString("PNo");
                    String pname=resultSet.getString("Name");
                    String dno=resultSet.getString("DNo");
                    String dname=resultSet.getString("DName");
                    String rno=resultSet.getString("RNo");
                    String bno=resultSet.getString("BNo");
                    String intime=resultSet.getString("InTime");
                    if(doctor.getDno().equals(dno)) {
                        list.add(new LiveInfo(lno, pno, pname, doctor.getDno(), dname, rno, bno, intime));
                    }
                }
                return list;
            }else{
                ResultSet resultSet=DataProcess.searchLiveCertificateLive("0",name);
                List<LiveInfo> list=new ArrayList<>();
                while(resultSet.next()){
                    String lno=resultSet.getString("LNo");
                    String pno=resultSet.getString("PNo");
                    String pname=resultSet.getString("Name");
                    String dno=resultSet.getString("DNo");
                    String dname=resultSet.getString("DName");
                    String rno=resultSet.getString("RNo");
                    String bno=resultSet.getString("BNo");
                    String intime=resultSet.getString("InTime");
                    if(doctor.getDno().equals(dno)&&id.equals(lno)) {
                        list.add(new LiveInfo(lno, pno, pname, doctor.getDno(), dname, rno, bno, intime));
                    }
                }
                return list;
            }
        }catch (SQLException e){
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<TreatInfo> listTreatPatient() {
        ResultSet resultSet=null;
        List<TreatInfo> list=new ArrayList<>();
        if(doctor!=null) {
            resultSet = DataProcess.searchDoctorNeedTreat(doctor.getDno());
            try{
                while(resultSet.next()){
                    String pno=resultSet.getString("PNo");
                    String treattime=resultSet.getString("Time");
                    Timestamp timestamp=resultSet.getTimestamp("Time");
                    String Case=resultSet.getString("case");
                    String diagnosis=resultSet.getString("diagnosis");
                    String treat= resultSet.getString("treat");
                    String tno=resultSet.getString("TNo");
                    TreatInfo treatInfo=new TreatInfo(pno,treattime,Case,diagnosis,treat,doctor.getDno(),tno);
                    treatInfo.setName(resultSet.getString("Name"));
                    treatInfo.setSex(resultSet.getString("PSex"));
                    treatInfo.setAge(resultSet.getString("PAge"));
                    treatInfo.setAddress(resultSet.getString("Address"));
                    treatInfo.setPhonenumber(resultSet.getString("Pphonenum"));
                    if(System.currentTimeMillis()+24*60*60*1000>timestamp.getTime()&&timestamp.getTime()>System.currentTimeMillis()-10*60*60*1000) {
                        list.add(treatInfo);
                    }
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
        return list;
    }

    @Override
    public List<TreatInfo> searchTreatPatient(String Name, String id) {
        ResultSet resultSet=null;
        List<TreatInfo> list=new ArrayList<>();
        try {
            if(Name.equals("")&&id.equals("")){
                return listTreatPatient();
            }else if(Name.equals("")&&!id.equals("")){
                resultSet = DataProcess.searchDoctorNeedTreat(doctor.getDno());
                while(resultSet.next()){
                    String pno=resultSet.getString("PNo");
                    String treattime=resultSet.getString("Time");
                    String Case=resultSet.getString("case");
                    String diagnosis=resultSet.getString("diagnosis");
                    String treat= resultSet.getString("treat");
                    String tno=resultSet.getString("TNo");
                    TreatInfo treatInfo=new TreatInfo(pno,treattime,Case,diagnosis,treat,doctor.getDno(),tno);
                    treatInfo.setName(resultSet.getString("Name"));
                    treatInfo.setSex(resultSet.getString("PSex"));
                    treatInfo.setAge(resultSet.getString("PAge"));
                    treatInfo.setAddress(resultSet.getString("Address"));
                    treatInfo.setPhonenumber(resultSet.getString("Pphonenum"));
                    if(tno.equals(id)) {
                        list.add(treatInfo);
                    }
                }
            }else if(!Name.equals("")&&id.equals("")){
                resultSet = DataProcess.searchDoctorNeedTreat(doctor.getDno(),Name);
                while(resultSet.next()){
                    String pno=resultSet.getString("PNo");
                    String treattime=resultSet.getString("Time");
                    String Case=resultSet.getString("case");
                    String diagnosis=resultSet.getString("diagnosis");
                    String treat= resultSet.getString("treat");
                    String tno=resultSet.getString("TNo");
                    TreatInfo treatInfo=new TreatInfo(pno,treattime,Case,diagnosis,treat,doctor.getDno(),tno);
                    treatInfo.setName(resultSet.getString("Name"));
                    treatInfo.setSex(resultSet.getString("PSex"));
                    treatInfo.setAge(resultSet.getString("PAge"));
                    treatInfo.setAddress(resultSet.getString("Address"));
                    treatInfo.setPhonenumber(resultSet.getString("Pphonenum"));
                    list.add(treatInfo);
                }
            }else{
                resultSet = DataProcess.searchDoctorNeedTreat(doctor.getDno(),Name);
                while(resultSet.next()){
                    String pno=resultSet.getString("PNo");
                    String treattime=resultSet.getString("Time");
                    String Case=resultSet.getString("case");
                    String diagnosis=resultSet.getString("diagnosis");
                    String treat= resultSet.getString("treat");
                    String tno=resultSet.getString("TNo");
                    TreatInfo treatInfo=new TreatInfo(pno,treattime,Case,diagnosis,treat,doctor.getDno(),tno);
                    treatInfo.setName(resultSet.getString("Name"));
                    treatInfo.setSex(resultSet.getString("PSex"));
                    treatInfo.setAge(resultSet.getString("PAge"));
                    treatInfo.setAddress(resultSet.getString("Address"));
                    treatInfo.setPhonenumber(resultSet.getString("Pphonenum"));
                    if(tno.equals(id)) {
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
    public boolean insertLiveCertificate(String preintime,String tno) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date = null;
        try {
            date = dateFormat.parse(preintime);
            Timestamp time= new Timestamp(date.getTime());
            ResultSet resultSet=DataProcess.searchDoctorTreatLive(doctor.getDno());
            while(resultSet.next()){
                String Tno=resultSet.getString("TNo");
                if(Tno.equals(tno)){
                    return DataProcess.insertLiveCertificate(doctor.getDno(),resultSet.getString("PNo"),time,tno);
                }
            }
        } catch (ParseException e) {
            throw new RuntimeException(e);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return false;
    }

    @Override
    public boolean insertCanOut(String lno,String preouttime) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date = null;
        try {
            date = dateFormat.parse(preouttime);
            Timestamp time= new Timestamp(date.getTime());
            return DataProcess.changeOutStatus(lno,time);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean updateTreatRecord(String tno, String Case, String diagosis, String treat) {
        return DataProcess.changeTreatRecord(tno,Case,diagosis,treat);
    }
}
