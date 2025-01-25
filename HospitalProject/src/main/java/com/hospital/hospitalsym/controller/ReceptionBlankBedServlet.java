package com.hospital.hospitalsym.controller;

import com.hospital.hospitalsym.service.SystemReceptionService;
import com.hospital.hospitalsym.service.impl.SystemReceptionServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/rblankbed")

public class ReceptionBlankBedServlet extends HttpServlet {
    SystemReceptionService systemReceptionService=new SystemReceptionServiceImpl();
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        this.doPost(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //super.doPost(req, resp);
        req.setCharacterEncoding("UTF-8");
        String method=req.getParameter("method");
        String department=req.getParameter("department");
        String time=req.getParameter("searchdatevalue");
        String rno=req.getParameter("rno");
        switch (method){
            case "list":
                req.setAttribute("list",systemReceptionService.listBlankBed());
                req.getRequestDispatcher("receptionblankbed.jsp").forward(req,resp);
                break;
            case "search":
                req.setAttribute("list",systemReceptionService.searchBlankBed(department,rno,time));
                req.getRequestDispatcher("receptionblankbed.jsp").forward(req,resp);
                break;
        }
    }
}
