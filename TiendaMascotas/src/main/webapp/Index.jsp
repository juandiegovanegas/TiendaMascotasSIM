<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
    <%@ page import="Controller.Conexion"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">

<link rel="stylesheet"
    href="https://cdn.jsdelivr.net/npm/bootstrap@4.6.2/dist/css/bootstrap.min.css">
<script
    src="https://cdn.jsdelivr.net/npm/bootstrap@4.6.2/dist/js/bootstrap.bundle.min.js"></script>

<title>Inicio</title>

<style>
    body {
        background-color: #f9f9f9;
    }
    .correo-container {
        margin-top: 30px;
        padding: 20px;
        background: #fff;
        border-radius: 12px;
        box-shadow: 0px 3px 6px rgba(0,0,0,0.1);
    }
    .correo-container h2 {
        margin-bottom: 15px;
    }
    .correo-form .input-group {
        margin-bottom: 15px;
        display: flex;
        flex-direction: column;
    }
    .correo-form label {
        font-weight: bold;
        margin-bottom: 5px;
    }
    .correo-form input, 
    .correo-form textarea {
        padding: 8px;
        border-radius: 6px;
        border: 1px solid #ccc;
    }
    .btn-enviar {
        background-color: #007bff;
        color: white;
        border: none;
        padding: 10px 15px;
        border-radius: 6px;
        cursor: pointer;
    }
    .btn-enviar:hover {
        background-color: #0056b3;
    }
</style>

</head>
<body>

    <div class="container py-5">
        <div class="text-center mb-4">
            <h1 class="display-4">🐾 Tipo de Mascotas</h1>
            <p class="lead">Gestiona los tipos disponibles en el sistema</p>
        </div>

        <div class="row justify-content-center">
            <div class="col-md-6">
                <div class="list-group">

                    <a href="SvTipoMascotas?accion=listar"
                        class="list-group-item list-group-item-action d-flex justify-content-between align-items-center">
                        📋 Ver Lista de Tipos <span class="badge badge-primary badge-pill">Ir</span>
                    </a> 

                    <a href="SvTestConexion"
                        class="list-group-item list-group-item-action d-flex justify-content-between align-items-center">
                        🔍 Probar conexión a la base de datos 
                        <span class="badge badge-success badge-pill">Test</span>
                    </a>
                            
                    <a href="SvTipoMascotas?accion=crear"
                        class="list-group-item list-group-item-action d-flex justify-content-between align-items-center">
                        ➕ Crear nuevo registro 
                        <span class="badge badge-warning badge-pill">Nuevo</span>
                    </a>
                        
                    <a href="SvGenerarPDF?accion=generarPDF"
                        class="list-group-item list-group-item-action d-flex justify-content-between align-items-center"
                        target="_blank"> 
                        📥 Descargar Reporte PDF 
                        <span class="badge bg-danger rounded-pill">PDF</span>
                    </a>

                    <!-- Formulario para enviar correos -->
                    <div class="correo-container">
                        <h2>Enviar Correo</h2>
                        <form action="SvEnviarcorreos" method="post" class="correo-form">
                            <div class="input-group">
                                <label for="destinatario">Destinatario</label> 
                                <input type="email" id="destinatario" name="destinario"
                                    placeholder="ejemplo@correo.com" required>
                            </div>

                            <div class="input-group">
                                <label for="asunto">Asunto</label> 
                                <input type="text" id="asunto" name="asunto"
                                    placeholder="Escribe el asunto" required>
                            </div>

                            <div class="input-group">
                                <label for="mensaje">Mensaje</label>
                                <textarea id="mensaje" name="mensaje"
                                    placeholder="Escribe tu mensaje..." required></textarea>
                            </div>

                            <button type="submit" class="btn-enviar">✉ Enviar</button>
                        </form>
                    </div>

                </div>
            </div>
        </div>
    </div>

</body>
</html>
