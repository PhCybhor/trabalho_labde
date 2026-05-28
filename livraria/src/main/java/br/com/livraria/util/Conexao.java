package br.com.livraria.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Conexao {

    private static final String ENDERECO_JDBC = "jdbc:postgresql://localhost:5432/";
    private static final String NOME_BANCO = "livraria";
    private static final String LOGIN_BD = "postgres";
    private static final String SENHA_BD = "postdba";

    public Connection getConexao() {
        try {
            Class.forName("org.postgresql.Driver");
            validarBanco();
            Connection conexaoJdbc = DriverManager.getConnection(ENDERECO_JDBC + NOME_BANCO, LOGIN_BD, SENHA_BD);
            criarTabelas(conexaoJdbc);
            return conexaoJdbc;
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    private void validarBanco() {
        try (Connection conexaoJdbc = DriverManager.getConnection(ENDERECO_JDBC + "postgres", LOGIN_BD, SENHA_BD);
             Statement comando = conexaoJdbc.createStatement()) {

            ResultSet resultado = conexaoJdbc.getMetaData().getCatalogs();
            boolean bancoExiste = false;
            while (resultado.next()) {
                if (NOME_BANCO.equals(resultado.getString(1))) {
                    bancoExiste = true;
                    break;
                }
            }

            if (!bancoExiste) {
                comando.executeUpdate("CREATE DATABASE " + NOME_BANCO);
            }

        } catch (SQLException e) {
        }
    }

    private void criarTabelas(Connection conexaoJdbc) {
        String ddlLivros = "CREATE TABLE IF NOT EXISTS livros ("
                + "id SERIAL PRIMARY KEY,"
                + "nome_livro VARCHAR(255) NOT NULL,"
                + "isbn VARCHAR(50),"
                + "autor VARCHAR(255),"
                + "email VARCHAR(255),"
                + "data_publicacao DATE,"
                + "valor_livro DECIMAL(10,2)"
                + ");";

        String ddlEstados = "CREATE TABLE IF NOT EXISTS estados ("
                + "id SERIAL PRIMARY KEY,"
                + "nome_estado VARCHAR(100) NOT NULL,"
                + "sigla_estado VARCHAR(2) NOT NULL UNIQUE"
                + ");";

        try (Statement comando = conexaoJdbc.createStatement()) {
            comando.execute(ddlLivros);
            comando.execute(ddlEstados);
            comando.execute("ALTER TABLE livros ADD COLUMN IF NOT EXISTS email VARCHAR(255)");
            comando.execute("CREATE UNIQUE INDEX IF NOT EXISTS ux_livros_email_lower ON livros (LOWER(email)) WHERE email IS NOT NULL");
        } catch (SQLException e) {
        }
    }
}
