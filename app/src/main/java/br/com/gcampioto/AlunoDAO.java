package br.com.gcampioto;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.annotation.NonNull;

import java.util.ArrayList;
import java.util.List;

import br.com.gcampioto.model.Aluno;

/**
 * Created by Gabris on 30/04/2016.
 */
public class AlunoDAO extends SQLiteOpenHelper {
    private static String DATABASE_NAME = "Alunos";
    private static Integer DATABASE_VERSION = 2;
    private static String TABLE_ALUNOS = "alunos";


    public AlunoDAO(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql = "CREATE TABLE " + TABLE_ALUNOS + "(" +
                "id INTEGER PRIMARY KEY," +
                "nome TEXT NOT NULL," +
                "endereco TEXT," +
                "telefone TEXT," +
                "site TEXT," +
                "nota REAL" +
                "pathFoto TEXT);";

        db.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String sql = "";
        switch (oldVersion){
            case 1:
                sql = "ALTER TABLE Alunos ADD COLUMN pathFoto TEXT";
                db.execSQL(sql);
        }
    }

    public void insert(Aluno aluno) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = getContentValues(aluno);
        db.insert(TABLE_ALUNOS, null, values);
    }

    public List<Aluno> getAllAlunos() {
        List<Aluno> allAlunos = new ArrayList<>();
        SQLiteDatabase db = getReadableDatabase();
        String sql = "SELECT * FROM " + TABLE_ALUNOS + ";";
        Cursor c = db.rawQuery(sql, null);
        while(c.moveToNext()){
            Aluno aluno = new Aluno();
            aluno.setId(c.getLong(c.getColumnIndex("id")));
            aluno.setNome(c.getString(c.getColumnIndex("nome")));
            aluno.setEndereco(c.getString(c.getColumnIndex("endereco")));
            aluno.setTelefone(c.getString(c.getColumnIndex("telefone")));
            aluno.setSite(c.getString(c.getColumnIndex("site")));
            aluno.setNota(c.getDouble(c.getColumnIndex("nota")));
            aluno.setPathFoto(c.getString(c.getColumnIndex("pathFoto")));
            allAlunos.add(aluno);
        }
        c.close();
        return allAlunos;
    }

    public void delete(Aluno aluno) {
        SQLiteDatabase db = getWritableDatabase();
        String [] params = {aluno.getId().toString()};
        db.delete(TABLE_ALUNOS, "id = ?", params);
    }

    public void updateAluno(Aluno aluno) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = getContentValues(aluno);
        String [] params = {aluno.getId().toString()};
        db.update(TABLE_ALUNOS, values, "id = ?", params);
    }

    @NonNull
    private ContentValues getContentValues(Aluno aluno) {
        ContentValues values = new ContentValues();
        values.put("nome", aluno.getNome());
        values.put("endereco", aluno.getEndereco());
        values.put("telefone", aluno.getTelefone());
        values.put("site", aluno.getSite());
        values.put("nota", aluno.getNota());
        values.put("pathFoto", aluno.getPathFoto());
        return values;
    }
}
