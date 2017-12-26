package com.example.simon.myapplication;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class buy extends AppCompatActivity {
    TextView buy_Name,buy_amount,buy_single,buy_total;
    Button buy_Button,buy_Back;
    EditText buy_Num;
    int price=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buy);
        buy_amount = (TextView) findViewById(R.id.buy_amount);
        buy_single = (TextView) findViewById(R.id.buy_single);
        buy_total = (TextView) findViewById(R.id.buy_total);
        buy_Name = (TextView) findViewById(R.id.buy_Name);
        buy_Button = (Button) findViewById(R.id.buy_Button);
        buy_Back = (Button) findViewById(R.id.buy_Back);
        buy_Num = (EditText) findViewById(R.id.buy_Num);

        buy_Button.setOnClickListener(buy_ltn);
        buy_Back.setOnClickListener(buy_ltn);

        Intent i = this.getIntent();
        Bundle b = i.getExtras();
        buy_Name.setText(b.getString("BUYNAME"));
        price =Integer.parseInt(b.getString("BUYPRICE"));
    }
    Button.OnClickListener buy_ltn =new Button.OnClickListener(){

        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.buy_Button:
                    try {
                        buy_amount.setText("數量:"+buy_Num.getText().toString()+"           ");
                        buy_single.setText("單價:"+price+"           ");
                        buy_total.setText("總計:"+price*Integer.parseInt(buy_Num.getText().toString()));
                    }catch (Exception e){
                        Toast.makeText(v.getContext(),"請輸入數量",Toast.LENGTH_SHORT).show();
                    }

                    break;
                case R.id.buy_Back:
                    finish();
                    break;
            }
        }
    };
}
