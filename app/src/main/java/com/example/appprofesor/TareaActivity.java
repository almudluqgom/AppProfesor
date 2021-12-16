package com.example.appprofesor;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;

import info.androidhive.sqlite.model.Profesor;
import info.androidhive.sqlite.model.Tareas;

public class TareaActivity extends AppCompatActivity {
    Tareas TareaActual; Profesor ProfesorActual;
    TextView HoraEntrega,Feedback,NombreObj,cantObj,status;
    ImageView FotoObjeto,FotoFeedback;
    ImageButton Bentr, BNoentr, BFeed;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tarea);

        Bentr = (ImageButton) findViewById(R.id.botonEntrega);
        BNoentr = (ImageButton) findViewById(R.id.botonNoEntrega);
        BFeed = (ImageButton) findViewById(R.id.botonFeedback);
        HoraEntrega = (TextView) findViewById(R.id.HoraEntregaTarea);
        NombreObj = (TextView) findViewById(R.id.nombreObjeto);
        cantObj = (TextView) findViewById(R.id.CantidadObjeto);
        status = (TextView) findViewById(R.id.statusTarea);
        Feedback = (TextView) findViewById(R.id.Feedback);
        FotoFeedback = (ImageView) findViewById(R.id.pictogramaFeedback);
        FotoObjeto =  (ImageView) findViewById(R.id.imagenObjeto);

        if(getIntent().getExtras() != null) {
            ProfesorActual = (Profesor) getIntent().getSerializableExtra("profe");
            TareaActual = (Tareas) getIntent().getSerializableExtra("tarea");
                HoraEntrega.setText(TareaActual.getHoraEntrega());
                NombreObj.setText(TareaActual.getNombreObjeto());
                cantObj.setText(String.valueOf(TareaActual.getCantidadObjeto()));
                switch (TareaActual.getStatus()){
                    case 0:
                       status.setText("Estado: Empezada");
                     break;
                    case 1:
                        status.setText("Estado: Entregada");
                        break;
                    case 2:
                        status.setText("Estado: Entregada y Validada por el Profesor");
                        Bentr.setVisibility(View.INVISIBLE);
                        BNoentr.setVisibility(View.INVISIBLE);
                        BFeed.setVisibility(View.VISIBLE);
                        break;
                    case 3:
                        status.setText("Estado: No entregada a Tiempo");
                        BFeed.setVisibility(View.VISIBLE);
                    break;
                    case 4:
                        status.setText("Estado: Finalizada con éxito");
                        BFeed.setVisibility(View.VISIBLE);
                        BNoentr.setVisibility(View.INVISIBLE);
                        Bentr.setVisibility(View.INVISIBLE);
                }

                FotoObjeto.setImageResource(TareaActual.getIdFoto());

                if(TareaActual.getIdFotoFeedback()!=0){//si es !=0 entonces es que hay feedback
                    FotoFeedback.setVisibility(View.VISIBLE);
                    FotoFeedback.setImageResource(TareaActual.getIdFotoFeedback());
                    Feedback.setVisibility(View.VISIBLE);
                    Feedback.setText(TareaActual.getComentario());
                    BFeed.setVisibility(View.VISIBLE);
                }
        }
    }
    public void Volver(View view){
        Intent Volver = new Intent(this, PeticionesActivity.class);
        Volver.putExtra("profe",ProfesorActual);
        startActivity(Volver);
    }
    public void EstaEntregada(View view){
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        String url="http://dgpsanrafael.000webhostapp.com/entregaTarea.php?confirmaQuien=2&id=" + TareaActual.getIdTarea();   //1 confirma entrega alumno, 2 confirma entrega profe, 3 no entrega
        //url= http://url
        StringRequest stringRequest = new StringRequest (Request.Method.POST,url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Toast.makeText(TareaActivity.this,"Tarea confirmada como ENTREGADA", Toast.LENGTH_LONG).show();
                        TareaActual.setStatus(2);
                        TareaActual.setConfirmaProfesor(true);
                        actualizaConteoTareas(String.valueOf(TareaActual.getIdAlumno()),2);

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(TareaActivity.this,error.getMessage(), Toast.LENGTH_LONG).show();
                    }
                }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                Map<String, String> params = new HashMap<String, String>();
                params.put("id", String.valueOf(TareaActual.getIdTarea()));
                params.put("confirmaQuien",String.valueOf(2));
                return params;
            }
        };
         requestQueue.add(stringRequest);
    }
    public void NoEstaEntregada(View view){
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        String url="http://dgpsanrafael.000webhostapp.com/entregaTarea.php?confirmaQuien=4?id=" + TareaActual.getIdTarea();   //1 confirma entrega alumno, 2 confirma entrega profe, 3 no entrega
        //url= http://url
        StringRequest stringRequest = new StringRequest (Request.Method.POST,url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Toast.makeText(TareaActivity.this,"Tarea confirmada como NO ENTREGADA", Toast.LENGTH_LONG).show();
                        TareaActual.setStatus(3);
                        TareaActual.setConfirmaProfesor(true);
                        actualizaConteoTareas(String.valueOf(TareaActual.getIdAlumno()),3);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(TareaActivity.this,error.getMessage(), Toast.LENGTH_LONG).show();
                    }
                }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                Map<String, String> params = new HashMap<String, String>();
                params.put("id", String.valueOf(TareaActual.getIdTarea()));
                params.put("confirmaQuien",String.valueOf(4));
                return params;
            }
        };
           requestQueue.add(stringRequest);
    }

    public void Feedback(View view){
        Intent FB = new Intent(this, FeedbackActivity.class);
        FB.putExtra("tarea", TareaActual);
        FB.putExtra("profe",ProfesorActual);
        startActivity(FB);
    }


    public void actualizaConteoTareas(String id, int que){

        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        String url="http://dgpsanrafael.000webhostapp.com/actualizaConteoTareas.php?id="+id +"&que="+que;   //
        //url= http://url
        StringRequest stringRequest = new StringRequest (Request.Method.POST,url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Toast.makeText(TareaActivity.this, response.toString(), Toast.LENGTH_SHORT).show();

                        Intent Volver = new Intent(TareaActivity.this, PeticionesActivity.class);
                        Volver.putExtra("profe",ProfesorActual);
                        Volver.putExtra("tarea",TareaActual);
                        startActivity(Volver);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(TareaActivity.this,error.getMessage(), Toast.LENGTH_LONG).show();
                    }
                }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                Map<String, String> params = new HashMap<String, String>();
                params.put("id", id);
                params.put("que", String.valueOf(que));
                return params;
            }
        };
        int socketTimeout = 30000;
        RetryPolicy retryPolicy = new DefaultRetryPolicy(socketTimeout, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
        stringRequest.setRetryPolicy(retryPolicy);
        requestQueue.add(stringRequest);
    }
}