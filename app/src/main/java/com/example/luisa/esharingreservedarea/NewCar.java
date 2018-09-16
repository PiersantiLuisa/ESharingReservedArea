package com.example.luisa.esharingreservedarea;

import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

public class NewCar extends AppCompatActivity {

    ArrayList<String> nameLocations;
    ArrayList<String> idLocations;
    Spinner spinner;

    Button addcar;
    EditText plate,model,maxspeed,nopass;
    String splate,smodel,smaxspeed,snopass,idBaseChosen,seller;

    public void setSpinner(){


        spinner = findViewById(R.id.spinner5);

        // Instantiate the RequestQueue.
        RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
        String url = "http://carsharingap.000webhostapp.com/server/api/basement/read.php";


        // Request a string response from the provided URL.

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (Request.Method.POST, url, null,
                        new Response.Listener<JSONObject>() {
                            @Override
                            public void onResponse(JSONObject response) {
                                // Display the first 500 characters of the response string.
                                //mTextView.setText("Response is: "+ response.toString());

                                nameLocations = new ArrayList<String>();
                                idLocations = new ArrayList<String>();
                                try {

                                    JSONArray basements = response.getJSONArray("basements");
                                    for (int i = 0; i < basements.length(); i++) {
                                        JSONObject names = basements.getJSONObject(i);
                                        String name = names.getString("name");
                                        String idBase = names.getString("id");

                                        nameLocations.add(name);
                                        idLocations.add(idBase);

                                    }
                                    ArrayAdapter<String> stringArrayAdapter = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_spinner_dropdown_item, nameLocations);

                                    spinner.setAdapter(stringArrayAdapter);


                                } catch (JSONException e1) {
                                    e1.printStackTrace();
                                }

                            }
                        }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                        Toast.makeText(getApplicationContext(), "Something went wrong", Toast.LENGTH_LONG).show();

                    }
                });

        // Add the request to the RequestQueue.
        MySingleton.getInstance(getApplicationContext()).addToRequestQueue(jsonObjectRequest);


        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {

                idBaseChosen = idLocations.get(position);

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }


    public void controlForm(){


        plate = findViewById(R.id.plate);
        model = findViewById(R.id.model);
        maxspeed = findViewById(R.id.maxspeed);
        nopass = findViewById(R.id.nopass);

        splate = plate.getText().toString();
        smodel = model.getText().toString();
        smaxspeed = maxspeed.getText().toString();
        snopass = nopass.getText().toString();


        if(splate.isEmpty() || smodel.isEmpty() || smaxspeed.isEmpty() || snopass.isEmpty()){
            String error = "";

            if(splate.isEmpty()){error = error + " plate ";}
            if(smodel.isEmpty()){error = error + " model ";}
            if(smaxspeed.isEmpty()){error = error + " max of speed ";}
            if(snopass.isEmpty()){error = error + " number of passengers ";}

            error = error + "not entered";
            Toast.makeText(getApplicationContext(), error, Toast.LENGTH_LONG).show();

        }

        else {
            Toast.makeText(getApplicationContext(), "ok", Toast.LENGTH_LONG).show();
            sendDatas();

        }

    }

    public void sendDatas(){


        // Instantiate the RequestQueue.
        RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
        String url = "http://carsharingap.000webhostapp.com/server/api/car/create.php";


        // Request a string response from the provided URL.

        HashMap newCarValues = new HashMap();
        newCarValues.put("id", splate);
        newCarValues.put("model", smodel);
        newCarValues.put("maxSpeed", smaxspeed);
        newCarValues.put("numberOfPassengers", snopass);
        newCarValues.put("seller", seller);


        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (Request.Method.POST, url, new JSONObject(newCarValues),
                        new Response.Listener<JSONObject>() {
                            @Override
                            public void onResponse(JSONObject response) {
                                // Display the first 500 characters of the response string.
                                //mTextView.setText("Response is: "+ response.toString());


                            }
                        }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getApplicationContext(), "Something went wrong", Toast.LENGTH_LONG).show();
                    }
                });

        // Add the request to the RequestQueue.
        MySingleton.getInstance(getApplicationContext()).addToRequestQueue(jsonObjectRequest);


        // Instantiate the RequestQueue.
        RequestQueue queue1 = Volley.newRequestQueue(getApplicationContext());
        String url1 = "http://carsharingap.000webhostapp.com/server/api/history/create.php";

        DateFormat dateFormat = new SimpleDateFormat ("yyyy-MM-dd HH:mm:ss");
        Date date  = new Date();


        // Request a string response from the provided URL.

        HashMap newHistoryValues = new HashMap();
        newHistoryValues.put("idCar", splate);


        newHistoryValues.put("user", seller);
        newHistoryValues.put("idBasementStart", idBaseChosen);
        newHistoryValues.put("idBasementEnd", idBaseChosen);
        newHistoryValues.put("pickUpDay", dateFormat.format(date));
        newHistoryValues.put("deliveryDay", dateFormat.format(date));



        JsonObjectRequest jsonObjectRequest1 = new JsonObjectRequest
                (Request.Method.POST, url1, new JSONObject(newHistoryValues),
                        new Response.Listener<JSONObject>() {
                            @Override
                            public void onResponse(JSONObject response) {
                                // Display the first 500 characters of the response string.
                                //mTextView.setText("Response is: "+ response.toString());


                            }
                        }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getApplicationContext(), "Something went wrong", Toast.LENGTH_LONG).show();
                    }
                });

        // Add the request to the RequestQueue.
        MySingleton.getInstance(getApplicationContext()).addToRequestQueue(jsonObjectRequest1);


        plate.getText().clear();
        model.getText().clear();
        maxspeed.getText().clear();
        nopass.getText().clear();
    }


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_car);

        seller = getIntent().getExtras().getString("id");


        setSpinner();

        addcar = findViewById(R.id.addcar);
        addcar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                controlForm();
            }
        });



    }}
