package br.com.gcampioto.agenda;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuInflater;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.List;

import br.com.gcampioto.AlunoDAO;
import br.com.gcampioto.model.Aluno;

public class ListaAlunosActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_alunos);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ListaAlunosActivity.this, FormularioActivity.class);
                startActivity(intent);
            }
        });

        // Find the toolbar view inside the activity layout
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_lista_alunos);
        // Sets the Toolbar to act as the ActionBar for this Activity window.
        // Make sure the toolbar exists in the activity and is not null
        setSupportActionBar(toolbar);
    }

    @Override
    protected void onResume() {
        super.onResume();
        populateListAlunos();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu_lista_alunos, menu);

        return super.onCreateOptionsMenu(menu);
    }

    private void populateListAlunos() {
        AlunoDAO alunoDAO = new AlunoDAO(this);
        try {
            List<Aluno> alunos = alunoDAO.getAllAlunos();
            ListView listViewAlunos = (ListView) findViewById(R.id.lista_alunos);
            ArrayAdapter<Aluno> adapterString = new ArrayAdapter<Aluno>(this, android.R.layout.simple_list_item_1, alunos);
            listViewAlunos.setAdapter(adapterString);
        } catch (Exception e){
            Toast.makeText(ListaAlunosActivity.this, "Erro ao recupera os alunos", Toast.LENGTH_SHORT).show();
            Log.e("ERRO: ", Log.getStackTraceString(e));
        } finally {
            alunoDAO.close();
        }
    }
}
