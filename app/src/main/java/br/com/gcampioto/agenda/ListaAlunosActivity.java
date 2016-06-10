package br.com.gcampioto.agenda;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Browser;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.ContextMenu;
import android.view.MenuInflater;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.webkit.WebView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.security.cert.CertPathBuilderSpi;
import java.util.List;

import br.com.gcampioto.AlunoDAO;
import br.com.gcampioto.SendNotasAsync;
import br.com.gcampioto.WebClient;
import br.com.gcampioto.adapter.AlunoAdapter;
import br.com.gcampioto.converter.AlunoConverter;
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
        if(ActivityCompat.checkSelfPermission(ListaAlunosActivity.this, Manifest.permission.RECEIVE_SMS)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(ListaAlunosActivity.this, new String[]{Manifest.permission.RECEIVE_SMS}, 1);
        }
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

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.menu_enviar_notas:
                new SendNotasAsync(this).execute();
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    private void populateListAlunos() {
        AlunoDAO alunoDAO = new AlunoDAO(this);
        try {
            List<Aluno> alunos = alunoDAO.getAllAlunos();
            AlunoAdapter adapter = new AlunoAdapter(this, alunos);
            listViewAlunos.setAdapter(adapter);
        } catch (Exception e) {
            Toast.makeText(ListaAlunosActivity.this, "Erro ao recuperar os alunos", Toast.LENGTH_SHORT).show();
            Log.e("ERRO: ", Log.getStackTraceString(e));
        } finally {
            alunoDAO.close();
        }
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) menuInfo;
        final Aluno aluno = (Aluno) listViewAlunos.getItemAtPosition(info.position);

        MenuItem menuItem = menu.add("Deletar");
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

        MenuItem itemSMS = menu.add("Enviar SMS");
        Intent sms = new Intent(Intent.ACTION_VIEW);
        sms.setData(Uri.parse("sms:" + aluno.getTelefone()));
        itemSMS.setIntent(sms);

        MenuItem itemCall = menu.add("Ligar");
        itemCall.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                Intent call = new Intent(Intent.ACTION_CALL);
                call.setData(Uri.parse("tel:" + aluno.getTelefone()));
                if(ActivityCompat.checkSelfPermission(ListaAlunosActivity.this, Manifest.permission.CALL_PHONE)
                        != PackageManager.PERMISSION_GRANTED){
                    ActivityCompat.requestPermissions(ListaAlunosActivity.this, new String [] {Manifest.permission.CALL_PHONE}, 1);
                } else {
                    startActivity(call);
                }
                return false;
            }
        });

        String site = aluno.getSite();
        if (!site.startsWith("http://")) {
            site = "http://" + aluno.getSite();
        }

        MenuItem itemSite = menu.add("Visitar site");
        Intent intentSite = new Intent(Intent.ACTION_VIEW);
        intentSite.setData(Uri.parse(site));
        itemSite.setIntent(intentSite);


        MenuItem itemMap = menu.add("Visualizar endereço");
        Intent map = new Intent(Intent.ACTION_VIEW);
        map.setData(Uri.parse("geo:0.0?q=" + aluno.getEndereco()));
        itemMap.setIntent(map);
    }
}
