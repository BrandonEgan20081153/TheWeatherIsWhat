package com.example.theweatheriswhat;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class ListCitiesFragment extends Fragment {

    EditText etCityName;
    ListView lvCities;
    ArrayList<String> citiesArrayList = new ArrayList<>();
    ArrayAdapter<String> arrayAdapter;
    Button btnAdd;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View fragmentView = inflater.inflate(R.layout.activity_list_cities_fragment, container, false);


        lvCities = fragmentView.findViewById(R.id.lvCities);
        btnAdd = fragmentView.findViewById(R.id.btnAdd);
        etCityName = fragmentView.findViewById(R.id.etCityName);


        arrayAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_dropdown_item_1line, citiesArrayList);

        lvCities.setAdapter(arrayAdapter);

        lvCities.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                TextView textView = (TextView) view;
//                Toast.makeText(getActivity(), textView.getText().toString(), Toast.LENGTH_LONG).show();
                Context context = getActivity();
                ((CitiesWeatherActivity) context).getClickedCityName(textView.getText().toString());
            }
        });

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                citiesArrayList.add(etCityName.getText().toString());
                arrayAdapter.notifyDataSetChanged();
            }
        });
        return fragmentView;
    }
}


