package br.com.gcampioto.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Image;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import br.com.gcampioto.agenda.ListaAlunosActivity;
import br.com.gcampioto.agenda.R;
import br.com.gcampioto.model.Aluno;

/**
 * Created by gcampioto on 10/06/16.
 */
public class AlunoAdapter extends BaseAdapter {
    private final Context context;
    private List<Aluno> alunos;

    public AlunoAdapter(Context context, List<Aluno> alunos) {
        this.context = context;
        this.alunos = alunos;
    }

    @Override
    public int getCount() {
        return alunos.size();
    }

    @Override
    public Object getItem(int position) {
        return alunos.get(position);
    }

    @Override
    public long getItemId(int position) {
        return alunos.get(position).getId();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Aluno aluno = alunos.get(position);
        View view = convertView;

        if (view == null){
            LayoutInflater inflater = LayoutInflater.from(context);
            //First parameter layout
            //Second parameter parent view
            //Third parameter - false = do not insert the element into the parent
            view = inflater.inflate(R.layout.list_item, parent, false);
        }

        TextView tvNome = (TextView) view.findViewById(R.id.item_nome);
        tvNome.setText(aluno.getNome());

        TextView tvTelefone = (TextView) view.findViewById(R.id.item_telefone);
        tvTelefone.setText(aluno.getTelefone());

        ImageView imgView = (ImageView) view.findViewById(R.id.item_foto);
        String pathFoto = aluno.getPathFoto();
        if(pathFoto != null) {
            Bitmap bitmap = BitmapFactory.decodeFile(pathFoto);
            bitmap = Bitmap.createScaledBitmap(bitmap, 300, 300, true);
            imgView.setImageBitmap(bitmap);
            imgView.setScaleType(ImageView.ScaleType.FIT_XY);
        }

        return view;
    }
}
