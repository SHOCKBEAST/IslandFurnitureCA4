/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package B_servlets;

import HelperClasses.Member;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 *
 * @author nga_w
 */
@WebServlet(name = "ECommerce_MemberEditProfileServlet", urlPatterns = {"/ECommerce_MemberEditProfileServlet"})
public class ECommerce_MemberEditProfileServlet extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");

        PrintWriter out = response.getWriter();
        HttpSession session = request.getSession();

        try {
            String email = (String) session.getAttribute("memberEmail");
            String name = request.getParameter("name");
            String phone = request.getParameter("phone");
            String city = request.getParameter("country");
            String address = request.getParameter("address");
            int securityQuestion = Integer.parseInt(request.getParameter("securityQuestion"));
            String securityAnswer = request.getParameter("securityAnswer");
            int age = Integer.parseInt(request.getParameter("age"));
            int income = Integer.parseInt(request.getParameter("income"));
            String password = request.getParameter("password");
            String repassword = request.getParameter("repassword");
            String serviceLevelAgreementStr = request.getParameter("serviceLevelAgreement");
            int serviceLevelAgreement = 0;
            
            if(serviceLevelAgreementStr != null){
                serviceLevelAgreement = 1;
            }
            
            String result = updateMemberRESTful(name, phone, city, address, securityQuestion,
                    securityAnswer, age, income, serviceLevelAgreement, password, repassword, email);
            out.println(result);

//            out.print("-------");
//            out.println(name);
//            //out.println(email);
//            //out.println(phone);
//            //out.println(address);
//            out.println(securityQuestion);
            //out.println(password);
            //out.println(repassword);
            out.println(city);
            
            if (result == "Updated") {
                
                response.sendRedirect("ECommerce_GetMember?goodMsg=" + result);
            } else {
                
                response.sendRedirect("/IS3102_Project-war/B/SG/memberLogin.jsp?errMsg=" + result);
            }
        } catch (Exception Ex) {
            out.println(Ex);
            Ex.printStackTrace();
        }
    }

    private String updateMemberRESTful(String name, String phone, String city,
            String address, int securityQuestion, String securityAnswer,
            int age, int income,int serviceLevelAgreement, String password,
            String repassword, String email) {
        
        Client client = ClientBuilder.newClient();
        WebTarget target = client.target("http://localhost:8080/IS3102_WebService-Student/webresources/entity.memberentity")
                .path("memberUpdate")
                .queryParam("name", name)
                .queryParam("phone", phone)
                .queryParam("city", city)
                .queryParam("address", address)
                .queryParam("securityQuestion", securityQuestion)
                .queryParam("securityAnswer", securityAnswer)
                .queryParam("age", age)
                .queryParam("income", income)
                .queryParam("serviceLevelAgreement", serviceLevelAgreement)
                .queryParam("password", password)
                .queryParam("repassword", repassword)
                .queryParam("email", email);

        Invocation.Builder invocationBuilder = target.request(MediaType.APPLICATION_JSON);
        Response res = invocationBuilder.put(Entity.entity("", "application/json"));
        if (res.getStatus() == 204) {
            return "Updated";
        } else {
            return "Update Failed" + res.getStatus() + res;
        }
    }

// <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
