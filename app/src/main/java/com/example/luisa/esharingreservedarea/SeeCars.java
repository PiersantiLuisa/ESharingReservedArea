package com.example.luisa.esharingreservedarea;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ExpandableListView;
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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class SeeCars extends AppCompatActivity {



    private CustomExpandableListView listAdapter;
    private List<String> listDataHeader;
    private HashMap<String,List<String>> listDataChild;
    private ExpandableListView listView;
    private CustomExpandableListView customExpandableListView;
    String idUser;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_see_cars);


        // Instantiate the RequestQueue.
      //  RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
        String url = "http://carsharingap.000webhostapp.com/server/api/car/read.php";


        idUser = getIntent().getExtras().getString("id");

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (Request.Method.POST, url, null,
                        new Response.Listener<JSONObject>() {
                            @Override
                            public void onResponse(JSONObject response) {

                                if (!(response.toString().equals("{\"message\":\"no cars found\"}"))) {
                                    listView = (ExpandableListView) findViewById(R.id.expandableListView);
                                    try {
                                        JSONArray cars = response.getJSONArray("cars");
                                        listDataHeader = new ArrayList<String>();
                                        listDataChild = new HashMap();


                                        for (int i = 0; i < cars.length(); i++) {


                                            JSONObject car = cars.getJSONObject(i);
                                            String seller = car.getString("seller");


                                            if (Integer.parseInt(seller) == Integer.parseInt(idUser)) {
                                                String id = car.getString("id");
                                                String model = car.getString("model");
                                                String maxSpeed = car.getString("maxSpeed");
                                                String numberOfPassengers = car.getString("numberOfPassengers");

                                                String carNo = "Car no. " + id;
                                                listDataHeader.add(carNo);

                                                ArrayList<String> listdetails = new ArrayList<>();
                                                listdetails.add("plate: " + id);
                                                listdetails.add("model: " + model);
                                                listdetails.add("maximum speed: " + maxSpeed);
                                                listdetails.add("number of passengers: " + numberOfPassengers);

                                                listDataChild.put(carNo, listdetails);
                                            }

                                        }

                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }

                                    customExpandableListView = new CustomExpandableListView(SeeCars.this, listDataHeader, listDataChild);
                                    listView.setAdapter(customExpandableListView);
                                    registerForContextMenu(listView);


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


    }


    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        menu.setHeaderTitle("SELECT OPTION");
        super.onCreateContextMenu(menu, v, menuInfo);
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.main_context_menu, menu);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        ExpandableListView.ExpandableListContextMenuInfo info = (ExpandableListView.ExpandableListContextMenuInfo) item.getMenuInfo();
        String idcar=  (listDataHeader.get((int)info.id)).toString().substring(8);
        customExpandableListView.deleteGroup((int)info.id);
        customExpandableListView.notifyDataSetChanged();

        // Instantiate the RequestQueue.
     //   RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
        String url = "http://carsharingap.000webhostapp.com/server/api/car/delete.php";

        HashMap deleteValues = new HashMap();
        deleteValues.put("seller", idUser);
        deleteValues.put("id", idcar);


        // Request a string response from the provided URL.

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (Request.Method.POST, url, new JSONObject(deleteValues),
                        new Response.Listener<JSONObject>() {
                            @Override
                            public void onResponse(JSONObject response) {
                                // Display the first 500 characters of the response string.
                                //mTextView.setText("Response is: "+ response.toString());
                                Toast.makeText(getApplicationContext(), "delete completed", Toast.LENGTH_LONG).show();

                            }
                        }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getApplicationContext(), "Something went wrong", Toast.LENGTH_LONG).show();
                    }
                });
        // Add the request to the RequestQueue.
        MySingleton.getInstance(getApplicationContext()).addToRequestQueue(jsonObjectRequest);



        return true;
    }


}
