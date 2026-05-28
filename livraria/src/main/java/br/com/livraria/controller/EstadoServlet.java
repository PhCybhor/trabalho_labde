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

    private EstadoDAO repositorioEstado;

    @Override
    public void init() {
        repositorioEstado = new EstadoDAO();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String rota = request.getServletPath();

        switch (rota) {
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
        String rota = request.getServletPath();

        switch (rota) {
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
        List<Estado> listaEstados = repositorioEstado.listarTodos();
        request.setAttribute("listaEstados", listaEstados);
        request.getRequestDispatcher("/estado-list.jsp").forward(request, response);
    }

    private void mostrarFormularioNovo(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        prepararFormulario(request);
        request.getRequestDispatcher("/estado-form.jsp").forward(request, response);
    }

    private void mostrarFormularioEdicao(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int codigo = Integer.parseInt(request.getParameter("id"));
        Estado estadoEdicao = repositorioEstado.buscarPorId(codigo);
        request.setAttribute("estado", estadoEdicao);
        prepararFormulario(request);
        request.getRequestDispatcher("/estado-form.jsp").forward(request, response);
    }

    private void inserirEstado(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Estado registroEstado = extrairEstadoDoRequest(request);

        if (repositorioEstado.ufJaCadastrada(registroEstado.getUf(), null)) {
            request.setAttribute("erro", "A sigla informada ja esta cadastrada.");
            request.setAttribute("estado", registroEstado);
            prepararFormulario(request);
            request.getRequestDispatcher("/estado-form.jsp").forward(request, response);
            return;
        }

        repositorioEstado.adicionar(registroEstado);
        response.sendRedirect(request.getContextPath() + "/estados");
    }

    private void atualizarEstado(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Estado estadoModificado = extrairEstadoDoRequest(request);
        estadoModificado.setCodigo(Integer.parseInt(request.getParameter("id")));

        if (repositorioEstado.ufJaCadastrada(estadoModificado.getUf(), estadoModificado.getCodigo())) {
            request.setAttribute("erro", "A sigla informada ja esta cadastrada.");
            request.setAttribute("estado", estadoModificado);
            prepararFormulario(request);
            request.getRequestDispatcher("/estado-form.jsp").forward(request, response);
            return;
        }

        repositorioEstado.atualizar(estadoModificado);
        response.sendRedirect(request.getContextPath() + "/estados");
    }

    private void excluirEstado(HttpServletRequest request, HttpServletResponse response) throws IOException {
        int codigo = Integer.parseInt(request.getParameter("id"));
        repositorioEstado.remover(codigo);
        response.sendRedirect(request.getContextPath() + "/estados");
    }

    private void prepararFormulario(HttpServletRequest request) {
        List<String> ufsCadastradas = repositorioEstado.listarTodos().stream()
                .map(e -> e.getUf().toUpperCase())
                .collect(Collectors.toList());
        request.setAttribute("ufsCadastradas", ufsCadastradas);
    }

    private Estado extrairEstadoDoRequest(HttpServletRequest request) {
        String descricao = request.getParameter("descricao");
        String uf = request.getParameter("uf");
        return new Estado(0, descricao, uf);
    }
}
