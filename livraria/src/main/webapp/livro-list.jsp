<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<!DOCTYPE html>
<html lang="pt-BR">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Biblioteca | Catálogo</title>
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
            --accent: #f59e0b;
        }

        * {
            box-sizing: border-box;
            margin: 0;
            padding: 0;
        }

        body {
            font-family: 'Poppins', sans-serif;
            background-color: var(--bg-dark);
            color: var(--text-main);
            min-height: 100vh;
            display: flex;
            flex-direction: column;
            line-height: 1.6;
        }

        .container {
            width: 100%;
            max-width: 1200px;
            margin: 0 auto;
            padding: 4rem 2rem;
            flex: 1;
        }

        .nav-links a {
            color: var(--primary);
            text-decoration: none;
            margin-right: 1rem;
            font-size: 0.875rem;
        }

        .header {
            display: flex;
            justify-content: space-between;
            align-items: center;
            margin-bottom: 5rem;
        }

        .header h1 {
            font-size: 3rem;
            font-weight: 800;
            letter-spacing: -0.05em;
            color: #fff;
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
            transition: all 0.2s cubic-bezier(0.4, 0, 0.2, 1);
            cursor: pointer;
            border: none;
        }

        .btn-primary {
            background: linear-gradient(to right, var(--primary), var(--primary-hover));
            color: #fff;
            box-shadow: 0 10px 15px -3px rgba(13, 148, 136, 0.3);
        }

        .btn-primary:hover {
            transform: translateY(-2px);
            box-shadow: 0 20px 25px -5px rgba(13, 148, 136, 0.4);
        }

        .btn-outline {
            background: rgba(255, 255, 255, 0.05);
            color: var(--text-main);
            border: 1px solid var(--border);
        }

        .btn-outline:hover {
            background: rgba(255, 255, 255, 0.1);
        }

        .btn-danger {
            background: rgba(220, 38, 38, 0.1);
            color: var(--danger);
            border: 1px solid rgba(220, 38, 38, 0.2);
        }

        .btn-danger:hover {
            background: var(--danger);
            color: #fff;
        }

        .book-grid {
            display: grid;
            grid-template-columns: repeat(auto-fill, minmax(320px, 1fr));
            gap: 2rem;
        }

        .book-card {
            background: var(--card-bg);
            border: 1px solid var(--border);
            border-radius: 24px;
            padding: 2rem;
            display: flex;
            flex-direction: column;
            transition: all 0.3s ease;
        }

        .book-card:hover {
            transform: translateY(-8px);
            border-color: var(--primary);
            box-shadow: 0 20px 25px -5px rgba(0, 0, 0, 0.3);
        }

        .book-id {
            font-size: 0.75rem;
            font-weight: 700;
            color: var(--primary);
            text-transform: uppercase;
            margin-bottom: 0.5rem;
            display: block;
        }

        .book-title {
            font-size: 1.5rem;
            font-weight: 700;
            margin-bottom: 0.25rem;
            line-height: 1.2;
        }

        .book-author {
            color: var(--text-muted);
            font-size: 0.875rem;
            margin-bottom: 1.5rem;
        }

        .book-details {
            margin-bottom: 2rem;
            padding: 1.25rem;
            background: rgba(0, 0, 0, 0.2);
            border-radius: 16px;
        }

        .detail-item {
            display: flex;
            justify-content: space-between;
            margin-bottom: 0.75rem;
            font-size: 0.875rem;
        }

        .detail-item:last-child {
            margin-bottom: 0;
        }

        .detail-label {
            color: var(--text-muted);
        }

        .detail-value {
            font-weight: 600;
        }

        .price-tag {
            font-size: 1.5rem;
            font-weight: 800;
            color: var(--accent);
            margin-bottom: 2rem;
            display: block;
        }

        .book-actions {
            display: flex;
            gap: 1rem;
            margin-top: auto;
        }

        .book-actions .btn {
            flex: 1;
            font-size: 0.875rem;
            padding: 0.625rem;
        }

        .empty-state {
            text-align: center;
            padding: 5rem 2rem;
            background: var(--card-bg);
            border: 2px dashed var(--border);
            border-radius: 24px;
        }

        .footer {
            padding: 4rem 2rem;
            text-align: center;
            border-top: 1px solid var(--border);
            color: var(--text-muted);
            font-size: 0.875rem;
        }

        .footer a {
            color: var(--primary);
            text-decoration: none;
            font-weight: 600;
        }

        @media (max-width: 640px) {
            .header { flex-direction: column; text-align: center; gap: 2rem; }
            .book-grid { grid-template-columns: 1fr; }
        }
    </style>
</head>
<body>

    <div class="container">
        <div class="nav-links">
            <a href="${pageContext.request.contextPath}/estados">Estados</a>
            <a href="${pageContext.request.contextPath}/">Menu</a>
        </div>

        <header class="header">
            <h1>Livraria</h1>
            <a href="${pageContext.request.contextPath}/livros/novo" class="btn btn-primary">
                + Adicionar Livro
            </a>
        </header>

        <c:choose>
            <c:when test="${empty listaLivros}">
                <div class="empty-state">
                    <h2 style="margin-bottom: 1rem;">Nenhum item encontrado</h2>
                    <p style="color: var(--text-muted);">A biblioteca esta vazia.</p>
                </div>
            </c:when>
            <c:otherwise>
                <div class="book-grid">
                    <c:forEach var="livro" items="${listaLivros}">
                        <div class="book-card">
                            <span class="book-id">ID #<c:out value="${livro.codigo}" /></span>
                            <h2 class="book-title"><c:out value="${livro.titulo}" /></h2>
                            <p class="book-author"><c:out value="${livro.autor}" /></p>

                            <div class="book-details">
                                <div class="detail-item">
                                    <span class="detail-label">ISBN</span>
                                    <span class="detail-value"><c:out value="${livro.isbn}" /></span>
                                </div>
                                <div class="detail-item">
                                    <span class="detail-label">Publicacao</span>
                                    <c:set var="dataParts" value="${fn:split(livro.dtPublicacao, '-')}"/>
                                    <span class="detail-value"><c:out value="${dataParts[2]}/${dataParts[1]}/${dataParts[0]}" /></span>
                                </div>
                            </div>

                            <span class="price-tag">
                                <fmt:setLocale value="pt_BR"/>
                                <fmt:formatNumber value="${livro.preco}" type="currency"/>
                            </span>

                            <div class="book-actions">
                                <a href="${pageContext.request.contextPath}/livros/editar?id=<c:out value='${livro.codigo}' />" class="btn btn-outline">
                                    Editar
                                </a>
                                <a href="${pageContext.request.contextPath}/livros/excluir?id=<c:out value='${livro.codigo}' />" class="btn btn-danger" onclick="return confirm('Excluir este registro?');">
                                    Remover
                                </a>
                            </div>
                        </div>
                    </c:forEach>
                </div>
            </c:otherwise>
        </c:choose>
    </div>

    <footer class="footer">
        <p>Trabalho Academico - Alunos: Murilo Rocha, Pedro Henrique, Marcos Paulo</p>
        <a href="https://github.com/murilolol/fef" target="_blank">Repositorio GitHub</a>
    </footer>

</body>
</html>
