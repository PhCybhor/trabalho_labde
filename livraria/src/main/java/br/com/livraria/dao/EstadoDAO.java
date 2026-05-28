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

    private final Conexao fonteDados;

    public EstadoDAO() {
        this.fonteDados = new Conexao();
    }

    public void adicionar(Estado registro) {
        String consulta = "INSERT INTO estados (nome_estado, sigla_estado) VALUES (?, ?)";
        try (Connection conexaoJdbc = fonteDados.getConexao();
             PreparedStatement comando = conexaoJdbc.prepareStatement(consulta)) {

            comando.setString(1, registro.getDescricao());
            comando.setString(2, formatarUf(registro.getUf()));
            comando.execute();
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao adicionar estado", e);
        }
    }

    public void atualizar(Estado registro) {
        String consulta = "UPDATE estados SET nome_estado = ?, sigla_estado = ? WHERE id = ?";
        try (Connection conexaoJdbc = fonteDados.getConexao();
             PreparedStatement comando = conexaoJdbc.prepareStatement(consulta)) {

            comando.setString(1, registro.getDescricao());
            comando.setString(2, formatarUf(registro.getUf()));
            comando.setInt(3, registro.getCodigo());
            comando.execute();
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao atualizar estado", e);
        }
    }

    public void remover(int codigo) {
        String consulta = "DELETE FROM estados WHERE id = ?";
        try (Connection conexaoJdbc = fonteDados.getConexao();
             PreparedStatement comando = conexaoJdbc.prepareStatement(consulta)) {

            comando.setInt(1, codigo);
            comando.execute();
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao remover estado", e);
        }
    }

    public List<Estado> listarTodos() {
        List<Estado> retorno = new ArrayList<>();
        String consulta = "SELECT * FROM estados ORDER BY nome_estado ASC";

        try (Connection conexaoJdbc = fonteDados.getConexao();
             PreparedStatement comando = conexaoJdbc.prepareStatement(consulta);
             ResultSet resultado = comando.executeQuery()) {

            while (resultado.next()) {
                retorno.add(montarEstado(resultado));
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao listar estados", e);
        }
        return retorno;
    }

    public Estado buscarPorId(int codigo) {
        String consulta = "SELECT * FROM estados WHERE id = ?";

        try (Connection conexaoJdbc = fonteDados.getConexao();
             PreparedStatement comando = conexaoJdbc.prepareStatement(consulta)) {

            comando.setInt(1, codigo);
            try (ResultSet resultado = comando.executeQuery()) {
                if (resultado.next()) {
                    return montarEstado(resultado);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao buscar estado", e);
        }
        return null;
    }

    public boolean ufJaCadastrada(String uf, Integer codigoIgnorar) {
        String consulta = "SELECT COUNT(*) FROM estados WHERE UPPER(sigla_estado) = ?";
        if (codigoIgnorar != null) {
            consulta += " AND id <> ?";
        }

        try (Connection conexaoJdbc = fonteDados.getConexao();
             PreparedStatement comando = conexaoJdbc.prepareStatement(consulta)) {

            comando.setString(1, formatarUf(uf));
            if (codigoIgnorar != null) {
                comando.setInt(2, codigoIgnorar);
            }

            try (ResultSet resultado = comando.executeQuery()) {
                if (resultado.next()) {
                    return resultado.getInt(1) > 0;
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao verificar sigla do estado", e);
        }
        return false;
    }

    private Estado montarEstado(ResultSet resultado) throws SQLException {
        Estado item = new Estado();
        item.setCodigo(resultado.getInt("id"));
        item.setDescricao(resultado.getString("nome_estado"));
        item.setUf(resultado.getString("sigla_estado"));
        return item;
    }

    private String formatarUf(String uf) {
        if (uf == null) {
            return "";
        }
        return uf.trim().toUpperCase();
    }
}
