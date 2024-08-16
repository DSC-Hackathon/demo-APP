package com.Floating_Cloud.Event_Point;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class MapFragment extends Fragment implements OnMapReadyCallback, GoogleMap.OnMapClickListener {
    private GoogleMap googleMap;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_map, container, false);

        // Initialize and display the map
        initMap();

        return view;
    }

    private void initMap() {
        SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.mapFragment);
        if (mapFragment != null) {
            mapFragment.getMapAsync(this);
        }
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        this.googleMap = googleMap;

        // Set the map click listener
        googleMap.setOnMapClickListener(this);

        // Optionally, set a default marker or other setup
        LatLng defaultLocation = new LatLng(35.20809411, 126.87056247);
        googleMap.addMarker(new MarkerOptions().position(defaultLocation).title("Default Location"));
        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(defaultLocation, 18));
    }

    @Override
    public void onMapClick(LatLng latLng) {

        showConfirmationDialog(latLng);
        // Add a new marker where the map was clicked
    }
    private void showConfirmationDialog(LatLng latLng) {
        // AlertDialog.Builder를 사용하여 대화상자 생성
        new AlertDialog.Builder(requireContext())
                .setTitle("Confirmation")
                .setMessage("마커를 추가?")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        googleMap.addMarker(new MarkerOptions().position(latLng).title("new marker"));
                        googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 18));
                    }
                })
                .setNegativeButton("No", null)  // "No" 버튼 클릭 시 아무 동작도 하지 않음
                .show();
    }
}