package com.hospital.hospitalsym.controller;


import com.hospital.hospitalsym.service.SystemReceptionService;
import com.hospital.hospitalsym.service.impl.SystemReceptionServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/rliverecord")
public class ReceptionRecordServlet extends HttpServlet {
    SystemReceptionService systemReceptionService=new SystemReceptionServiceImpl();
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
                req.setAttribute("list",systemReceptionService.listLiveRecord(""));
                req.getRequestDispatcher("receptionLiveRecord.jsp").forward(req,resp);
                break;
            case "search":
                String status=req.getParameter("key");
                String name=req.getParameter("patientnamevalue");
                req.setAttribute("list",systemReceptionService.searchLiveRecord(name,status));
                req.getRequestDispatcher("receptionLiveRecord.jsp").forward(req,resp);
                break;
        }
    }
}
