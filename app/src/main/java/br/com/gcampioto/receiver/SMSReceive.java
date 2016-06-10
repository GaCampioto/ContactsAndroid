package br.com.gcampioto.receiver;

import android.Manifest;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.telephony.SmsMessage;
import android.util.Log;
import android.widget.Toast;

import java.io.File;

import br.com.gcampioto.AlunoDAO;
import br.com.gcampioto.agenda.ListaAlunosActivity;
import br.com.gcampioto.agenda.R;

/**
 * Created by gcampioto on 10/06/16.
 */
public class SMSReceive extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        Object[] pdus = (Object[]) intent.getSerializableExtra("pdus");
        byte[] pdu = (byte[]) pdus[0];

        String format = (String) intent.getSerializableExtra("format");

        SmsMessage msg = SmsMessage.createFromPdu(pdu, format);
        String telefone = msg.getDisplayOriginatingAddress();
        AlunoDAO dao = new AlunoDAO(context);

        if(dao.isAluno(telefone)) {
            MediaPlayer mp = MediaPlayer.create(context, R.raw.msg);
            mp.start();
            Toast.makeText(context, "Getting sms via broadcast", Toast.LENGTH_SHORT).show();
        }
        dao.close();
    }
}
