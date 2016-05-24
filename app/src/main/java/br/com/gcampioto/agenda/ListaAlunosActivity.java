package br.com.gcampioto.agenda;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.ContextMenu;
import android.view.MenuInflater;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.security.cert.CertPathBuilderSpi;
import java.util.List;

import br.com.gcampioto.AlunoDAO;
import br.com.gcampioto.model.Aluno;

public class ListaAlunosActivity extends AppCompatActivity {
    private ListView listViewAlunos;

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

        listViewAlunos = (ListView) findViewById(R.id.lista_alunos);
        listViewAlunos.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(ListaAlunosActivity.this, FormularioActivity.class);
                intent.putExtra("aluno", (Aluno) listViewAlunos.getItemAtPosition(position));
                startActivity(intent);
            }
        });
        registerForContextMenu(listViewAlunos);
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
            ArrayAdapter<Aluno> adapterString = new ArrayAdapter<Aluno>(this, android.R.layout.simple_list_item_1, alunos);
            listViewAlunos.setAdapter(adapterString);
        } catch (Exception e){
            Toast.makeText(ListaAlunosActivity.this, "Erro ao recupera os alunos", Toast.LENGTH_SHORT).show();
            Log.e("ERRO: ", Log.getStackTraceString(e));
        } finally {
            alunoDAO.close();
        }
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        MenuItem menuItem = menu.add("Deletar");
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) menuInfo;
        final Aluno aluno = (Aluno) listViewAlunos.getItemAtPosition(info.position);
        menuItem.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                AlunoDAO alunoDAO = new AlunoDAO(ListaAlunosActivity.this);
                try {
                    alunoDAO.delete(aluno);
                    Toast.makeText(ListaAlunosActivity.this, "Aluno deletado", Toast.LENGTH_SHORT).show();
                } catch (Exception e){
                    Toast.makeText(ListaAlunosActivity.this, "Erro ao deletar aluno", Toast.LENGTH_SHORT).show();
                    Log.e("ERRO: ", Log.getStackTraceString(e));
                } finally {
                    alunoDAO.close();
                }
                populateListAlunos();
                return false;
            }
        });
    }
}
