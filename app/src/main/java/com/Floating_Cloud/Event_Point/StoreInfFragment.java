package com.Floating_Cloud.Event_Point;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;


public class StoreInfFragment extends Fragment {

    EditText editText1,editText2;
    RadioButton radioButton1,radioButton2;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_store_inf,container, false);
        editText1 = view.findViewById(R.id.sName2);
        editText2 = view.findViewById(R.id.inf2);
        radioButton1 = view.findViewById(R.id.radioType3);
        radioButton2 = view.findViewById(R.id.radioType4);
        Bundle bundle = getArguments();
        if(bundle!=null){
            editText1.setText(bundle.getString("key1"));
            if (bundle.getString("key2").equals("체험형")){
                radioButton1.setChecked(true);
            }
            else {
                radioButton2.setChecked(true);
            }
            editText2.setText(bundle.getString("key3"));
        }

        return view;
    }
}