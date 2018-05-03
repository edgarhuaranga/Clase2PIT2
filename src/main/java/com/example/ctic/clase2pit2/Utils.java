package com.example.ctic.clase2pit2;

import com.google.android.gms.maps.model.LatLng;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class Utils {

    static ArrayList<String> getCodigosUNI(){
        ArrayList<String> codigos = new ArrayList<>();
        codigos.add("20101272K");
        codigos.add("20152734A");
        codigos.add("20187005F");
        codigos.add("20152003G");
        codigos.add("20142003D");
        codigos.add("20121160C");
        return codigos;
    }

    static LatLng obtenerLatLngFacultad(String nombreFacultad){
        LatLng ubicacion;
        if(nombreFacultad.equalsIgnoreCase("CIENCIAS")){
            ubicacion = new LatLng(-12.0174211+Math.random()/1000.0, -77.0507562+Math.random()/1000.0);
        }
        else if(nombreFacultad.equalsIgnoreCase("INGENIERÍA MECÁNICA")){
            ubicacion = new LatLng(-12.0243022+Math.random()/1000.0, -77.0473595+Math.random()/1000.0);
        }

        else if(nombreFacultad.equalsIgnoreCase("INGENIERÍA ELÉCTRICA Y ELECTRÓNICA")){
            ubicacion = new LatLng(-12.05644139999+Math.random()/1000.0, -77.0813893999+Math.random()/1000.0);
        }
        else
            ubicacion = new LatLng(-12.0185581+Math.random()/1000.0,-77.0497471+Math.random()/1000.0);

        return ubicacion;
    }
}
