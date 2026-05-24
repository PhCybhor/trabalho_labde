package br.com.livraria.dao;

import br.com.livraria.model.Estado;
import br.com.livraria.util.Conexao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class EstadoDAO {

    private final Conexao conexao;

    public EstadoDAO() {
        this.conexao = new Conexao();
    }

    public void adicionar(Estado estado) {
        String sql = "INSERT INTO estados (nome_estado, sigla_estado) VALUES (?, ?)";
        try (Connection conn = conexao.getConexao();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, estado.getNomeEstado());
            stmt.setString(2, normalizarSigla(estado.getSiglaEstado()));
            stmt.execute();
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao adicionar estado", e);
        }
    }

    public void atualizar(Estado estado) {
        String sql = "UPDATE estados SET nome_estado = ?, sigla_estado = ? WHERE id = ?";
        try (Connection conn = conexao.getConexao();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, estado.getNomeEstado());
            stmt.setString(2, normalizarSigla(estado.getSiglaEstado()));
            stmt.setInt(3, estado.getId());
            stmt.execute();
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao atualizar estado", e);
        }
    }

    public void remover(int id) {
        String sql = "DELETE FROM estados WHERE id = ?";
        try (Connection conn = conexao.getConexao();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            stmt.execute();
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao remover estado", e);
        }
    }

    public List<Estado> listarTodos() {
        List<Estado> estados = new ArrayList<>();
        String sql = "SELECT * FROM estados ORDER BY nome_estado ASC";

        try (Connection conn = conexao.getConexao();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                estados.add(mapearEstado(rs));
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao listar estados", e);
        }
        return estados;
    }

    public Estado buscarPorId(int id) {
        String sql = "SELECT * FROM estados WHERE id = ?";

        try (Connection conn = conexao.getConexao();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return mapearEstado(rs);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao buscar estado", e);
        }
        return null;
    }

    public boolean existeSiglaEstado(String sigla, Integer idIgnorar) {
        String sql = "SELECT COUNT(*) FROM estados WHERE UPPER(sigla_estado) = ?";
        if (idIgnorar != null) {
            sql += " AND id <> ?";
        }

        try (Connection conn = conexao.getConexao();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, normalizarSigla(sigla));
            if (idIgnorar != null) {
                stmt.setInt(2, idIgnorar);
            }

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1) > 0;
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao verificar sigla do estado", e);
        }
        return false;
    }

    private Estado mapearEstado(ResultSet rs) throws SQLException {
        Estado estado = new Estado();
        estado.setId(rs.getInt("id"));
        estado.setNomeEstado(rs.getString("nome_estado"));
        estado.setSiglaEstado(rs.getString("sigla_estado"));
        return estado;
    }

    private String normalizarSigla(String sigla) {
        if (sigla == null) {
            return "";
        }
        return sigla.trim().toUpperCase();
    }
}
