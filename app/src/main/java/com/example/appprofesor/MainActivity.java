package com.example.appprofesor;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import info.androidhive.sqlite.helper.DatabaseHelper;

public class MainActivity extends AppCompatActivity {
    // Database Helper
    DatabaseHelper db;
    private TextView textPr;
    //Profesor profesorregistrado;
    String IdProfesor="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        View b = findViewById(R.id.botonAdmin);
        if(db.isAdmin(IdProfesor))
            b.setVisibility(View.VISIBLE);
        else
            b.setVisibility(View.INVISIBLE);

        textPr = (TextView)findViewById(R.id.textoSaludoProfe);
        IdProfesor = getIntent().getStringExtra("IdProfesor");

        if(IdProfesor == ""){
            textPr.setText("<- Inicia sesión pulsando el icono");
        }
        else{
            //if(alumnoregistrado == null)       alumnoregistrado =db.getAlumno(IdAlumno);
            textPr.setText("Hola, " + db.getNombreProfesor(IdProfesor));
        }

        setContentView(R.layout.activity_main);
    }

    public void AccedeTareas(View view){
        Intent PedirItem = new Intent(this, SolicitaActivity.class);
        PedirItem.putExtra("IdProfesor",IdProfesor);
        startActivity(PedirItem);
    }

    public void MiraPeticiones(View view){
        Intent VerPeticiones = new Intent(this, PeticionesActivity.class);
        VerPeticiones.putExtra("IdProfesor",IdProfesor);
        startActivity(VerPeticiones);
    }
    //Boton Login
    public void seleccionaUsuario(View view){
        //profesorregistrado = new Profesor();    //borramos los datos del alumno anterior
        IdProfesor = "";                      //tanto usuario como ID
        Intent LoginAct = new Intent(this, LoginActivity.class);
        startActivity(LoginAct);
    }

}