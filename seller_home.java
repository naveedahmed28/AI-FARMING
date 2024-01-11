package com.example.fertilizer;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.example.fertilizer.Soilest.MainActivity;

public class seller_home extends AppCompatActivity {

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seller_home);
 CardView listed  = findViewById(R.id.orders);
        CardView feedback  = findViewById(R.id.listed);
//        order.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent i = new Intent(getApplicationContext(), Admin_orders.class);
//                startActivity(i);
//            }
//        });

        findViewById(R.id.soil).setOnClickListener(
                v->{
                    startActivity(new Intent(this, MainActivity.class));
                }
        );
        listed.setOnClickListener(view -> {
            Intent i = new Intent(getApplicationContext(), seller_Listed_items.class);
            startActivity(i);
        });
        feedback.setOnClickListener(view -> {
            Intent i = new Intent(getApplicationContext(), FromUsers.class);
            i.putExtra("charan","charan");
            startActivity(i);
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
            getSharedPreferences("sharedPrefs",MODE_PRIVATE).edit().clear().apply();
            startActivity(new Intent(getApplicationContext(),Login.class));
            finish();
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onDestroy() {
    getSharedPreferences("sharedPrefs",MODE_PRIVATE).edit().clear().apply();
        super.onDestroy();
    }
}
