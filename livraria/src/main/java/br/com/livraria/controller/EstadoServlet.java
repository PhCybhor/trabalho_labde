package br.com.livraria.controller;

import br.com.livraria.dao.EstadoDAO;
import br.com.livraria.model.Estado;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@WebServlet(urlPatterns = {"/estados", "/estados/novo", "/estados/inserir", "/estados/editar", "/estados/atualizar", "/estados/excluir"})
public class EstadoServlet extends HttpServlet {

    private EstadoDAO estadoDAO;

    @Override
    public void init() {
        estadoDAO = new EstadoDAO();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getServletPath();

        switch (action) {
            case "/estados/novo":
                mostrarFormularioNovo(request, response);
                break;
            case "/estados/editar":
                mostrarFormularioEdicao(request, response);
                break;
            case "/estados/excluir":
                excluirEstado(request, response);
                break;
            default:
                listarEstados(request, response);
                break;
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        String action = request.getServletPath();

        switch (action) {
            case "/estados/inserir":
                inserirEstado(request, response);
                break;
            case "/estados/atualizar":
                atualizarEstado(request, response);
                break;
            default:
                response.sendRedirect(request.getContextPath() + "/estados");
                break;
        }
    }

    private void listarEstados(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<Estado> listEstados = estadoDAO.listarTodos();
        request.setAttribute("listEstados", listEstados);
        request.getRequestDispatcher("/estado-list.jsp").forward(request, response);
    }

    private void mostrarFormularioNovo(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        prepararFormulario(request);
        request.getRequestDispatcher("/estado-form.jsp").forward(request, response);
    }

    private void mostrarFormularioEdicao(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        Estado estadoExistente = estadoDAO.buscarPorId(id);
        request.setAttribute("estado", estadoExistente);
        prepararFormulario(request);
        request.getRequestDispatcher("/estado-form.jsp").forward(request, response);
    }

    private void inserirEstado(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Estado novoEstado = extrairEstadoDoRequest(request);

        if (estadoDAO.existeSiglaEstado(novoEstado.getSiglaEstado(), null)) {
            request.setAttribute("erro", "A sigla informada ja esta cadastrada.");
            request.setAttribute("estado", novoEstado);
            prepararFormulario(request);
            request.getRequestDispatcher("/estado-form.jsp").forward(request, response);
            return;
        }

        estadoDAO.adicionar(novoEstado);
        response.sendRedirect(request.getContextPath() + "/estados");
    }

    private void atualizarEstado(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Estado estadoAtualizado = extrairEstadoDoRequest(request);
        estadoAtualizado.setId(Integer.parseInt(request.getParameter("id")));

        if (estadoDAO.existeSiglaEstado(estadoAtualizado.getSiglaEstado(), estadoAtualizado.getId())) {
            request.setAttribute("erro", "A sigla informada ja esta cadastrada.");
            request.setAttribute("estado", estadoAtualizado);
            prepararFormulario(request);
            request.getRequestDispatcher("/estado-form.jsp").forward(request, response);
            return;
        }

        estadoDAO.atualizar(estadoAtualizado);
        response.sendRedirect(request.getContextPath() + "/estados");
    }

    private void excluirEstado(HttpServletRequest request, HttpServletResponse response) throws IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        estadoDAO.remover(id);
        response.sendRedirect(request.getContextPath() + "/estados");
    }

    private void prepararFormulario(HttpServletRequest request) {
        List<String> siglasExistentes = estadoDAO.listarTodos().stream()
                .map(e -> e.getSiglaEstado().toUpperCase())
                .collect(Collectors.toList());
        request.setAttribute("siglasExistentes", siglasExistentes);
    }

    private Estado extrairEstadoDoRequest(HttpServletRequest request) {
        String nomeEstado = request.getParameter("nomeEstado");
        String siglaEstado = request.getParameter("siglaEstado");
        return new Estado(0, nomeEstado, siglaEstado);
    }
}
