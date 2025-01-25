package com.hospital.hospitalsym.controller;
import com.hospital.hospitalsym.dto.SystemDoctorDto;
import com.hospital.hospitalsym.dto.SystemPatientDto;
import com.hospital.hospitalsym.entity.Doctor;
import com.hospital.hospitalsym.service.SystemDoctorService;
import com.hospital.hospitalsym.service.SystemPatientService;
import com.hospital.hospitalsym.service.impl.SystemDoctorServiceImpl;
import com.hospital.hospitalsym.service.impl.SystemPatientServiceImpl;

import java.io.*;
import javax.servlet.ServletException;
import javax.servlet.http.*;
import javax.servlet.annotation.*;

@WebServlet("/treatinfo")
public class TreatInfoServlet extends HttpServlet{
    private SystemDoctorService systemDoctorService=new SystemDoctorServiceImpl();
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        this.doPost(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String method=req.getParameter("method");
        switch (method){
            case "list":
                req.setAttribute("list",this.systemDoctorService.listPatient(""));
                req.getRequestDispatcher("doctormanager.jsp").forward(req,resp);
                break;
            case "search":
                String patientid=req.getParameter("patientidvalue");
                String treatdate=req.getParameter("treatdatevalue");
                String tempstatus=req.getParameter("key");
                String status;
                switch (tempstatus){
                    case "noneed":
                        status="无需住院";
                        break;
                    case "notin":
                        status="还未住院";
                        break;
                    case "living":
                        status="正在住院";
                        break;
                    case "notout":
                        status="还未出院";
                        break;
                    case "out":
                        status="已经出院";
                        break;
                    default:
                        status="";
                }
                req.setAttribute("list",this.systemDoctorService.searchPatient(patientid,treatdate,status));
                req.getRequestDispatcher("doctormanager.jsp").forward(req,resp);
        }
    }
}
