package com.example.fertilizer;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class UserDataActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_data);
    }

    public void farmers(View view) {
        startActivity(new Intent(getApplicationContext(),FarmersList.class));
    }

    public void users(View view) {
        startActivity(new Intent(getApplicationContext(),UsersList.class));
    }
}