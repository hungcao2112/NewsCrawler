package com.example.trungnguyen.newsapp;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.ImageButton;

public class SearchActivity extends AppCompatActivity {

    ImageButton backbutton;
    AutoCompleteTextView ACTV;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        backbutton = (ImageButton) findViewById(R.id.imageButton);
        backbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        ACTV = (AutoCompleteTextView) findViewById(R.id.autoCompleteTextView);
        //addHistory();
        //Thiết lập ArrayADapter
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,android.R.layout.simple_dropdown_item_1line, History);
        ACTV.setAdapter(adapter);
    }
    public static final String[] History= new String[]
            {"hà nội","Huế","Sài gòn",
                    "hà giang","Hội an","Kiên giang",
                    "Lâm đồng","Long khánh"};
    /*
    public void addHistory(){
        int i, j;
        j = History.length;
        for (i= j; i>=0; i--)
            if(ACTV.getText().toString().equalsIgnoreCase(History[i]))
                History[j+1] = ACTV.getText().toString();
    }
    */
}
