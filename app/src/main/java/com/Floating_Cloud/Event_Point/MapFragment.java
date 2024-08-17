package com.Floating_Cloud.Event_Point;

import android.app.AlertDialog;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.List;

public class MapFragment extends Fragment implements OnMapReadyCallback, GoogleMap.OnMapClickListener {
    private GoogleMap googleMap;
    LinearLayout panel;
    TextView sName;
    TextView sType;
    Button infB;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_map, container, false);

        initMap();
        panel = view.findViewById(R.id.panel);
        sName = view.findViewById(R.id.storeName);
        sType = view.findViewById(R.id.storeType);
        infB = view.findViewById(R.id.infBTN);
        return view;
    }

    private void initMap() {
        SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.mapFragment);
        if (mapFragment != null) {
            mapFragment.getMapAsync(this);
        }
        ApiClient apiClient = new ApiClient();
        apiClient.fetchSites(new ApiCallback<List<SiteData>>() {
            @Override
            public void onSuccess(List<SiteData> result) {
                // GET 요청의 결과 처리
                for (SiteData site : result) {
                    LatLng latLng = new LatLng(site.getLatitude(),site.getLongitude());
                    addMarkerWithStoreInfo(latLng,site.getName(),site.getTag(),site.getDescription());
                }
            }

            @Override
            public void onFailure(Throwable t) {
                t.printStackTrace();
            }
        });
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        this.googleMap = googleMap;
        LatLng latLng = new LatLng(36.37003, 127.34594);
        googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 18));
        googleMap.setOnMapClickListener(this);
    }

    @Override
    public void onMapClick(LatLng latLng) {
        // 다이얼로그를 표시하여 가게 정보 입력받기
        panel.setVisibility(View.GONE);
        showStoreInfoDialog(latLng);

    }

    private void showStoreInfoDialog(LatLng latLng) {
        // Custom Dialog Layout을 inflate
        View dialogView = LayoutInflater.from(getContext()).inflate(R.layout.dialog_confirm, null);

        EditText storeNameInput = dialogView.findViewById(R.id.editTextStoreName);
        EditText storeDescription = dialogView.findViewById(R.id.editTextStoreDescription);
        RadioGroup radioGroupType = dialogView.findViewById(R.id.radioGroupType);
        Button yesButton = dialogView.findViewById(R.id.yesButton);
        Button noButton = dialogView.findViewById(R.id.noButton);

        // 초기에는 확인 버튼을 비활성화
        yesButton.setEnabled(false);

        // TextWatcher를 사용하여 EditText의 내용이 변경될 때마다 호출
        TextWatcher textWatcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // 이름 입력과 라디오 버튼 선택 여부를 체크하여 확인 버튼 활성화 여부 결정
                yesButton.setEnabled(!storeNameInput.getText().toString().trim().isEmpty() &&
                        radioGroupType.getCheckedRadioButtonId() != -1);
            }

            @Override
            public void afterTextChanged(Editable s) {}
        };

        // EditText와 RadioGroup에 TextWatcher와 리스너를 등록
        storeNameInput.addTextChangedListener(textWatcher);
        radioGroupType.setOnCheckedChangeListener((group, checkedId) -> {
            yesButton.setEnabled(!storeNameInput.getText().toString().trim().isEmpty() &&
                    checkedId != -1);
        });

        // 다이얼로그 생성
        AlertDialog dialog = new AlertDialog.Builder(requireContext())
                .setView(dialogView)
                .create();

        // 확인 버튼 클릭 리스너
        yesButton.setOnClickListener(v -> {
            // 사용자 입력 정보 가져오기
            String storeName = storeNameInput.getText().toString();
            String storeType = ((RadioButton) dialogView.findViewById(radioGroupType.getCheckedRadioButtonId())).getText().toString();
            String storedescription = storeDescription.getText().toString();

            // 마커에 입력된 정보 추가
            SiteData siteData = new SiteData(
                    storeName,
                    storeType,
                    storedescription,
                    latLng.latitude,
                    latLng.longitude
            );

            ApiClient apiClient = new ApiClient();
            apiClient.sendSiteData(siteData);
            addMarkerWithStoreInfo(latLng, storeName, storeType,storedescription);

            // 다이얼로그 닫기
            dialog.dismiss();

        });

        // 취소 버튼 클릭 리스너
        noButton.setOnClickListener(v -> dialog.dismiss());

        // 다이얼로그 표시
        dialog.show();
    }

    private void addMarkerWithStoreInfo(LatLng latLng, String storeName, String storeType, String storeDescription) {
        // 마커에 가게 정보를 포함하여 추가
        Marker marker = googleMap.addMarker(new MarkerOptions()
                .position(latLng)
                .title(storeName)
                .snippet(storeType)
        );
        marker.setTag(storeDescription);
        googleMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(@NonNull Marker marker) {
                panel.setVisibility(View.VISIBLE);
                sName.setText(marker.getTitle());
                sType.setText(marker.getSnippet());
                String description = (String) marker.getTag();
                LatLng latLng1 = marker.getPosition();
                infB.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        StoreInfFragment storeInfFragment = new StoreInfFragment();
                        Bundle bundle = new Bundle();
                        bundle.putString("key1", marker.getTitle());
                        bundle.putString("key2", marker.getSnippet());
                        bundle.putString("key3", description);
                        bundle.putDouble("key4",latLng1.latitude );
                        bundle.putDouble("key5", latLng1.longitude);
                        storeInfFragment.setArguments(bundle);

                        getFragmentManager().beginTransaction()
                                .replace(R.id.main_frame,storeInfFragment)
                                .addToBackStack(null)
                                .commit();

                    }
                });
                return true;
            }
        });

    }
}