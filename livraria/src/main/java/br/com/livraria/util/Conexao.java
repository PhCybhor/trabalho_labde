package br.com.livraria.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Conexao {
    
    private static final String URL = "jdbc:postgresql://localhost:5432/";
    private static final String BANCO = "livraria";
    private static final String USUARIO = "postgres";
    private static final String SENHA = "postdba"; 

    public Connection getConexao() {
        try {
            Class.forName("org.postgresql.Driver");
            validarBanco();
            Connection conn = DriverManager.getConnection(URL + BANCO, USUARIO, SENHA);
            criarTabelas(conn);
            return conn;
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    private void validarBanco() {
        try (Connection conn = DriverManager.getConnection(URL + "postgres", USUARIO, SENHA);
             Statement stmt = conn.createStatement()) {
            
            ResultSet rs = conn.getMetaData().getCatalogs();
            boolean existe = false;
            while (rs.next()) {
                if (BANCO.equals(rs.getString(1))) {
                    existe = true;
                    break;
                }
            }
            
            if (!existe) {
                stmt.executeUpdate("CREATE DATABASE " + BANCO);
            }
            
        } catch (SQLException e) {
        }
    }

    private void criarTabelas(Connection conn) {
        String sqlLivros = "CREATE TABLE IF NOT EXISTS livros ("
                + "id SERIAL PRIMARY KEY,"
                + "nome_livro VARCHAR(255) NOT NULL,"
                + "isbn VARCHAR(50),"
                + "autor VARCHAR(255),"
                + "data_publicacao DATE,"
                + "valor_livro DECIMAL(10,2)"
                + ");";

        String sqlEstados = "CREATE TABLE IF NOT EXISTS estados ("
                + "id SERIAL PRIMARY KEY,"
                + "nome_estado VARCHAR(100) NOT NULL,"
                + "sigla_estado VARCHAR(2) NOT NULL UNIQUE"
                + ");";

        try (Statement stmt = conn.createStatement()) {
            stmt.execute(sqlLivros);
            stmt.execute(sqlEstados);
        } catch (SQLException e) {
        }
    }
}
