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

@WebServlet("/dlivecanout")

public class DoctorCanOutServlet extends HttpServlet{
    private SystemDoctorService systemDoctorService=new SystemDoctorServiceImpl();
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        this.doPost(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        String name=req.getParameter("searchnamevalue");
        String id=req.getParameter("searchidvalue");
        String method=req.getParameter("method");
        switch (method){
            case "list":
                req.setAttribute("list",this.systemDoctorService.listLiveCanOut());
                req.getRequestDispatcher("doctorcanout.jsp").forward(req,resp);
                break;
            case "search":
                req.setAttribute("list",this.systemDoctorService.searchLiveCanOut(name,id));
                req.getRequestDispatcher("doctorcanout.jsp").forward(req,resp);
                break;
            case "update":
                String lno=req.getParameter("id");
                String preouttime=req.getParameter("preouttime");
                systemDoctorService.insertCanOut(lno,preouttime);
                resp.sendRedirect("/dlivecanout?method=list");
        }
    }
}
