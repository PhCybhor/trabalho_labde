<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="pt-BR">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Estados | Cadastro</title>
    <link href="https://fonts.googleapis.com/css2?family=Poppins:wght@300;400;500;600;700&display=swap" rel="stylesheet">
    <style>
        :root {
            --primary: #0d9488;
            --primary-hover: #0f766e;
            --bg-dark: #18181b;
            --card-bg: #27272a;
            --text-main: #fafafa;
            --text-muted: #a1a1aa;
            --border: #3f3f46;
            --danger: #dc2626;
        }

        * { box-sizing: border-box; margin: 0; padding: 0; }

        body {
            font-family: 'Poppins', sans-serif;
            background-color: var(--bg-dark);
            color: var(--text-main);
            min-height: 100vh;
            display: flex;
            flex-direction: column;
        }

        .container {
            width: 100%;
            max-width: 1000px;
            margin: 0 auto;
            padding: 4rem 2rem;
            flex: 1;
        }

        .header {
            display: flex;
            justify-content: space-between;
            align-items: center;
            margin-bottom: 3rem;
            flex-wrap: wrap;
            gap: 1.5rem;
        }

        .header h1 {
            font-size: 2.5rem;
            font-weight: 800;
        }

        .nav-links a {
            color: var(--primary);
            text-decoration: none;
            margin-right: 1rem;
            font-size: 0.875rem;
        }

        .btn {
            display: inline-flex;
            align-items: center;
            justify-content: center;
            padding: 0.75rem 1.5rem;
            font-size: 1rem;
            font-weight: 600;
            text-decoration: none;
            border-radius: 12px;
            border: none;
            cursor: pointer;
        }

        .btn-primary {
            background: linear-gradient(to right, var(--primary), var(--primary-hover));
            color: #fff;
        }

        .btn-outline {
            background: rgba(255, 255, 255, 0.05);
            color: var(--text-main);
            border: 1px solid var(--border);
        }

        .btn-danger {
            background: rgba(220, 38, 38, 0.1);
            color: var(--danger);
            border: 1px solid rgba(220, 38, 38, 0.2);
        }

        .table-card {
            background: var(--card-bg);
            border: 1px solid var(--border);
            border-radius: 24px;
            overflow: hidden;
        }

        table {
            width: 100%;
            border-collapse: collapse;
        }

        th, td {
            padding: 1.25rem 1.5rem;
            text-align: left;
            border-bottom: 1px solid var(--border);
        }

        th {
            color: var(--text-muted);
            font-size: 0.8rem;
            text-transform: uppercase;
            letter-spacing: 0.05em;
        }

        tr:last-child td { border-bottom: none; }

        .sigla-badge {
            display: inline-block;
            background: rgba(13, 148, 136, 0.2);
            color: var(--primary);
            font-weight: 700;
            padding: 0.25rem 0.75rem;
            border-radius: 8px;
            letter-spacing: 0.05em;
        }

        .actions {
            display: flex;
            gap: 0.75rem;
        }

        .actions .btn {
            padding: 0.5rem 1rem;
            font-size: 0.875rem;
        }

        .empty-state {
            text-align: center;
            padding: 4rem;
            color: var(--text-muted);
        }

        .footer {
            padding: 3rem 2rem;
            text-align: center;
            border-top: 1px solid var(--border);
            color: var(--text-muted);
            font-size: 0.875rem;
        }

        @media (max-width: 768px) {
            .table-card { overflow-x: auto; }
            table { min-width: 600px; }
        }
    </style>
</head>
<body>

    <div class="container">
        <div class="nav-links">
            <a href="${pageContext.request.contextPath}/livros">Livros</a>
        </div>

        <header class="header">
            <h1>Estados</h1>
            <a href="${pageContext.request.contextPath}/estados/novo" class="btn btn-primary">+ Novo Estado</a>
        </header>

        <c:choose>
            <c:when test="${empty listaEstados}">
                <div class="empty-state">
                    <h2 style="margin-bottom: 1rem;">Nenhum estado cadastrado</h2>
                    <p>Cadastre o primeiro estado do sistema.</p>
                </div>
            </c:when>
            <c:otherwise>
                <div class="table-card">
                    <table>
                        <thead>
                            <tr>
                                <th>ID</th>
                                <th>Nome</th>
                                <th>Sigla</th>
                                <th>Acoes</th>
                            </tr>
                        </thead>
                        <tbody>
                            <c:forEach var="estado" items="${listaEstados}">
                                <tr>
                                    <td><c:out value="${estado.codigo}" /></td>
                                    <td><c:out value="${estado.descricao}" /></td>
                                    <td><span class="sigla-badge"><c:out value="${estado.uf}" /></span></td>
                                    <td>
                                        <div class="actions">
                                            <a href="${pageContext.request.contextPath}/estados/editar?id=<c:out value='${estado.codigo}' />"
                                               class="btn btn-outline">Editar</a>
                                            <a href="${pageContext.request.contextPath}/estados/excluir?id=<c:out value='${estado.codigo}' />"
                                               class="btn btn-danger"
                                               onclick="return confirm('Excluir este estado?');">Excluir</a>
                                        </div>
                                    </td>
                                </tr>
                            </c:forEach>
                        </tbody>
                    </table>
                </div>
            </c:otherwise>
        </c:choose>
    </div>

    <footer class="footer">
        <p>Cap 5.4 - Desafio 02 • CRUD de Estado</p>
    </footer>

</body>
</html>
