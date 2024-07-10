package com.example.uasgps;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import org.osmdroid.config.Configuration;
import org.osmdroid.util.GeoPoint;
import org.osmdroid.views.MapView;
import org.osmdroid.views.overlay.Marker;

public class MainActivity extends AppCompatActivity {

    private MapView map;
    private static final int REQUEST_PERMISSIONS_REQUEST_CODE = 1;

    private final GeoPoint kampusBinus = new GeoPoint(-6.9153653, 107.5886954);
    private final GeoPoint braga = new GeoPoint(-6.9178283, 107.6045685);
    private final GeoPoint alunAlun = new GeoPoint(-6.9218295, 107.6021967);
    private final GeoPoint gazibu = new GeoPoint(-6.9002779, 107.6161296);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Load/initialize the osmdroid configuration
        Configuration.getInstance().load(getApplicationContext(), getPreferences(MODE_PRIVATE));

        setContentView(R.layout.activity_main);

        map = findViewById(R.id.map);
        map.setMultiTouchControls(true);
        map.getController().setZoom(15.0);
        map.getController().setCenter(kampusBinus);

        addMarker(kampusBinus, "Kampus Binus Bandung");
        addMarker(braga, "Braga");
        addMarker(alunAlun, "Alun-Alun Kota Bandung");
        addMarker(gazibu, "Lapangan Gazibu");

        findViewById(R.id.btnKampusBinus).setOnClickListener(v -> recenterMap(kampusBinus));
        findViewById(R.id.btnBraga).setOnClickListener(v -> recenterMap(braga));
        findViewById(R.id.btnAlunAlun).setOnClickListener(v -> recenterMap(alunAlun));
        findViewById(R.id.btnGazibu).setOnClickListener(v -> recenterMap(gazibu));

        requestPermissionsIfNecessary(new String[]{
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.WRITE_EXTERNAL_STORAGE
        });
    }

    private void addMarker(GeoPoint point, String title) {
        Marker marker = new Marker(map);
        marker.setPosition(point);
        marker.setTitle(title);
        map.getOverlays().add(marker);
    }

    private void recenterMap(GeoPoint point) {
        map.getController().setCenter(point);
    }

    private void requestPermissionsIfNecessary(String[] permissions) {
        for (String permission : permissions) {
            if (ContextCompat.checkSelfPermission(this, permission) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this, permissions, REQUEST_PERMISSIONS_REQUEST_CODE);
                return;
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_PERMISSIONS_REQUEST_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Permission granted, proceed with functionality
            } else {
                // Permission denied, disable functionality that depends on this permission
            }
        }
    }
}
