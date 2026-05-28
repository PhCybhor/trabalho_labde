<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="pt-BR">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Biblioteca | Gerenciar</title>
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
            --input-bg: #18181b;
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
            max-width: 800px;
            margin: auto;
            padding: 4rem 2rem;
            flex: 1;
            display: flex;
            align-items: center;
            justify-content: center;
        }

        .form-card {
            background: var(--card-bg);
            border: 1px solid var(--border);
            border-radius: 24px;
            padding: 4rem;
            width: 100%;
            box-shadow: 0 25px 50px -12px rgba(0, 0, 0, 0.5);
        }

        .form-title {
            font-size: 2.5rem;
            font-weight: 800;
            margin-bottom: 3rem;
            text-align: center;
            letter-spacing: -0.05em;
        }

        .form-group {
            margin-bottom: 1.75rem;
        }

        .form-label {
            display: block;
            font-size: 0.875rem;
            font-weight: 500;
            color: var(--text-muted);
            margin-bottom: 0.5rem;
            padding-left: 0.25rem;
        }

        .form-control {
            width: 100%;
            padding: 0.875rem 1.25rem;
            font-family: inherit;
            font-size: 1rem;
            color: var(--text-main);
            background: var(--input-bg);
            border: 1px solid var(--border);
            border-radius: 12px;
            transition: all 0.2s cubic-bezier(0.4, 0, 0.2, 1);
        }

        .form-control:focus {
            outline: none;
            border-color: var(--primary);
            background: rgba(0, 0, 0, 0.6);
            box-shadow: 0 0 0 4px rgba(13, 148, 136, 0.15);
        }

        .form-control.invalid {
            border-color: #ef4444;
            box-shadow: 0 0 0 4px rgba(239, 68, 68, 0.15);
        }

        .field-error {
            display: none;
            color: #f87171;
            margin-top: 0.5rem;
            font-size: 0.875rem;
        }

        .alert-error {
            background: rgba(220, 38, 38, 0.15);
            border: 1px solid rgba(220, 38, 38, 0.35);
            color: #fca5a5;
            padding: 0.875rem 1rem;
            border-radius: 12px;
            margin-bottom: 1.5rem;
        }

        .form-row {
            display: grid;
            grid-template-columns: 1fr 1fr;
            gap: 1.5rem;
        }

        .action-buttons {
            display: flex;
            gap: 1.25rem;
            margin-top: 3rem;
        }

        .btn {
            flex: 1;
            display: inline-flex;
            align-items: center;
            justify-content: center;
            padding: 1rem;
            font-size: 1rem;
            font-weight: 600;
            text-decoration: none;
            border-radius: 12px;
            transition: all 0.2s ease;
            cursor: pointer;
            border: none;
        }

        .btn-primary {
            background: linear-gradient(to right, var(--primary), var(--primary-hover));
            color: #fff;
        }

        .btn-primary:hover {
            transform: translateY(-2px);
            filter: brightness(1.1);
        }

        .btn-outline {
            background: rgba(255, 255, 255, 0.05);
            color: var(--text-main);
            border: 1px solid var(--border);
        }

        .btn-outline:hover {
            background: rgba(255, 255, 255, 0.1);
            transform: translateY(-2px);
        }

        .footer {
            padding: 4rem 2rem;
            text-align: center;
            border-top: 1px solid var(--border);
            color: var(--text-muted);
            font-size: 0.875rem;
        }

        .footer p {
            margin-bottom: 1rem;
        }

        .footer a {
            color: var(--primary);
            text-decoration: none;
            font-weight: 600;
        }

        @media (max-width: 640px) {
            .form-row { grid-template-columns: 1fr; gap: 0; }
            .form-card { padding: 2rem 1.5rem; }
            .action-buttons { flex-direction: column-reverse; }
        }
    </style>
