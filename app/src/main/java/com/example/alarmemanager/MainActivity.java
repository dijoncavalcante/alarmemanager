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
        // primeiro cria a intenção
        Intent alarmIntent = new Intent(this, AlarmeReceiver.class);
        //cria a intenção pendente
        pendingIntent = PendingIntent.getBroadcast(this, 0, alarmIntent, 0);
        /*
            Métodos dos Clicks dos botões
         */
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
        Calendar c = Calendar.getInstance();
        c.setTimeInMillis(System.currentTimeMillis());
        String txtNotificacao = "Start do Alarme  às: "
                + Calendar.getInstance().get(Calendar.HOUR) + ":"
                + Calendar.getInstance().get(Calendar.MINUTE) + ":"
                + Calendar.getInstance().get(Calendar.SECOND);
        Log.d(TAG, txtNotificacao);
        Toast.makeText(getApplicationContext(), "Alarm Start = " + txtNotificacao, Toast.LENGTH_LONG).show();
        int minutos = Integer.valueOf(etMinutos.getText().toString()); //recupera os minutos informados pelo usuário
        int segundos = Integer.valueOf(etSegundos.getText().toString());//recupera os segundos informados pelo usuário
        c.add(Calendar.MINUTE, minutos);//add minutos a mais
        c.add(Calendar.SECOND, segundos);//add segundosa mais
        long time = c.getTimeInMillis();//usa uma variável do tipo long para receber os milisegundos
        // agenda o alarme
        alarmManager.setExactAndAllowWhileIdle (AlarmManager.RTC_WAKEUP, time, pendingIntent);
        //mostra o start do alarme e para que horas/minuto/segundo esta agendado o alarme
        tvText.setText(txtNotificacao + ". Agendado para: "
                                               + c.get(Calendar.HOUR)
                                               +":"+c.get(Calendar.MINUTE)
                                               +":"+c.get(Calendar.SECOND));
    }

    public void cancelAlarm(){
        alarmManager.cancel(pendingIntent);
        tvText.setText(R.string.exemploAlarmeManager);
        Toast.makeText(getApplicationContext(), getString(R.string.alarme_cancelado) , Toast.LENGTH_LONG).show();
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
        boolean error = true;
        if (TextUtils.isEmpty(etMinutos.getText())){
            etMinutos.setError(getString(R.string.campo_minuto_obrigatorio));
            etMinutos.setFocusable(true);
            error = false;
        }
        if (TextUtils.isEmpty(etSegundos.getText())){
            etSegundos.setError(getString(R.string.campo_segundo_obrigatorio));
            etSegundos.setFocusable(true);
            error = false;
        }
        if (error){
            etMinutos.setFocusable(false);
            etSegundos.setFocusable(false);
            return error;
        }else   {
            return error;
        }

    }
}
