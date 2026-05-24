package br.com.livraria.model;

public class Estado {

    private int id;
    private String nomeEstado;
    private String siglaEstado;

    public Estado() {
    }

    public Estado(int id, String nomeEstado, String siglaEstado) {
        this.id = id;
        this.nomeEstado = nomeEstado;
        this.siglaEstado = siglaEstado;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNomeEstado() {
        return nomeEstado;
    }

    public void setNomeEstado(String nomeEstado) {
        this.nomeEstado = nomeEstado;
    }

    public String getSiglaEstado() {
        return siglaEstado;
    }

    public void setSiglaEstado(String siglaEstado) {
        this.siglaEstado = siglaEstado;
    }
}
