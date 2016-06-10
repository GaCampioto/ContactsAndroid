package br.com.gcampioto;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

import br.com.gcampioto.agenda.R;
import br.com.gcampioto.converter.AlunoConverter;
import br.com.gcampioto.model.Aluno;

/**
 * Created by gcampioto on 10/06/16.
 */
public class SendNotasAsync extends AsyncTask <Void, Void, String> {
    private Context context;
    private ProgressDialog progressDialog;

    public SendNotasAsync(Context context) {
        this.context = context;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        progressDialog = ProgressDialog.show(context, context.getString(R.string.dialog_wait),
                context.getString(R.string.dialog_wait_message), true, true);
    }

    @Override
    protected String doInBackground(Void... params) {
        AlunoDAO dao = new AlunoDAO(context);
        List<Aluno> alunos = dao.getAllAlunos();
        dao.close();
        String json = AlunoConverter.toJSON(alunos);
        String resposta = WebClient.post(json);
        return resposta;
    }

    @Override
    protected void onPostExecute(String resposta) {
        progressDialog.dismiss();
        String media = "";
        String quantidade = "";
        try {
            JSONObject json = new JSONObject(resposta);
            media = json.getString("media");
            quantidade = json.getString("quantidade");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(context);
        alertDialog.setTitle(context.getString(R.string.dialog_success_title));
        alertDialog.setMessage("Resultado da chamado do serviço:"
                + "\nQuantidade de alunos: " + quantidade
                + "\nMédia dos alunos: " + media);
        alertDialog.create();
        alertDialog.show();
        super.onPostExecute(resposta);
    }
}
