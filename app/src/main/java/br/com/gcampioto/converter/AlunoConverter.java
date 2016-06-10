package br.com.gcampioto.converter;

import android.content.Context;

import org.json.JSONException;
import org.json.JSONStringer;

import java.util.List;

import br.com.gcampioto.agenda.ListaAlunosActivity;
import br.com.gcampioto.model.Aluno;

/**
 * Created by gcampioto on 10/06/16.
 */
public class AlunoConverter {
    public static String toJSON (List<Aluno> alunos){
        JSONStringer jsonStringer = new JSONStringer();
        try {
            jsonStringer.object().key("list").array()
                        .object().key("aluno").array();
            for(Aluno aluno : alunos){
                jsonStringer.object();
                jsonStringer.key("nome").value(aluno.getNome());
                jsonStringer.key("telefone").value(aluno.getTelefone());
                jsonStringer.key("endereco").value(aluno.getEndereco());
                jsonStringer.key("site").value(aluno.getSite());
                jsonStringer.key("nota").value(aluno.getNota());
                jsonStringer.endObject();
            }
            jsonStringer.endArray().endObject()
                        .endArray().endObject();
            return jsonStringer.toString();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }
}
