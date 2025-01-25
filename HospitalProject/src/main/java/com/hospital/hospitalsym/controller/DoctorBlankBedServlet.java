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

@WebServlet("/dblankbed")

public class DoctorBlankBedServlet extends HttpServlet{
    private SystemDoctorService systemDoctorService=new SystemDoctorServiceImpl();
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        this.doPost(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String method=req.getParameter("method");
        String time=req.getParameter("searchdatevalue");
        String rno=req.getParameter("searchvalue");
        switch (method){
            case "list":
                req.setAttribute("list",this.systemDoctorService.listBlankBed());
                req.getRequestDispatcher("doctorblankbed.jsp").forward(req,resp);
                break;
            case "search":
                req.setAttribute("list",this.systemDoctorService.searchBlankBed(time,rno));
                req.getRequestDispatcher("doctorblankbed.jsp").forward(req,resp);
        }
    }
}
