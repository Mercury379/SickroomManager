package com.hospital.hospitalsym.controller;

import com.hospital.hospitalsym.dao.DataProcess;
import com.hospital.hospitalsym.service.SystemAdminService;
import com.hospital.hospitalsym.service.impl.SystemAdminServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/admindoctor")

public class AdminDoctorServlet1 extends HttpServlet {
    private SystemAdminService systemAdminService=new SystemAdminServiceImpl();
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
                req.setAttribute("list",this.systemAdminService.listDoctor());
                req.getRequestDispatcher("admindoctor.jsp").forward(req,resp);
                break;
            case "search":
                String dname=req.getParameter("searchnamevalue");
                String Department1=req.getParameter("department");
                req.setAttribute("list",this.systemAdminService.searchDoctor(dname,Department1));
                req.getRequestDispatcher("admindoctor.jsp").forward(req,resp);
                break;
            case "update":
                String dno=req.getParameter("id");
                String name=req.getParameter("username");
                String sex=req.getParameter("password");
                String age=req.getParameter("name");
                String title=req.getParameter("gender");
                String phonenum=req.getParameter("telephone");
                String department=req.getParameter("status");
                systemAdminService.updateDoctor(dno,name,sex,age,title,phonenum,department);
                resp.sendRedirect("/admindoctor?method=list");
                break;
            case "delete":
                String Dno=req.getParameter("tno");
                systemAdminService.deleteDoctor(Dno);
                resp.sendRedirect("/admindoctor?method=list");
                break;
            case "add":
                String Name=req.getParameter("dname");
                String Sex=req.getParameter("dsex");
                String Age=req.getParameter("age");
                String Title=req.getParameter("title");
                String Phonenum=req.getParameter("dtelephone");
                String Department=req.getParameter("dormitoryId");
                systemAdminService.insertDoctor(Name,Sex,Age,Title,Phonenum,Department);
                resp.sendRedirect("/admindoctor?method=list");
                break;
        }
    }
}
