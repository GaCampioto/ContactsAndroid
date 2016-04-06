package br.com.gcampioto.agenda;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class ListaAlunosActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_alunos);
        String [] arrayAlunos = {"Aluno 1", "Aluno 2", "Aluno 3"};
        ListView listViewAlunos = (ListView) findViewById(R.id.lista_alunos);
        ArrayAdapter<String> adapterString = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, arrayAlunos);
        listViewAlunos.setAdapter(adapterString);
    }
}
