package com.example.ctic.clase2pit2;

import android.graphics.drawable.Drawable;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
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
                        final String codigo = obtenerValorEtiqueta(response, "Codigo UNI:</td><td>", "</td>");
                        String nombre = obtenerValorEtiqueta(response, "Nombres:</td><td>", "</td>");
                        String especialidad = obtenerValorEtiqueta(response, "Especialidad:</td><td>", "</td>");
                        String facultad = obtenerValorEtiqueta(response, "Facultad:</td><td>", "</td>");

                        LatLng ubicacion = Utils.obtenerLatLngFacultad(facultad);
                        mMap.addMarker(new MarkerOptions().position(ubicacion).title(codigo));
                        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(ubicacion, 16));
                        mMap.setInfoWindowAdapter(new GoogleMap.InfoWindowAdapter() {
                            @Override
                            public View getInfoWindow(Marker marker) {
                                View layout = getLayoutInflater().inflate(R.layout.info_uni, null);
                                ImageView imageViewFoto = layout.findViewById(R.id.imageview_foto);
                                TextView textViewCodigo = layout.findViewById(R.id.textview_codigo_alumno);
                                textViewCodigo.setText(marker.getTitle());
                                String urlFoto = "http://www.orce.uni.edu.pe/fotosuni/0060"+marker.getTitle()+".jpg";
                                Log.d("glidex", urlFoto);
                                Glide.with(layout)
                                        .load(urlFoto)
                                        .listener(new RequestListener<Drawable>() {
                                            @Override
                                            public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                                                Log.d("glidex", "La descarga fallo "+e.getMessage());
                                                return false;
                                            }

                                            @Override
                                            public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                                                Log.d("glidex", "La descarga esta lista ");
                                                return false;
                                            }
                                        })
                                        .into(imageViewFoto);
                                return layout;
                            }

                            @Override
                            public View getInfoContents(Marker marker) {
                                return null;
                            }
                        });
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });

        requestQueue.add(request);
    }
}
