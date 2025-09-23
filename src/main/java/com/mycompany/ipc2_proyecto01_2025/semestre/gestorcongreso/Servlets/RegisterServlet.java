/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package com.mycompany.ipc2_proyecto01_2025.semestre.gestorcongreso.Servlets;

import com.mycompany.ipc2_proyecto01_2025.semestre.gestorcongreso.Db.DatabaseConnectionSingleStone;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author cesar
 */
@WebServlet(name = "RegisterServlet", urlPatterns = {"/RegisterServlet"})
public class RegisterServlet extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {

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

    String nombreCompleto = request.getParameter("nombre_completo");
    String correo = request.getParameter("correo");
    String password = request.getParameter("password");
    String telefono = request.getParameter("telefono");
    String identificacion = request.getParameter("identificacion");
    String organizacion = request.getParameter("organizacion");
    String foto = request.getParameter("foto");
    String rol = request.getParameter("rol");
    
    System.out.println(nombreCompleto + " " + correo);

    Connection con = null;
    PreparedStatement ps = null;

    try {
        con = DatabaseConnectionSingleStone.getInstance().getConnection();
        con.setAutoCommit(false); 

        String sql = "INSERT INTO usuario (nombre_completo, correo, password, telefono, identificacion, organizacion, foto, rol, activo, saldo) "
                   + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, TRUE, 0.00)";
        ps = con.prepareStatement(sql);
        ps.setString(1, nombreCompleto);
        ps.setString(2, correo);
        ps.setString(3, password);
        ps.setString(4, telefono);
        ps.setString(5, identificacion);
        ps.setString(6, organizacion);
        ps.setString(7, foto);
        ps.setString(8, rol);

        int filas = ps.executeUpdate();

        if (filas > 0) {
            con.commit(); 
            response.sendRedirect("Login.jsp");
            return; 
        } else {
            con.rollback(); 
            request.setAttribute("error", "No se pudo registrar el usuario.");
            request.getRequestDispatcher("Register.jsp").forward(request, response);
            return;
        }
    } catch (SQLException | ClassNotFoundException e) {
        try {
            if (con != null) con.rollback(); 
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        e.printStackTrace();
        request.setAttribute("error", "Error al registrar el usuario: " + e.getMessage());
        request.getRequestDispatcher("Register.jsp").forward(request, response);
        return;
    } finally {
        try {
            if (ps != null) ps.close();
            if (con != null) con.setAutoCommit(true); 
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
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
