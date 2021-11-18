package com.example.appprofesor;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import java.util.ArrayList;

import info.androidhive.sqlite.helper.DatabaseHelper;

public class PeticionesActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    String IdProfesor= "";
    DatabaseHelper admin = new DatabaseHelper(this, "administracion", null, 1);
    ArrayList<String> tareas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_peticiones);

        Spinner spin = (Spinner) findViewById(R.id.SpinnerTareas);
        spin.setOnItemSelectedListener(this);
        IdProfesor = getIntent().getStringExtra("IdProfesor");
        tareas = admin.getTareasProfe(IdProfesor);

        //Creating the ArrayAdapter instance having the country list
        ArrayAdapter aa = new ArrayAdapter(this,android.R.layout.simple_spinner_item,tareas);
        aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        //Setting the ArrayAdapter data on the Spinner
        spin.setAdapter(aa);
    }

    public void Volver(View view){
        Intent Volver = new Intent(this, MainActivity.class);
        startActivity(Volver);
    }


    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        Intent IrATarea = new Intent(this, TareaActivity.class);
        String IdTarea = tareas.get(position);
        IrATarea.putExtra("IdTarea",IdTarea);
        startActivity(IrATarea);
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}