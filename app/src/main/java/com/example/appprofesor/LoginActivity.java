package com.example.appprofesor;

import android.content.Intent;
import android.net.wifi.WifiManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.format.Formatter;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.transform.Result;

import info.androidhive.sqlite.model.Profesor;

public class LoginActivity extends AppCompatActivity{
    protected RecyclerView recyclerViewProfe;
    private RecViewAdaptProfe adaptadorProfe;
    List<Profesor> listProfesores;
    List<String> listanombres;
    RequestQueue requestQueue;
    Profesor profesor;
    String IdProfesor;
    TextView    ipadd,text;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        //---------CODIGO PARA CONSEGUIR LA IP DEL DISPOSITIVO-----------------
        //ipadd = (TextView) findViewById(R.id.ipaddr);
        //WifiManager wifiManager = (WifiManager) getApplicationContext().getSystemService(WIFI_SERVICE);
        //ipadd.setText(Formatter.formatIpAddress(wifiManager.getConnectionInfo().getIpAddress()));
        //String ip = Formatter.formatIpAddress(wifiManager.getConnectionInfo().getIpAddress());
        listProfesores = new ArrayList<Profesor>();
        obtenerListaProfes(listProfesores);

        text=findViewById(R.id.result);
        recyclerViewProfe= (RecyclerView) findViewById(R.id.recyclerProfe);
        recyclerViewProfe.setHasFixedSize(true);
        recyclerViewProfe.setLayoutManager(new LinearLayoutManager(this));
//        adaptadorProfe = new RecyclerViewAdaptador(listProfesores);
//        recyclerViewProfe.setAdapter(adaptadorProfe);
        //Toast.makeText(LoginActivity.this, listProfesores.size(), Toast.LENGTH_LONG).show();

    }

    public void Volver(View view){
        Intent Volver = new Intent(this, MainActivity.class);
        startActivity(Volver);
    }
    public void obtenerListaProfes(List Profesores){

            String url="http://192.168.1.14:80/android_mysql/select.php";
            StringRequest stringRequest = new StringRequest (Request.Method.GET,url,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            try {

                                JSONArray itemArray = new JSONArray(response);
                                for (int i = 0; i < itemArray.length(); i++) {
                                    JSONObject Profe = itemArray.getJSONObject(i);
                                    listProfesores.add(new Profesor(
                                            Profe.getString("id"),
                                            Profe.getString("nombre"),
                                            Profe.getInt("idfoto")
                                    ));
                                }
                                RecViewAdaptProfe adapter = new RecViewAdaptProfe(listProfesores);
                                recyclerViewProfe.setAdapter(adapter);
                                text.setText(response);
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
            Volley.newRequestQueue(LoginActivity.this).add(stringRequest);

    }
}
