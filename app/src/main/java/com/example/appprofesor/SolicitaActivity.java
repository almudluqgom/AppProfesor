package com.example.appprofesor;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import info.androidhive.sqlite.helper.RecViewAdaptMaterial;
import info.androidhive.sqlite.model.Material;
import info.androidhive.sqlite.model.Profesor;

public class SolicitaActivity extends AppCompatActivity{
    protected RecyclerView recyclerViewMaterial;
    private RecViewAdaptMaterial adaptadorMaterial;
    List<Material> listMaterial;
    Material MaterialActual; Profesor ProfesorActual;
    TextView cantidad;
    TextView hora;
    String IdProfesor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_solicita);

        if(getIntent().getExtras() != null) {
            ProfesorActual = (Profesor) getIntent().getSerializableExtra("profe");
            IdProfesor =ProfesorActual.getIdProfesor();
        }
        cantidad = (TextView) findViewById(R.id.Cantidad);
        hora = (TextView) findViewById(R.id.Hora);

        listMaterial = new ArrayList<Material>();
        recyclerViewMaterial = (RecyclerView) findViewById(R.id.recyclerMaterial);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerViewMaterial.setLayoutManager(layoutManager);

        adaptadorMaterial = new RecViewAdaptMaterial(listMaterial);
        adaptadorMaterial.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(v.getContext(), "Seleccion: "+
                listMaterial.get(recyclerViewMaterial.getChildAdapterPosition(v)).getNombre(), Toast.LENGTH_LONG).show();
                MaterialActual = new Material (listMaterial.get(recyclerViewMaterial.getChildAdapterPosition(v)));
            }
        });
        adaptadorMaterial.notifyDataSetChanged();
        obtenerListaMaterial();
    }
    public void Volver(View view){
        Intent Volver = new Intent(this, MainActivity.class);
        Volver.putExtra("profe",ProfesorActual);
        startActivity(Volver);
    }

    public void CrearRequest(View view){

        String h =hora.getText().toString();
        String c = cantidad.getText().toString();

        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        String url="http://dgpsanrafael.000webhostapp.com/crearTarea.php";
        StringRequest request = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        int restante = MaterialActual.getCantidad() -  Integer.valueOf(c);
                        String r = String.valueOf(restante);
                        actualizaAlmacen(String.valueOf(MaterialActual.getID()),r);

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(SolicitaActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }
        ) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                    params.put("nombre", MaterialActual.getNombre());
                    params.put("idf", String.valueOf(MaterialActual.getIdFoto()));
                    params.put("idp",IdProfesor);
                    params.put("ido",String.valueOf(MaterialActual.getID()));
                    params.put("horae",h);
                    params.put("c",c);
                return params;
            }
        };
        int socketTimeout = 30000;
        RetryPolicy retryPolicy = new DefaultRetryPolicy(socketTimeout, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
        request.setRetryPolicy(retryPolicy);
        requestQueue.add(request);
    }


    public void actualizaAlmacen(String idObj,String cantidad){

        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        String url="http://dgpsanrafael.000webhostapp.com/actualizaCantidad.php?id="+idObj +"&cantidad="+cantidad;   //
        //url= http://url
        StringRequest stringRequest = new StringRequest (Request.Method.POST,url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Toast.makeText(SolicitaActivity.this, response.toString(), Toast.LENGTH_SHORT).show();
                        Intent Volver = new Intent(SolicitaActivity.this, MainActivity.class);
                        Volver.putExtra("profe",ProfesorActual);
                        startActivity(Volver);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(SolicitaActivity.this,error.getMessage(), Toast.LENGTH_LONG).show();
                    }
                }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                Map<String, String> params = new HashMap<String, String>();
                params.put("id", idObj);
                params.put("cantidad", cantidad);
                return params;
            }
        };
        int socketTimeout = 30000;
        RetryPolicy retryPolicy = new DefaultRetryPolicy(socketTimeout, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
        stringRequest.setRetryPolicy(retryPolicy);
        requestQueue.add(stringRequest);
    }

    public void obtenerListaMaterial(){
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        String url="http://dgpsanrafael.000webhostapp.com/selectMaterial.php";
        //url= http://url
        StringRequest stringRequest = new StringRequest (Request.Method.POST,url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {

                            JSONArray itemArray = new JSONArray(response);
                            for (int i = 0; i < itemArray.length(); i++) {
                                JSONObject Mat = itemArray.getJSONObject(i);
                                listMaterial.add(new Material(
                                        Mat.getInt("id"),
                                        Mat.getInt("cantidad"),
                                        Mat.getString("descripcion"),
                                        Mat.getString("nombre"),
                                        Mat.getString("idfoto")
                                ));
                            }
                            recyclerViewMaterial.setAdapter(adaptadorMaterial);
                            adaptadorMaterial.notifyDataSetChanged();
                        }
                        catch (JSONException e) {
                            Toast.makeText(SolicitaActivity.this, e.getMessage(), Toast.LENGTH_LONG).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                });
        int socketTimeout = 30000;
        RetryPolicy retryPolicy = new DefaultRetryPolicy(socketTimeout, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
        stringRequest.setRetryPolicy(retryPolicy);
        requestQueue.add(stringRequest);

    }
}