<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Registrar Alquiler</title>
</head>
<body>
    <h1>Formulario de Registro de Alquiler</h1>

    <c:if test="${not empty error}">
        <div style="color: red; font-weight: bold;">
            ${error}
        </div>
    </c:if>

    <form method="post" action="${pageContext.request.contextPath}/registrarAlquiler">
        <label for="cliente">Cliente:</label>
        <select name="clienteId" id="cliente" required>
            <option value="">-- Seleccione un cliente --</option>
            <c:forEach var="cliente" items="${clientes}">
                <option value="${cliente.idCliente}">${cliente.nombre}</option>
            </c:forEach>
        </select>
        <br><br>

        <label for="pelicula">Película:</label>
        <select name="peliculaId" id="pelicula" required>
            <option value="">-- Seleccione una película --</option>
            <c:forEach var="pelicula" items="${peliculas}">
                <option value="${pelicula.idPelicula}">${pelicula.titulo}</option>
            </c:forEach>
        </select>
        <br><br>

        <label for="cantidad">Cantidad:</label>
        <input type="number" name="cantidad" id="cantidad" min="1" required>
        <br><br>

        <input type="submit" value="Registrar Alquiler">
    </form>

    <br>
    <a href="${pageContext.request.contextPath}/">Volver al inicio</a>
</body>
</html>
