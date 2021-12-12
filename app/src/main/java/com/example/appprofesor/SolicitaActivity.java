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

public class SolicitaActivity extends AppCompatActivity{
    protected RecyclerView recyclerViewMaterial;
    private RecViewAdaptMaterial adaptadorMaterial;
    List<Material> listMaterial;
    TextView cantidad;
    TextView hora;
    String idMat,IdProfesor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_solicita);

        IdProfesor = getIntent().getStringExtra("IdProfesor");
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
                idMat =  String.valueOf(listMaterial.get(recyclerViewMaterial.getChildAdapterPosition(v)).getID());
            }
        });
        adaptadorMaterial.notifyDataSetChanged();
        obtenerListaMaterial();
    }
    public void Volver(View view){
        Intent Volver = new Intent(this, MainActivity.class);
        startActivity(Volver);
    }
    public void CrearRequest(View view){

        StringRequest request = new StringRequest(Request.Method.POST, "http://192.168.1.14/android_mysql/crearTarea.php",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        if(response.equalsIgnoreCase("Datos insertados")){
                            //limpiar();
                            Toast.makeText(SolicitaActivity.this, "Datos insertados", Toast.LENGTH_SHORT).show();
                            //progressDialog.dismiss();
                        }
                        else{
                            Toast.makeText(SolicitaActivity.this, response, Toast.LENGTH_SHORT).show();
                            //progressDialog.dismiss();
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(SolicitaActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
                //progressDialog.dismiss();
            }
        }
        ) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                Map<String, String> params = new HashMap<String, String>();

                //params.put("nombre", nombre);
                return params;
            }
        };
    }
    public void obtenerListaMaterial(){
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        String url="http://192.168.1.14/android_mysql/selectMaterial.php";
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
                                        Mat.getInt("idfoto")
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