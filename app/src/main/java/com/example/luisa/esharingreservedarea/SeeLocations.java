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

public class SeeLocations extends AppCompatActivity {


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
        String url = "http://carsharingap.000webhostapp.com/server/api/basement/read.php";

        //HashMap readOrder = new HashMap();
        final String idUser = getIntent().getExtras().getString("id");

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (Request.Method.POST, url, null,
                        new Response.Listener<JSONObject>() {
                            @Override
                            public void onResponse(JSONObject response) {


                                listView = (ExpandableListView)findViewById(R.id.expandableListView);
                                try {
                                    JSONArray basements = response.getJSONArray("basements");
                                    listDataHeader = new ArrayList<String>();
                                    listDataChild =new HashMap();


                                    for(int i=0; i < basements.length(); i++){

                                        //textView.setText(textView.getText()+"---"+(histories.getJSONObject(i).toString()));
                                        JSONObject basement = basements.getJSONObject(i);
                                        String seller = basement.getString("seller");


                                        if(Integer.parseInt(seller)==Integer.parseInt(idUser)) {
                                            String id = basement.getString("id");
                                            String name = basement.getString("name");
                                            String address = basement.getString("address");

                                            String locationNo = "Location no. " + id;
                                            listDataHeader.add(locationNo);

                                            ArrayList<String> listdetails = new ArrayList<>();
                                            listdetails.add("name: "+name);
                                            listdetails.add("address: "+address);

                                            listDataChild.put(locationNo, listdetails);
                                        }

                                    }

                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }

                                customExpandableListView = new CustomExpandableListView(SeeLocations.this,listDataHeader,listDataChild);
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
