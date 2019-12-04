package com.example.SpinIt;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class Popup extends Activity {
    private String[] selection;
    Button accepting;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        setContentView(R.layout.popupwindow);
        TextView choice;
        Intent displayPopUp = getIntent();
       // String[] info = displayPopUp.getStringArrayExtra("info");
        String info = displayPopUp.getStringExtra("info");
        choice = (TextView)findViewById(R.id.shownPopUp);
        accepting = (Button) findViewById(R.id.yelpAccept);
        final String tempChoice = (String) choice.getText();
        accepting.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view)
        {
//            Intent registerIntent = new Intent(Popup.this, GroupChatActivity.class);
//            startActivity(registerIntent);

            new Handler().postDelayed(new Runnable(){
                @Override
                public void run(){
                    Intent displayPopUp = new Intent(Popup.this, MainActivity.class);
                    displayPopUp.putExtra("chatInfo", tempChoice);
                    startActivity(displayPopUp);
                }
            }, 1000);

        }
    });

        super.onCreate(savedInstanceState);



        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        int width = dm.widthPixels;
        int height = dm.heightPixels;

        getWindow().setLayout((int)(width*.7), (int)(height*.5));
       choice.setText(info);

    }
}
