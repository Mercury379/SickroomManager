package com.hospital.hospitalsym.controller;

import com.hospital.hospitalsym.entity.BedInfo;
import com.hospital.hospitalsym.entity.Doctor;
import com.hospital.hospitalsym.service.SystemReceptionService;
import com.hospital.hospitalsym.service.impl.SystemReceptionServiceImpl;
import net.sf.json.JSONArray;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet("/rlive")
public class ReceptionLiveServlet extends HttpServlet {
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
                req.setAttribute("list",this.systemReceptionService.listLiveCertificate());
                req.getRequestDispatcher("receptionlivecertificate.jsp").forward(req,resp);
                break;
            case "search":
                req.setAttribute("list",this.systemReceptionService.searchLiveCertificate(lno,time,department));
                req.getRequestDispatcher("receptionlivecertificate.jsp").forward(req,resp);
                break;
            case "findByDormitoryId":
                String deparment=req.getParameter("dormitoryId");
                List<BedInfo> list=systemReceptionService.searchRoom(deparment);
                JSONArray jsonArray=JSONArray.fromObject(list);
                resp.setContentType("text/json;charset=UTF-8");
                resp.getWriter().write(jsonArray.toString());
                break;
            case "findByDormitoryId1":
                String rno=req.getParameter("studentId");
                List<BedInfo> list1=systemReceptionService.searchBed(rno);
                JSONArray jsonArray1=JSONArray.fromObject(list1);
                resp.setContentType("text/json;charset=UTF-8");
                resp.getWriter().write(jsonArray1.toString());
                break;
            case "live":
                String Rno=req.getParameter("studentId");
                String bno=req.getParameter("bno");
                String Lno=req.getParameter("telephone");
                systemReceptionService.insertLive(Lno,Rno,bno);
                resp.sendRedirect("/rlive?method=list");
                break;
        }
    }
}