</head>
<body>

    <div class="container">
        <div class="form-card">
            <h2 class="form-title">
                <c:choose>
                    <c:when test="${livro != null}">Editar</c:when>
                    <c:otherwise>Novo</c:otherwise>
                </c:choose>
            </h2>

            <c:if test="${not empty erroEmail}">
                <div class="alert-error"><c:out value="${erroEmail}" /></div>
            </c:if>

            <form id="formLivro" action="${pageContext.request.contextPath}/livros/${livro != null ? 'atualizar' : 'inserir'}" method="post" novalidate>
                <c:if test="${livro != null}">
                    <input type="hidden" name="id" value="<c:out value='${livro.codigo}' />" />
                </c:if>

                <div class="form-group">
                    <label class="form-label">Titulo</label>
                    <input type="text" class="form-control" name="titulo" value="<c:out value='${livro.titulo}' />" required maxlength="255">
                </div>

                <div class="form-group">
                    <label class="form-label">Autor</label>
                    <input type="text" class="form-control" name="autor" value="<c:out value='${livro.autor}' />" required maxlength="255">
                </div>

                <div class="form-group">
                    <label class="form-label" for="email">E-mail</label>
                    <input type="email" id="email" class="form-control" name="email" value="<c:out value='${livro.email}' />" required maxlength="255">
                    <p id="emailErro" class="field-error">E-mail invalido ou ja cadastrado.</p>
                </div>

                <div class="form-row">
                    <div class="form-group">
                        <label class="form-label">ISBN</label>
                        <input type="text" class="form-control" name="isbn" value="<c:out value='${livro.isbn}' />" required maxlength="50">
                    </div>
                    
                    <div class="form-group">
                        <label class="form-label">Publicacao</label>
                        <input type="date" class="form-control" name="dtPublicacao" value="<c:out value='${livro.dtPublicacao}' />" min="1500-01-01" max="2100-12-31" required>
                    </div>
                </div>

                <div class="form-group">
                    <label class="form-label">Valor (R$)</label>
                    <input type="number" step="0.01" class="form-control" name="preco" value="<c:out value='${livro.preco}' />" min="0" required>
                </div>

                <div class="action-buttons">
                    <a href="${pageContext.request.contextPath}/livros" class="btn btn-outline">Cancelar</a>
                    <button type="submit" class="btn btn-primary">Salvar</button>
                </div>
            </form>
        </div>
    </div>

    <footer class="footer">
        <p>Sistema de Biblioteca • Desenvolvido por Equipe</p>
    </footer>

    <script>
        const emailsCadastrados = [
            <c:forEach var="emailItem" items="${emailsCadastrados}" varStatus="status">
            '<c:out value="${emailItem}" />'<c:if test="${!status.last}">,</c:if>
            </c:forEach>
        ];

        const emailInput = document.getElementById('email');
        const emailErro = document.getElementById('emailErro');
        const formLivro = document.getElementById('formLivro');
        const idInput = document.querySelector('input[name="id"]');
        const emailEdicao = ('<c:out value="${livro.email}" />' || '').trim().toLowerCase();
        const regexEmail = /^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$/;

        function validarEmail(mostrarErro) {
            const emailAtual = (emailInput.value || '').trim().toLowerCase();
            let mensagem = '';

            if (!emailAtual) {
                mensagem = 'O e-mail e obrigatorio.';
            } else if (!regexEmail.test(emailAtual)) {
                mensagem = 'Informe um e-mail valido.';
            } else {
                const emEdicao = !!idInput && emailAtual === emailEdicao;
                if (!emEdicao && emailsCadastrados.includes(emailAtual)) {
                    mensagem = 'O e-mail informado ja esta cadastrado.';
                }
            }

            emailErro.textContent = mensagem;
            emailErro.style.display = mensagem && mostrarErro ? 'block' : 'none';
            emailInput.classList.toggle('invalid', !!mensagem);
            return !mensagem;
        }

        emailInput.addEventListener('input', function () {
            validarEmail(true);
        });

        formLivro.addEventListener('submit', function (event) {
            if (!validarEmail(true)) {
                event.preventDefault();
                emailInput.focus();
            }
        });
    </script>

</body>
</html>
