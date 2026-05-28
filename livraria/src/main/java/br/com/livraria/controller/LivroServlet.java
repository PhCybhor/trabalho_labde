package br.com.livraria.controller;

import br.com.livraria.dao.LivroDAO;
import br.com.livraria.model.Livro;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;
import java.util.regex.Pattern;

@WebServlet(urlPatterns = {"/livros", "/livros/novo", "/livros/inserir", "/livros/editar", "/livros/atualizar", "/livros/excluir"})
public class LivroServlet extends HttpServlet {

    private LivroDAO repositorioLivro;
    private static final Pattern EMAIL_REGEX = Pattern.compile("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$");

    @Override
    public void init() {
        repositorioLivro = new LivroDAO();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String rota = request.getServletPath();

        switch (rota) {
            case "/livros/novo":
                mostrarFormularioNovo(request, response);
                break;
            case "/livros/editar":
                mostrarFormularioEdicao(request, response);
                break;
            case "/livros/excluir":
                excluirLivro(request, response);
                break;
            default:
                listarLivros(request, response);
                break;
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        String rota = request.getServletPath();

        switch (rota) {
            case "/livros/inserir":
                inserirLivro(request, response);
                break;
            case "/livros/atualizar":
                atualizarLivro(request, response);
                break;
            default:
                response.sendRedirect(request.getContextPath() + "/livros");
                break;
        }
    }

    private void listarLivros(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<Livro> listaLivros = repositorioLivro.listarTodos();
        request.setAttribute("listaLivros", listaLivros);
        request.getRequestDispatcher("/livro-list.jsp").forward(request, response);
    }

    private void mostrarFormularioNovo(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        prepararFormulario(request);
        request.getRequestDispatcher("/livro-form.jsp").forward(request, response);
    }

    private void mostrarFormularioEdicao(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int codigo = Integer.parseInt(request.getParameter("id"));
        Livro livroEdicao = repositorioLivro.buscarPorId(codigo);
        request.setAttribute("livro", livroEdicao);
        prepararFormulario(request);
        request.getRequestDispatcher("/livro-form.jsp").forward(request, response);
    }

    private void inserirLivro(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Livro registroLivro = extrairLivroDoRequest(request);
        String erroEmail = validarEmailLivro(registroLivro, null);
        if (erroEmail != null) {
            request.setAttribute("erroEmail", erroEmail);
            request.setAttribute("livro", registroLivro);
            prepararFormulario(request);
            request.getRequestDispatcher("/livro-form.jsp").forward(request, response);
            return;
        }
        repositorioLivro.adicionar(registroLivro);
        response.sendRedirect(request.getContextPath() + "/livros");
    }

    private void atualizarLivro(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Livro livroModificado = extrairLivroDoRequest(request);
        livroModificado.setCodigo(Integer.parseInt(request.getParameter("id")));
        String erroEmail = validarEmailLivro(livroModificado, livroModificado.getCodigo());
        if (erroEmail != null) {
            request.setAttribute("erroEmail", erroEmail);
            request.setAttribute("livro", livroModificado);
            prepararFormulario(request);
            request.getRequestDispatcher("/livro-form.jsp").forward(request, response);
            return;
        }
        repositorioLivro.atualizar(livroModificado);
        response.sendRedirect(request.getContextPath() + "/livros");
    }

    private void excluirLivro(HttpServletRequest request, HttpServletResponse response) throws IOException {
        int codigo = Integer.parseInt(request.getParameter("id"));
        repositorioLivro.remover(codigo);
        response.sendRedirect(request.getContextPath() + "/livros");
    }

    private Livro extrairLivroDoRequest(HttpServletRequest request) {
        String titulo = request.getParameter("titulo");
        String isbn = request.getParameter("isbn");
        String autor = request.getParameter("autor");
        String email = request.getParameter("email");
        LocalDate dtPublicacao = LocalDate.parse(request.getParameter("dtPublicacao"));
        double preco = Double.parseDouble(request.getParameter("preco").replace(",", "."));

        return new Livro(0, titulo, isbn, autor, email, dtPublicacao, preco);
    }

    private String validarEmailLivro(Livro livro, Integer codigoIgnorar) {
        String email = livro.getEmail() == null ? "" : livro.getEmail().trim();
        if (email.isEmpty()) {
            return "O e-mail e obrigatorio.";
        }
        if (!EMAIL_REGEX.matcher(email).matches()) {
            return "Informe um e-mail valido.";
        }
        if (repositorioLivro.emailJaCadastrado(email, codigoIgnorar)) {
            return "O e-mail informado ja esta cadastrado.";
        }
        return null;
    }

    private void prepararFormulario(HttpServletRequest request) {
        List<String> emailsCadastrados = repositorioLivro.listarTodos().stream()
                .map(livro -> livro.getEmail() == null ? "" : livro.getEmail().trim().toLowerCase())
                .filter(email -> !email.isEmpty())
                .collect(Collectors.toList());
        request.setAttribute("emailsCadastrados", emailsCadastrados);
    }
}
