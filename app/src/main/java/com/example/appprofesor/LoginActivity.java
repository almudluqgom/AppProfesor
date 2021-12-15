package com.example.appprofesor;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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
import java.util.List;

import info.androidhive.sqlite.helper.RecViewAdaptProfe;
import info.androidhive.sqlite.model.Profesor;

public class LoginActivity extends AppCompatActivity {
    protected RecyclerView recyclerViewProfe;
    private RecViewAdaptProfe adaptadorProfe;
    List<Profesor> listProfesores;
    String url_ip;
    TextView text,ipadd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        text = (TextView) findViewById(R.id.ipaddr);

        listProfesores = new ArrayList<Profesor>();
        recyclerViewProfe= (RecyclerView) findViewById(R.id.recyclerMaterial);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerViewProfe.setLayoutManager(layoutManager);

        adaptadorProfe = new RecViewAdaptProfe(listProfesores);
        adaptadorProfe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(v.getContext(), "Seleccion: "+
                        listProfesores.get(recyclerViewProfe.getChildAdapterPosition(v)).getNombreApell(), Toast.LENGTH_LONG).show();

                Intent Volver = new Intent(getApplicationContext(), MainActivity.class);
                Profesor profesor = new Profesor(listProfesores.get(recyclerViewProfe.getChildAdapterPosition(v)));
                Volver.putExtra("profe",profesor);
                startActivity(Volver);
            }
        });
        adaptadorProfe.notifyDataSetChanged();
        obtenerListaProfes();
    }

    public void obtenerListaProfes(){
            RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
            String url="http://dgpsanrafael.000webhostapp.com/selectProfesores.php";
            //url= http://url
            StringRequest stringRequest = new StringRequest (Request.Method.POST,url,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            try {

                                JSONArray itemArray = new JSONArray(response);
                                for (int i = 0; i < itemArray.length(); i++) {
                                    JSONObject Profe = itemArray.getJSONObject(i);
                                    boolean isadmin = false;
                                    if(Profe.getInt("admin") == 1)
                                    isadmin = true;
                                    listProfesores.add(new Profesor(
                                            Profe.getString("id"),
                                            Profe.getString("nombre"),
                                            Profe.getInt("idfoto"),
                                            isadmin
                                    ));
                                }
                                recyclerViewProfe.setAdapter(adaptadorProfe);
                                adaptadorProfe.notifyDataSetChanged();
                            }
                            catch (JSONException e) {
                                text.setText(e.getMessage());
                                Toast.makeText(LoginActivity.this, e.getMessage(), Toast.LENGTH_LONG).show();
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
