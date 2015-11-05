package com.pgfmusic.simplemysqlapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;

public class ListActivity extends AppCompatActivity implements View.OnClickListener{

    ListView listView;
    Button btn_add_user;
    Button btn_get_data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);
        listView = (ListView) findViewById(R.id.listView);
        btn_add_user = (Button) findViewById(R.id.btn_new_user);
        btn_get_data = (Button) findViewById(R.id.btn_get_data);

        btn_get_data.setOnClickListener(this);
        btn_add_user.setOnClickListener(this);

        ObtainData();
    }

    public void ObtainData() {
        AsyncHttpClient clientObtainData = new AsyncHttpClient();

        // filtering by age, for instance
        // RequestParams parameters = new RequestParams();
        // parameters.put("edad", 65);

        clientObtainData.post(Utilities.URL_GET_DATA, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                if (statusCode == 200) { // I think success means always code 200
                    String res = null;
                    try {
                        res = new String(responseBody, "UTF-8");
                    } catch (UnsupportedEncodingException e) {
                        e.printStackTrace();
                    }
                    PopulateListView(ParserJSON(res));
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                Toast.makeText(getApplicationContext(), "Error getting data from server", Toast.LENGTH_SHORT)
                        .show();
                Log.e("app", "ERROR: " + String.valueOf(error));
            }
        });
    }

    public void PopulateListView(ArrayList<String> itemsList) {
        ArrayAdapter arrayAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, itemsList);
        listView.setAdapter(arrayAdapter);
    }

    public ArrayList<String> ParserJSON(String response) {
        ArrayList<String> itemsList = new ArrayList<>();
        try {
            JSONArray jsonArray = new JSONArray(response);
            String text;
            for (int i = 0; i < jsonArray.length(); i++) {
                text = jsonArray.getJSONObject(i).getString("nombre") + " " +
                        jsonArray.getJSONObject(i).getString("apellido") + " " +
                        jsonArray.getJSONObject(i).getString("edad") + " " +
                        jsonArray.getJSONObject(i).getString("modo");
                itemsList.add(text);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return itemsList;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_get_data:
                ObtainData();
                break;
            case R.id.btn_new_user:
                startActivity(new Intent(this, CrearUsuario.class));
                break;
        }
    }
}
