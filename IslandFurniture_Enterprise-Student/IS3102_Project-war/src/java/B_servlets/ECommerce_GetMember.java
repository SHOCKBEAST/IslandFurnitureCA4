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
import javax.ws.rs.client.Invocation;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 *
 * @author nga_w
 */
@WebServlet(name = "ECommerce_GetMember", urlPatterns = {"/ECommerce_GetMember"})
public class ECommerce_GetMember extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        HttpSession session = request.getSession();
        String result = request.getParameter("goodMsg");
        
//        try {
//            String email = (String) session.getAttribute("memberEmail");
//            Client client = ClientBuilder.newClient();
//            WebTarget target = client.target("http://localhost:8080/IS3102_WebService-Student/webresources/entity.memberentity")
//                    .path("member")
//                    .queryParam("email", email);
//
//            Invocation.Builder invocationBuilder = target.request(MediaType.APPLICATION_JSON);
//            Response res = invocationBuilder.get();
//            if (res.getStatus() == 200) {
//                Member member = res.readEntity(new GenericType<Member>() {
//                });
//                session.setAttribute("member", member);
//                session.setAttribute("memberName", member.getName());
//                if(result != null){
//                response.sendRedirect("/IS3102_Project-war/B/SG/memberProfile.jsp?goodMsg=Account updated successfully.");
//                }
//                else{
//                    response.sendRedirect("/IS3102_Project-war/B/SG/memberProfile.jsp");
//                }
//                out.println(member.getName());
//                out.println(member.getCity());
//            }else{
//                out.println("Error!");
//            }
        try {
            String email = (String) session.getAttribute("memberEmail");

            Member member = getMemberRESTful(email);
            out.print(member);
            session.setAttribute("member", member);
            session.setAttribute("memberName",member.getName());
            
            if(result != null){
                response.sendRedirect("/IS3102_Project-war/B/SG/memberProfile.jsp?goodMsg=Account updated successfully.");
                }
                else{
                    response.sendRedirect("/IS3102_Project-war/B/SG/memberProfile.jsp");
                }
        } catch (Exception Ex) {
            out.println(Ex);
            Ex.printStackTrace();
        }
    }

    private Member getMemberRESTful(String email) {
        Client client = ClientBuilder.newClient();
        WebTarget target = client.target("http://localhost:8080/IS3102_WebService-Student/webresources/entity.memberentity")
                .path("member")
                .queryParam("email", email);

        Invocation.Builder invocationBuilder = target.request(MediaType.APPLICATION_JSON);
        Response res = invocationBuilder.get();
        if (res.getStatus() == 200) {
            return res.readEntity(new GenericType<Member>() {
            });
        } else {
            return null;
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
