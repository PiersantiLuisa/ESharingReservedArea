package com.example.luisa.esharingreservedarea;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

import java.util.HashMap;

public class NewLocation extends AppCompatActivity {

    Button addlocation;
    EditText name,address;
    String sname, saddress,seller;


    public void controlForm(){

        name = findViewById(R.id.name);
        address = findViewById(R.id.address);

        sname = name.getText().toString();
        saddress = address.getText().toString();

        if(sname.isEmpty() || saddress.isEmpty()){
            String error = "";

            if(sname.isEmpty()){error = error + " name ";}
            if(saddress.isEmpty()){error = error + " address ";}

            error = error + "not entered";
            Toast.makeText(getApplicationContext(), error, Toast.LENGTH_LONG).show();

        }

        else {
            Toast.makeText(getApplicationContext(), "ok", Toast.LENGTH_LONG).show();
            sendDatas();

        }

    }

    public void sendDatas() {


        // Instantiate the RequestQueue.
      //  RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
        String url = "http://carsharingap.000webhostapp.com/server/api/basement/create.php";


        // Request a string response from the provided URL.

        HashMap newLocationValues = new HashMap();
        newLocationValues.put("name", sname);
        newLocationValues.put("address", saddress);
        newLocationValues.put("seller", seller);

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (Request.Method.POST, url, new JSONObject(newLocationValues),
                        new Response.Listener<JSONObject>() {
                            @Override
                            public void onResponse(JSONObject response) {
                                // Display the first 500 characters of the response string.
                                //mTextView.setText("Response is: "+ response.toString());
                                Toast.makeText(getApplicationContext(), "Location created", Toast.LENGTH_LONG).show();

                            }
                        }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getApplicationContext(), "Something went wrong", Toast.LENGTH_LONG).show();
                    }
                });

        // Add the request to the RequestQueue.
        MySingleton.getInstance(getApplicationContext()).addToRequestQueue(jsonObjectRequest);

name.getText().clear();
address.getText().clear();
    }

        @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_location);


        seller = getIntent().getExtras().getString("id");

        addlocation = findViewById(R.id.addlocation);
        addlocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                controlForm();
            }
        });

    }

}
