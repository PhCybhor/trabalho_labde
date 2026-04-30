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

    private Conexao conexao;

    public LivroDAO() {
        this.conexao = new Conexao();
    }

    public void adicionar(Livro livro) {
        String sql = "INSERT INTO livros (nome_livro, isbn, autor, data_publicacao, valor_livro) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = conexao.getConexao();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, livro.getNomeLivro());
            stmt.setString(2, livro.getIsbn());
            stmt.setString(3, livro.getAutor());
            stmt.setDate(4, Date.valueOf(livro.getDataPublicacao()));
            stmt.setDouble(5, livro.getValorLivro());

            stmt.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void atualizar(Livro livro) {
        String sql = "UPDATE livros SET nome_livro = ?, isbn = ?, autor = ?, data_publicacao = ?, valor_livro = ? WHERE id = ?";
        try (Connection conn = conexao.getConexao();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, livro.getNomeLivro());
            stmt.setString(2, livro.getIsbn());
            stmt.setString(3, livro.getAutor());
            stmt.setDate(4, Date.valueOf(livro.getDataPublicacao()));
            stmt.setDouble(5, livro.getValorLivro());
            stmt.setInt(6, livro.getId());

            stmt.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void remover(int id) {
        String sql = "DELETE FROM livros WHERE id = ?";
        try (Connection conn = conexao.getConexao();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            stmt.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Livro> listarTodos() {
        List<Livro> livros = new ArrayList<>();
        String sql = "SELECT * FROM livros ORDER BY id ASC";

        try (Connection conn = conexao.getConexao();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Livro livro = new Livro();
                livro.setId(rs.getInt("id"));
                livro.setNomeLivro(rs.getString("nome_livro"));
                livro.setIsbn(rs.getString("isbn"));
                livro.setAutor(rs.getString("autor"));
                Date dataSql = rs.getDate("data_publicacao");
                if (dataSql != null) {
                    livro.setDataPublicacao(dataSql.toLocalDate());
                }
                livro.setValorLivro(rs.getDouble("valor_livro"));
                livros.add(livro);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return livros;
    }

    public Livro buscarPorId(int id) {
        String sql = "SELECT * FROM livros WHERE id = ?";
        Livro livro = null;

        try (Connection conn = conexao.getConexao();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    livro = new Livro();
                    livro.setId(rs.getInt("id"));
                    livro.setNomeLivro(rs.getString("nome_livro"));
                    livro.setIsbn(rs.getString("isbn"));
                    livro.setAutor(rs.getString("autor"));
                    Date dataSql = rs.getDate("data_publicacao");
                    if (dataSql != null) {
                        livro.setDataPublicacao(dataSql.toLocalDate());
                    }
                    livro.setValorLivro(rs.getDouble("valor_livro"));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return livro;
    }
}
