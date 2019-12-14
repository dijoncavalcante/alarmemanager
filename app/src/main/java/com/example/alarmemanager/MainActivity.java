package com.example.alarmemanager;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Calendar;

public class MainActivity extends AppCompatActivity {

    Button buttonStart;
    Button buttonCancel;
    TextView tvText;
    EditText etMinutos;
    EditText etSegundos;
    private static final String TAG = "MainActivity";
    AlarmManager alarmManager;
    PendingIntent pendingIntent;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
//        Intent alarmIntent = new Intent(this, AlarmeReceiver.class);
//        pendingIntent = PendingIntent.getBroadcast(this, 0, alarmIntent, 0);

        buttonStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (validarCampos())
                    startAlarm();
            }
        });

        buttonCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cancelAlarm();
            }
        });
    }

    public void startAlarm() {
        //alarmManager.setInexactRepeating(AlarmManager.RTC_WAKEUP, 0, 5000, pendingIntent);
        //alarmManager.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP,5000,pendingIntent);
//        alarmManager.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP, 5000 * 60 * 5, pendingIntent);
//

        // primeiro cria a intenção

        Intent it = new Intent(this, AlarmeReceiver.class);

        PendingIntent p = PendingIntent.getBroadcast(this, 0, it, 0);


        // precisamos pegar agora + 10segundos

        Calendar c = Calendar.getInstance();

        c.setTimeInMillis(System.currentTimeMillis());
        String txtNotificacao = "Start do Alarme  às: "
                + Calendar.getInstance().get(Calendar.HOUR) + ":"
                + Calendar.getInstance().get(Calendar.MINUTE) + ":"
                + Calendar.getInstance().get(Calendar.SECOND);
        Log.d(TAG, txtNotificacao);
        Toast.makeText(getApplicationContext(), "Alarm Start = " + txtNotificacao, Toast.LENGTH_LONG).show();
        int minutos = Integer.valueOf(etMinutos.getText().toString());
        int segundos = Integer.valueOf(etSegundos.getText().toString());
        c.add(Calendar.MINUTE, minutos);
        c.add(Calendar.SECOND, segundos);
        // agendar o alarme
        AlarmManager alarme = (AlarmManager) getSystemService(ALARM_SERVICE);
        long time = c.getTimeInMillis();
//        alarme.set(AlarmManager.RTC_WAKEUP, time, p);
        alarme.setExactAndAllowWhileIdle (AlarmManager.RTC_WAKEUP, time, p);
        tvText.setText(txtNotificacao + ". Agendado para: "
                                               + c.get(Calendar.HOUR)
                                               +":"+c.get(Calendar.MINUTE)
                                               +":"+c.get(Calendar.SECOND));
    }

    public void cancelAlarm(){
        alarmManager.cancel(pendingIntent);
        Toast.makeText(getApplicationContext(), "Alarm Cancelled", Toast.LENGTH_LONG).show();
    }

    public void init(){
        buttonStart = findViewById(R.id.buttonStart);
        buttonCancel = findViewById(R.id.buttonCancel);
        tvText = findViewById(R.id.tvText);
        etMinutos = findViewById(R.id.etMinutos);
        etSegundos = findViewById(R.id.etSegundos);
        alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
    }

    public boolean validarCampos(){
        if (TextUtils.isEmpty(etMinutos.getText())){
            etMinutos.setError("Campo etMinutos obrigatório");
            etMinutos.setFocusable(true);
            return false;
        }
        if (TextUtils.isEmpty(etSegundos.getText())){
            etSegundos.setError("Campo etSegundos obrigatório");
            etSegundos.setFocusable(true);
            return false;
        }
        return true;
    }
}
