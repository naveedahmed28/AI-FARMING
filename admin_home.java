package com.example.fertilizer;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

public class admin_home extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_home);
        CardView order = findViewById(R.id.listed);
        CardView listed  = findViewById(R.id.userslist);
        CardView feedback  = findViewById(R.id.feedback);

        order.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(), Admin_orders.class);
                startActivity(i);
            }
        });
        listed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(), UsersList.class);
                startActivity(i);
            }
        });
        feedback.setOnClickListener(view -> {
            Intent i = new Intent(getApplicationContext(), AdminActivity.class);
            startActivity(i);
        });
        CardView videos=findViewById(R.id.videos);
        videos.setOnClickListener(j->{
            startActivity(new Intent(this,CreateVideos.class));
        });



    }
    int backButtonCount=0;
    @Override
    public void onBackPressed()
    {

        if(backButtonCount >= 1)
        {
            Intent intent = new Intent(Intent.ACTION_MAIN);
            intent.addCategory(Intent.CATEGORY_HOME);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
        }
        else
        {
            Toast.makeText(this, "Press the back button once again to close the application.", Toast.LENGTH_SHORT).show();
            backButtonCount++;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.adminmenu,menu);
        MenuItem logout = menu.findItem(R.id.adminlogout);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        if(item.getItemId()==R.id.adminlogout){
            startActivity(new Intent(getApplicationContext(),Login.class));
            finish();
        }

        return super.onOptionsItemSelected(item);
    }

    public void farmers(View view) {
        startActivity(new Intent(getApplicationContext(),FarmersList.class));
    }
}
