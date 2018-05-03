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

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    String urlAlumno;
    String codigoAlumno;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        codigoAlumno = getIntent().getStringExtra("codigo");
        urlAlumno = "http://www.orce.uni.edu.pe/detaalu.php?id="+codigoAlumno+"&op=detalu";

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

        // Add a marker in Sydney and move the camera
        final LatLng ciencias = new LatLng(-12.0185025, -77.0500628);
        final LatLng comedor = new LatLng(-12.0185581,-77.0497471);


        RequestQueue requestQueue = Volley.newRequestQueue(this);
        StringRequest request = new StringRequest(Request.Method.GET, urlAlumno,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        String codigo = obtenerValorEtiqueta(response, "Codigo UNI:</td><td>", "</td>");
                        String nombre = obtenerValorEtiqueta(response, "Nombres:</td><td>", "</td>");
                        String especialidad = obtenerValorEtiqueta(response, "Especialidad:</td><td>", "</td>");
                        String faculdad = obtenerValorEtiqueta(response, "Facultad:</td><td>", "</td>");

                        if(faculdad.equalsIgnoreCase("CIENCIAS")){
                            mMap.addMarker(new MarkerOptions().position(ciencias).title(nombre));
                            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(ciencias, 16));
                        }
                        else{
                            mMap.addMarker(new MarkerOptions().position(comedor).title(nombre));
                            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(comedor, 16));
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });

        requestQueue.add(request);
    }
}
