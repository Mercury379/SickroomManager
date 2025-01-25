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

@WebServlet("/ptreat")

public class PatientTreatServlet extends HttpServlet{
    SystemPatientService  systemPatientService=new SystemPatientServiceImpl();
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        this.doPost(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        String department=req.getParameter("department");
        String starttime=req.getParameter("starttime");
        String endtime=req.getParameter("endtime");
        String method=req.getParameter("method");
        switch (method){
            case "list":
                req.setAttribute("list",this.systemPatientService.listTreatRecord());
                req.getRequestDispatcher("patienttreat.jsp").forward(req,resp);
                break;
            case "search":
                req.setAttribute("list",this.systemPatientService.searchTreatRecord(department,starttime,endtime));
                req.getRequestDispatcher("patienttreat.jsp").forward(req,resp);
        }
    }
}
