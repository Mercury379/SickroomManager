package com.hospital.hospitalsym.controller;

import com.hospital.hospitalsym.service.SystemPatientService;
import com.hospital.hospitalsym.service.impl.SystemPatientServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/pcanout")
public class PatientCanOutServlet extends HttpServlet {
    SystemPatientService systemPatientService=new SystemPatientServiceImpl();
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        this.doPost(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        String status=req.getParameter("key");
        String method=req.getParameter("method");
        switch (method){
            case "list":
                req.setAttribute("list",this.systemPatientService.listCanOut());
                req.getRequestDispatcher("patientcanout.jsp").forward(req,resp);
                break;
            case "search":
                req.setAttribute("list",this.systemPatientService.searchCanOut(status));
                req.getRequestDispatcher("patientcanout.jsp").forward(req,resp);
                break;
            case "print":
                resp.sendRedirect("/pcanout?method=list");
        }
    }
}
