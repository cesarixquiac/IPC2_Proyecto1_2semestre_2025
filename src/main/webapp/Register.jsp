<%-- 
    Document   : Register
    Created on : 23 sept 2025, 11:57:02
    Author     : cesar
--%>

<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.sql.*, com.mycompany.ipc2_proyecto01_2025.semestre.gestorcongreso.Db.DatabaseConnectionSingleStone" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Registro de Usuario</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body>
<div class="container mt-5">
    <h2 class="mb-4">Registro de Usuario</h2>

    <% 
        // Obtener instituciones desde la base de datos
        Connection con = DatabaseConnectionSingleStone.getInstance().getConnection();
        PreparedStatement ps = null;
        ResultSet rs = null;
    %>

    <form action="RegisterServlet" method="post" >
        <div class="mb-3">
            <label for="nombre" class="form-label">Nombre completo</label>
            <input type="text" class="form-control" id="nombre_completo" name="nombre_completo" required>
        </div>

        <div class="mb-3">
            <label for="correo" class="form-label">Correo electrónico</label>
            <input type="email" class="form-control" id="correo" name="correo" required>
        </div>

        <div class="mb-3">
            <label for="password" class="form-label">Contraseña</label>
            <input type="password" class="form-control" id="password" name="password" required>
        </div>

        <div class="mb-3">
            <label for="telefono" class="form-label">Teléfono</label>
            <input type="text" class="form-control" id="telefono" name="telefono">
        </div>

        <div class="mb-3">
            <label for="identificacion" class="form-label">Identificación</label>
            <input type="text" class="form-control" id="identificacion" name="identificacion">
        </div>

        <div class="mb-3">
            <label for="organizacion" class="form-label">Organización / Institución</label>
            <select class="form-select" id="organizacion" name="organizacion" required>
                <option value="">Seleccione una institución</option>
                <%
                    try {
                        ps = con.prepareStatement("SELECT nombre FROM institucion");
                        rs = ps.executeQuery();
                        while (rs.next()) {
                            String nombreInst = rs.getString("nombre");
                %>
                            <option value="<%=nombreInst%>"><%=nombreInst%></option>
                <%
                        }
                    } catch (SQLException e) {
                        out.println("<option value=''>Error cargando instituciones</option>");
                        e.printStackTrace();
                    } finally {
                        if(rs != null) rs.close();
                        if(ps != null) ps.close();
                    }
                %>
            </select>
        </div>

        <div class="mb-3">
            <label for="foto" class="form-label">Foto de perfil</label>
          
        </div>

        <div class="mb-3">
            <label for="rol" class="form-label">Rol</label>
            <select class="form-select" id="rol" name="rol" required>
                <option value="PARTICIPANTE" selected>Participante</option>
                <option value="ADMIN_CONGRESO">Administrador de Congreso</option>
                <option value="COMITE">Comité</option>
            </select>
        </div>

        <button type="submit" class="btn btn-primary">Registrar</button>
    </form>
</div>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>

