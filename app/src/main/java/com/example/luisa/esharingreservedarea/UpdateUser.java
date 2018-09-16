package com.example.luisa.esharingreservedarea;


import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
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

public class UpdateUser extends AppCompatActivity {

        String gender;
        EditText firstName,lastName,username,password,address,city,state,prefix,telephone;

        @Override
        public void onBackPressed() {
            Intent goToWelcome = new Intent (UpdateUser.this, welcomeS.class);

            goToWelcome.putExtra("firstName", getIntent().getExtras().getString("firstName"));
            goToWelcome.putExtra("lastName", getIntent().getExtras().getString("lastName"));
            goToWelcome.putExtra("username",getIntent().getExtras().getString("username"));
            goToWelcome.putExtra("password", getIntent().getExtras().getString("password"));
            goToWelcome.putExtra("address",  getIntent().getExtras().getString("address"));
            goToWelcome.putExtra("city",  getIntent().getExtras().getString("city"));
            goToWelcome.putExtra("state",getIntent().getExtras().getString("state"));
            goToWelcome.putExtra("prefix",  getIntent().getExtras().getString("prefix"));
            goToWelcome.putExtra("phoneNumber",getIntent().getExtras().getString("phoneNumber"));
            goToWelcome.putExtra("gender", getIntent().getExtras().getString("gender"));
            goToWelcome.putExtra("id", getIntent().getExtras().getString("id"));
            goToWelcome.putExtra("role", "1");

            onNewIntent(goToWelcome);
            startActivity(goToWelcome);
        }


        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_update_user);


            firstName = findViewById(R.id.fname1);
            firstName.setText(getIntent().getExtras().getString("firstName"));
            lastName = findViewById(R.id.sname);
            lastName.setText(getIntent().getExtras().getString("lastName"));
            username = findViewById(R.id.user);
            username.setText( getIntent().getExtras().getString("username"));
            password = findViewById(R.id.pwd);
            password.setText( getIntent().getExtras().getString("password"));
            address = findViewById(R.id.addr);
            address.setText( getIntent().getExtras().getString("address"));
            city = findViewById(R.id.city);
            city.setText( getIntent().getExtras().getString("city"));
            state = findViewById(R.id.state);
            state.setText( getIntent().getExtras().getString("state"));
            prefix = findViewById(R.id.pref);
            prefix.setText( getIntent().getExtras().getString("prefix"));
            telephone = findViewById(R.id.tel);
            telephone.setText( getIntent().getExtras().getString("phoneNumber"));



            findViewById(R.id.radioButton8).setClickable(true);
            findViewById(R.id.radioButton7).setClickable(true);
            final RadioGroup radioGroup = findViewById(R.id.radiogroup);
            String valueOfGender = getIntent().getExtras().getString("gender");

            if(valueOfGender.equals("M")) {

                radioGroup.check(R.id.radioButton8);
                gender = "M";
            }else if(valueOfGender.equals("F")){
                radioGroup.check(R.id.radioButton7);

                gender = "F";
            }


            Button update = findViewById(R.id.register);
            update.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    // Instantiate the RequestQueue.
                    RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
                    String url = "http://carsharingap.000webhostapp.com/server/api/user/update.php";



                    // Request a string response from the provided URL.


                    HashMap userValues = new HashMap();
                    userValues.put("role", "1");
                    userValues.put("firstName", firstName.getText().toString());
                    userValues.put("lastName", lastName.getText().toString());
                    userValues.put("username", username.getText().toString());
                    userValues.put("password", password.getText().toString());
                    userValues.put("address", address.getText().toString());
                    userValues.put("city", city.getText().toString());
                    userValues.put("state", state.getText().toString());
                    userValues.put("prefix", prefix.getText().toString());
                    userValues.put("phoneNumber", telephone.getText().toString());

                    int radioButtonId = radioGroup.getCheckedRadioButtonId();
                    RadioButton radioButtonSelected = radioGroup.findViewById(radioButtonId);
                    String selectedtext = (String) radioButtonSelected.getText();
                    if(selectedtext.equals("M")) {
                        gender = "M";
                    }else if(selectedtext.equals("F")) {
                        gender = "F";
                    }


                    userValues.put("gender",gender);
                    userValues.put("id",getIntent().getExtras().getString("id"));


                    JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                            (Request.Method.POST, url, new JSONObject(userValues),
                                    new Response.Listener<JSONObject>() {
                                        @Override
                                        public void onResponse(JSONObject response) {
                                            // Display the first 500 characters of the response string.
                                            //mTextView.setText("Response is: "+ response.toString());
                                            Toast.makeText(getApplicationContext(), "update completed", Toast.LENGTH_LONG).show();

                                        }
                                    }, new Response.ErrorListener() {
                                @Override
                                public void onErrorResponse(VolleyError error) {
                                    Toast.makeText(getApplicationContext(), "Something went wrong", Toast.LENGTH_LONG).show();
                                }
                            });
                    // Add the request to the RequestQueue.
                    MySingleton.getInstance(getApplicationContext()).addToRequestQueue(jsonObjectRequest);

                    Intent goToWelcome = new Intent (UpdateUser.this, welcomeS.class);

                    goToWelcome.putExtra("firstName", firstName.getText().toString());
                    goToWelcome.putExtra("lastName", lastName.getText().toString());
                    goToWelcome.putExtra("username",  username.getText().toString());
                    goToWelcome.putExtra("password",  password.getText().toString());
                    goToWelcome.putExtra("address",  address.getText().toString());
                    goToWelcome.putExtra("city",  city.getText().toString());
                    goToWelcome.putExtra("state",  state.getText().toString());
                    goToWelcome.putExtra("prefix",  prefix.getText().toString());
                    goToWelcome.putExtra("phoneNumber",telephone.getText().toString());
                    goToWelcome.putExtra("gender", gender);
                    goToWelcome.putExtra("id", getIntent().getExtras().getString("id"));
                    goToWelcome.putExtra("role", "1");

                    onNewIntent(goToWelcome);
                    startActivity(goToWelcome);
                }



     /*   // Instantiate the RequestQueue.
        RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
        String url = "http://carsharingap.000webhostapp.com/server/api/user/update.php";

        // Controlli form

        String firstName = ((TextView)findViewById(R.id.fname1)).getText().toString();
        String lastName = ((TextView)findViewById(R.id.)).getText().toString();
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
            loginValues.put("role", "0");
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
                                            mTextView.setText("USER DOES NOT EXIST");
                                        else {

                                            mTextView.setText("ciao " + response.getString("firstName"));

                                            Intent intent = new Intent(getContext(), welcomeL.class);

                                            intent.putExtra("firstName",response.getString("firstName"));
                                            intent.putExtra("lastName",response.getString("lastName"));
                                            intent.putExtra("username", response.getString("username"));
                                            intent.putExtra("password",response.getString("password"));
                                            intent.putExtra("address",response.getString("address"));
                                            intent.putExtra("city",response.getString("city"));
                                            intent.putExtra("state",response.getString("state"));

                                            if(response.getString("gender")=="M")
                                                intent.putExtra("gender","M");
                                            else
                                                intent.putExtra("gender","F");

                                            intent.putExtra("prefix",response.getString("prefix"));
                                            intent.putExtra("phoneNumber",response.getString("phoneNumber"));

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
*/
            });


        }
        @Override
        protected void onNewIntent(final Intent intent) {
            super.onNewIntent(intent);
            this.setIntent(intent);
        }




    }
