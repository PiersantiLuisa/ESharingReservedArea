package com.example.luisa.esharingreservedarea;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ExpandableListView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class SeeCars extends AppCompatActivity {



    private CustomExpandableListView listAdapter;
    private List<String> listDataHeader;
    private HashMap<String,List<String>> listDataChild;
    private ExpandableListView listView;
    private CustomExpandableListView customExpandableListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_see_cars);
        //  final TextView textView = findViewById(R.id.textView19);

        // Instantiate the RequestQueue.
        RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
        String url = "http://carsharingap.000webhostapp.com/server/api/car/read.php";

        //HashMap readOrder = new HashMap();
        final String idUser = getIntent().getExtras().getString("id");

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (Request.Method.POST, url, null,
                        new Response.Listener<JSONObject>() {
                            @Override
                            public void onResponse(JSONObject response) {


                                listView = (ExpandableListView)findViewById(R.id.expandableListView);
                                try {
                                    JSONArray cars = response.getJSONArray("cars");
                                    listDataHeader = new ArrayList<String>();
                                    listDataChild =new HashMap();


                                    for(int i=0; i < cars.length(); i++){

                                        //textView.setText(textView.getText()+"---"+(histories.getJSONObject(i).toString()));
                                        JSONObject car = cars.getJSONObject(i);
                                        String seller = car.getString("seller");


                                        if(Integer.parseInt(seller)==Integer.parseInt(idUser)) {
                                            String id = car.getString("id");
                                            String model = car.getString("model");
                                            String maxSpeed = car.getString("maxSpeed");
                                            String numberOfPassengers = car.getString("numberOfPassengers");

                                            String carNo = "Car no. " + id;
                                            listDataHeader.add(carNo);

                                            ArrayList<String> listdetails = new ArrayList<>();
                                            listdetails.add("plate: "+id);
                                            listdetails.add("model: "+model);
                                            listdetails.add("maximum speed: "+maxSpeed);
                                            listdetails.add("number of passengers: "+numberOfPassengers);

                                            listDataChild.put(carNo, listdetails);
                                        }

                                    }

                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }

                                customExpandableListView = new CustomExpandableListView(SeeCars.this,listDataHeader,listDataChild);
                                listView.setAdapter(customExpandableListView);

                            }


                        }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        //    textView.setText("That didn't work!");
                    }
                });
        // Add the request to the RequestQueue.
        MySingleton.getInstance(getApplicationContext()).addToRequestQueue(jsonObjectRequest);


    }


}
