package com.Floating_Cloud.Event_Point;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;


public class StoreInfFragment extends Fragment {
    private ApiClient apiClient;
    EditText editText1,editText2;
    RadioButton radioButton1,radioButton2;
    Button btn1,btn2;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_store_inf,container, false);
        editText1 = view.findViewById(R.id.sName2);
        editText2 = view.findViewById(R.id.inf2);
        radioButton1 = view.findViewById(R.id.radioType3);
        radioButton2 = view.findViewById(R.id.radioType4);
        btn1 = view.findViewById(R.id.button);
        btn2 = view.findViewById(R.id.button2);
        apiClient = new ApiClient();
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
        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String type;
                if (radioButton1.isChecked()){
                    type = "체험형";
                }
                else {
                    type = "먹거리형";
                }
                SiteData updatedSite = new SiteData(
                        editText1.getText().toString(),
                        type,
                        editText2.getText().toString(),
                        bundle.getDouble("key4"),
                        bundle.getDouble("key5")
                );
                apiClient.updateSite(bundle.getString("key1"), updatedSite);
                apiClient.deleteSite(bundle.getString("key1"));
                close();
            }
        });
        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                apiClient.deleteSite(bundle.getString("key1"));
                close();
            }
        });

        return view;
    }
    public void close(){
        // 현재의 Fragment를 참조
        Fragment currentFragment = getParentFragmentManager().findFragmentById(R.id.main_frame);


        Fragment newFragment = new MapFragment();  // 새로운 프래그먼트 객체 생성

        FragmentTransaction transaction = getParentFragmentManager().beginTransaction();


        if (currentFragment != null) {
            transaction.remove(currentFragment);
        }

        transaction.add(R.id.main_frame, newFragment);

        transaction.commit();
    }

}

