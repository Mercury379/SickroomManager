package com.hospital.hospitalsym.controller;
import com.hospital.hospitalsym.dao.DataProcess;
import com.hospital.hospitalsym.dto.SystemDoctorDto;
import com.hospital.hospitalsym.dto.SystemPatientDto;
import com.hospital.hospitalsym.dto.SystemReceptionDto;
import com.hospital.hospitalsym.service.SystemAdminService;
import com.hospital.hospitalsym.service.SystemDoctorService;
import com.hospital.hospitalsym.service.SystemPatientService;
import com.hospital.hospitalsym.service.SystemReceptionService;
import com.hospital.hospitalsym.service.impl.SystemAdminServiceImpl;
import com.hospital.hospitalsym.service.impl.SystemDoctorServiceImpl;
import com.hospital.hospitalsym.service.impl.SystemPatientServiceImpl;
import com.hospital.hospitalsym.service.impl.SystemReceptionServiceImpl;

import java.io.*;
import javax.servlet.ServletException;
import javax.servlet.http.*;
import javax.servlet.annotation.*;

@WebServlet("/logindoctor")
public class LoginDoctorServlet extends HttpServlet{
    public SystemDoctorService systemDoctorService=new SystemDoctorServiceImpl();
    public SystemReceptionService systemReceptionService=new SystemReceptionServiceImpl();
    public SystemAdminService systemAdminService=new SystemAdminServiceImpl();
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        this.doPost(req,resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String method=req.getParameter("method");
        if(method!=null&&method.equals("logout")){
            req.getSession().invalidate();
            resp.sendRedirect("/login.jsp");
        }else{
            String doctorId = req.getParameter("doctorid");
            String password=req.getParameter("doctorpassword");
            SystemDoctorDto systemDoctorDto=this.systemDoctorService.loginDoctor(doctorId,password);
            SystemReceptionDto systemReceptionDto=this.systemReceptionService.loginReception(doctorId,password);
            if(systemAdminService.loginAdmin(doctorId,password)){
                HttpSession session = req.getSession();
                session.setAttribute("newDno", DataProcess.getNewDno());
                resp.sendRedirect("/systemadmin.jsp");
            }else
            {
                if (systemDoctorDto.getMsg().equals("医生不存在") && systemReceptionDto.equals("服务台工作人员不存在")) {
                    req.setAttribute("doctoridError", "该用户不存在");
                    req.getRequestDispatcher("login.jsp").forward(req, resp);
                } else if (systemDoctorDto.getMsg().equals("密码输入错误") || systemReceptionDto.getMsg().equals("密码输入错误")) {
                    req.setAttribute("passwordError", "密码输入错误");
                    req.getRequestDispatcher("login.jsp").forward(req, resp);
                } else {
//            System.out.println("登陆成功");
                    if (systemDoctorDto.getMsg().equals("登陆成功")) {
                        HttpSession session = req.getSession();
                        session.setAttribute("systemDoctor", systemDoctorDto.getDoctor());
                        resp.sendRedirect("/systemdoctor.jsp");
                    } else {
                        HttpSession session = req.getSession();
                        session.setAttribute("systemReception", systemReceptionDto.getReception());
                        resp.sendRedirect("/systemreception.jsp");
                    }
                }
            }
        }
    }
}
