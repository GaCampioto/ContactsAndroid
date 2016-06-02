package br.com.gcampioto.agenda;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;

import java.io.File;
import java.util.zip.Inflater;

import br.com.gcampioto.AlunoDAO;
import br.com.gcampioto.helper.FormularioHelper;
import br.com.gcampioto.model.Aluno;

public class FormularioActivity extends AppCompatActivity {
    public static final int CAMERA_CODE = 1;
    private FormularioHelper formularioHelper;
    private Aluno alunoExtra;
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;
    private String pathFoto;

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
        if (alunoExtra != null) {
            fillField(alunoExtra);
        }

        FloatingActionButton btnFoto = (FloatingActionButton) findViewById(R.id.formulario_btn_foto);
        btnFoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(ActivityCompat.checkSelfPermission(FormularioActivity.this, Manifest.permission.CAMERA)
                        != PackageManager.PERMISSION_GRANTED){
                    ActivityCompat.requestPermissions(FormularioActivity.this, new String [] {Manifest.permission.CAMERA}, CAMERA_CODE);
                } else {
                    Intent intentCamera = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    pathFoto = getExternalFilesDir(null) + "/"+ System.currentTimeMillis() + ".jpg";//parametro representa sub pasta padrão
                    File foto = new File(pathFoto);
                    intentCamera.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(foto));
                    startActivityForResult(intentCamera, CAMERA_CODE);
                }

            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == CAMERA_CODE && resultCode == RESULT_OK){
            formularioHelper.loadImageView(pathFoto);
        }
    }

    private void fillField(Aluno alunoExtra) {
        formularioHelper.setId(alunoExtra.getId());
        formularioHelper.setNome(alunoExtra.getNome());
        formularioHelper.setEndereco(alunoExtra.getEndereco());
        formularioHelper.setTelefone(alunoExtra.getTelefone());
        formularioHelper.setSite(alunoExtra.getSite());
        formularioHelper.setNota(alunoExtra.getNota());
        formularioHelper.loadImageView(alunoExtra.getPathFoto());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu_formulario, menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
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
                } catch (Exception e) {
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
