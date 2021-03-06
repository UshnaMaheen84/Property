package com.example.property;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

public class Dashboard extends AppCompatActivity {

    CardView view_property_cv, add_property_cv;
    FirebaseAuth firebaseAuth;
    Toolbar toolbar;

    RelativeLayout main_layout;
    LinearLayout noNetworkLayout;

    Button retry_btn;

    String agentId, companyId, agent_name;

    TextView add_pro, view_pro, textView;
    Typeface myfonts;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dashboard_activity);

        toolbar = (Toolbar) findViewById(R.id.toolbar3);
        setSupportActionBar(toolbar);
        textView=findViewById(R.id.toolbar_title);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        Typeface typeface= Typeface.createFromAsset(getAssets(),"fonts/Montserrat-Italic.ttf");

        //Calibre-LightItalic
        textView.setTypeface(typeface);


        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        getSupportActionBar().setHomeButtonEnabled(false);

        firebaseAuth = FirebaseAuth.getInstance();


        main_layout = findViewById(R.id.main_layout);
        noNetworkLayout = findViewById(R.id.noNetworkLayout);
        retry_btn = findViewById(R.id.retry);

        add_pro= findViewById(R.id.add_prperty_tv);
        myfonts = Typeface.createFromAsset(this.getAssets(), "fonts/SourceCodePro-ExtraLight.ttf");
        add_pro.setTypeface(myfonts);

        view_pro= findViewById(R.id.view_prperty_tv);
        myfonts = Typeface.createFromAsset(this.getAssets(), "fonts/SourceCodePro-ExtraLight.ttf");
        view_pro.setTypeface(myfonts);




        add_property_cv = findViewById(R.id.add_prperty_cv);
        view_property_cv = findViewById(R.id.view_prperty_cv);


        if (haveNetwork()) {
            //Connected to the internet
            main_layout.setVisibility(View.VISIBLE);
            noNetworkLayout.setVisibility(View.GONE);
        } else {
            main_layout.setVisibility(View.GONE);
            noNetworkLayout.setVisibility(View.VISIBLE);
        }

        retry_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(haveNetwork()){
                    main_layout.setVisibility(View.VISIBLE);
                    noNetworkLayout.setVisibility(View.GONE);
                }
                else{
                    Toast.makeText(Dashboard.this, " Please get Online first. ", Toast.LENGTH_SHORT).show();
                }
            }
        });


        add_property_cv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(Dashboard.this, Add_Property.class);
                startActivity(intent);

            }
        });

        view_property_cv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(Dashboard.this, MainActivity_view.class);
                startActivity(intent);
            }
        });


    }

    public boolean haveNetwork() {

        boolean haveConnectedWifi = false;
        boolean haveConnectedMobile = false;

        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo[] netInfo = cm.getAllNetworkInfo();
        for (NetworkInfo ni : netInfo) {
            if (ni.getTypeName().equalsIgnoreCase("WIFI"))
                if (ni.isConnected())
                    haveConnectedWifi = true;
            if (ni.getTypeName().equalsIgnoreCase("MOBILE"))
                if (ni.isConnected())
                    haveConnectedMobile = true;
        }
        return haveConnectedWifi || haveConnectedMobile;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
        return true;

    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();


        if (id == R.id.action_logout) {


            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            LayoutInflater inflater = this.getLayoutInflater();
            final View dialogView = inflater.inflate(R.layout.logout_dialog, null);
            builder.setView(dialogView);
            final  AlertDialog alertDialog = builder.create();

            final Button logout = dialogView.findViewById(R.id.YesBtn);
            final Button cancel = dialogView.findViewById(R.id.cancel_action);

            logout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    SharedPreferences.Editor preferences = getSharedPreferences("SharedPreferences", MODE_PRIVATE).edit();

                    preferences.clear();
                    preferences.apply();
                    firebaseAuth.signOut();
                    startActivity(new Intent(Dashboard.this, Login.class));
                    finish();


                }
            });
            cancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    alertDialog.cancel();
                }
            });

            alertDialog.show();

            return true;

           }

        return super.onOptionsItemSelected(item);

    }

}