package br.com.gcampioto.helper;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Image;
import android.widget.EditText;
import android.widget.ImageView;
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
    private ImageView foto;

    public FormularioHelper (Activity formulario){
        this.formulario = formulario;
        this.nome = (EditText) formulario.findViewById(R.id.formulario_nome);
        this.endereco = (EditText) formulario.findViewById(R.id.formulario_endereco);
        this.telefone = (EditText) formulario.findViewById(R.id.formulario_telefone);
        this.site = (EditText) formulario.findViewById(R.id.formulario_site);
        this.nota = (RatingBar) formulario.findViewById(R.id.formulario_rating);
        this.foto = (ImageView) formulario.findViewById(R.id.formulario_foto);
    }

    public Aluno getAlunoFromFormulario(){
        Aluno aluno = new Aluno();
        aluno.setId(getId());
        aluno.setNome(getNome());
        aluno.setEndereco(getEndereco());
        aluno.setTelefone(getTelefone());
        aluno.setSite(getSite());
        aluno.setNota(getNota());
        aluno.setPathFoto(getPathFoto());
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

    public String getPathFoto() {return (String) this.foto.getTag();}


    public void loadImageView(String pathFoto) {
        if(pathFoto != null) {
            Bitmap bitmap = BitmapFactory.decodeFile(pathFoto);
            bitmap = Bitmap.createScaledBitmap(bitmap, 300, 300, true);
            foto.setImageBitmap(bitmap);
            foto.setScaleType(ImageView.ScaleType.FIT_XY);
            foto.setTag(pathFoto);
        }
    }
}
