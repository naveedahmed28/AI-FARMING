package com.example.fertilizer;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

public class SeasonsActivity extends Activity {

    Button b1;
     EditText ed1,ed2,ed3,ed4;
     Spinner sp1,sp2,sp3,sp4,wr;
    ArrayAdapter adp1,adp2,adp3,adp4,adp5;
    RadioGroup rg;
    RadioButton radioButton;
    static String s,d,st,season,area,production,avg_rain;
   static String district_rain,waterres;
    int pos;
    int pos1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seasons);

        rg=findViewById(R.id.radiogroup);

        b1=findViewById(R.id.button2);
        ed4=findViewById(R.id.editText4);
        sp1=findViewById(R.id.sp1);
        sp2=findViewById(R.id.sp2);
        sp3=findViewById(R.id.sp3);
        sp4=findViewById(R.id.sp4);

        wr=findViewById(R.id.wr);

        String s1[]={"Select State","Andhra Pradesh"};
        String s2[]={"Select District","Ananthapur","chittoor","East Godavari","Guntur","kadapa","krishna","kurnool","Nellore","prakasam","Srikakulam","vizianagaram","vishakapatnam","west godavari"};


        String s3[]={"Kharif","Rabi","Whole Year"};
        String s4[]={"red","black","red-black","red-black-mixed","sandy","mixed-sandy"};


        String s5[]={"high","medium","low"};

        adp1=new ArrayAdapter(this,android.R.layout.simple_dropdown_item_1line,s1);
        sp1.setAdapter(adp1);

        adp2=new ArrayAdapter(this,android.R.layout.simple_dropdown_item_1line,s2);
        sp2.setAdapter(adp2);


        adp3=new ArrayAdapter(this,android.R.layout.simple_dropdown_item_1line,s3);
        sp3.setAdapter(adp3);


        adp4=new ArrayAdapter(this,android.R.layout.simple_dropdown_item_1line,s4);
        sp4.setAdapter(adp4);

        adp5=new ArrayAdapter(this,android.R.layout.simple_dropdown_item_1line,s5);
       wr.setAdapter(adp5);



        rg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {



                pos=rg.indexOfChild(findViewById(i));


                pos1=rg.indexOfChild(findViewById(rg.getCheckedRadioButtonId()));



                switch (pos)
                {
                    case 0 :


                        wr.setEnabled(false);
                        waterres="notselect";
                        break;


                    default :

                        waterres="select";
                        wr.setEnabled(true);

                        break;
                }
            }



        });





        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {



                if(sp1.getSelectedItem().equals("")){

                    Toast.makeText(SeasonsActivity.this, "Select State", Toast.LENGTH_SHORT).show();

                }
              else  if(sp2.getSelectedItem().equals("")){

                    Toast.makeText(SeasonsActivity.this, "Select District", Toast.LENGTH_SHORT).show();

                }

              else  if(sp3.getSelectedItem().equals("")){

                    Toast.makeText(SeasonsActivity.this, "Select Season", Toast.LENGTH_SHORT).show();

                }
                else  if(sp4.getSelectedItem().equals("")){

                    Toast.makeText(SeasonsActivity.this, "Select Soiltype", Toast.LENGTH_SHORT).show();

                }

            else {






                    if(waterres.equals("notselect")){

                    waterres="notselect";

                    }

                    else{


                        waterres=wr.getSelectedItem().toString().trim();
                    }






                    s = sp1.getSelectedItem().toString().trim();
                    d = sp2.getSelectedItem().toString().trim();
                    season = sp3.getSelectedItem().toString().trim();
                    st = sp4.getSelectedItem().toString().trim();
                   // area = ed3.getText().toString().trim();
                    production = ed4.getText().toString().trim();










                    Intent intent = new Intent(getApplicationContext(), CropInformation.class);
                    startActivity(intent);



                }



            }
        });

            }


}