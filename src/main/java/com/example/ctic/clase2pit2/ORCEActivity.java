package com.example.ctic.clase2pit2;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

public class ORCEActivity extends AppCompatActivity {

    TextView textViewCodigo;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_orce);

        textViewCodigo = findViewById(R.id.textview_codigo);
        String codigoAlumno = getIntent().getStringExtra("codigo");
        textViewCodigo.setText(codigoAlumno);

        String urlAlumno = "http://www.orce.uni.edu.pe/detaalu.php?id="+codigoAlumno+"&op=detalu";

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        StringRequest request = new StringRequest(Request.Method.GET, urlAlumno,
                new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                /*String etiqueta =  "Especialidad:</td><td>";
                int indiceNombre = response.indexOf(etiqueta);
                int indiceFin = response.indexOf("</td>", indiceNombre+etiqueta.length());
                String nombre = response.substring(indiceNombre+etiqueta.length(), indiceFin);*/

                String codigo = obtenerValorEtiqueta(response, "Codigo UNI:</td><td>", "</td>");
                String nombre = obtenerValorEtiqueta(response, "Nombres:</td><td>", "</td>");
                String especialidad = obtenerValorEtiqueta(response, "Especialidad:</td><td>", "</td>");
                String faculdad = obtenerValorEtiqueta(response, "Facultad:</td><td>", "</td>");

                textViewCodigo.setText(codigo+"\n");
                textViewCodigo.append(nombre+"\n");
                textViewCodigo.append(especialidad+"\n");
                textViewCodigo.append(faculdad+"\n");
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });

        requestQueue.add(request);
    }

    public String obtenerValorEtiqueta(String respuesta,String etiqueta, String finEtiqueta){
        int indiceNombre = respuesta.indexOf(etiqueta);
        int indiceFin = respuesta.indexOf(finEtiqueta, indiceNombre+etiqueta.length());
        String nombre = respuesta.substring(indiceNombre+etiqueta.length(), indiceFin);
        return nombre;
    }

}
