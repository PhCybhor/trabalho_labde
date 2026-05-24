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

@WebServlet(urlPatterns = {"/livros", "/livros/novo", "/livros/inserir", "/livros/editar", "/livros/atualizar", "/livros/excluir"})
public class LivroServlet extends HttpServlet {

    private LivroDAO livroDAO;

    @Override
    public void init() {
        livroDAO = new LivroDAO();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getServletPath();

        switch (action) {
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
        String action = request.getServletPath();

        switch (action) {
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
        List<Livro> listLivros = livroDAO.listarTodos();
        request.setAttribute("listLivros", listLivros);
        request.getRequestDispatcher("/livro-list.jsp").forward(request, response);
    }

    private void mostrarFormularioNovo(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher("/livro-form.jsp").forward(request, response);
    }

    private void mostrarFormularioEdicao(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        Livro livroExistente = livroDAO.buscarPorId(id);
        request.setAttribute("livro", livroExistente);
        request.getRequestDispatcher("/livro-form.jsp").forward(request, response);
    }

    private void inserirLivro(HttpServletRequest request, HttpServletResponse response) throws IOException {
        Livro novoLivro = extrairLivroDoRequest(request);
        livroDAO.adicionar(novoLivro);
        response.sendRedirect(request.getContextPath() + "/livros");
    }

    private void atualizarLivro(HttpServletRequest request, HttpServletResponse response) throws IOException {
        Livro livroAtualizado = extrairLivroDoRequest(request);
        livroAtualizado.setId(Integer.parseInt(request.getParameter("id")));
        livroDAO.atualizar(livroAtualizado);
        response.sendRedirect(request.getContextPath() + "/livros");
    }

    private void excluirLivro(HttpServletRequest request, HttpServletResponse response) throws IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        livroDAO.remover(id);
        response.sendRedirect(request.getContextPath() + "/livros");
    }

    private Livro extrairLivroDoRequest(HttpServletRequest request) {
        String nomeLivro = request.getParameter("nomeLivro");
        String isbn = request.getParameter("isbn");
        String autor = request.getParameter("autor");
        LocalDate dataPublicacao = LocalDate.parse(request.getParameter("dataPublicacao"));
        double valorLivro = Double.parseDouble(request.getParameter("valorLivro").replace(",", "."));

        return new Livro(0, nomeLivro, isbn, autor, dataPublicacao, valorLivro);
    }
}
