package com.example.appprofesor;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import info.androidhive.sqlite.model.Profesor;
import info.androidhive.sqlite.model.Tareas;

public class TareaActivity extends AppCompatActivity {
    Tareas TareaActual;
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
        HoraEntrega = (TextView) findViewById(R.id.HoraEntrega);
        NombreObj = (TextView) findViewById(R.id.nombreObjeto);
        cantObj = (TextView) findViewById(R.id.CantidadObjeto);
        status = (TextView) findViewById(R.id.statusTarea);
        Feedback = (TextView) findViewById(R.id.Feedback);
        FotoFeedback = (ImageView) findViewById(R.id.pictogramaFeedback);
        FotoObjeto =  (ImageView) findViewById(R.id.imagenObjeto);

        if(getIntent().getExtras() != null) {
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
        Intent Volver = new Intent(this, MainActivity.class);
        startActivity(Volver);
    }
    public void EstaEntregada(View view){
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        String url="http://192.168.1.14/android_mysql/entregaTarea.php?confirmaQuien=2&id=" + TareaActual.getIdTarea();   //1 confirma entrega alumno, 2 confirma entrega profe, 3 no entrega
        //url= http://url
        StringRequest stringRequest = new StringRequest (Request.Method.GET,url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Toast.makeText(TareaActivity.this,"Tarea confirmada como ENTREGADA", Toast.LENGTH_LONG).show();
                        TareaActual.setStatus(2);
                        TareaActual.setConfirmaProfesor(true);
                        finish();
                        startActivity(getIntent());
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(TareaActivity.this,error.getMessage(), Toast.LENGTH_LONG).show();
                    }
                });
        int socketTimeout = 30000;
        RetryPolicy retryPolicy = new DefaultRetryPolicy(socketTimeout, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
        stringRequest.setRetryPolicy(retryPolicy);
        requestQueue.add(stringRequest);
    }
    public void NoEstaEntregada(View view){
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        String url="http://192.168.1.14/android_mysql/entregaTarea.php?confirmaQuien=4?id=" + TareaActual.getIdTarea();   //1 confirma entrega alumno, 2 confirma entrega profe, 3 no entrega
        //url= http://url
        StringRequest stringRequest = new StringRequest (Request.Method.GET,url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Toast.makeText(TareaActivity.this,"Tarea confirmada como NO ENTREGADA", Toast.LENGTH_LONG).show();
                        TareaActual.setStatus(3);
                        TareaActual.setConfirmaProfesor(true);
                        finish();
                        startActivity(getIntent());
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(TareaActivity.this,error.getMessage(), Toast.LENGTH_LONG).show();
                    }
                });
        int socketTimeout = 30000;
        RetryPolicy retryPolicy = new DefaultRetryPolicy(socketTimeout, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
        stringRequest.setRetryPolicy(retryPolicy);
        requestQueue.add(stringRequest);
    }
    public void Feedback(View view){
        Intent FB = new Intent(this, FeedbackActivity.class);
        FB.putExtra("id", String.valueOf(TareaActual.getIdTarea()));
        startActivity(FB);
    }
}