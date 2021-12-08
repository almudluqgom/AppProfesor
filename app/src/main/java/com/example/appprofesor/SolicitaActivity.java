package com.example.appprofesor;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;

import info.androidhive.sqlite.helper.DatabaseHelper;

public class SolicitaActivity extends AppCompatActivity{
/*

    ArrayList<String> items;
    private EditText et_cantidad, et_hora;
    DatabaseHelper admin = new DatabaseHelper(this, "administracion", null, 1);
    String IdProfesor="", itemguardado;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_solicita);

        IdProfesor = getIntent().getStringExtra("IdProfesor");
        items = admin.getObjetos();
        et_cantidad = (EditText)findViewById(R.id.txt_cantidad);
        et_hora = (EditText)findViewById(R.id.txt_hora);
        Spinner spin = (Spinner) findViewById(R.id.spinner);

        spin.setOnItemSelectedListener(this);

        //Creating the ArrayAdapter instance having the country list
        ArrayAdapter aa = new ArrayAdapter(this,android.R.layout.simple_spinner_item,items);
        aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        //Setting the ArrayAdapter data on the Spinner
        spin.setAdapter(aa);
    }


    //Méotdo para dar de alta los productos
    public void Añadir(View view){
        SQLiteDatabase BaseDeDatos = admin.getWritableDatabase();

        Integer cantidad = Integer.parseInt(et_cantidad.getText().toString());
        String Hora = et_hora.getText().toString();

        if((cantidad != 0 ) && ( !Hora.isEmpty() )) {
            if (admin.HaySuficiente(itemguardado, cantidad)){
                ContentValues registro = new ContentValues();
                registro.put("cantidad", cantidad);
                registro.put("Hora", Hora);

                BaseDeDatos.insert("Tareas", null, registro);

                BaseDeDatos.close();
                et_cantidad.setText("");
                et_hora.setText("");
                Toast.makeText(this, "Se ha realizado la petición con éxito", Toast.LENGTH_SHORT).show();

                //ahora vamos a visualizar la lista de la compra
                Intent CarritoCompra = new Intent(this, CarritoActivity.class);
                CarritoCompra.putExtra("IdProfesor",IdProfesor);
                startActivity(CarritoCompra);
            }
            else{
                Toast.makeText(this, "No hay suficientes " + itemguardado + ", selecciona una cantidad menor", Toast.LENGTH_SHORT).show();
            }
        } else{
            Toast.makeText(this, "Debes llenar todos los campos", Toast.LENGTH_SHORT).show();
        }

    }

    @Override
    public void onItemSelected(AdapterView<?> arg0, View view, int position, long id) {
        Toast.makeText(getApplicationContext(), items.get(position), Toast.LENGTH_LONG).show();
        itemguardado = items.get(position);
    }

    @Override
    public void onNothingSelected(AdapterView<?> arg0) {

    }

    public void Volver(View view){
        Intent Volver = new Intent(this, MainActivity.class);
        Volver.putExtra("IdProfesor",IdProfesor);
        startActivity(Volver);
    }
*/

}