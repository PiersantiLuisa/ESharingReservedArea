package com.example.luisa.esharingreservedarea;


import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.luisa.esharingreservedarea.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class Login extends Fragment {

    Button login = null;
    View inflatedView = null;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {


        this.inflatedView = inflater.inflate(R.layout.fragment_login2, container, false);
        login = (Button) inflatedView.findViewById(R.id.button);

        

        final TextView textViewUser = (TextView) inflatedView.findViewById(R.id.user);
        final TextView textViewPassword = (TextView) inflatedView.findViewById(R.id.password);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                final TextView mTextView = (TextView) inflatedView.findViewById(R.id.text);


                // Instantiate the RequestQueue.
                RequestQueue queue = Volley.newRequestQueue(getContext());
                String url = "http://carsharingap.000webhostapp.com/server/api/user/read_single.php";


                // Controlli form

                String username = textViewUser.getText().toString();
                String password = textViewPassword.getText().toString();
                if (username.isEmpty() || password.isEmpty()) {
                    String error = "";
                    if (username.isEmpty()) error = error + " username ";
                    if (password.isEmpty()) error = error + " password ";
                    error = error + "not entered";
                    Toast.makeText(getContext(), error, Toast.LENGTH_LONG).show();
                }
                else {
                    // Request a string response from the provided URL.

                    HashMap loginValues = new HashMap();
                    loginValues.put("role", "1");
                    loginValues.put("username", textViewUser.getText().toString());
                    loginValues.put("password", textViewPassword.getText().toString());

                    JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                            (Request.Method.POST, url, new JSONObject(loginValues),
                                    new Response.Listener<JSONObject>() {
                                        @Override
                                        public void onResponse(JSONObject response) {
                                            // Display the first 500 characters of the response string.
                                            //mTextView.setText("Response is: "+ response.toString());
                                            try {
                                                if (response.isNull("id"))
                                                  Toast.makeText(getContext(), "USER DOES NOT EXIST", Toast.LENGTH_LONG).show();
                                                else {



                                                    mTextView.setText("ciao " + response.getString("firstName"));

                                                    Intent intent = new Intent(getContext(), welcomeS.class);

                                                    intent.putExtra("firstName",response.getString("firstName"));
                                                    intent.putExtra("lastName",response.getString("lastName"));
                                                    intent.putExtra("username", response.getString("username"));
                                                    intent.putExtra("password",response.getString("password"));
                                                    intent.putExtra("address",response.getString("address"));
                                                    intent.putExtra("city",response.getString("city"));
                                                    intent.putExtra("state",response.getString("state"));
                                                    intent.putExtra("gender",response.getString("gender"));
                                                    intent.putExtra("prefix",response.getString("prefix"));
                                                    intent.putExtra("phoneNumber",response.getString("phoneNumber"));
                                                    intent.putExtra("id",response.getString("id"));
                                                    startActivity(intent);
                                                }
                                            } catch (JSONException e) {
                                                e.printStackTrace();
                                            }
                                        }
                                    }, new Response.ErrorListener() {
                                @Override
                                public void onErrorResponse(VolleyError error) {
                                    mTextView.setText("That didn't work!");
                                }
                            });

                    // Add the request to the RequestQueue.
                    MySingleton.getInstance(getActivity().getApplicationContext()).addToRequestQueue(jsonObjectRequest);





                }
            }
        });

        return inflatedView;
    }



}




