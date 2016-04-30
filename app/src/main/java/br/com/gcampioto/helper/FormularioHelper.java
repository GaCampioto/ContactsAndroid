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

    public String getEndereco() {
        return endereco.getText().toString();
    }

    public String getTelefone() {
        return telefone.getText().toString();
    }

    public String getSite() {
        return site.getText().toString();
    }

    public Double getNota() {
        return Double.valueOf(nota.getRating());
    }
}
