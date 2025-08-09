package org.delta.scoutil;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import org.delta.scoutil.core.SCOService;

public class MainActivity extends AppCompatActivity {
    private static final int REQ_PERMS = 101;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button btnStart = findViewById(R.id.btnStartSCO);
        btnStart.setOnClickListener(v -> {
            Intent i = new Intent(this, SCOService.class);
            i.setAction("START");
            ContextCompat.startForegroundService(this, i);
        });

        Button btnStop = findViewById(R.id.btnStopSCO);
        btnStop.setOnClickListener(v -> {
            Intent i = new Intent(this, SCOService.class);
            i.setAction("STOP");
            startService(i);
        });

    }

    private void requestNeededPermissions() {
        // Build a list of permissions to ask
        String[] perms;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            perms = new String[] {
                    Manifest.permission.RECORD_AUDIO,
                    Manifest.permission.BLUETOOTH_CONNECT
            };
        } else {
            perms = new String[] {
                    Manifest.permission.RECORD_AUDIO
            };
        }

        boolean need = false;
        for (String p : perms) {
            if (ContextCompat.checkSelfPermission(this, p) != PackageManager.PERMISSION_GRANTED) {
                need = true;
                break;
            }
        }
        if (need) {
            ActivityCompat.requestPermissions(this, perms, REQ_PERMS);
        } else {
            Toast.makeText(this, "Permissions already granted", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        boolean ok = true;
        for (int r : grantResults) if (r != PackageManager.PERMISSION_GRANTED) ok = false;
        Toast.makeText(this, ok ? "Permissions granted" : "Permissions denied", Toast.LENGTH_SHORT).show();
    }
}

