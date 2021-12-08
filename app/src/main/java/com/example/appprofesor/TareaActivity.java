package com.example.appprofesor;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import info.androidhive.sqlite.helper.DatabaseHelper;
import info.androidhive.sqlite.model.Tareas;

public class TareaActivity extends AppCompatActivity {
/*
    DatabaseHelper admin = new DatabaseHelper(this, "administracion", null, 1);
    Tareas tarea;
    TextView textstatus,textcantidad,texthora_feedback;
    ImageView fotoObjeto, fotoFeedback;
    View botonEntrega, botonNoEntrega, botonFeedb;
    private String IdTarea = "";
    private String IdProfe = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        IdTarea = getIntent().getStringExtra("IdTarea");
        IdProfe= getIntent().getStringExtra("IdProfe");

        tarea = admin.getTarea(IdTarea);
        setViewTarea(tarea);

        setContentView(R.layout.activity_tarea);
    }

    public void setViewTarea(Tareas tarea){

        String status="",
                hora = "",
                cantidad = "";

        textstatus = (TextView)findViewById(R.id.statusTarea);
        textcantidad = (TextView)findViewById(R.id.CantidadObjeto);
        texthora_feedback= (TextView)findViewById(R.id.horaentregaFeedback);
        fotoObjeto=(ImageView)findViewById(R.id.imagenObjeto);
        fotoFeedback=(ImageView)findViewById(R.id.pictogramaFeedback);
        botonFeedb=  findViewById(R.id.botonFeedback);
        botonNoEntrega = findViewById(R.id.botonNoEntrega);
        botonEntrega = findViewById(R.id.botonEntrega);


        //status de la tarea: 0 Sin empezar 1 Empezada, 2 Entregada, 3 Completa, 4 Sin Finalizar
        //según el estado de las tareas se muestran unos campos u otros
        switch(tarea.getStatus()) {
            case 1:
                status = "Empezada";
                fotoFeedback.setVisibility(View.INVISIBLE);
                botonFeedb.setVisibility(View.INVISIBLE);
                break;
            case 2:
                status = "Entregada";
                fotoFeedback.setVisibility(View.INVISIBLE);
                botonEntrega.setVisibility(View.INVISIBLE);
                botonNoEntrega.setVisibility(View.INVISIBLE);
                botonFeedb.setVisibility(View.INVISIBLE);
                //añadir un if de si el profe ha puesto feedback, si no pos que cambie un poco.
                texthora_feedback.setText("Tarea entregada. Esperando confirmación del profesor");
                break;
            case 3:
                status = "Completa";
                fotoFeedback.setVisibility(View.VISIBLE);
                botonEntrega.setVisibility(View.INVISIBLE);
                botonNoEntrega.setVisibility(View.INVISIBLE);
                botonFeedb.setVisibility(View.VISIBLE);
                texthora_feedback.setText(tarea.getComentario());
                break;
            case 4:
                status = "Sin finalizar";
                fotoFeedback.setVisibility(View.VISIBLE);
                botonEntrega.setVisibility(View.INVISIBLE);
                botonFeedb.setVisibility(View.VISIBLE);
                botonNoEntrega.setVisibility(View.INVISIBLE);
                break;

        }
        textstatus.setText("Status de la tarea= "+ status);
    }

    public void ConfirmarEntrega(View view){
        admin.setTareaConfirmada(tarea,IdTarea);
        Intent Volver = new Intent(this, MainActivity.class);
        Volver.putExtra("IdProfe",IdProfe);
        startActivity(Volver);
    }

    public void Volver(View view){
        Intent Volver = new Intent(this, MainActivity.class);
        Volver.putExtra("IdProfe",IdProfe);
        startActivity(Volver);
    }*/
}