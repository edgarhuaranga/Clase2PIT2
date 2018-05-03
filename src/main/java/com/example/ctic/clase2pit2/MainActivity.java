package com.example.ctic.clase2pit2;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {

    EditText editTextCodigo;
    Button botonConsultar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        editTextCodigo = findViewById(R.id.edittext_codigo);
        botonConsultar = findViewById(R.id.boton_consultar);
    }

    public void consultaORCE(View view) {
        String codigoAlumno = editTextCodigo.getText().toString();
        Intent intent = new Intent(this, MapsActivity.class);
        intent.putExtra("codigo", codigoAlumno);
        startActivity(intent);
    }
}
