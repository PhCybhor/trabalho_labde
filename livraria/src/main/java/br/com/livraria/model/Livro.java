package br.com.livraria.model;

import java.time.LocalDate;

public class Livro {

    private int codigo;
    private String titulo;
    private String isbn;
    private String autor;
    private String email;
    private LocalDate dtPublicacao;
    private double preco;

    public Livro() {
    }

    public Livro(int codigo, String titulo, String isbn, String autor, String email, LocalDate dtPublicacao, double preco) {
        this.codigo = codigo;
        this.titulo = titulo;
        this.isbn = isbn;
        this.autor = autor;
        this.email = email;
        this.dtPublicacao = dtPublicacao;
        this.preco = preco;
    }

    public int getCodigo() {
        return codigo;
    }

    public void setCodigo(int codigo) {
        this.codigo = codigo;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public String getAutor() {
        return autor;
    }

    public void setAutor(String autor) {
        this.autor = autor;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public LocalDate getDtPublicacao() {
        return dtPublicacao;
    }

    public void setDtPublicacao(LocalDate dtPublicacao) {
        this.dtPublicacao = dtPublicacao;
    }

    public double getPreco() {
        return preco;
    }

    public void setPreco(double preco) {
        this.preco = preco;
    }

    public String getPublicacaoFormatada() {
        if (this.dtPublicacao != null) {
            return this.dtPublicacao.format(java.time.format.DateTimeFormatter.ofPattern("dd/MM/yyyy"));
        }
        return "";
    }

    public String getPrecoFormatado() {
        return String.format(java.util.Locale.forLanguageTag("pt-BR"), "%.2f", this.preco);
    }
}
