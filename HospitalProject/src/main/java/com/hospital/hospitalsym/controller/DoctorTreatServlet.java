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

@WebServlet("/dtreat")
public class DoctorTreatServlet extends HttpServlet{
    private SystemDoctorService systemDoctorService=new SystemDoctorServiceImpl();
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        this.doPost(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        String method=req.getParameter("method");
        String name=req.getParameter("patientnamevalue");
        String id=req.getParameter("patientidvalue");
        switch (method){
            case "list":
                req.setAttribute("list",this.systemDoctorService.listTreatPatient());
                req.getRequestDispatcher("doctortreat.jsp").forward(req,resp);
                break;
            case "search":
                req.setAttribute("list",this.systemDoctorService.searchTreatPatient(name,id));
                req.getRequestDispatcher("doctortreat.jsp").forward(req,resp);
                break;
            case "update":
                String Case=req.getParameter("pcase");
                String diagnosis=req.getParameter("diagosis");
                String treat=req.getParameter("treat");
                String tno=req.getParameter("id");
                systemDoctorService.updateTreatRecord(tno,Case,diagnosis,treat);
                resp.sendRedirect("/dtreat?method=list");
        }

    }
}
