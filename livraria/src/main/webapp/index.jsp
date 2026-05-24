<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="pt-BR">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Livraria | Menu</title>
    <link href="https://fonts.googleapis.com/css2?family=Poppins:wght@400;600;700&display=swap" rel="stylesheet">
    <style>
        body {
            font-family: 'Poppins', sans-serif;
            background: #18181b;
            color: #fafafa;
            min-height: 100vh;
            display: flex;
            align-items: center;
            justify-content: center;
            margin: 0;
        }
        .menu {
            background: #27272a;
            border: 1px solid #3f3f46;
            border-radius: 24px;
            padding: 3rem;
            text-align: center;
            min-width: 320px;
        }
        h1 { margin-bottom: 2rem; font-size: 2rem; }
        a {
            display: block;
            margin: 0.75rem 0;
            padding: 1rem;
            background: #0d9488;
            color: #fff;
            text-decoration: none;
            border-radius: 12px;
            font-weight: 600;
        }
        a:hover { background: #0f766e; }
    </style>
</head>
<body>
    <div class="menu">
        <h1>Sistema Livraria</h1>
        <a href="${pageContext.request.contextPath}/livros">Gerenciar Livros</a>
        <a href="${pageContext.request.contextPath}/estados">Gerenciar Estados</a>
    </div>
</body>
</html>
