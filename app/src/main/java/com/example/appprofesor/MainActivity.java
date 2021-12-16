package com.example.appprofesor;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.wifi.WifiManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Parcelable;
import android.os.StrictMode;
import android.text.TextUtils;
import android.text.format.Formatter;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

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

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.Callable;

import info.androidhive.sqlite.model.Profesor;

public class MainActivity extends AppCompatActivity {

    private TextView textPr;
    String IdProfesor="";
    Profesor ProfesorActual;
    Button botonE, botonP;
    ImageButton botonA;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ImageButton login = (ImageButton)findViewById(R.id.botonLogin);
        login.setBackgroundResource(R.drawable.pato);
        textPr = (TextView)findViewById(R.id.textoSaludoProfe);
        botonE = (Button)findViewById(R.id.BotonNuevoEncargo);
        botonP = (Button)findViewById(R.id.BotonPeticiones);
        botonA = (ImageButton) findViewById(R.id.botonAdmin);

        if(getIntent().getExtras() != null) {
            ProfesorActual = (Profesor) getIntent().getSerializableExtra("profe");
            login.setBackgroundResource(ProfesorActual.getIDFoto());
            IdProfesor =ProfesorActual.getIdProfesor();
            if (ProfesorActual.isAdmin()){
                //botonA.setVisibility(View.VISIBLE);   //falta de tiempo
            }
        }

        if(!TextUtils.isEmpty(IdProfesor)){
            textPr.setText("Hola, " +ProfesorActual.getNombreApell());
            botonE.setEnabled(true);
            botonP.setEnabled(true);
        }
        else{
            textPr.setText("Inicia sesión");
        }

    }
    //Boton nuevo encargo
    public void AccedeTareas(View view){
        if(botonE.isEnabled()) {
            Intent PedirItem = new Intent(this, SolicitaActivity.class);
            PedirItem.putExtra("IdProfesor",IdProfesor);
            PedirItem.putExtra("profe",ProfesorActual);
            startActivity(PedirItem);
        }
    }
    //Boton Peticiones
    public void MiraPeticiones(View view){
        if(botonP.isEnabled()) {
            Intent VerPeticiones = new Intent(this, PeticionesActivity.class);
            VerPeticiones.putExtra("profe", ProfesorActual);
            startActivity(VerPeticiones);
        }
    }
    //Boton Login
    public void seleccionaUsuario(View view){
        IdProfesor="";
        ProfesorActual = new Profesor();
        Intent LoginAct = new Intent(this, LoginActivity.class);
        startActivity(LoginAct);
    }
}