package com.hospital.hospitalsym.controller;

import com.hospital.hospitalsym.service.SystemReceptionService;
import com.hospital.hospitalsym.service.impl.SystemReceptionServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/rupdateroom")

public class ReceptionUpdateRoomServlet extends HttpServlet {
    SystemReceptionService systemReceptionService=new SystemReceptionServiceImpl();
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        this.doPost(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        String department=req.getParameter("department");
        String method=req.getParameter("method");
        String floor=req.getParameter("floor");
        switch (method){
            case "list":
                req.setAttribute("list",this.systemReceptionService.listRoom());
                req.getRequestDispatcher("receptionroom.jsp").forward(req,resp);
                break;
            case "search":
                req.setAttribute("list",this.systemReceptionService.searchRoom(department,floor));
                req.getRequestDispatcher("receptionroom.jsp").forward(req,resp);
                break;
            case "update":
                String rno=req.getParameter("id");
                String standard=req.getParameter("password");
                String department1=req.getParameter("name");
                String bnum=req.getParameter("telephone");
                systemReceptionService.updateRoom(rno,standard,department1,bnum);
                resp.sendRedirect("/rupdateroom?method=list");
                break;
        }
    }
}
