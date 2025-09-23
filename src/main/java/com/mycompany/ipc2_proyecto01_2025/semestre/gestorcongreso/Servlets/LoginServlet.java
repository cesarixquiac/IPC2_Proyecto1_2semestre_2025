/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package com.mycompany.ipc2_proyecto01_2025.semestre.gestorcongreso.Servlets;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.mycompany.ipc2_proyecto01_2025.semestre.gestorcongreso.Db.DatabaseConnectionSingleStone;
import java.sql.* ;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.http.*;


/**
 *
 * @author cesar
 */
@WebServlet(name = "LoginServlet", urlPatterns = {"/LoginServlet"})
public class LoginServlet extends HttpServlet {

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
            /* TODO output your page here. You may use following sample code. */
            // Establecer el tipo de contenido como HTML
        response.setContentType("text/html;charset=UTF-8");

        // Obtener el RequestDispatcher para la página JSP de login
        RequestDispatcher dispatcher = request.getRequestDispatcher("/Login.jsp");

        // Redirigir la solicitud al JSP de login
        dispatcher.forward(request, response);
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
        
             String correo = request.getParameter("correo");
            String password = request.getParameter("password");
            
            Connection con = null;
            
             try {
            con = DatabaseConnectionSingleStone.getInstance().getConnection();

            String sql = "SELECT id, nombre_completo, rol FROM usuario " +
                         "WHERE correo=? AND password=? AND activo=TRUE";

            PreparedStatement ps = con.prepareStatement(sql);   
            ps.setString(1, correo);
            ps.setString(2, password); 

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                // Usuario valido
                HttpSession session = request.getSession();
                session.setAttribute("usuarioId", rs.getInt("id"));
                session.setAttribute("nombre", rs.getString("nombre_completo"));
                session.setAttribute("rol", rs.getString("rol"));

                // Redirigir segun rol
                String rol = rs.getString("rol");
                switch (rol) {
                    case "ADMIN_SISTEMA":
                        response.sendRedirect("AdminSistema.jsp");
                        break;
                    case "ADMIN_CONGRESO":
                        response.sendRedirect("Congreso.jsp");
                        break;
                    default:
                        response.sendRedirect("Participante.jsp");
                        break;
                }

            } else {
                // Usuario o contraseña incorrectos
                request.setAttribute("error", "Correo o contraseña incorrectos");
                request.getRequestDispatcher("Login.jsp").forward(request, response);
            }

        } catch (SQLException e) {
            throw new ServletException("Error al conectar a la base de datos", e);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(LoginServlet.class.getName()).log(Level.SEVERE, null, ex);
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
