package br.com.livraria.dao;

import br.com.livraria.model.Livro;
import br.com.livraria.util.Conexao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class LivroDAO {

    private Conexao fonteDados;

    public LivroDAO() {
        this.fonteDados = new Conexao();
    }

    public void adicionar(Livro registro) {
        String consulta = "INSERT INTO livros (nome_livro, isbn, autor, email, data_publicacao, valor_livro) VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection conexaoJdbc = fonteDados.getConexao();
             PreparedStatement comando = conexaoJdbc.prepareStatement(consulta)) {

            comando.setString(1, registro.getTitulo());
            comando.setString(2, registro.getIsbn());
            comando.setString(3, registro.getAutor());
            comando.setString(4, normalizarEmail(registro.getEmail()));
            comando.setDate(5, Date.valueOf(registro.getDtPublicacao()));
            comando.setDouble(6, registro.getPreco());

            comando.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void atualizar(Livro registro) {
        String consulta = "UPDATE livros SET nome_livro = ?, isbn = ?, autor = ?, email = ?, data_publicacao = ?, valor_livro = ? WHERE id = ?";
        try (Connection conexaoJdbc = fonteDados.getConexao();
             PreparedStatement comando = conexaoJdbc.prepareStatement(consulta)) {

            comando.setString(1, registro.getTitulo());
            comando.setString(2, registro.getIsbn());
            comando.setString(3, registro.getAutor());
            comando.setString(4, normalizarEmail(registro.getEmail()));
            comando.setDate(5, Date.valueOf(registro.getDtPublicacao()));
            comando.setDouble(6, registro.getPreco());
            comando.setInt(7, registro.getCodigo());

            comando.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void remover(int codigo) {
        String consulta = "DELETE FROM livros WHERE id = ?";
        try (Connection conexaoJdbc = fonteDados.getConexao();
             PreparedStatement comando = conexaoJdbc.prepareStatement(consulta)) {

            comando.setInt(1, codigo);
            comando.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Livro> listarTodos() {
        List<Livro> retorno = new ArrayList<>();
        String consulta = "SELECT * FROM livros ORDER BY id ASC";

        try (Connection conexaoJdbc = fonteDados.getConexao();
             PreparedStatement comando = conexaoJdbc.prepareStatement(consulta);
             ResultSet resultado = comando.executeQuery()) {

            while (resultado.next()) {
                retorno.add(montarLivro(resultado));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return retorno;
    }

    public Livro buscarPorId(int codigo) {
        String consulta = "SELECT * FROM livros WHERE id = ?";
        Livro item = null;

        try (Connection conexaoJdbc = fonteDados.getConexao();
             PreparedStatement comando = conexaoJdbc.prepareStatement(consulta)) {

            comando.setInt(1, codigo);
            try (ResultSet resultado = comando.executeQuery()) {
                if (resultado.next()) {
                    item = montarLivro(resultado);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return item;
    }

    public boolean emailJaCadastrado(String email, Integer codigoIgnorar) {
        String consulta = "SELECT COUNT(*) FROM livros WHERE LOWER(email) = ?";
        if (codigoIgnorar != null) {
            consulta += " AND id <> ?";
        }

        try (Connection conexaoJdbc = fonteDados.getConexao();
             PreparedStatement comando = conexaoJdbc.prepareStatement(consulta)) {

            comando.setString(1, normalizarEmail(email));
            if (codigoIgnorar != null) {
                comando.setInt(2, codigoIgnorar);
            }

            try (ResultSet resultado = comando.executeQuery()) {
                if (resultado.next()) {
                    return resultado.getInt(1) > 0;
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao verificar e-mail do livro", e);
        }
        return false;
    }

    private Livro montarLivro(ResultSet resultado) throws SQLException {
        Livro item = new Livro();
        item.setCodigo(resultado.getInt("id"));
        item.setTitulo(resultado.getString("nome_livro"));
        item.setIsbn(resultado.getString("isbn"));
        item.setAutor(resultado.getString("autor"));
        item.setEmail(resultado.getString("email"));
        Date dataBanco = resultado.getDate("data_publicacao");
        if (dataBanco != null) {
            item.setDtPublicacao(dataBanco.toLocalDate());
        }
        item.setPreco(resultado.getDouble("valor_livro"));
        return item;
    }

    private String normalizarEmail(String email) {
        if (email == null) {
            return "";
        }
        return email.trim().toLowerCase();
    }
}
