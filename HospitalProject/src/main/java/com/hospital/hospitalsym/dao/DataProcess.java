package com.hospital.hospitalsym.dao;

import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.hospital.hospitalsym.entity.*;

public class DataProcess {
    private static boolean connectToDB = false;
    private static Connection connection;
    private static Statement statement;
    private static PreparedStatement preparedStatement;
    private static ResultSet resultSet;
    /**
     * TODO 连接数据库
     */
    public static boolean connectToDatabase(){
        String driverName="com.mysql.cj.jdbc.Driver"; // 加载数据库驱动类
        String url="jdbc:mysql://localhost:3306/hospitaldb?serverTimezone=GMT%2B8&useUnicode=true&characterEncoding=utf8&autoReconnect=true&useSSL=false"; // 声明数据库的URL
        String user="root"; // 数据库用户
        String password="HEQuan20031108";
        try{
            Class.forName(driverName);
            connection= DriverManager.getConnection(url,user,password);
            connectToDB=true;
        }catch (ClassNotFoundException e){
            System.out.println("数据库驱动类名错误！"+e.getMessage());
        }catch (SQLException e){
            System.out.println("数据库连接错误！");
        }
        return connectToDB;
    }
    /**
     * TODO 增加时间
     */
    public static long getTime(Timestamp starttime,int days){
        java.util.Date date=new java.util.Date(starttime.getTime());
        java.util.Date endTime = new Date(date.getTime() +  days * 24 * 60 * 60 * 1000);
        return endTime.getTime();
    }
    /**
     * TODO 返回病人对象
     */
    public static Patient searchPatient(String pno){
        if (!connectToDB) {
            System.out.println("数据库连接错误！");
            return null;
        }else{
            try {
                statement=connection.createStatement();
                String sql="select * from patient";
                resultSet=statement.executeQuery(sql);
                while(resultSet.next()) {
                    String Pno = resultSet.getString("PNo");
                    String Name=resultSet.getString("Name");
                    String Sex=resultSet.getString("Sex");
                    int Age=resultSet.getInt("Age");
                    String Address=resultSet.getString("Address");
                    String PhoneNum=resultSet.getString("PhoneNum");
                    if (Pno.equals(pno)) {
                        Patient patient=new Patient(Pno,Name,Sex,Age,Address,PhoneNum);
                        return patient;
                    }
                }
                return null;
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }
    /**
     * TODO 查询病人病例
     */
    public static ResultSet searchPatientTreat(String pno,String dno){
        if (!connectToDB) {
            System.out.println("数据库连接错误！");
            return null;
        }else{
            try {
                statement=connection.createStatement();
                String sql="select * from treatinformation where PNo='"+pno+"' and DNo='"+dno+"'";
                resultSet=statement.executeQuery(sql);
                return resultSet;
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }
    /**
     * TODO 查询医生的登录信息
     */
    public static boolean searchDoctorLogin(String dno){
        if (!connectToDB) {
            System.out.println("数据库连接错误！");
            return false;
        }else{
            try {
                statement=connection.createStatement();
                String sql="select * from login";
                resultSet=statement.executeQuery(sql);
                while(resultSet.next()) {
                    String id = resultSet.getString("id");
                    String isReception=resultSet.getString("isReception");
                    if(id.equals(dno)&&isReception.equals("0")){
                        return true;
                    }
                }
                return false;
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }
    public static boolean searchReceptionLogin(String dno){
        if (!connectToDB) {
            System.out.println("数据库连接错误！");
            return false;
        }else{
            try {
                statement=connection.createStatement();
                String sql="select * from login";
                resultSet=statement.executeQuery(sql);
                while(resultSet.next()) {
                    String id = resultSet.getString("id");
                    String isReception=resultSet.getString("isReception");
                    if(id.equals(dno)&&isReception.equals("1")){
                        return true;
                    }
                }
                return false;
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }
    /**
     * TODO 查询密码是否正确
     */
    public static boolean searchPassword(String id,String password){
        if (!connectToDB) {
            System.out.println("数据库连接错误！");
            return false;
        }else{
            try {
                statement=connection.createStatement();
                String sql="select * from login";
                resultSet=statement.executeQuery(sql);
                while(resultSet.next()) {
                    String Id = resultSet.getString("id");
                    String Password=resultSet.getString("password");
                    if(id.equals(Id)&&password.equals(Password)){
                        return true;
                    }
                }
                return false;
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }
    /**
     * TODO 查询病人的治疗记录(病例)
     */
    public static ResultSet searchTreatRecord(String pno){
        if (!connectToDB) {
            System.out.println("数据库连接错误！");
            return null;
        }else{
            try {
                statement=connection.createStatement();
                String sql="select * from treatrecord where PNo = '"+pno+"' and diagnosis is not null order by time desc";
                resultSet=statement.executeQuery(sql);
                return resultSet;
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }
    /**
     * TODO 查询医生的诊疗记录
     */
    public static ResultSet searchDoctorTreatRecord(String dno){
        if (!connectToDB) {
            System.out.println("数据库连接错误！");
            return null;
        }else{
            try {
                statement=connection.createStatement();
                String sql="select * from treatinformation where DNo = '"+dno+"' and diagnosis is not null order by Time desc";
                resultSet=statement.executeQuery(sql);
                return resultSet;
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }
    /**
     * TODO 查询所有的住院记录
     */
    public static ResultSet listLiveInfo(){
        if (!connectToDB) {
            System.out.println("数据库连接错误！");
            return null;
        }else{
            try {
                statement=connection.createStatement();
                String sql="select * from treatinformation where diagnosis is not null order by Time desc";
                resultSet=statement.executeQuery(sql);
                return resultSet;
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }
    /**
     * TODO 查询所有登录信息
     */
    public static ResultSet listLoginInfo(){
        if (!connectToDB) {
            System.out.println("数据库连接错误！");
            return null;
        }else{
            try {
                statement=connection.createStatement();
                String sql="select * from doctor,login where DNo=id";
                resultSet=statement.executeQuery(sql);
                return resultSet;
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }
    /**
     * TODO 查询医生需要进行治疗的病人
     */
    public static ResultSet searchDoctorNeedTreat(String dno){
        if (!connectToDB) {
            System.out.println("数据库连接错误！");
            return null;
        }else{
            try {
                statement=connection.createStatement();
                String sql="select * from treatinformation where DNo = '"+dno+"' and diagnosis is null and time > UNIX_TIMESTAMP('"+new Timestamp(System.currentTimeMillis())+"') order by Time ASC";
                resultSet=statement.executeQuery(sql);
                return resultSet;
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }
    /**
     * TODO 病人姓名的模糊查询医生需要进行治疗的病人
     */
    public static ResultSet searchDoctorNeedTreat(String dno,String name){
        if (!connectToDB) {
            System.out.println("数据库连接错误！");
            return null;
        }else{
            try {
                statement=connection.createStatement();
                String sql="select * from treatinformation where DNo = '"+dno+"' and diagnosis is null and Name like '%"+name+"%' order by Time ASC";
                resultSet=statement.executeQuery(sql);
                return resultSet;
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }
    /**
     * TODO 查询医生诊疗记录中还没开入院证明的
     */
    public static ResultSet searchDoctorTreatLive(String dno){
        if (!connectToDB) {
            System.out.println("数据库连接错误！");
            return null;
        }else{
            try {
                statement=connection.createStatement();
                String sql="SELECT * FROM treatinformation WHERE Dno='"+dno+"' and TNo not in (SELECT TNo FROM livecertificate WHERE TNo is not null) and diagnosis is not null ORDER BY Time DESC ";
                resultSet=statement.executeQuery(sql);
                return resultSet;
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }
    /**
     * TODO 模糊查询对应医生病人姓名的治疗记录
     */
    public static ResultSet searchDoctorTreatLive(String dno,String name){
        if (!connectToDB) {
            System.out.println("数据库连接错误！");
            return null;
        }else{
            try {
                statement=connection.createStatement();
                String sql="SELECT * FROM treatinformation WHERE Dno='"+dno+"' and TNo not in (SELECT TNo FROM livecertificate WHERE TNo is not null) and diagnosis is not null and Name like'%"+name+"%' ORDER BY Time DESC ";
                resultSet=statement.executeQuery(sql);
                return resultSet;
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }
    /**
     * TODO 查询医生的病人的所有住宿信息
     */
    public static ResultSet listDoctorLiveCertificate(String dno){
        if (!connectToDB) {
            System.out.println("数据库连接错误！");
            return null;
        }else{
            try {
                statement=connection.createStatement();
                String sql="SELECT * FROM liveinformation WHERE Dno='"+dno+"'";
                resultSet=statement.executeQuery(sql);
                return resultSet;
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }
    /**
     * TODO 查询所有住宿信息
     */
    public static ResultSet listDoctorLiveCertificate(){
        if (!connectToDB) {
            System.out.println("数据库连接错误！");
            return null;
        }else{
            try {
                statement=connection.createStatement();
                String sql="SELECT * FROM liveinformation";
                resultSet=statement.executeQuery(sql);
                return resultSet;
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }
    /**
     * TODO 查询对应病人的住宿信息
     */
    public static ResultSet searchPatientLiveInfo(String tno){
        if (!connectToDB) {
            System.out.println("数据库连接错误！");
            return null;
        }else{
            try {
                statement=connection.createStatement();
                String sql="SELECT * FROM liveinformation WHERE Tno='"+tno+"'";
                resultSet=statement.executeQuery(sql);
                return resultSet;
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }
    /**
     * TODO 查询挂号单
     */
    public static ResultSet searchRegister(String pno){
        if (!connectToDB) {
            System.out.println("数据库连接错误！");
            return null;
        }else{
            try {
                statement=connection.createStatement();
                String sql="select * from treatinformation where PNo='"+pno+"' order by Time desc";
                resultSet=statement.executeQuery(sql);
                return resultSet;
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }
    /**
     * TODO 改变账单的支付状态
     */
    public static Boolean changePayStatus(String ino,String status){
        if (!connectToDB) {
            System.out.println("数据库连接错误！");
            return null;
        }else{
            try {
                statement=connection.createStatement();
                String sql="update invoice set Status='"+status+"',PayTime='"+new Timestamp(System.currentTimeMillis())+"' where INo = '"+ino+"'";
                if(statement.executeUpdate(sql)!=0){
                    return true;
                }else{
                    return false;
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }
    /**
     * TODO 出院许可
     */
    public static Boolean changeOutStatus(String lno,Timestamp timestamp){
        if (!connectToDB) {
            System.out.println("数据库连接错误！");
            return null;
        }else{
            try {
                statement=connection.createStatement();
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                String time=sdf.format(timestamp);
                String sql="update livecertificate set CanOut='"+1+"',PreOutTime='"+time+"',Time='"+(new Timestamp(System.currentTimeMillis())).toString()+"' where LNo = '"+lno+"'";
                if(statement.executeUpdate(sql)!=0){
                    return true;
                }else{
                    return false;
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }
    /**
     * TODO 改变病床的状态
     */
    public static Boolean changeBedStatus(String rno,String bno,String status){
        if (!connectToDB) {
            System.out.println("数据库连接错误！");
            return null;
        }else{
            try {
                statement=connection.createStatement();
                String sql="update sickbed set Status='"+status+"' where RNo ='"+rno+"' and BNo='"+bno+"'";
                if(statement.executeUpdate(sql)!=0){
                    return true;
                }else{
                    return false;
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }
    /**
     * TODO 更新出院的时间
     */
    public static Boolean changeOutTime(String lno,Timestamp timestamp){
        if (!connectToDB) {
            System.out.println("数据库连接错误！");
            return null;
        }else{
            try {
                statement=connection.createStatement();
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                String time=sdf.format(timestamp);
                String sql="update liverecord set OutTime ='"+time+"' where LNo='"+lno+"'";
                if(statement.executeUpdate(sql)!=0){
                    return true;
                }else{
                    return false;
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }
    /**
     * TODO 改变病房所属部门
     */
    public static Boolean changeRoomDepartment(String rno,String department){
        if (!connectToDB) {
            System.out.println("数据库连接错误！");
            return null;
        }else{
            try {
                statement=connection.createStatement();
                String sql="update sickroom set Department ='"+department+"' where RNo='"+rno+"'";
                if(statement.executeUpdate(sql)!=0){
                    return true;
                }else{
                    return false;
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }
    /**
     * TODO 填写病人的病例
     */
    public static Boolean changeTreatRecord(String TNo,String Case,String diagnosis,String treat){
        if (!connectToDB) {
            System.out.println("数据库连接错误！");
            return null;
        }else{
            try {
                statement=connection.createStatement();
                String sql="update treatrecord set treatrecord.Case ='"+Case+"',Diagnosis='"+diagnosis+"',Treat='"+treat+"' where TNo='"+TNo+"'";
                if(statement.executeUpdate(sql)!=0){
                    return true;
                }else{
                    return false;
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }
    /**
     * TODO 填写病人的病例
     */
    public static Boolean changeDoctor(String dno, String name, String sex, String age, String title, String phonenum, String department){
        if (!connectToDB) {
            System.out.println("数据库连接错误！");
            return null;
        }else{
            try {
                statement=connection.createStatement();
                String sql="update doctor set Name ='"+name+"',Sex='"+sex+"',Age='"+age+"',Title='"+title+"',PhoneNum='"+phonenum+"',Department='"+department+"' where DNo='"+dno+"'";
                if(statement.executeUpdate(sql)!=0){
                    return true;
                }else{
                    return false;
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }
    /**
     * TODO 刷新床位状态
     */
    public static Boolean flashBedStatus(){
        if (!connectToDB) {
            System.out.println("数据库连接错误！");
            return null;
        }else{
            try {
                statement=connection.createStatement();
                String sql="update sickbed set `Status`=1 \n" +
                        "\twhere (BNo,RNo) in \n" +
                        "\t\t\t\t(SELECT BNo,RNo \n" +
                        "\t\t\t\tFROM liverecord \n" +
                        "\t\t\t\twhere UNIX_TIMESTAMP(liverecord.InTime) < UNIX_TIMESTAMP(NOW()) \n" +
                        "\t\t\t\t\t\t\tAND UNIX_TIMESTAMP(liverecord.OutTime)>UNIX_TIMESTAMP(NOW()))";
                if(statement.executeUpdate(sql)!=0){
                    return true;
                }else{
                    return false;
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }
    /**
     * TODO 查找病人的全部账单
     */
    public static ResultSet searchBill(String pno){
        if (!connectToDB) {
            System.out.println("数据库连接错误！");
            return null;
        }else{
            try {
                statement=connection.createStatement();
                String sql="select * from bill where PNo = '"+pno+"'";
                resultSet=statement.executeQuery(sql);
                return resultSet;
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }
    /**
     *TODO 返回对应部门的对应病房
     * */
    public static ResultSet searchRoom(String department){
        if (!connectToDB) {
            System.out.println("数据库连接错误！");
            return null;
        }else{
            try {
                statement=connection.createStatement();
                String sql="SELECT RNo FROM sickroom WHERE Department='"+department+"'";
                resultSet=statement.executeQuery(sql);
                return resultSet;
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }
    /**
     *TODO 返回对应房间的空床位
     * */
    public static ResultSet searchBed(String rno){
        if (!connectToDB) {
            System.out.println("数据库连接错误！");
            return null;
        }else{
            try {
                statement=connection.createStatement();
                String sql="SELECT BNo FROM sickbed WHERE RNo='"+rno+"' and `Status`='0'";
                resultSet=statement.executeQuery(sql);
                return resultSet;
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }
    /**
     * TODO 返回医生对象
     */
    public static Doctor searchDoctor(String dno){
        if (!connectToDB) {
            System.out.println("数据库连接错误！");
            return null;
        }else{
            try {
                statement=connection.createStatement();
                String sql="select * from doctor";
                resultSet=statement.executeQuery(sql);
                while(resultSet.next()) {
                    String Dno = resultSet.getString("DNo");
                    String Name=resultSet.getString("Name");
                    String Sex=resultSet.getString("Sex");
                    int Age=resultSet.getInt("Age");
                    String Title=resultSet.getString("Title");
                    String PhoneNum=resultSet.getString("PhoneNum");
                    String Department=resultSet.getString("Department");
                    if (Dno.equals(dno)) {
                        Doctor doctor=new Doctor(Dno,Name,Sex,Age,Title,PhoneNum,Department);
                        return doctor;
                    }
                }
                return null;
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }
    /**
     * TODO 插入新病人
     */
    public static boolean newPatient(String name,String sex,int age,String address,String phonenum){
        if(!connectToDB){
            throw new IllegalStateException("数据库未连接！");
        }else {
            try {
                statement = connection.createStatement();
                String sql = "select * from patient";
                resultSet = statement.executeQuery(sql);
                String Pmax="00000000";
                String pno="00000000";
                while (resultSet.next()) {
                    pno = resultSet.getString("PNo");
                    if(pno.compareTo(Pmax)>0){
                        Pmax=pno;
                    }
                }
                int Pnum=Integer.valueOf(Pmax);
                Pnum=Pnum+1;
                String Pno=String.valueOf(Pnum);
                int length=Pno.length();
                String zero="0";
                for(int i=0;i<8-length;i++){
                    Pno=zero+Pno;
                }
                String sql1 = "INSERT INTO patient (PNo,Name,Sex,Age,Address,PhoneNum) VALUES (?,?,?,?,?,?)";
                preparedStatement = connection.prepareStatement(sql1);
                preparedStatement.setString(1, Pno);
                preparedStatement.setString(2, name);
                preparedStatement.setString(3, sex);
                preparedStatement.setInt(4, age);
                preparedStatement.setString(5, address);
                preparedStatement.setString(6, phonenum);
                int temp = preparedStatement.executeUpdate();
                preparedStatement.close();
                if (temp != 0)
                    return true;
                else {
                    return false;
                }
            }catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }
    /**
     * TODO 获得最新的病案号
     */
    public static String searchNewPno(){
        try {
            statement = connection.createStatement();
            String sql = "select * from patient";
            resultSet = statement.executeQuery(sql);
            String Pmax = "00000000";
            String pno = "00000000";
            while (resultSet.next()) {
                pno = resultSet.getString("PNo");
                if (pno.compareTo(Pmax) > 0) {
                    Pmax = pno;
                }
            }
            int Pnum = Integer.valueOf(Pmax);
            Pnum = Pnum + 1;
            String Pno = String.valueOf(Pnum);
            int length = Pno.length();
            String zero = "0";
            for (int i = 0; i < 8 - length; i++) {
                Pno = zero + Pno;
            }
            return Pno;
        }catch (SQLException e){
            throw new RuntimeException(e);
        }
    }
    /**
     * TODO 查找医生所属目前空病房
     */
    public static ResultSet searchVancantbed(String department){
        if (!connectToDB) {
            System.out.println("数据库连接错误！");
            return null;
        }else{
            try {
                statement=connection.createStatement();
                String sql="select * from vacantbed where Department = '"+department+"'";
                resultSet=statement.executeQuery(sql);
                return resultSet;
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }
    /**
     * TODO 查找目前所有空病房
     */
    public static ResultSet searchVancantbed(){
        if (!connectToDB) {
            System.out.println("数据库连接错误！");
            return null;
        }else{
            try {
                statement=connection.createStatement();
                String sql="select * from vacantbed ";
                resultSet=statement.executeQuery(sql);
                return resultSet;
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }
    /**
     * TODO 查找所有病房信息
     */
    public static ResultSet searchRoom(){
        if (!connectToDB) {
            System.out.println("数据库连接错误！");
            return null;
        }else{
            try {
                statement=connection.createStatement();
                String sql="SELECT sickroom.*, bed_counts.BedNum\n" +
                        "FROM sickroom\n" +
                        "JOIN (\n" +
                        "  SELECT RNo, COUNT(*) AS BedNum\n" +
                        "  FROM sickbed\n" +
                        "  GROUP BY RNo\n" +
                        ") AS bed_counts ON sickroom.RNo = bed_counts.RNo;";
                resultSet=statement.executeQuery(sql);
                return resultSet;
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }
    /**
     * TODO 查找指定时间点的空病房
     */
    public static ResultSet searchVancantbed(Timestamp timestamp){
        if (!connectToDB) {
            System.out.println("数据库连接错误！");
            return null;
        }else{
            try {
                statement=connection.createStatement();
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                String time=sdf.format(timestamp);
                //System.out.println(time);
                String sql="SELECT * FROM sickbed \n" +
                        "where (rno,bno) not in \n" +
                        "(select rno,bno from livecertificate,liverecord WHERE livecertificate.LNo=liverecord.LNo and ((liverecord.OutTime is null and livecertificate.PreOutTime is null) or (Outtime is null and livecertificate.PreOutTime is not null and UNIX_TIMESTAMP(livecertificate.PreOutTime) > UNIX_TIMESTAMP('"+time+"'))))";
                resultSet=statement.executeQuery(sql);
                return resultSet;
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }
    /**
     * TODO 查找指定时间点预计空出来的病房
     */
    public static ResultSet searchPreVancantbed(Timestamp timestamp){
        if (!connectToDB) {
            System.out.println("数据库连接错误！");
            return null;
        }else{
            try {
                statement=connection.createStatement();
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                String time=sdf.format(timestamp);
                //System.out.println(time);
                String sql="SELECT * FROM sickbed \n" +
                        "where (rno,bno) in \n" +
                        "(select rno,bno from livecertificate,liverecord WHERE livecertificate.LNo=liverecord.LNo and (Outtime is null and livecertificate.PreOutTime is not null and UNIX_TIMESTAMP(livecertificate.PreOutTime) < UNIX_TIMESTAMP('"+time+"')))";
                resultSet=statement.executeQuery(sql);
                return resultSet;
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }
    /**
     * TODO 查找指定时间点的病人
     */
    public static ResultSet searchTimeTreat(Timestamp timestamp,String dno){
        if (!connectToDB) {
            System.out.println("数据库连接错误！");
            return null;
        }else{
            try {
                statement=connection.createStatement();
                long nexttime=getTime(timestamp,1);
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                String time=sdf.format(timestamp);
                String time2=sdf.format(new Timestamp(nexttime));
                //System.out.println(time);
                String sql="SELECT * FROM treatinformation\n" +
                        "where UNIX_TIMESTAMP(Time)>UNIX_TIMESTAMP('"+time+"') and UNIX_TIMESTAMP(Time)<UNIX_TIMESTAMP('"+time2+"') and DNo='"+dno+"';";
                resultSet=statement.executeQuery(sql);
                return resultSet;
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }
    /**
     * TODO 查找指定的病人
     */
    public static ResultSet searchTimePatientTreat(Timestamp timestamp,String pno,String dno){
        if (!connectToDB) {
            System.out.println("数据库连接错误！");
            return null;
        }else{
            try {
                statement=connection.createStatement();
                long nexttime=getTime(timestamp,1);
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                String time=sdf.format(timestamp);
                String time2=sdf.format(new Timestamp(nexttime));
                //System.out.println(time);
                String sql="SELECT * FROM treatinformation\n" +
                        "where UNIX_TIMESTAMP(Time)>UNIX_TIMESTAMP('"+time+"') and UNIX_TIMESTAMP(Time)<UNIX_TIMESTAMP('"+time2+"') and DNo='"+dno+"' and PNo='"+pno+"';";
                resultSet=statement.executeQuery(sql);
                return resultSet;
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }
    /**
     * TODO 查找指定住院号的住院记录
     */
    public static ResultSet searchLiveRecord(String lno){
        if (!connectToDB) {
            System.out.println("数据库连接错误！");
            return null;
        }else{
            try {
                statement=connection.createStatement();
                String sql="select * from liverecord where LNo='"+lno+"'";
                resultSet=statement.executeQuery(sql);
                return resultSet;
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }
    /**
     * TODO 查找病床信息
     */
    public static ResultSet searchBedInformation(String rno){
        if (!connectToDB) {
            System.out.println("数据库连接错误！");
            return null;
        }else{
            try {
                statement=connection.createStatement();
                String sql="SELECT * FROM sickroom where RNo='"+rno+"'";
                resultSet=statement.executeQuery(sql);
                return resultSet;
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }
    /**
     * TODO 查找所有的病案号
     */
    public static ResultSet searchAllPatient(){
        if (!connectToDB) {
            System.out.println("数据库连接错误！");
            return null;
        }else{
            try {
                statement=connection.createStatement();
                String sql="select * from patient ";
                resultSet=statement.executeQuery(sql);
                return resultSet;
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }
    public static ResultSet searchDepartmentDoctor(String department){
        if (!connectToDB) {
            System.out.println("数据库连接错误！");
            return null;
        }else{
            try {
                statement=connection.createStatement();
                String sql="select * from doctor where department='"+department+"'";
                resultSet=statement.executeQuery(sql);
                return resultSet;
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }
    public static ResultSet listAllDepartment(String department){
        if (!connectToDB) {
            System.out.println("数据库连接错误！");
            return null;
        }else{
            try {
                statement=connection.createStatement();
                String sql="SELECT DNo,Name FROM doctor where Department='"+department+"'";
                resultSet=statement.executeQuery(sql);
                return resultSet;
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }
    /**
     * TODO 查找病人有住院证明但还没有住院记录（入院）
     */
    public static ResultSet searchLiveCertificateNotLive(){
        if (!connectToDB) {
            System.out.println("数据库连接错误！");
            return null;
        }else{
            try {
                statement=connection.createStatement();
                String sql="select * from livecertificate where LNo not in (select LNo from liverecord)";
                resultSet=statement.executeQuery(sql);
                return resultSet;
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }
    /**
     * TODO 查找已住院的住院记录
     */
    public static ResultSet searchLiveCertificate(){
        if (!connectToDB) {
            System.out.println("数据库连接错误！");
            return null;
        }else{
            try {
                statement=connection.createStatement();
                String sql="select * from livecertificate where LNo in (select LNo from liverecord)";
                resultSet=statement.executeQuery(sql);
                return resultSet;
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }

//    public static ResultSet searchLiveCertificate(String pno){
//        if (!connectToDB) {
//            System.out.println("数据库连接错误！");
//            return null;
//        }else{
//            try {
//                statement=connection.createStatement();
//                String sql="select * from livecertificate where PNo ='"+pno+"'";
//                resultSet=statement.executeQuery(sql);
//                return resultSet;
//            } catch (SQLException e) {
//                throw new RuntimeException(e);
//            }
//        }
//    }
//    public static ResultSet searchLiveCertificateNotLeaf(){
//        if (!connectToDB) {
//            System.out.println("数据库连接错误！");
//            return null;
//        }else{
//            try {
//                statement=connection.createStatement();
//                String sql="select * from livecertificate where LNo in (select LNo from liverecord where OutTime is null)";
//                resultSet=statement.executeQuery(sql);
//                return resultSet;
//            } catch (SQLException e) {
//                throw new RuntimeException(e);
//            }
//        }
//    }
    /**
     * TODO 查找有住院证明且已住院的（是否有出院证明）
     */
    public static ResultSet searchLiveCertificateLive(String canout){
        if (!connectToDB) {
            System.out.println("数据库连接错误！");
            return null;
        }else{
            try {
                statement=connection.createStatement();
                String sql="select * from liveinformation where  OutTime is null and CanOut='"+canout+"'";
                resultSet=statement.executeQuery(sql);
                return resultSet;
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }
    public static ResultSet searchLiveInfomation(String canout){
        if (!connectToDB) {
            System.out.println("数据库连接错误！");
            return null;
        }else{
            try {
                statement=connection.createStatement();
                String sql="select * from liveinformation where  CanOut='"+canout+"'";
                resultSet=statement.executeQuery(sql);
                return resultSet;
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }
    /**
     * TODO 姓名的模糊查找有住院证明且已住院的（是否有出院证明）
     */
    public static ResultSet searchLiveCertificateLive(String canout,String name){
        if (!connectToDB) {
            System.out.println("数据库连接错误！");
            return null;
        }else{
            try {
                statement=connection.createStatement();
                String sql="select * from liveinformation where  OutTime is null and CanOut='"+canout+"' and Name like '%"+name+"%'";
                resultSet=statement.executeQuery(sql);
                return resultSet;
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }
//    /**
//     * TODO 查找有住院证明且已住院的（是否有出院证明）
//     */
//    public static ResultSet searchAllLiveCertificate(){
//        if (!connectToDB) {
//            System.out.println("数据库连接错误！");
//            return null;
//        }else{
//            try {
//                statement=connection.createStatement();
//                String sql="select * from livecertificate";
//                resultSet=statement.executeQuery(sql);
//                return resultSet;
//            } catch (SQLException e) {
//                throw new RuntimeException(e);
//            }
//        }
//    }
    /**
     * TODO 插入入院记录
     */
    public static boolean insertLiveRecord(String lno,String rno, String bno, Timestamp intime){
        if (!connectToDB) {
            System.out.println("数据库连接错误！");
            return false;
        }else{
            try {
//                statement = connection.createStatement();
//                String sql = "select * from liverecord";
//                resultSet = statement.executeQuery(sql);
//                String Lmax="00000000";
//                String lno="00000000";
//                while (resultSet.next()) {
//                    lno = resultSet.getString("LNo");
//                    if(lno.compareTo(Lmax)>0){
//                        Lmax=lno;
//                    }
//                }
//                int Lnum=Integer.valueOf(Lmax);
//                Lnum=Lnum+1;
//                String Lno=String.valueOf(Lnum);
//                int length=Lno.length();
//                String zero="0";
//                for(int i=0;i<8-length;i++){
//                    Lno=zero+Lno;
//                }
                statement=connection.createStatement();
                String sql1="INSERT INTO liverecord (InTime,OutTime,RNo,BNo,LNo) VALUES (?,?,?,?,?)";
                preparedStatement = connection.prepareStatement(sql1);
                preparedStatement.setTimestamp(1, intime);
                preparedStatement.setTimestamp(2, null);
                preparedStatement.setString(3, rno);
                preparedStatement.setString(4, bno);
                preparedStatement.setString(5, lno);
                int temp = preparedStatement.executeUpdate();
                preparedStatement.close();
                if (temp != 0)
                    return true;
                else {
                    return false;
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }
    public static String getNextLno(){
        if (!connectToDB) {
            System.out.println("数据库连接错误！");
            return null;
        }else {
            try {
                statement = connection.createStatement();
                String sql = "select * from livecertificate";
                resultSet = statement.executeQuery(sql);
                String Lmax = "00000000";
                String lno = "00000000";
                while (resultSet.next()) {
                    lno = resultSet.getString("LNo");
                    if (lno.compareTo(Lmax) > 0) {
                        Lmax = lno;
                    }
                }
                int Lnum = Integer.valueOf(Lmax);
                Lnum = Lnum + 1;
                String Lno = String.valueOf(Lnum);
                int length=Lno.length();
                String zero="0";
                for(int i=0;i<8-length;i++){
                    Lno=zero+Lno;
                }
                return Lno;
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }
    /**
     * TODO 插入入院证明
     */
    public static boolean insertLiveCertificate(String dno,String pno,Timestamp timestamp,String tno){
        if (!connectToDB) {
            System.out.println("数据库连接错误！");
            return false;
        }else{
            try {
                statement = connection.createStatement();
                String sql = "select * from livecertificate";
                resultSet = statement.executeQuery(sql);
                String Lmax="00000000";
                String lno="00000000";
                while (resultSet.next()) {
                    lno = resultSet.getString("LNo");
                    if(lno.compareTo(Lmax)>0){
                        Lmax=lno;
                    }
                }
                int Lnum=Integer.valueOf(Lmax);
                Lnum=Lnum+1;
                String Lno=String.valueOf(Lnum);
                int length=Lno.length();
                String zero="0";
                for(int i=0;i<8-length;i++){
                    Lno=zero+Lno;
                }
                statement=connection.createStatement();
                String sql1="INSERT INTO livecertificate (LNo,DNo,PNo,Time,PreInTime,PreOutTime,CanOut,TNo) VALUES (?,?,?,?,?,?,?,?)";
                preparedStatement = connection.prepareStatement(sql1);
                preparedStatement.setString(1, Lno);
                preparedStatement.setString(2, dno);
                preparedStatement.setString(3, pno);
                preparedStatement.setTimestamp(4, new Timestamp(System.currentTimeMillis()));
                preparedStatement.setTimestamp(5, timestamp);
                preparedStatement.setTimestamp(6, null);
                preparedStatement.setString(7, "0");
                preparedStatement.setString(8, tno);
                int temp = preparedStatement.executeUpdate();
                preparedStatement.close();
                if (temp != 0)
                    return true;
                else {
                    return false;
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }
    /**
     * TODO 插入治疗记录(挂号)
     */
    public static boolean insertTreatRecord(String dno,String pno){
        if (!connectToDB) {
            System.out.println("数据库连接错误！");
            return false;
        }else{
            try {
                statement = connection.createStatement();
                String sql = "select * from treatrecord";
                resultSet = statement.executeQuery(sql);
                String Tmax="00000000";
                String tno="00000000";
                while (resultSet.next()) {
                    tno = resultSet.getString("TNo");
                    if(tno.compareTo(Tmax)>0){
                        Tmax=tno;
                    }
                }
                int Tnum=Integer.valueOf(Tmax);
                Tnum=Tnum+1;
                String Tno=String.valueOf(Tnum);
                int length=Tno.length();
                String zero="0";
                for(int i=0;i<8-length;i++){
                    Tno=zero+Tno;
                }
                statement=connection.createStatement();
                String sql1="INSERT INTO treatrecord (TNo,Time,PNo,DNo,treatrecord.Case,Diagnosis,Treat) VALUES (?,?,?,?,?,?,?)";
                preparedStatement = connection.prepareStatement(sql1);
                preparedStatement.setString(1, Tno);
                preparedStatement.setTimestamp(2, new Timestamp(System.currentTimeMillis()));
                preparedStatement.setString(3, pno);
                preparedStatement.setString(4, dno);
                preparedStatement.setString(5, null);
                preparedStatement.setString(6, null);
                preparedStatement.setString(7, null);
                int temp = preparedStatement.executeUpdate();
                preparedStatement.close();
                if (temp != 0)
                    return true;
                else {
                    return false;
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }
    public static boolean insertTreatRecord(String dno,String pno,Timestamp timestamp){
        if (!connectToDB) {
            System.out.println("数据库连接错误！");
            return false;
        }else{
            try {
                statement = connection.createStatement();
                String sql = "select * from treatrecord";
                resultSet = statement.executeQuery(sql);
                String Tmax="00000000";
                String tno="00000000";
                while (resultSet.next()) {
                    tno = resultSet.getString("TNo");
                    if(tno.compareTo(Tmax)>0){
                        Tmax=tno;
                    }
                }
                int Tnum=Integer.valueOf(Tmax);
                Tnum=Tnum+1;
                String Tno=String.valueOf(Tnum);
                int length=Tno.length();
                String zero="0";
                for(int i=0;i<8-length;i++){
                    Tno=zero+Tno;
                }
                statement=connection.createStatement();
                String sql1="INSERT INTO treatrecord (TNo,Time,PNo,DNo,treatrecord.Case,Diagnosis,Treat) VALUES (?,?,?,?,?,?,?)";
                preparedStatement = connection.prepareStatement(sql1);
                preparedStatement.setString(1, Tno);
                preparedStatement.setTimestamp(2,timestamp);
                preparedStatement.setString(3, pno);
                preparedStatement.setString(4, dno);
                preparedStatement.setString(5, null);
                preparedStatement.setString(6, null);
                preparedStatement.setString(7, null);
                int temp = preparedStatement.executeUpdate();
                preparedStatement.close();
                if (temp != 0)
                    return true;
                else {
                    return false;
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }
    /**
     * TODO 插入住院发票
     */
    public static boolean insertInvoice(String lno){
        if (!connectToDB) {
            System.out.println("数据库连接错误！");
            return false;
        }else{
            try {
                statement = connection.createStatement();
                String sql = "select * from invoice";
                resultSet = statement.executeQuery(sql);
                String Imax="000000000000";
                String ino="000000000000";
                while (resultSet.next()) {
                    ino = resultSet.getString("INo");
                    if(ino.compareTo(Imax)>0){
                        Imax=ino;
                    }
                }
                int Inum=Integer.valueOf(Imax);
                Inum=Inum+1;
                String Ino=String.valueOf(Inum);
                int length=Ino.length();
                String zero="0";
                for(int i=0;i<12-length;i++){
                    Ino=zero+Ino;
                }
                resultSet=DataProcess.searchLiveRecord(lno);
                resultSet.next();
                long outtime=resultSet.getTimestamp("OutTime").getTime();
                long intime=resultSet.getTimestamp("InTime").getTime();
                ResultSet resultSet1=DataProcess.searchBedInformation(resultSet.getString("RNo"));
                resultSet1.next();
                Double standard=resultSet1.getDouble("Standard");
                Double money=((outtime-intime)/(24 * 60 * 60 * 1000)+1)*standard;
                statement=connection.createStatement();
                String sql1="INSERT INTO invoice (INo,Money,Status,PayTime,Time,LNo) VALUES (?,?,?,?,?,?)";
                preparedStatement = connection.prepareStatement(sql1);
                preparedStatement.setString(1, Ino);
                preparedStatement.setDouble(2, money);
                preparedStatement.setString(3, "0");
                preparedStatement.setTimestamp(4, null);
                preparedStatement.setTimestamp(5, new Timestamp(System.currentTimeMillis()));
                preparedStatement.setString(6, lno);
                int temp = preparedStatement.executeUpdate();
                preparedStatement.close();
                if (temp != 0)
                    return true;
                else {
                    return false;
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }
    /**
     * TODO 改变房间的价格
     */
    public static Boolean changeRoomStandard(String rno,Double standard){
        if (!connectToDB) {
            System.out.println("数据库连接错误！");
            return null;
        }else{
            try {
                statement=connection.createStatement();
                String sql="update sickroom set Standard='"+standard+"' where RNo ='"+rno+"'";
                if(statement.executeUpdate(sql)!=0){
                    return true;
                }else{
                    return false;
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }
    public static Boolean updateLogin(String dno){
        if (!connectToDB) {
            System.out.println("数据库连接错误！");
            return null;
        }else{
            try {
                statement=connection.createStatement();
                String sql="update login set password='"+dno+"' where id ='"+dno+"'";
                if(statement.executeUpdate(sql)!=0){
                    return true;
                }else{
                    return false;
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }
    public static boolean deleteRegister(String tno){
        if(!connectToDB){
            throw new IllegalStateException("数据库未连接！");
        }else{
            try {
                statement = connection.createStatement();
                String sql = "delete from treatrecord where TNo='"+tno+"'";
                if (statement.executeUpdate(sql) == 0) {
                    return false;
                } else {
                    return true;
                }
            }catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }
//    public static Doctor searchNowLive(String dno){
//        if (!connectToDB) {
//            System.out.println("数据库连接错误！");
//            return null;
//        }else{
//            try {
//                statement=connection.createStatement();
//                String sql="select * from doctor";
//                resultSet=statement.executeQuery(sql);
//                while(resultSet.next()) {
//                    String Dno = resultSet.getString("DNo");
//                    String Name=resultSet.getString("Name");
//                    String Sex=resultSet.getString("Sex");
//                    int Age=resultSet.getInt("Age");
//                    String Title=resultSet.getString("Title");
//                    String PhoneNum=resultSet.getString("PhoneNum");
//                    String Department=resultSet.getString("Department");
//                    if (Dno.equals(dno)) {
//                        Doctor doctor=new Doctor(Dno,Name,Sex,Age,Title,PhoneNum,Department);
//                        return doctor;
//                    }
//                }
//                return null;
//            } catch (SQLException e) {
//                throw new RuntimeException(e);
//            }
//        }
//    }
    public static boolean deleteBed(String rno,int bedNum,int oldbed){
        if(!connectToDB){
            throw new IllegalStateException("数据库未连接！");
        }else{
            try {
                statement = connection.createStatement();
                while(oldbed>bedNum){
                    bedNum++;
                    String sql1="delete from sickbed where RNo='"+rno+"' and BNo='"+bedNum+"'";;
                    statement.executeUpdate(sql1);
                }
                return true;
            }catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }
    public static boolean deleteDoctor(String dno){
        if(!connectToDB){
            throw new IllegalStateException("数据库未连接！");
        }else{
            try {
                statement = connection.createStatement();
                String sql1="delete from doctor where DNo='"+dno+"'";
                statement.executeUpdate(sql1);
                return true;
            }catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }
    public static boolean deleteLogin(String id){
        if(!connectToDB){
            throw new IllegalStateException("数据库未连接！");
        }else{
            try {
                statement = connection.createStatement();
                String sql1="delete from login where id='"+id+"'";
                statement.executeUpdate(sql1);
                return true;
            }catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }
    public static boolean insertBed(String rno,int bedNum,int oldbed){
        if(!connectToDB){
            throw new IllegalStateException("数据库未连接！");
        }else{
            try {
                statement=connection.createStatement();
                while(oldbed<bedNum){
                    oldbed++;
                    String sql1="INSERT INTO sickbed (RNo,BNo,Status) VALUES (?,?,?)";
                    preparedStatement = connection.prepareStatement(sql1);
                    preparedStatement.setString(1, rno);
                    preparedStatement.setString(2, String.valueOf(oldbed));
                    preparedStatement.setString(3, "0");
                    int temp = preparedStatement.executeUpdate();
                }
                return true;
            }catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }
    public static boolean insertLogin(String dno,String password){
        if(!connectToDB){
            throw new IllegalStateException("数据库未连接！");
        }else{
            try {
                statement=connection.createStatement();
                String sql1="INSERT INTO login (id,password,isReception) VALUES (?,?,?)";
                preparedStatement = connection.prepareStatement(sql1);
                preparedStatement.setString(1, dno);
                preparedStatement.setString(2, password);
                if(DataProcess.searchDoctor(dno).getDepartment().equals("服务台")) {
                    preparedStatement.setString(3, "1");
                }else{
                    preparedStatement.setString(3, "0");
                }
                int temp = preparedStatement.executeUpdate();
                return true;
            }catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }
    public static ResultSet listDoctor(){
        if(!connectToDB){
            throw new IllegalStateException("数据库未连接！");
        }else{
            try {
                statement = connection.createStatement();
                String sql="select * from doctor";
                return statement.executeQuery(sql);
            }catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }
    public static String getNewDno(){
        try {
            if (!connectToDB) {
                System.out.println("数据库连接错误！");
                return null;
            } else {
                statement = connection.createStatement();
                String sql = "select * from doctor";
                resultSet = statement.executeQuery(sql);
                String Imax = "00000000";
                String ino = "00000000";
                while (resultSet.next()) {
                    ino = resultSet.getString("DNo");
                    if (ino.compareTo(Imax) > 0) {
                        Imax = ino;
                    }
                }
                int Inum = Integer.valueOf(Imax);
                Inum = Inum + 1;
                String Ino = String.valueOf(Inum);
                int length = Ino.length();
                String zero = "0";
                for (int i = 0; i < 8 - length; i++) {
                    Ino = zero + Ino;
                }
                return Ino;
            }
        }catch (SQLException e){
            throw new RuntimeException(e);
        }
    }
    /**
     * TODO 插入住院发票
     */
    public static boolean insertDoctor(String name,String sex,String age,String title,String phonenum,String department){
        if (!connectToDB) {
            System.out.println("数据库连接错误！");
            return false;
        }else{
            try {
                statement = connection.createStatement();
                String sql = "select * from doctor";
                resultSet = statement.executeQuery(sql);
                String Imax="00000000";
                String ino="00000000";
                while (resultSet.next()) {
                    ino = resultSet.getString("DNo");
                    if(ino.compareTo(Imax)>0){
                        Imax=ino;
                    }
                }
                int Inum=Integer.valueOf(Imax);
                Inum=Inum+1;
                String Ino=String.valueOf(Inum);
                int length=Ino.length();
                String zero="0";
                for(int i=0;i<8-length;i++){
                    Ino=zero+Ino;
                }
                statement=connection.createStatement();
                String sql1="INSERT INTO doctor (DNo,Name,Sex,Age,Title,PhoneNum,Department) VALUES (?,?,?,?,?,?,?)";
                preparedStatement = connection.prepareStatement(sql1);
                preparedStatement.setString(1, Ino);
                preparedStatement.setString(2, name);
                preparedStatement.setString(3, sex);
                preparedStatement.setInt(4, Integer.valueOf(age));
                preparedStatement.setString(5, title);
                preparedStatement.setString(6, phonenum);
                preparedStatement.setString(7, department);
                int temp = preparedStatement.executeUpdate();
                preparedStatement.close();
                if (temp != 0)
                    return true;
                else {
                    return false;
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
