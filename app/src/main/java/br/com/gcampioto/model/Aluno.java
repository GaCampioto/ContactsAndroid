package br.com.gcampioto.model;

import java.io.Serializable;

/**
 * Created by Gabris on 30/04/2016.
 */
public class Aluno implements Serializable{
    private Long    Id;
    private String  nome;
    private String  endereco;
    private String  telefone;
    private String  site;
    private Double  nota;

    public Long getId() {
        return Id;
    }

    public void setId(Long id) {
        Id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEndereco() {
        return endereco;
    }

    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public String getSite() {
        return site;
    }

    public void setSite(String site) {
        this.site = site;
    }

    public Double getNota() {
        return nota;
    }

    public void setNota(Double nota) {
        this.nota = nota;
    }

    @Override
    public String toString() {
        return getNome() + " - nota: " + getNota();
    }
}
