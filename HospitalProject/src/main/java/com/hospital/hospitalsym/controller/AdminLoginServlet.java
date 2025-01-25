package com.hospital.hospitalsym.controller;

import com.hospital.hospitalsym.dao.DataProcess;
import com.hospital.hospitalsym.entity.Doctor;
import com.hospital.hospitalsym.service.SystemAdminService;
import com.hospital.hospitalsym.service.SystemPatientService;
import com.hospital.hospitalsym.service.impl.SystemAdminServiceImpl;
import com.hospital.hospitalsym.service.impl.SystemPatientServiceImpl;
import net.sf.json.JSONArray;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet("/adminlogin")
public class AdminLoginServlet extends HttpServlet {
    private SystemAdminService systemAdminService=new SystemAdminServiceImpl();
    SystemPatientService systemPatientService=new SystemPatientServiceImpl();
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        this.doPost(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        String method=req.getParameter("method");
        switch (method){
            case "list":
                req.setAttribute("list",this.systemAdminService.listLoginInfo());
                req.getRequestDispatcher("adminlogin.jsp").forward(req,resp);
                break;
            case "search":
                String dname=req.getParameter("searchnamevalue");
                String Department1=req.getParameter("department");
                req.setAttribute("list",this.systemAdminService.searchLoginInfo(dname,Department1));
                req.getRequestDispatcher("adminlogin.jsp").forward(req,resp);
                break;
            case "update":
                String Dno=req.getParameter("id");
                DataProcess.updateLogin(Dno);
                resp.sendRedirect("/adminlogin?method=list");
                break;
            case "delete":
                String dno=req.getParameter("tno");
                DataProcess.deleteLogin(dno);
                resp.sendRedirect("/adminlogin?method=list");
                break;
            case "findByDormitoryId":
                String deparment=req.getParameter("dormitoryId");
                List<Doctor> list=systemPatientService.listAllDepartment(deparment);
                JSONArray jsonArray=JSONArray.fromObject(list);
                resp.setContentType("text/json;charset=UTF-8");
                resp.getWriter().write(jsonArray.toString());
                break;
            case "add":
                String dno1=req.getParameter("studentId");
                String password1=req.getParameter("ppp");
                DataProcess.insertLogin(dno1,password1);
                resp.sendRedirect("/adminlogin?method=list");
                break;
        }
    }
}
