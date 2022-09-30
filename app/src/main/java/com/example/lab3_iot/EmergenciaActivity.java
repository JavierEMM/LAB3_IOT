package com.example.lab3_iot;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.pm.PackageManager;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;


public class EmergenciaActivity extends AppCompatActivity implements SensorEventListener{
    SensorManager sensorManager;



    //Obtenemos ubicacion

    public void obtenerUbicacion(View view) {

        int permissionCoarse = ActivityCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_COARSE_LOCATION);

        int permissionFine = ActivityCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION);

        if (permissionCoarse == PackageManager.PERMISSION_GRANTED &&
                permissionFine == PackageManager.PERMISSION_GRANTED) {
            //tengo permisos
            FusedLocationProviderClient fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);
            fusedLocationProviderClient.getLastLocation().addOnSuccessListener(
                    this,
                    location -> {
                        if (location != null) {
                            //mostramos a unicacion con un textView
                            //TextView t1 = findViewById(R.id.textViewLat);
                            //TextView t2 = findViewById(R.id.textViewLong);
                            t1.setText(String.valueOf(location.getLatitude()));
                            t2.setText(String.valueOf(location.getLongitude()));
                        }
                    });
        } else {
            //no tengo permisos
            requestPermissionLocation.launch(new String[]{
                    Manifest.permission.ACCESS_COARSE_LOCATION,
                    Manifest.permission.ACCESS_FINE_LOCATION
            });
        }


    }





    //Solicitamos los permisos
    ActivityResultLauncher<String[]> requestPermissionLocation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        requestPermissionLocation = registerForActivityResult(
                new ActivityResultContracts.RequestMultiplePermissions(),
                result -> {
                    Boolean clGranted = result.get(Manifest.permission.ACCESS_COARSE_LOCATION);
                    Boolean flGranted = result.get(Manifest.permission.ACCESS_FINE_LOCATION);

                    if (flGranted != null && flGranted) {
                        Log.d("msg", "me dio permisos Fine!");
                        obtenerUbicacion(null);
                    } else if (clGranted != null && clGranted) {
                        Log.d("msg", "me dio permisos Coarsed!");
                    } else {
                        Log.d("msg", "no me dio permisos");
                    }
                }
        );
    }



    private float xAnt = 0f;
    private float yAnt = 0f;
    private float zAnt = 0f;
    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {

        int sensorType = sensorEvent.sensor.getType();

        if (sensorType == Sensor.TYPE_ACCELEROMETER) {
            float xAcce = sensorEvent.values[0];
            float yAcce = sensorEvent.values[1];
            float zAcce = sensorEvent.values[2];
            if (xAcce != xAnt && yAcce != yAnt && zAcce != zAnt) {
                String msg = "x: " + xAcce + " | y: " + yAcce + " | z: " + zAcce;
                Log.d("msgSensor", msg);
            }
            xAnt = xAcce;
            yAnt = yAcce;
            zAnt = zAcce;
        }

    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }
}