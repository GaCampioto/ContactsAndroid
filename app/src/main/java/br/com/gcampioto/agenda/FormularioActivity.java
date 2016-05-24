package br.com.gcampioto.agenda;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.util.zip.Inflater;

import br.com.gcampioto.AlunoDAO;
import br.com.gcampioto.helper.FormularioHelper;
import br.com.gcampioto.model.Aluno;

public class FormularioActivity extends AppCompatActivity {
    private FormularioHelper formularioHelper;
    private Aluno alunoExtra;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_formulario);

        // Find the toolbar view inside the activity layout
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_formulario);
        // Sets the Toolbar to act as the ActionBar for this Activity window.
        // Make sure the toolbar exists in the activity and is not null
        setSupportActionBar(toolbar);
        formularioHelper = new FormularioHelper(this);
        alunoExtra = (Aluno) getIntent().getSerializableExtra("aluno");
        if(alunoExtra != null){
            fillField(alunoExtra);
        }
    }

    private void fillField(Aluno alunoExtra) {
        formularioHelper.setId(alunoExtra.getId());
        formularioHelper.setNome(alunoExtra.getNome());
        formularioHelper.setEndereco(alunoExtra.getEndereco());
        formularioHelper.setTelefone(alunoExtra.getTelefone());
        formularioHelper.setSite(alunoExtra.getSite());
        formularioHelper.setNota(alunoExtra.getNota());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu_formulario, menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.menu_confirmar:
                Aluno aluno = formularioHelper.getAlunoFromFormulario();
                AlunoDAO alunoDAO = new AlunoDAO(this);
                try {
                    if (aluno.getId() != null) {
                        alunoDAO.updateAluno(aluno);
                    } else {
                        alunoDAO.insert(aluno);
                    }
                    Toast.makeText(FormularioActivity.this, "Aluno salvo com sucesso!", Toast.LENGTH_SHORT)
                            .show();
                } catch (Exception e){
                    Toast.makeText(FormularioActivity.this, "Erro ao guardar aluno: " + aluno.getNome(), Toast.LENGTH_SHORT)
                            .show();
                    Log.e("ERRO: ", Log.getStackTraceString(e));
                } finally {
                    alunoDAO.close();
                }
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
