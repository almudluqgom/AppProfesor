package com.example.appprofesor;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
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

import info.androidhive.sqlite.helper.RecViewAdaptTarea;
import info.androidhive.sqlite.model.Profesor;
import info.androidhive.sqlite.model.Tareas;

public class PeticionesActivity extends AppCompatActivity{
    protected RecyclerView recyclerViewTareas;
    private RecViewAdaptTarea adaptadorTarea;
    List<Tareas> listTareas;
    Profesor ProfesorActual;
    String IdProfesor="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_peticiones);

        if(getIntent().getExtras() != null) {
            ProfesorActual = (Profesor) getIntent().getSerializableExtra("profe");
            IdProfesor =ProfesorActual.getIdProfesor();
        }
        listTareas = new ArrayList<Tareas>();
        recyclerViewTareas= (RecyclerView) findViewById(R.id.recyclerMaterial);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerViewTareas.setLayoutManager(layoutManager);

        adaptadorTarea = new RecViewAdaptTarea(listTareas);
        adaptadorTarea.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent IrATarea = new Intent(getApplicationContext(), TareaActivity.class);
                Tareas t = new Tareas(listTareas.get(recyclerViewTareas.getChildAdapterPosition(v)));
                IrATarea.putExtra("tarea",t);
                IrATarea.putExtra("profe",ProfesorActual);
                startActivity(IrATarea);
            }
        });
        adaptadorTarea.notifyDataSetChanged();
        obtenerListaTareas();
    }

    public void Volver(View view){
        Intent Volver = new Intent(this, MainActivity.class);
        Volver.putExtra("profe",ProfesorActual);
        startActivity(Volver);
    }

    public void obtenerListaTareas(){
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        String url="http://dgpsanrafael.000webhostapp.com/selectTarea.php?id="+ IdProfesor;
        StringRequest stringRequest = new StringRequest (Request.Method.POST,url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONArray itemArray = new JSONArray(response);
                            for (int i = 0; i < itemArray.length(); i++) {
                                JSONObject T = itemArray.getJSONObject(i);
                                boolean al=false,pr=false;
                                if( T.getInt("ConfirmacionAlumno")==1) al = true;
                                if( T.getInt("ConfirmacionProfesor")==1) pr=true;
                                listTareas.add(new Tareas(
                                T.getInt("id"),       //int id,
                                T.getString("nombre"), //String NombreObjeto
                                T.getString("idFoto"),       //int idFoto,
                                T.getInt("idProfesor"),//int idProfe,
                                T.getInt("idAlumno"),//int idAlumno,
                                T.getInt("idObjeto"),//int idObjeto,
                                T.getString("HoraEntrega"),//String horaEntrega,
                                T.getString("Comentario"),//String comentario,
                                T.getInt("Cantidad"),//int cantidadObjeto,
                                al,//boolean confirmaAlumno,
                                pr,//boolean confirmaProfesor,
                                T.getInt("EstadoTarea"),//int status
                                        T.getInt("idFeedback")
                                ));
                            }
                            recyclerViewTareas.setAdapter(adaptadorTarea);
                            adaptadorTarea.notifyDataSetChanged();
                        }
                        catch (JSONException e) {
                            Toast.makeText(PeticionesActivity.this, e.getMessage(), Toast.LENGTH_LONG).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(PeticionesActivity.this, error.getMessage(), Toast.LENGTH_LONG).show();
                    }
                }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                Map<String,String> params = new HashMap<String,String>();
                params.put("id",IdProfesor);
                return params;
            }
        };
          requestQueue.add(stringRequest);
    }
}