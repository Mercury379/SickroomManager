package com.hospital.hospitalsym.controller;
import com.hospital.hospitalsym.dao.DataProcess;
import com.hospital.hospitalsym.dto.SystemPatientDto;
import com.hospital.hospitalsym.service.SystemPatientService;
import com.hospital.hospitalsym.service.impl.SystemPatientServiceImpl;

import java.io.*;
import javax.servlet.ServletException;
import javax.servlet.http.*;
import javax.servlet.annotation.*;

@WebServlet("/loginpatient")
public class LoginPatientServlet extends HttpServlet{
    static String pno;
    private SystemPatientService systemPatientService=new SystemPatientServiceImpl();
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        this.doPost(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        String method=req.getParameter("method");
        String patientId = req.getParameter("patientid");
        SystemPatientDto systemPatientDto=this.systemPatientService.loginPatient(patientId);
        if(method.equals("new")){
            pno=DataProcess.searchNewPno();
            req.setAttribute("pno", pno);
            req.getRequestDispatcher("signup.jsp").forward(req, resp);
        } else if (method.equals("newpatient")) {
            String name=req.getParameter("name");
            String sex=req.getParameter("sex");
            String age=req.getParameter("age");
            String address=req.getParameter("address");
            String phonenum=req.getParameter("phonenum");
            systemPatientService.inseratPatient(name,sex,age,address,phonenum);
            systemPatientDto.setPatient(DataProcess.searchPatient(pno));
            systemPatientService.setPatient(DataProcess.searchPatient(pno));
            HttpSession session = req.getSession();
            session.setAttribute("systemPatient", systemPatientDto.getPatient());
            resp.sendRedirect("/systempatient.jsp");
//            req.setAttribute("systemPatient",systemPatientDto.getPatient());
//            req.getRequestDispatcher("/systempatient.jsp").forward(req,resp);
        } else if(method!=null&&method.equals("logout")){
            req.getSession().invalidate();
            resp.sendRedirect("/login.jsp");
        }else {
            if (systemPatientDto.getMsg().equals("病案号不存在")) {
                req.setAttribute("patientidError", "病案号不存在");
                req.getRequestDispatcher("login.jsp").forward(req, resp);
            } else {
                HttpSession session = req.getSession();
                session.setAttribute("systemPatient", systemPatientDto.getPatient());
                resp.sendRedirect("/systempatient.jsp");
            }
        }
    }
}
