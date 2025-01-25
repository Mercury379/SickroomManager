package com.hospital.hospitalsym.controller;
import com.hospital.hospitalsym.dto.SystemPatientDto;
import com.hospital.hospitalsym.service.SystemPatientService;
import com.hospital.hospitalsym.service.impl.SystemPatientServiceImpl;

import java.io.*;
import javax.servlet.ServletException;
import javax.servlet.http.*;
import javax.servlet.annotation.*;

@WebServlet("/plivecertificate")

public class PatientLiveCertServlet extends HttpServlet{
    SystemPatientService  systemPatientService=new SystemPatientServiceImpl();
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        this.doPost(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        String status=req.getParameter("key");
        String method=req.getParameter("method");
        switch (method) {
            case "list":
                req.setAttribute("list", this.systemPatientService.listLiveCert());
                req.getRequestDispatcher("patientlivecertificate.jsp").forward(req, resp);
                break;
            case "search":
                req.setAttribute("list", this.systemPatientService.searchLiveCert(status));
                req.getRequestDispatcher("patientlivecertificate.jsp").forward(req, resp);
                break;
            case "print":
                resp.sendRedirect("/plivecertificate?method=list");
                break;
        }
    }
}
