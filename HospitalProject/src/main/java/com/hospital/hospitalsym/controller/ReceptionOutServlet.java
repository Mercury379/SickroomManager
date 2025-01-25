package com.hospital.hospitalsym.controller;
import com.hospital.hospitalsym.service.SystemReceptionService;
import com.hospital.hospitalsym.service.impl.SystemReceptionServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/rcanout")

public class ReceptionOutServlet extends HttpServlet{
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
        String time=req.getParameter("searchdatevalue");
        String lno=req.getParameter("searchidvalue");
        switch (method){
            case "list":
                req.setAttribute("list",this.systemReceptionService.listCanOut());
                req.getRequestDispatcher("receptionout.jsp").forward(req,resp);
                break;
            case "search":
                req.setAttribute("list",this.systemReceptionService.searchCanOut(lno,time,department));
                req.getRequestDispatcher("receptionout.jsp").forward(req,resp);
                break;
            case "out":
                String Lno=req.getParameter("id");
                systemReceptionService.roomOut(Lno);
                resp.sendRedirect("/rcanout?method=list");
                break;
        }
    }
}
