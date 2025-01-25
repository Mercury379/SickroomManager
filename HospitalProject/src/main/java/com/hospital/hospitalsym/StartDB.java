package com.hospital.hospitalsym;
import com.hospital.hospitalsym.dao.*;
public class StartDB {
    public StartDB(){
        DataProcess.connectToDatabase();
    }
}
