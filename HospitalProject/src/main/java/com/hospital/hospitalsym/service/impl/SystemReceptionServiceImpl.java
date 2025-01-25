package com.hospital.hospitalsym.service.impl;

import com.hospital.hospitalsym.StartDB;
import com.hospital.hospitalsym.dao.DataProcess;
import com.hospital.hospitalsym.dto.SystemDoctorDto;
import com.hospital.hospitalsym.dto.SystemReceptionDto;
import com.hospital.hospitalsym.entity.*;
import com.hospital.hospitalsym.service.SystemReceptionService;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class SystemReceptionServiceImpl implements SystemReceptionService {
    static Doctor doctor;
    @Override
    public SystemReceptionDto loginReception(String doctorid, String password) {
        SystemReceptionDto systemReceptionDto=new SystemReceptionDto();
        StartDB startDB=new StartDB();
        if(DataProcess.searchReceptionLogin(doctorid)&&DataProcess.searchPassword(doctorid,password)){
            systemReceptionDto.setMsg("登陆成功");
            doctor=DataProcess.searchDoctor(doctorid);
            systemReceptionDto.setReception(doctor);
        }else if(!DataProcess.searchReceptionLogin(doctorid)){
            systemReceptionDto.setMsg("服务台工作人员不存在");
            doctor=null;
        }else {
            systemReceptionDto.setMsg("密码输入错误");
            doctor=null;
        }
        return systemReceptionDto;
    }


    @Override
    public List<BedInfo> listBlankBed() {
        try{
            ResultSet resultSet=DataProcess.searchVancantbed();
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

    @Override
    public List<BedInfo> searchBlankBed(String Department,String Rno,String time) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        try{
            if(time.equals("") && Rno.equals("")){
                if(Department.equals("")){
                    return listBlankBed();
                }else {
                    ResultSet resultSet=DataProcess.searchVancantbed(Department);
                    List<BedInfo> list=new ArrayList<>();
                    while(resultSet.next()){
                        String rno=resultSet.getString("RNo");
                        String bno=resultSet.getString("BNo");
                        String department=resultSet.getString("Department");
                        Double standard=resultSet.getDouble("Standard");
                        list.add(new BedInfo(rno,bno,standard.toString(),department,"空闲"));
                    }
                    return list;
                }
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
                    if (department.equals(Department)||Department.equals("")) {
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
                ResultSet resultSet=DataProcess.searchVancantbed();
                List<BedInfo> list=new ArrayList<>();
                while(resultSet.next()){
                    String rno=resultSet.getString("RNo");
                    String bno=resultSet.getString("BNo");
                    String department=resultSet.getString("Department");
                    Double standard=resultSet.getDouble("Standard");
                    if(Rno.equals(rno)&&(Department.equals(department)||Department.equals(""))) {
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
                    if ((department.equals(Department)||Department.equals(""))&&rno.equals(Rno)) {
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
    public List<LiveCert> listLiveCertificate() {
        ResultSet resultSet=DataProcess.searchLiveCertificateNotLive();
        List<LiveCert> list=new ArrayList<>();
        try{
            while (resultSet.next()) {
                String lno = resultSet.getString("LNo");
                String dno = resultSet.getString("DNo");
                String pno = resultSet.getString("PNo");
                String pretime = resultSet.getString("PreInTime");
                String time = resultSet.getString("Time");
                String tno = resultSet.getString("TNo");
                LiveCert liveCert = new LiveCert(lno, dno, pno, time, pretime, tno);
                Doctor doctor = DataProcess.searchDoctor(dno);
                liveCert.setDname(doctor.getName());
                liveCert.setDepartment(doctor.getDepartment());
                Patient patient=DataProcess.searchPatient(pno);
                liveCert.setName(patient.getName());
                list.add(liveCert);
            }
        }catch (SQLException e){
            throw new RuntimeException(e);
        }
        return list;
    }

    @Override
    public List<LiveCert> searchLiveCertificate(String Lno,String Time,String department) {
        ResultSet resultSet=DataProcess.searchLiveCertificateNotLive();
        List<LiveCert> list=new ArrayList<>();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        try{
            if(Time.equals("")){
                if(Lno.equals("")&&department.equals("")){
                    return listLiveCertificate();
                }else{
                    while (resultSet.next()) {
                        String lno = resultSet.getString("LNo");
                        String dno = resultSet.getString("DNo");
                        String pno = resultSet.getString("PNo");
                        String pretime = resultSet.getString("PreInTime");
                        String time = resultSet.getString("Time");
                        String tno = resultSet.getString("TNo");
                        LiveCert liveCert = new LiveCert(lno, dno, pno, time, pretime, tno);
                        Doctor doctor = DataProcess.searchDoctor(dno);
                        liveCert.setDname(doctor.getName());
                        liveCert.setDepartment(doctor.getDepartment());
                        Patient patient=DataProcess.searchPatient(pno);
                        liveCert.setName(patient.getName());
                        if((Lno.equals("")&&department.equals(doctor.getDepartment()))||(Lno.equals(lno)&&department.equals(doctor.getDepartment()))||(Lno.equals(lno)&&department.equals(""))) {
                            list.add(liveCert);
                        }
                    }
                }
            }else{
                Date date = dateFormat.parse(Time);
                long time1=date.getTime();
                long time2=time1+24 * 60 * 60 * 1000;
                while (resultSet.next()) {
                    String lno = resultSet.getString("LNo");
                    String dno = resultSet.getString("DNo");
                    String pno = resultSet.getString("PNo");
                    String pretime = resultSet.getString("PreInTime");
                    String time = resultSet.getString("Time");
                    long time3=resultSet.getTimestamp("Time").getTime();
                    String tno = resultSet.getString("TNo");
                    LiveCert liveCert = new LiveCert(lno, dno, pno, time, pretime, tno);
                    Doctor doctor = DataProcess.searchDoctor(dno);
                    liveCert.setDname(doctor.getName());
                    liveCert.setDepartment(doctor.getDepartment());
                    Patient patient=DataProcess.searchPatient(pno);
                    liveCert.setName(patient.getName());
                    if(time1<time3&&time3<time2) {
                        if ((Lno.equals("")&&department.equals(""))||(Lno.equals("") && department.equals(doctor.getDepartment())) || (Lno.equals(lno) && department.equals(doctor.getDepartment())) || (Lno.equals(lno) && department.equals(""))) {
                            list.add(liveCert);
                        }
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
    public List<LiveCert> listCanOut() {
        ResultSet resultSet=DataProcess.searchLiveCertificateLive("1");
        List<LiveCert> list=new ArrayList<>();
        try {
            while (resultSet.next()) {
                String lno = resultSet.getString("LNo");
                String dno = resultSet.getString("DNo");
                String pno = resultSet.getString("PNo");
                String pretime = resultSet.getString("PreInTime");
                String time = resultSet.getString("Time");
                String tno = resultSet.getString("TNo");
                //String outtime = resultSet.getString("OutTime");
                LiveCert liveCert = new LiveCert(lno, dno, pno, time, pretime, tno);
                Doctor doctor=DataProcess.searchDoctor(dno);
                liveCert.setDname(doctor.getName());
                liveCert.setDepartment(doctor.getDepartment());
                liveCert.setIntime(resultSet.getString("InTime"));
                liveCert.setPreouttime(resultSet.getString("PreOutTime"));
                Patient patient=DataProcess.searchPatient(pno);
                liveCert.setName(patient.getName());
                list.add(liveCert);
            }
        }catch (SQLException e){
            throw new RuntimeException(e);
        }
        return list;
    }

    @Override
    public List<LiveCert> searchCanOut(String Lno,String Time,String department) {
        ResultSet resultSet=DataProcess.searchLiveCertificateLive("1");
        List<LiveCert> list=new ArrayList<>();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        try {
            if(Time.equals("")){
                if(Lno.equals("")&&department.equals("")){
                    return listLiveCertificate();
                }else{
                    while (resultSet.next()) {
                        String lno = resultSet.getString("LNo");
                        String dno = resultSet.getString("DNo");
                        String pno = resultSet.getString("PNo");
                        String pretime = resultSet.getString("PreInTime");
                        String time = resultSet.getString("Time");
                        String tno = resultSet.getString("TNo");
                        //String outtime = resultSet.getString("OutTime");
                        LiveCert liveCert = new LiveCert(lno, dno, pno, time, pretime, tno);
                        Doctor doctor=DataProcess.searchDoctor(dno);
                        liveCert.setDname(doctor.getName());
                        liveCert.setDepartment(doctor.getDepartment());
                        liveCert.setIntime(resultSet.getString("InTime"));
                        liveCert.setPreouttime(resultSet.getString("PreOutTime"));
                        Patient patient=DataProcess.searchPatient(pno);
                        liveCert.setName(patient.getName());
                        if((Lno.equals("")&&department.equals(doctor.getDepartment()))||(Lno.equals(lno)&&department.equals(doctor.getDepartment()))||(Lno.equals(lno)&&department.equals(""))) {
                            list.add(liveCert);
                        }
                    }
                }
            }else{
                Date date = dateFormat.parse(Time);
                long time1=date.getTime();
                long time2=time1+24 * 60 * 60 * 1000;
                while (resultSet.next()) {
                    String lno = resultSet.getString("LNo");
                    String dno = resultSet.getString("DNo");
                    String pno = resultSet.getString("PNo");
                    String pretime = resultSet.getString("PreInTime");
                    String time = resultSet.getString("Time");
                    String tno = resultSet.getString("TNo");
                    //String outtime = resultSet.getString("OutTime");
                    LiveCert liveCert = new LiveCert(lno, dno, pno, time, pretime, tno);
                    Doctor doctor=DataProcess.searchDoctor(dno);
                    liveCert.setDname(doctor.getName());
                    liveCert.setDepartment(doctor.getDepartment());
                    liveCert.setIntime(resultSet.getString("InTime"));
                    long time3=resultSet.getTimestamp("InTime").getTime();
                    liveCert.setPreouttime(resultSet.getString("PreOutTime"));
                    Patient patient=DataProcess.searchPatient(pno);
                    liveCert.setName(patient.getName());
                    if(time1<time3&&time3<time2) {
                        if ((Lno.equals("")&&department.equals(""))||(Lno.equals("") && department.equals(doctor.getDepartment())) || (Lno.equals(lno) && department.equals(doctor.getDepartment())) || (Lno.equals(lno) && department.equals(""))) {
                            list.add(liveCert);
                        }
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
    public List<BedInfo> listRoom() {
        try{
            ResultSet resultSet=DataProcess.searchRoom();
            List<BedInfo> list=new ArrayList<>();
            while(resultSet.next()){
                String rno=resultSet.getString("RNo");
                //String bno=resultSet.getString("BNo");
                String department=resultSet.getString("Department");
                Double standard=resultSet.getDouble("Standard");
                BedInfo bedInfo=new BedInfo(rno,"",standard.toString(),department,"");
                bedInfo.setBedNum(resultSet.getString("BedNum"));
                list.add(bedInfo);
            }
            return list;
        }catch (SQLException e){
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<BedInfo> searchRoom(String Department,String floor) {
        List<BedInfo> list=new ArrayList<>();
        try {
            if (Department.equals("") && floor.equals("")) {
                return listRoom();
            } else if (!Department.equals("") && floor.equals("")) {
                ResultSet resultSet = DataProcess.searchRoom();
                while (resultSet.next()) {
                    String rno = resultSet.getString("RNo");
                    //String bno=resultSet.getString("BNo");
                    String department = resultSet.getString("Department");
                    Double standard = resultSet.getDouble("Standard");
                    BedInfo bedInfo = new BedInfo(rno, "", standard.toString(), department, "");
                    bedInfo.setBedNum(resultSet.getString("BedNum"));
                    if(department.equals(Department)) {
                        list.add(bedInfo);
                    }
                }
            } else if (Department.equals("") && !floor.equals("")) {
                ResultSet resultSet = DataProcess.searchRoom();
                while(resultSet.next()) {
                    String rno = resultSet.getString("RNo");
                    //String bno=resultSet.getString("BNo");
                    String department = resultSet.getString("Department");
                    Double standard = resultSet.getDouble("Standard");
                    BedInfo bedInfo = new BedInfo(rno, "", standard.toString(), department, "");
                    bedInfo.setBedNum(resultSet.getString("BedNum"));
                    char[] Rno = rno.toCharArray();
                    char[] Floor = floor.toCharArray();
                    if (Rno[0] == Floor[0]) {
                        list.add(bedInfo);
                    }
                }
            }else{
                ResultSet resultSet = DataProcess.searchRoom();
                while (resultSet.next()) {
                    String rno = resultSet.getString("RNo");
                    //String bno=resultSet.getString("BNo");
                    String department = resultSet.getString("Department");
                    Double standard = resultSet.getDouble("Standard");
                    BedInfo bedInfo = new BedInfo(rno, "", standard.toString(), department, "");
                    bedInfo.setBedNum(resultSet.getString("BedNum"));
                    char[] Rno = rno.toCharArray();
                    char[] Floor = floor.toCharArray();
                    if (Rno[0] == Floor[0] && department.equals(Department)) {
                        list.add(bedInfo);
                    }
                }
            }
        }catch (SQLException e){
            throw new RuntimeException(e);
        }
        return list;
    }

    @Override
    public void roomOut(String lno) {
        try {
            DataProcess.changeOutTime(lno, new Timestamp(System.currentTimeMillis()));
            ResultSet resultSet = DataProcess.searchLiveRecord(lno);
            resultSet.next();
            String rno = resultSet.getString("RNo");
            String bno = resultSet.getString("BNo");
            DataProcess.changeBedStatus(rno, bno, "0");
            DataProcess.insertInvoice(lno);
        }catch (SQLException e){
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<BedInfo> searchRoom(String department) {
        ResultSet resultSet=DataProcess.searchRoom(department);
        List<BedInfo> list=new ArrayList<>();
        try {
            while(resultSet.next()) {
                BedInfo room = new BedInfo(resultSet.getString("RNo"), "", "", "", "");
                list.add(room);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return list;
    }

    @Override
    public List<BedInfo> searchBed(String rno) {
        ResultSet resultSet=DataProcess.searchBed(rno);
        List<BedInfo> list=new ArrayList<>();
        try {
            while(resultSet.next()) {
                BedInfo bed = new BedInfo("", resultSet.getString("BNo"), "", "", "");
                list.add(bed);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return list;
    }
    public void insertLive(String lno,String rno,String bno){
        DataProcess.insertLiveRecord(lno,rno,bno,new Timestamp(System.currentTimeMillis()));
        DataProcess.changeBedStatus(rno,bno,"1");
    }

    @Override
    public void updateRoom(String rno, String standard, String department, String bnum) {
        DataProcess.changeRoomStandard(rno,Double.valueOf(standard));
        DataProcess.changeRoomDepartment(rno,department);
        try{
            ResultSet resultSet=DataProcess.searchRoom();
            while(resultSet.next()){
                String Rno=resultSet.getString("RNo");
                if(rno.equals(Rno)){
                    int bedNum=Integer.valueOf(resultSet.getString("BedNum"));
                    int bNum=Integer.valueOf(bnum);
                    if(bedNum==bNum){} else if (bNum<bedNum) {
                        DataProcess.deleteBed(rno,bNum,bedNum);
                    }else {
                        DataProcess.insertBed(rno,bNum,bedNum);
                    }
                }
            }
        }catch (SQLException e){
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<TreatInfo> listLiveRecord(String status) {
        ResultSet resultSet=null;
        List<TreatInfo> list=new ArrayList<>();
        if(doctor!=null) {
            resultSet = DataProcess.listLiveInfo();
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
        ResultSet resultSet1=DataProcess.listDoctorLiveCertificate();
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
                        status="还未入院";
                    } else if (preouttime==null) {
                        status="正在住院";
                    }else if(outtime==null){
                        status="可以出院";
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
    public List<TreatInfo> searchLiveRecord(String name, String status) {
        if(name.equals("")){
            return listLiveRecord(status);
        }else{
            ResultSet resultSet=null;
            List<TreatInfo> list=new ArrayList<>();
            if(doctor!=null) {
                resultSet = DataProcess.listLiveInfo();
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
                            if(resultSet.getString("Name").contains(name)) {
                                treatInfo.setStatus(temp);
                                list.add(treatInfo);
                            }
                        }
                    }
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }
            return list;
        }
    }
}
