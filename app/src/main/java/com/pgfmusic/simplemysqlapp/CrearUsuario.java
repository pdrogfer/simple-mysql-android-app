package com.pgfmusic.simplemysqlapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.Toast;

import com.loopj.android.http.*;

public class CrearUsuario extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crear_usuario);
    }

    public void SendUser(View view) {

        EditText nombre = (EditText) findViewById(R.id.et_name);
        EditText apellido = (EditText) findViewById(R.id.et_surname);
        EditText edad = (EditText) findViewById(R.id.et_age);
        Switch modo = (Switch) findViewById(R.id.switch_getpost);
        final String result;

        if (modo.isChecked()) {
            result = enviarGet(nombre.getText().toString(), apellido.getText().toString(),
                    Integer.parseInt(edad.getText().toString()));
        } else {
            result = enviarPost(nombre.getText().toString(), apellido.getText().toString(),
                    Integer.parseInt(edad.getText().toString()));
        }

        Toast.makeText(getApplicationContext(), result, Toast.LENGTH_SHORT).show();
    }

    private String enviarGet(String nombre, String apellido, int edad) {
        return null;
    }

    private String enviarPost(String nombre, String apellido, int edad) {
        return null;
    }

    public void ShowUsers(View view) {
        startActivity(new Intent(this, ListActivity.class));
    }
}
