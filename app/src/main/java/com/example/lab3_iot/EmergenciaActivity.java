package com.example.lab3_iot;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.View;
import android.view.WindowInsetsAnimation;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.lab3_iot.models.ContadorActualizarMapa;
import com.example.lab3_iot.models.ContadorViewModel;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.gson.Gson;
import com.google.maps.android.PolyUtil;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class EmergenciaActivity extends AppCompatActivity  implements OnMapReadyCallback {
    String url="";
    GoogleMap mMap;
    String apiKey = "AIzaSyCL6jxFZbLet8aY4aygQuc4pTyH7Y2tYac";
    ArrayList<LatLng> markerPoints= new ArrayList();
    LatLng origen = new LatLng(-12.084538, -77.031396);
    List<LatLng> polyline = null;
    LatLng destino = null;
    String destinoStr = "";
    LatLng direccion = new LatLng(-12.084538, -77.031396);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_emergencia);
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        final int[] contador1 = {0};
        Button button = (Button) findViewById(R.id.button_calcular);
        (button).setOnClickListener(view -> {
            if(contador1[0] == 0){
                EditText view1 = findViewById(R.id.editText_destino);
                if(view1.getText() != null && !view1.getText().toString().equals("")){
                    int contador2 = 10*60;
                    if(view1.getText().toString().contains("Lince") || destinoStr.contains("Lince")){
                        contador2 = 10*60;
                    }else if(view1.getText().toString().contains("San Isidro") || destinoStr.contains("San Isidro")){
                        contador2 = 15*60;
                    }else if(view1.getText().toString().contains("Magdalena") || destinoStr.contains("Magdalena")){
                        contador2 = 20*60;
                    }else if(view1.getText().toString().contains("Jesús María") || destinoStr.contains("Jesús María")){
                        contador2 = 25*60;
                    }else{
                        Toast.makeText(EmergenciaActivity.this,"No hay cobertura, pero igual para verificar contador 10 min",Toast.LENGTH_SHORT).show();
                    }
                    ContadorViewModel contadorViewModel = new ViewModelProvider(this).get(ContadorViewModel.class);
                    contadorViewModel.iniciarContador(contador2);
                    contadorViewModel.getContador().observe(this, new Observer<String>() {
                        @Override
                        public void onChanged(String s) {
                            TextView textView = findViewById(R.id.textView_contador);
                            textView.setText(s);
                        }
                    });
                    String destino2 = String.valueOf(view1.getText());
                    ContadorActualizarMapa contadorActualizarMapa = new ViewModelProvider(this).get(ContadorActualizarMapa.class);
                    contadorActualizarMapa.iniciarContador(contador2);
                    contadorActualizarMapa.getContador().observe(this, new Observer<Integer>() {
                        @Override
                        public void onChanged(Integer integer) {
                            try {
                                url = getDirectionsUrl(direccion,destino2);
                            } catch (UnsupportedEncodingException e) {
                                e.printStackTrace();
                            }
                            if(!url.equals("") && url != null) {
                                requestDirections(url);
                                if(polyline != null){
                                    mMap.clear();
                                    mMap.addMarker(new MarkerOptions().position(origen));
                                    mMap.addPolyline(new PolylineOptions().addAll(polyline));
                                }else{
                                    Toast.makeText(EmergenciaActivity.this,"Calculando ruta espere por favor",Toast.LENGTH_SHORT).show();
                                }
                            }
                            LatLng latLng =  destino;
                            if(latLng != null){
                                MarkerOptions markerOptions = new MarkerOptions();
                                markerOptions.position(latLng);
                                markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED));
                                markerPoints.add(latLng);
                                mMap.addMarker(markerOptions);
                            }
                        }
                    });
                    contador1[0]++;
                }else{
                    Toast.makeText(EmergenciaActivity.this,"Ingrese un destino valido",Toast.LENGTH_SHORT).show();
                }

            }
        });

    }

    private String getDirectionsUrl(LatLng origin, String dest) throws UnsupportedEncodingException {
        String str_origin = "origin=" + origin.latitude + "," + origin.longitude;
        String str_dest =  dest+",Peru";
        String sensor = "sensor=false";
        String mode = "mode=DRIVING";
        String parameters = str_origin + "&destination=" + encodeValue(str_dest) + "&" + sensor + "&" + mode + "&key=" + apiKey;
        String output = "json";
        String url = "https://maps.googleapis.com/maps/api/directions/" + output + "?" + parameters;
        Log.d("msg",url);
        return url;
    }
    private String encodeValue(String value) throws UnsupportedEncodingException {
        return URLEncoder.encode(value, StandardCharsets.UTF_8.toString());
    }
    private void requestDirections(String url){
        RequestQueue queue = Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject js = new JSONObject(response);
                    JSONObject js2 = (JSONObject) js.getJSONArray("routes").get(0);
                    String polylinepoints = js2.getJSONObject("overview_polyline").get("points").toString();
                    JSONObject llegada = (JSONObject) js2.getJSONArray("legs").get(0);
                    Double latitud= (Double) llegada.getJSONObject("end_location").get("lat");
                    Double longitud=(Double) llegada.getJSONObject("end_location").get("lng");
                    destinoStr = llegada.get("end_address").toString();
                    destino = new LatLng(latitud,longitud);
                    Log.d("msg",polylinepoints);
                    List<LatLng> polylines = PolyUtil.decode(polylinepoints);
                    Log.d("msg", String.valueOf(polylines.get(0).latitude));
                    polyline = polylines;
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }   ,new Response.ErrorListener(){
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        queue.add(stringRequest);
    }
    @SuppressLint("UseCompatLoadingForDrawables")
    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        mMap = googleMap;
        mMap.setLatLngBoundsForCameraTarget(new LatLngBounds(new LatLng(-12.138278999362807, -77.12407465861685),new LatLng(-11.735263322409695, -76.82648760207812)));
        markerPoints.add(direccion);
        mMap.addMarker(new MarkerOptions().position(origen));
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(direccion,13.0f));
    }
    /*public void mostrarUbicacion(View view){
        if((ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) && (ActivityCompat.checkSelfPermission(this,Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED)){
            //Tenemos permisos
            FusedLocationProviderClient fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);
            fusedLocationProviderClient.getLastLocation().addOnSuccessListener(this,location -> {
               if (location != null){
                   Log.d("msg", String.valueOf(location.getLatitude()));
                   Log.d("msg", String.valueOf(location.getLongitude()));
               }
            });
        }else{
            //no tenemos permiso
            requestPermissionForLocation.launch(new String[]{
                    Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.ACCESS_COARSE_LOCATION
            });
        }
        }*/
}