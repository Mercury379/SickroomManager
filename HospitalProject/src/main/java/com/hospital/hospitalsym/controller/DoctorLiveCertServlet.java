package com.hospital.hospitalsym.controller;
import com.hospital.hospitalsym.dto.SystemDoctorDto;
import com.hospital.hospitalsym.dto.SystemPatientDto;
import com.hospital.hospitalsym.entity.Doctor;
import com.hospital.hospitalsym.service.SystemDoctorService;
import com.hospital.hospitalsym.service.SystemPatientService;
import com.hospital.hospitalsym.service.impl.SystemDoctorServiceImpl;
import com.hospital.hospitalsym.service.impl.SystemPatientServiceImpl;

import java.io.*;
import javax.naming.Name;
import javax.servlet.ServletException;
import javax.servlet.http.*;
import javax.servlet.annotation.*;

@WebServlet("/dlivecertificate")

public class DoctorLiveCertServlet extends HttpServlet{
    private SystemDoctorService systemDoctorService=new SystemDoctorServiceImpl();
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        this.doPost(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        String method=req.getParameter("method");
        String name=req.getParameter("searchnamevalue");
        switch (method){
            case "list":
                req.setAttribute("list",this.systemDoctorService.listNotCertPatient());
                req.getRequestDispatcher("doctorlivecertificate.jsp").forward(req,resp);
                break;
            case "search":
                req.setAttribute("list",this.systemDoctorService.searchNotCertPatient(name));
                req.getRequestDispatcher("doctorlivecertificate.jsp").forward(req,resp);
                break;
            case "insert":
                String preintime=req.getParameter("preintime");
                String tno=req.getParameter("tno");
                systemDoctorService.insertLiveCertificate(preintime,tno);
                resp.sendRedirect("/dlivecertificate?method=list");
//                req.setAttribute("list",this.systemDoctorService.listNotCertPatient());
//                req.getRequestDispatcher("doctorlivecertificate.jsp").forward(req,resp);
                break;
        }
    }
}
