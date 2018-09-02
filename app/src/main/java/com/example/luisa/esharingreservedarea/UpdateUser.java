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

    String ufirstName, ulastName, uusername, upassword,uaddress, ucity,ustate, uprefix, uphoneNumber, ugender, uid, urole;

    @Override
    public void onBackPressed() {

        Intent goToWelcome = new Intent (UpdateUser.this, welcomeS.class);

        goToWelcome.putExtra("firstName", ufirstName);
        goToWelcome.putExtra("lastName", ulastName);
        goToWelcome.putExtra("username", uusername);
        goToWelcome.putExtra("password", upassword);
        goToWelcome.putExtra("address", uaddress);
        goToWelcome.putExtra("city", ucity);
        goToWelcome.putExtra("state", ustate);
        goToWelcome.putExtra("prefix", uprefix);
        goToWelcome.putExtra("phoneNumber", uphoneNumber);
        goToWelcome.putExtra("gender", ugender);
        goToWelcome.putExtra("id", getIntent().getExtras().getString("id"));
        goToWelcome.putExtra("role", "1");


        startActivity(goToWelcome);

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_user);


        final EditText firstName = findViewById(R.id.fname1);
        firstName.setText(getIntent().getExtras().getString("firstName"));
        final EditText lastName = findViewById(R.id.sname);
        lastName.setText(getIntent().getExtras().getString("lastName"));
        final EditText username = findViewById(R.id.user);
        username.setText( getIntent().getExtras().getString("username"));
        final EditText password = findViewById(R.id.pwd);
        password.setText( getIntent().getExtras().getString("password"));
        final EditText address = findViewById(R.id.addr);
        address.setText( getIntent().getExtras().getString("address"));
        final EditText city = findViewById(R.id.city);
        city.setText( getIntent().getExtras().getString("city"));
        final EditText state = findViewById(R.id.state);
        state.setText( getIntent().getExtras().getString("state"));
        final EditText prefix = findViewById(R.id.pref);
        prefix.setText( getIntent().getExtras().getString("prefix"));
        final EditText telephone = findViewById(R.id.tel);
        telephone.setText( getIntent().getExtras().getString("phoneNumber"));

        final String gender;

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

                String gender1 = null;

                // Request a string response from the provided URL.

                EditText fname = findViewById(R.id.fname1);
                EditText lname = findViewById(R.id.sname);
                EditText uname = findViewById(R.id.user);
                EditText pass = findViewById(R.id.pwd);
                EditText addr = findViewById(R.id.addr);
                EditText city = findViewById(R.id.city);
                EditText state = findViewById(R.id.state);
                EditText pref = findViewById(R.id.pref);
                EditText tel = findViewById(R.id.tel);


                HashMap userValues = new HashMap();
                userValues.put("role", "1");
                userValues.put("firstName", fname.getText().toString());
                userValues.put("lastName", lname.getText().toString());
                userValues.put("username", uname.getText().toString());
                userValues.put("password", pass.getText().toString());
                userValues.put("address", addr.getText().toString());
                userValues.put("city", city.getText().toString());
                userValues.put("state", state.getText().toString());
                userValues.put("prefix", pref.getText().toString());
                userValues.put("phoneNumber", tel.getText().toString());

                int radioButtonId = radioGroup.getCheckedRadioButtonId();
                RadioButton radioButtonSelected = radioGroup.findViewById(radioButtonId);
                String selectedtext = (String) radioButtonSelected.getText();
                if(selectedtext.equals("M")) {
                    gender1 = "M";
                }else if(selectedtext.equals("F")) {
                    gender1 = "F";
                }


                userValues.put("gender",gender1);
                userValues.put("id",getIntent().getExtras().getString("id"));

                ufirstName = fname.getText().toString();
                ulastName = lname.getText().toString();
                uusername = uname.getText().toString();
                upassword =  pass.getText().toString();
                uaddress = addr.getText().toString();
                ucity =  city.getText().toString();
                ustate = state.getText().toString();
                uprefix =  pref.getText().toString();
                uphoneNumber = tel.getText().toString();
                ugender = gender1;
                uid = getIntent().getExtras().getString("id");
                urole = getIntent().getExtras().getString("role");

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


            }

        });


    }}
