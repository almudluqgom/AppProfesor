package com.example.appprofesor;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
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

public class FeedbackActivity extends AppCompatActivity {
    String idTarea, fb;
    Tareas TareaActual; Profesor ProfesorActual;
    EditText Feedback;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feedback);
        Feedback = (EditText) findViewById(R.id.FeedbackText);

        if(getIntent().getExtras() != null) {
            ProfesorActual = (Profesor) getIntent().getSerializableExtra("profe");
            TareaActual = (Tareas) getIntent().getSerializableExtra("tarea");
            idTarea = String.valueOf(TareaActual.getIdTarea());
        }
    }

    public void Volver(View view){
        Intent Volver = new Intent(this, TareaActivity.class);
        Volver.putExtra("tarea", TareaActual);
        Volver.putExtra("profe",ProfesorActual);
        startActivity(Volver);
    }

    public void MandarFeedback(View view){
        fb=Feedback.getText().toString();

        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        String url="http://dgpsanrafael.000webhostapp.com/mandaFeedback.php?id="+ idTarea +"&fb=" + fb;   //
        //url= http://url
        StringRequest stringRequest = new StringRequest (Request.Method.POST,url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Toast.makeText(FeedbackActivity.this,"Feedback registrado", Toast.LENGTH_LONG).show();
                        Intent Volver = new Intent(FeedbackActivity.this, TareaActivity.class);
                        Volver.putExtra("tarea", TareaActual);
                        Volver.putExtra("profe",ProfesorActual);
                        startActivity(Volver);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(FeedbackActivity.this,error.getMessage(), Toast.LENGTH_LONG).show();
                    }
                }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                Map<String, String> params = new HashMap<String, String>();
                params.put("id", idTarea);
                params.put("fb", fb);
                return params;
            }
            };
        int socketTimeout = 30000;
        RetryPolicy retryPolicy = new DefaultRetryPolicy(socketTimeout, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
        stringRequest.setRetryPolicy(retryPolicy);
        requestQueue.add(stringRequest);
    }
}