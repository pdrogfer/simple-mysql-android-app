package com.pgfmusic.simplemysqlapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.Toast;

import com.loopj.android.http.*;

import org.json.JSONObject;

import java.io.UnsupportedEncodingException;

import cz.msebera.android.httpclient.Header;

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

        if (!modo.isChecked()) {
            enviarGet(nombre.getText().toString(), apellido.getText().toString(),
                    Integer.parseInt(edad.getText().toString()), modo.isChecked());
        } else {
            enviarPost(nombre.getText().toString(), apellido.getText().toString(),
                    Integer.parseInt(edad.getText().toString()), modo.isChecked());
        }


    }

    private void enviarGet(String nombre, String apellido, int edad, boolean modo) {

        AsyncHttpClient clientSendGet = new AsyncHttpClient();
        String mode = modo ? "GET" : "POST";
        RequestParams requestParams = new RequestParams();
        requestParams.put("nombre", nombre);
        requestParams.put("apellido", apellido);
        requestParams.put("edad", edad);
        requestParams.put("mode", mode);

        clientSendGet.get(Utilities.URL_ADD_DATA, requestParams, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                Log.i("app", "User created with GET");
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                Log.e("app", "User could not be created");
            }
        });
    }

    private void enviarPost(String nombre, String apellido, int edad, boolean modo) {

        AsyncHttpClient clientSendPost = new AsyncHttpClient();
        RequestParams requestParams = new RequestParams();
        String mode = !modo ? "GET" : "POST";
        requestParams.put("nombre", nombre);
        requestParams.put("apellido", apellido);
        requestParams.put("edad", edad);
        requestParams.put("modo", mode);

        clientSendPost.post(Utilities.URL_ADD_DATA, requestParams, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                Log.i("app", "User created with POST");
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                Log.e("app", "User could not be created");
            }
        });
    }

    public void ShowUsers(View view) {
        startActivity(new Intent(this, ListActivity.class));
    }
}
