package br.com.gcampioto.helper;

import android.app.Activity;
import android.widget.EditText;
import android.widget.RatingBar;

import br.com.gcampioto.agenda.R;
import br.com.gcampioto.model.Aluno;

/**
 * Created by Gabris on 30/04/2016.
 */
public class FormularioHelper {
    private Activity formulario;
    private Long id;
    private EditText nome;
    private EditText endereco;
    private EditText telefone;
    private EditText site;
    private RatingBar nota;

    public FormularioHelper (Activity formulario){
        this.formulario = formulario;
        this.nome = (EditText) formulario.findViewById(R.id.formulario_nome);
        this.endereco = (EditText) formulario.findViewById(R.id.formulario_endereco);
        this.telefone = (EditText) formulario.findViewById(R.id.formulario_telefone);
        this.site = (EditText) formulario.findViewById(R.id.formulario_site);
        this.nota = (RatingBar) formulario.findViewById(R.id.formulario_rating);
    }

    public Aluno getAlunoFromFormulario(){
        Aluno aluno = new Aluno();
        aluno.setId(getId());
        aluno.setNome(getNome());
        aluno.setEndereco(getEndereco());
        aluno.setTelefone(getTelefone());
        aluno.setSite(getSite());
        aluno.setNota(getNota());
        return aluno;
    }

    public String getNome() {
        return nome.getText().toString();
    }

    public void setNome(String nome) {this.nome.setText(nome);}

    public String getEndereco() {
        return endereco.getText().toString();
    }

    public void setEndereco(String endereco) {this.endereco.setText(endereco);}

    public String getTelefone() {
        return telefone.getText().toString();
    }

    public void setTelefone(String telefone) {this.telefone.setText(telefone);}

    public String getSite() {
        return site.getText().toString();
    }

    public void setSite (String site) {this.site.setText(site);}

    public Double getNota() {
        return Double.valueOf(nota.getRating());
    }

    public void setNota(Double nota) {this.nota.setRating(nota.floatValue());}

    public Long getId() { return id; }

    public void setId(Long id) { this.id = id; }
}
