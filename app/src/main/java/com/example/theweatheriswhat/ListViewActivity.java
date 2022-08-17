package com.example.theweatheriswhat;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class ListViewActivity extends AppCompatActivity {
    EditText enterCityName;
    ListView lvCities;
    ArrayList<String> citiesArrayList = new ArrayList<>();
    ArrayAdapter<String> arrayAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_view);

        lvCities = findViewById(R.id.lvCities);
        enterCityName = findViewById(R.id.etEnterCityName);

//        citiesArrayList.add("City A");
//        citiesArrayList.add("City B");
//        citiesArrayList.add("City C");
//        citiesArrayList.remove("City C");
//        citiesArrayList.clear();

        arrayAdapter = new ArrayAdapter<String>(ListViewActivity.this, android.R.layout.simple_dropdown_item_1line, citiesArrayList);

        lvCities.setAdapter(arrayAdapter);

        lvCities.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                TextView textView = (TextView) view;
                Toast.makeText(ListViewActivity.this, textView.getText().toString(), Toast.LENGTH_LONG).show();
            }
        });

    }

    public void AddCity(View view) {
        String CityNameStr = enterCityName.getText().toString();
        citiesArrayList.add(CityNameStr);
        arrayAdapter.notifyDataSetChanged();
    }
}