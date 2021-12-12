package com.example.appprofesor;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import info.androidhive.sqlite.model.Profesor;
import info.androidhive.sqlite.model.Tareas;

public class TareaActivity extends AppCompatActivity {
    Tareas TareaActual;
    TextView HoraEntrega,Feedback,NombreObj,cantObj,status;
    ImageView FotoObjeto,FotoFeedback;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tarea);

        HoraEntrega = (TextView) findViewById(R.id.HoraEntrega);
        NombreObj = (TextView) findViewById(R.id.nombreObjeto);
        cantObj = (TextView) findViewById(R.id.CantidadObjeto);
        status = (TextView) findViewById(R.id.statusTarea);
        Feedback = (TextView) findViewById(R.id.Feedback);
        FotoFeedback = (ImageView) findViewById(R.id.pictogramaFeedback);
        FotoObjeto =  (ImageView) findViewById(R.id.imagenObjeto);

        if(getIntent().getExtras() != null) {
            TareaActual = (Tareas) getIntent().getSerializableExtra("tarea");
            //if(TareaActual != null) {
                HoraEntrega.setText(TareaActual.getHoraEntrega());
                NombreObj.setText(TareaActual.getNombreObjeto());
                cantObj.setText(String.valueOf(TareaActual.getCantidadObjeto()));
                status.setText(String.valueOf(TareaActual.getStatus()));
                FotoObjeto.setImageResource(TareaActual.getIdFoto());

                if(TareaActual.getIdFotoFeedback()!=0){//si es !=0 entonces es que hay feedback
                    FotoFeedback.setVisibility(View.VISIBLE);
                    FotoFeedback.setImageResource(TareaActual.getIdFotoFeedback());
                    Feedback.setVisibility(View.VISIBLE);
                    Feedback.setText(TareaActual.getComentario());
                }
            //}
        }
    }
    public void Volver(View view){
        Intent Volver = new Intent(this, MainActivity.class);
        startActivity(Volver);
    }
}