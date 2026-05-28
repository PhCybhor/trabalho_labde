<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="pt-BR">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Estados | Gerenciar</title>
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
        }

        .form-title {
            font-size: 2.5rem;
            font-weight: 800;
            margin-bottom: 2rem;
            text-align: center;
        }

        .alert-error {
            background: rgba(220, 38, 38, 0.15);
            border: 1px solid rgba(220, 38, 38, 0.4);
            color: #fca5a5;
            padding: 1rem 1.25rem;
            border-radius: 12px;
            margin-bottom: 1.5rem;
            font-size: 0.9rem;
        }

        .form-group { margin-bottom: 1.75rem; }

        .form-label {
            display: block;
            font-size: 0.875rem;
            color: var(--text-muted);
            margin-bottom: 0.5rem;
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
        }

        .form-control:focus {
            outline: none;
            border-color: var(--primary);
            box-shadow: 0 0 0 4px rgba(13, 148, 136, 0.15);
        }

        .form-control.invalid {
            border-color: var(--danger);
        }

        .field-error {
            color: #fca5a5;
            font-size: 0.8rem;
            margin-top: 0.5rem;
            display: none;
        }

        .form-row {
            display: grid;
            grid-template-columns: 2fr 1fr;
            gap: 1.5rem;
        }

        .action-buttons {
            display: flex;
            gap: 1.25rem;
            margin-top: 2rem;
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

        .nav-links {
            text-align: center;
            margin-bottom: 1.5rem;
        }

        .nav-links a {
            color: var(--primary);
            text-decoration: none;
            margin: 0 0.75rem;
            font-size: 0.875rem;
        }

        @media (max-width: 640px) {
            .form-row { grid-template-columns: 1fr; }
            .form-card { padding: 2rem 1.5rem; }
            .action-buttons { flex-direction: column-reverse; }
        }
    </style>
</head>
<body>

    <div class="container">
        <div class="form-card">
            <div class="nav-links">
                <a href="${pageContext.request.contextPath}/estados">Estados</a>
                <a href="${pageContext.request.contextPath}/livros">Livros</a>
            </div>

            <h2 class="form-title">
                <c:choose>
                    <c:when test="${estado != null && estado.codigo > 0}">Editar Estado</c:when>
                    <c:otherwise>Novo Estado</c:otherwise>
                </c:choose>
            </h2>

            <c:if test="${not empty erro}">
                <div class="alert-error"><c:out value="${erro}" /></div>
            </c:if>

            <form id="formEstado" action="${pageContext.request.contextPath}/estados/${estado != null && estado.codigo > 0 ? 'atualizar' : 'inserir'}" method="post" novalidate>
                <c:if test="${estado != null && estado.codigo > 0}">
                    <input type="hidden" name="id" id="estadoId" value="<c:out value='${estado.codigo}' />" />
                </c:if>

                <div class="form-group">
                    <label class="form-label" for="descricao">Nome do Estado</label>
                    <input type="text" id="descricao" class="form-control" name="descricao"
                           value="<c:out value='${estado.descricao}' />"
                           required maxlength="100">
                </div>

                <div class="form-row">
                    <div class="form-group">
                        <label class="form-label" for="uf">Sigla (UF)</label>
                        <input type="text" id="uf" class="form-control" name="uf"
                               value="<c:out value='${estado.uf}' />"
                               required maxlength="2" minlength="2"
                               pattern="[A-Za-z]{2}" title="Informe exatamente 2 letras">
                        <p id="siglaErro" class="field-error">Esta sigla ja esta cadastrada.</p>
                    </div>
                </div>

                <div class="action-buttons">
                    <a href="${pageContext.request.contextPath}/estados" class="btn btn-outline">Cancelar</a>
                    <button type="submit" class="btn btn-primary">Salvar</button>
                </div>
            </form>
        </div>
    </div>

    <script>
        const ufsCadastradas = [
            <c:forEach var="sigla" items="${ufsCadastradas}" varStatus="status">
            '<c:out value="${sigla}" />'<c:if test="${!status.last}">,</c:if>
            </c:forEach>
        ];

        const estadoIdEl = document.getElementById('estadoId');
        const estadoId = estadoIdEl ? parseInt(estadoIdEl.value, 10) : null;
        const siglaInput = document.getElementById('uf');
        const siglaErro = document.getElementById('siglaErro');
        const form = document.getElementById('formEstado');

        function normalizarSigla(valor) {
            return (valor || '').trim().toUpperCase();
        }

        function siglaDuplicada(sigla) {
            const siglaNormalizada = normalizarSigla(sigla);
            if (siglaNormalizada.length !== 2) {
                return false;
            }

            const siglaAtualEdicao = normalizarSigla('${estado.uf}');
            if (estadoId && siglaNormalizada === siglaAtualEdicao) {
                return false;
            }

            return ufsCadastradas.includes(siglaNormalizada);
        }

        function validarSigla(mostrarErro) {
            const duplicada = siglaDuplicada(siglaInput.value);
            siglaInput.classList.toggle('invalid', duplicada);
            siglaErro.style.display = duplicada && mostrarErro ? 'block' : 'none';
            return !duplicada;
        }

        siglaInput.addEventListener('input', function () {
            this.value = this.value.toUpperCase().replace(/[^A-Z]/g, '').slice(0, 2);
            validarSigla(true);
        });

        form.addEventListener('submit', function (event) {
            if (!validarSigla(true)) {
                event.preventDefault();
                siglaInput.focus();
            }
        });
    </script>

</body>
</html>
