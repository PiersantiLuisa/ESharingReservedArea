package com.example.luisa.esharingreservedarea;



import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

public class welcomeS extends AppCompatActivity {

    String sfname,ssname,suname,spass,saddr,scity,sstate,gender1,spref,stel,sid,srole;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome_s);

        TextView firstName = findViewById(R.id.fname1);
        sfname = getIntent().getExtras().getString("firstName");
        firstName.setText(sfname);
        TextView lastName = findViewById(R.id.sname);
        ssname = getIntent().getExtras().getString("lastName");
        lastName.setText(ssname);
        TextView username = findViewById(R.id.user);
        suname = getIntent().getExtras().getString("username");
        username.setText(suname);
        TextView password = findViewById(R.id.pwd);
        spass = getIntent().getExtras().getString("password");
        password.setText(spass);
        TextView address = findViewById(R.id.addr);
        saddr = getIntent().getExtras().getString("address");
        address.setText(saddr);
        TextView city = findViewById(R.id.city);
        scity = getIntent().getExtras().getString("city");
        city.setText(scity);
        TextView state = findViewById(R.id.state);
        sstate = getIntent().getExtras().getString("state");
        state.setText(sstate);

        String gender = null;

        RadioGroup radioGroup = findViewById(R.id.radiogroup);

        String valueOfGender = getIntent().getExtras().getString("gender");
        if(valueOfGender.equals("M")) {


            radioGroup.check(R.id.radioButton8);
            findViewById(R.id.radioButton8).setClickable(false);
            findViewById(R.id.radioButton7).setClickable(false);
            gender = "M";

        }else if (valueOfGender.equals("F")) {
            radioGroup.check(R.id.radioButton7);
            findViewById(R.id.radioButton8).setClickable(false);
            findViewById(R.id.radioButton7).setClickable(false);
            gender = "F";



        }
        gender1 = gender;

        TextView prefix = findViewById(R.id.pref);
        spref = getIntent().getExtras().getString("prefix");
        prefix.setText(spref);
        TextView telephone = findViewById(R.id.tel);
        stel = getIntent().getExtras().getString("phoneNumber");
        telephone.setText(stel);
        sid = getIntent().getExtras().getString("id");
        srole = getIntent().getExtras().getString("role");

        findViewById(R.id.button2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent updateIntent = new Intent(welcomeS.this, UpdateUser.class);

                updateIntent.putExtra("firstName",sfname);
                updateIntent.putExtra("lastName",ssname);
                updateIntent.putExtra("username",suname);
                updateIntent.putExtra("password",spass);
                updateIntent.putExtra("address",saddr);
                updateIntent.putExtra("city",scity);
                updateIntent.putExtra("state",sstate);
                updateIntent.putExtra("prefix",spref);
                updateIntent.putExtra("phoneNumber",stel);
                updateIntent.putExtra("gender",gender1);
                updateIntent.putExtra("id",sid);
                updateIntent.putExtra("role",srole);
                startActivity(updateIntent);

            }
        });

        findViewById(R.id.addcar).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent newCarIntent = new Intent(welcomeS.this, NewCar.class);

                newCarIntent.putExtra("id",sid);
                startActivity(newCarIntent);

            }
        });

        findViewById(R.id.addlocation).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent newLocationIntent = new Intent(welcomeS.this, NewLocation.class);

                newLocationIntent.putExtra("id",sid);
                startActivity(newLocationIntent);

            }
        });

        findViewById(R.id.seecar).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent carsIntent = new Intent(welcomeS.this, SeeCars.class);

                carsIntent.putExtra("id",sid);
                startActivity(carsIntent);

            }
        });

        findViewById(R.id.seelocation).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent locationsIntent = new Intent(welcomeS.this, SeeLocations.class);

                locationsIntent.putExtra("id",sid);
                startActivity(locationsIntent);

            }
        });



        findViewById(R.id.logout).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent goToMain = new Intent(welcomeS.this, MainActivity.class);

                goToMain.putExtra("finish", true); // if you are checking for this in your other Activities
                goToMain.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP |
                        Intent.FLAG_ACTIVITY_CLEAR_TASK |
                        Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(goToMain);
                finish();
            }
        });

    }


    @Override
    public void onBackPressed() {

    }

}


