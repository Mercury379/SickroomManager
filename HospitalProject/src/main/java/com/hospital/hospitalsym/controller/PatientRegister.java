package com.hospital.hospitalsym.controller;
import com.hospital.hospitalsym.dto.SystemDoctorDto;
import com.hospital.hospitalsym.dto.SystemPatientDto;
import com.hospital.hospitalsym.entity.Doctor;
import com.hospital.hospitalsym.service.SystemDoctorService;
import com.hospital.hospitalsym.service.SystemPatientService;
import com.hospital.hospitalsym.service.impl.SystemDoctorServiceImpl;
import com.hospital.hospitalsym.service.impl.SystemPatientServiceImpl;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import java.io.*;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.*;
import javax.servlet.annotation.*;

@WebServlet("/pregister")

public class PatientRegister extends HttpServlet{
    SystemPatientService  systemPatientService=new SystemPatientServiceImpl();
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        this.doPost(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        String status=req.getParameter("key");
        String department=req.getParameter("department");
        String method=req.getParameter("method");
        switch (method){
            case "list":
                req.setAttribute("list",this.systemPatientService.listRegister());
                req.getRequestDispatcher("patientregister.jsp").forward(req,resp);
                break;
            case "search":
                req.setAttribute("list",this.systemPatientService.searchRegister(status,department));
                req.getRequestDispatcher("patientregister.jsp").forward(req,resp);
                break;
            case "print":
                resp.sendRedirect("/pregister?method=list");
                break;
            case "delete":
                String tno=req.getParameter("tno");
                systemPatientService.deleteRegister(tno);
                resp.sendRedirect("/pregister?method=list");
                break;
            case "register":
                String time=req.getParameter("preintime");
                String dno=req.getParameter("studentId");
                if(time.equals("")) {
                    systemPatientService.insertRegister(dno);
                }else{
                    systemPatientService.insertRegister(dno,time);
                }
                resp.sendRedirect("/pregister?method=list");
                break;
            case "findByDormitoryId":
                String deparment=req.getParameter("dormitoryId");
                List<Doctor> list=systemPatientService.listAllDepartment(deparment);
                JSONArray jsonArray=JSONArray.fromObject(list);
                resp.setContentType("text/json;charset=UTF-8");
                resp.getWriter().write(jsonArray.toString());
                break;
        }
    }
}

