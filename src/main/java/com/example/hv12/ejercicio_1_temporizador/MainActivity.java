package com.example.hv12.ejercicio_1_temporizador;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {


    TextView lblContador;
    Button btniIniciar;
    ContadorAsincrono contador;
    EditText txtcantidad;
    ProgressBar barra;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        lblContador = findViewById(R.id.lblContador);
        btniIniciar = findViewById(R.id.btnIniciar);
        txtcantidad = findViewById(R.id.txtcantidad);
        barra = findViewById(R.id.ProgressBarra);


        btniIniciar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                iniciarContador();
            }
        });
    }

    private void iniciarContador(){
        /**si es primera vez -> se inicia **/
        if(contador==null){
            contador = new ContadorAsincrono(this,lblContador,barra);
            contador.execute(Integer.parseInt(txtcantidad.getText().toString()));
            /**si ha terminado de ejecutar el hilo -> se crea otro hilo **/
        }else if(contador.getStatus()==AsyncTask.Status.FINISHED){
            contador = new ContadorAsincrono(this,lblContador,barra);
            contador.execute(Integer.parseInt(txtcantidad.getText().toString()));
            btniIniciar.setText("pausar");
            /** si esta ejecutado y no esta pausado -> entonces se pausa**/
        }else if(contador.getStatus()== AsyncTask.Status.RUNNING && !contador.esPausa()  ){
            contador.pausarContador();
            btniIniciar.setText("Reanudar");
            /** si no entro en las condiciones anteriores por defecto esta pausado -> se reanuda*/
        }else{
            contador.reanudarContador();
            btniIniciar.setText("Pausar");

        }
    }
}
