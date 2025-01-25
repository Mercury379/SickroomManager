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

@WebServlet("/patientpay")

public class PatientPayServlet extends HttpServlet{
    SystemPatientService  systemPatientService=new SystemPatientServiceImpl();
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        this.doPost(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        String status=req.getParameter("key");
        String Rno=req.getParameter("roomvalue");
        String method=req.getParameter("method");
        switch (method){
            case "list":
                req.setAttribute("list",this.systemPatientService.listBill());
                req.getRequestDispatcher("patientpay.jsp").forward(req,resp);
                break;
            case "search":
                req.setAttribute("list",this.systemPatientService.searchBill(status,Rno));
                req.getRequestDispatcher("patientpay.jsp").forward(req,resp);
                break;
            case "print":
                resp.sendRedirect("/patientpay?method=list");
                break;
            case "pay":
                String ino=req.getParameter("invoice");
                systemPatientService.payInvoice(ino);
                resp.sendRedirect("/patientpay?method=list");
                break;
        }
    }
}
