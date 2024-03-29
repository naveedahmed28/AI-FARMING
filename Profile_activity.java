package com.example.fertilizer;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.drawable.DrawableCompat;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class Profile_activity extends AppCompatActivity {
    SharedPreferences sharedPreferences ;
    TextView name,mail,num,add;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_activity);
        sharedPreferences = getSharedPreferences(Login.SHARED_PREFS,MODE_PRIVATE);
        name = findViewById(R.id.cartName);
        mail = findViewById(R.id.cartmail);
        num = findViewById(R.id.cartmobile);

        add = findViewById(R.id.addresscart);
        name.setText(sharedPreferences.getString("un",""));
        mail.setText(sharedPreferences.getString("email",""));
        num.setText(sharedPreferences.getString("no",""));
        add.setText(sharedPreferences.getString("add",""));
        ImageView lo = findViewById(R.id.logoutimg);
        DrawableCompat.setTint(
                DrawableCompat.wrap(lo.getDrawable()),
                ContextCompat.getColor(this, R.color.grey)
        );

        CardView logout = findViewById(R.id.logout);
        CardView myorders = findViewById(R.id.myorders);
        myorders.setOnClickListener(view -> {
            Intent i = new Intent(getApplicationContext(),OrderidVise.class);
            startActivity(i);
        });
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                SharedPreferences preferences =getSharedPreferences(Login.SHARED_PREFS,MODE_PRIVATE);
                SharedPreferences.Editor editor = preferences.edit();
                editor.clear();
                editor.apply();
                finishAffinity();
                Intent i = new Intent(getApplicationContext(),Login.class);
                startActivity(i);
            }
        });
        Button edit = findViewById(R.id.edit2);
        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(),edit_profile.class);
                startActivity(i);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        name.setText(sharedPreferences.getString("un",""));
        mail.setText(sharedPreferences.getString("email",""));
        num.setText(sharedPreferences.getString("no",""));
        add.setText(sharedPreferences.getString("add",""));
    }

    public void onBackPressed() {
        finish();
    }
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }
}