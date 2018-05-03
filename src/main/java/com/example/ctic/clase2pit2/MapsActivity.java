package com.example.ctic.clase2pit2;

import android.support.v4.app.FragmentActivity;
import android.os.Bundle;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    String urlAlumno;
    ArrayList<String> codigos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        //codigoAlumno = getIntent().getStringExtra("codigo");

        codigos = Utils.getCodigosUNI();

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    public String obtenerValorEtiqueta(String respuesta,String etiqueta, String finEtiqueta){
        int indiceNombre = respuesta.indexOf(etiqueta);
        int indiceFin = respuesta.indexOf(finEtiqueta, indiceNombre+etiqueta.length());
        String nombre = respuesta.substring(indiceNombre+etiqueta.length(), indiceFin);
        return nombre;
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        for(String codigo : codigos){
            agregarAlumnoAlMapa(codigo);
        }

    }

    public void agregarAlumnoAlMapa(String codigouni){
        urlAlumno = "http://www.orce.uni.edu.pe/detaalu.php?id="+codigouni+"&op=detalu";

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        StringRequest request = new StringRequest(Request.Method.GET, urlAlumno,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        String codigo = obtenerValorEtiqueta(response, "Codigo UNI:</td><td>", "</td>");
                        String nombre = obtenerValorEtiqueta(response, "Nombres:</td><td>", "</td>");
                        String especialidad = obtenerValorEtiqueta(response, "Especialidad:</td><td>", "</td>");
                        String facultad = obtenerValorEtiqueta(response, "Facultad:</td><td>", "</td>");

                        LatLng ubicacion = Utils.obtenerLatLngFacultad(facultad);
                        mMap.addMarker(new MarkerOptions().position(ubicacion).title(nombre));
                        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(ubicacion, 16));

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });

        requestQueue.add(request);
    }
}
