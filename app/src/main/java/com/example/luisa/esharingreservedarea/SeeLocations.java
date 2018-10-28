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

public class SeeLocations extends AppCompatActivity {


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
        String url = "http://carsharingap.000webhostapp.com/server/api/basement/read.php";


        idUser = getIntent().getExtras().getString("id");

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (Request.Method.POST, url, null,
                        new Response.Listener<JSONObject>() {
                            @Override
                            public void onResponse(JSONObject response) {

                                if (!(response.toString().equals("{\"message\":\"no basement found\"}"))) {
                                    listView = (ExpandableListView) findViewById(R.id.expandableListView);
                                    try {
                                        JSONArray basements = response.getJSONArray("basements");
                                        listDataHeader = new ArrayList<String>();
                                        listDataChild = new HashMap();


                                        for (int i = 0; i < basements.length(); i++) {

                                            //textView.setText(textView.getText()+"---"+(histories.getJSONObject(i).toString()));
                                            JSONObject basement = basements.getJSONObject(i);
                                            String seller = basement.getString("seller");


                                            if (Integer.parseInt(seller) == Integer.parseInt(idUser)) {
                                                String id = basement.getString("id");
                                                String name = basement.getString("name");
                                                String address = basement.getString("address");

                                                String locationNo = "Location no. " + id;
                                                listDataHeader.add(locationNo);

                                                ArrayList<String> listdetails = new ArrayList<>();
                                                listdetails.add("name: " + name);
                                                listdetails.add("address: " + address);

                                                listDataChild.put(locationNo, listdetails);
                                            }

                                        }

                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }

                                    customExpandableListView = new CustomExpandableListView(SeeLocations.this, listDataHeader, listDataChild);
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
        menuInflater.inflate(R.menu.main_context_menu1, menu);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        ExpandableListView.ExpandableListContextMenuInfo info = (ExpandableListView.ExpandableListContextMenuInfo) item.getMenuInfo();
        String idlocation=  (listDataHeader.get((int)info.id)).toString().substring(13);
        customExpandableListView.deleteGroup((int)info.id);
        customExpandableListView.notifyDataSetChanged();


        // Instantiate the RequestQueue.
      //  RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
        String url = "http://carsharingap.000webhostapp.com/server/api/basement/delete.php";

        HashMap deleteValues = new HashMap();
        deleteValues.put("seller", idUser);
        deleteValues.put("id", idlocation);


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
