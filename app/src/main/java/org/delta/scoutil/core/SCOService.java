package org.delta.scoutil.core;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.os.Build;
import android.os.IBinder;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;

public class SCOService extends Service {

    private static final String CHANNEL_ID = "sco_service_channel";
    private AudioManager audioManager;

    @Override
    public void onCreate() {
        super.onCreate();
        audioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        String action = intent != null ? intent.getAction() : null;
        if ("START".equals(action)) {
            startForegroundService();
            startSCO();
        } else if ("STOP".equals(action)) {
            stopSCO();
            stopForeground(true);
            stopSelf();
        }
        return START_NOT_STICKY;
    }

    private void startForegroundService() {
        createNotificationChannel();

        Intent stopIntent = new Intent(this, SCOService.class);
        stopIntent.setAction("STOP");
        PendingIntent stopPendingIntent = PendingIntent.getService(
                this, 1, stopIntent,
                Build.VERSION.SDK_INT >= 31 ?
                        PendingIntent.FLAG_IMMUTABLE : 0
        );

        Notification notification = new NotificationCompat.Builder(this, CHANNEL_ID)
                .setContentTitle("SCO Mode Active")
                .setContentText("Bluetooth mic routing is ON")
                .setSmallIcon(android.R.drawable.stat_sys_phone_call)
                .addAction(android.R.drawable.ic_media_pause, "Stop", stopPendingIntent)
                .build();

        startForeground(1, notification);
    }

    private void createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel serviceChannel = new NotificationChannel(
                    CHANNEL_ID,
                    "SCO Service Channel",
                    NotificationManager.IMPORTANCE_LOW
            );
            NotificationManager manager = getSystemService(NotificationManager.class);
            manager.createNotificationChannel(serviceChannel);
        }
    }

    private void startSCO() {
        if (!audioManager.isBluetoothScoOn()) {
            audioManager.startBluetoothSco();
            audioManager.setBluetoothScoOn(true);
            Toast.makeText(this, "Bluetooth SCO started", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "SCO already active", Toast.LENGTH_SHORT).show();
        }
    }

    private void stopSCO() {
        if (audioManager.isBluetoothScoOn()) {
            audioManager.setBluetoothScoOn(false);
            audioManager.stopBluetoothSco();
            Toast.makeText(this, "Bluetooth SCO stopped", Toast.LENGTH_SHORT).show();
        }
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
