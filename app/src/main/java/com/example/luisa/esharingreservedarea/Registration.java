package com.example.luisa.esharingreservedarea;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
import com.example.luisa.esharingreservedarea.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

public class Registration extends Fragment {

    View inflatedView = null;
    Button registration = null;
    TextView mTextView = null;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        inflatedView = inflater.inflate(R.layout.fragment_registration, container, false);
        registration = (Button) inflatedView.findViewById(R.id.register);
        mTextView = (TextView) inflatedView.findViewById(R.id.msg);

        registration.setOnClickListener(new View.OnClickListener() {
            final EditText fName =  (EditText) inflatedView.findViewById(R.id.fname1);
            final EditText sname = (EditText) inflatedView.findViewById(R.id.sname);
            final EditText user = (EditText) inflatedView.findViewById(R.id.user);
            final EditText pwd = (EditText) inflatedView.findViewById(R.id.pwd);
            final EditText addr = (EditText) inflatedView.findViewById(R.id.addr);
            final EditText city = (EditText) inflatedView.findViewById(R.id.city);
            final EditText state = (EditText) inflatedView.findViewById(R.id.state);
            final EditText pref = (EditText) inflatedView.findViewById(R.id.pref);
            final EditText tel = (EditText) inflatedView.findViewById(R.id.tel);



            @Override
            public void onClick(View view) {

                Toast.makeText(getContext(), "selezionato" , Toast.LENGTH_SHORT).show();

                // Controlli form

                String firstname = fName.getText().toString();
                String surname = sname.getText().toString();
                String username = user.getText().toString();
                String password = pwd.getText().toString();
                String address = addr.getText().toString();
                String cit = city.getText().toString();
                String stat = state.getText().toString();
                String prefix = pref.getText().toString();
                String telephone = tel.getText().toString();

                RadioGroup idRadio = (RadioGroup) inflatedView.findViewById(R.id.radiogroup);
                int idradio = idRadio.getCheckedRadioButtonId();
                RadioButton rb = (RadioButton) inflatedView.findViewById(idradio);
                String sex = rb.getText().toString();

                if(firstname.isEmpty() || surname.isEmpty() || username.isEmpty() || password.isEmpty() || address.isEmpty() || cit.isEmpty() ||  stat.isEmpty() || prefix.isEmpty() || telephone.isEmpty()) {
                    String error = "";
                    if(firstname.isEmpty()) error = error + " firstname ";
                    if(surname.isEmpty()) error = error + " surname ";
                    if(username.isEmpty()) error = error + " username ";
                    if(password.isEmpty()) error = error + " password ";
                    if(address.isEmpty()) error = error + " address ";
                    if(cit.isEmpty()) error = error + " city ";
                    if(stat.isEmpty()) error = error + " state ";
                    if(prefix.isEmpty()) error = error + " prefix ";
                    if(telephone.isEmpty()) error = error + " telephone ";

                    error = error + "not entered";
                    Toast.makeText(getContext(), error, Toast.LENGTH_SHORT).show();
                }
                else{

                    // Instantiate the RequestQueue.
                  //  RequestQueue queue = Volley.newRequestQueue(getContext());
                    String url ="http://carsharingap.000webhostapp.com/server/api/user/create.php";

                    // Request a string response from the provided URL.

                    HashMap registrationValues = new HashMap();
                    registrationValues.put("role", "1");
                    registrationValues.put("firstName", firstname);
                    registrationValues.put("lastName", surname);
                    registrationValues.put("username", username);
                    registrationValues.put("password", password);
                    registrationValues.put("address", address);
                    registrationValues.put("city", cit);
                    registrationValues.put("state", stat);
                    registrationValues.put("prefix", prefix);
                    registrationValues.put("phoneNumber", telephone);
                    registrationValues.put("gender",sex);

                    JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                            (Request.Method.POST, url, new JSONObject(registrationValues),
                                    new Response.Listener<JSONObject>() {
                                        @Override
                                        public void onResponse(JSONObject response) {
                                            // Display the first 500 characters of the response string.
                                            //mTextView.setText("Response is: "+ response.toString());
                                            try {
                                                if (response.isNull("id"))
                                                    mTextView.setText("USER DOES NOT EXIST");
                                                else
                                                    mTextView.setText("ciao " + response.getString("firstName"));

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

                    fName.getText().clear();
                    sname.getText().clear();
                    user.getText().clear();
                    pwd.getText().clear();
                    addr.getText().clear();
                    city.getText().clear();
                    state.getText().clear();
                    pref.getText().clear();
                    tel.getText().clear();


                }




            }
        });

        return inflatedView;
    }

}
