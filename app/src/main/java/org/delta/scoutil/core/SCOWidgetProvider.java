package org.delta.scoutil.core;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.widget.RemoteViews;
import androidx.core.content.ContextCompat;

import org.delta.scoutil.R;

public class SCOWidgetProvider extends AppWidgetProvider {
    public static final String ACTION_SCO_ON = "org.delta.scoutil.core.ACTION_SCO_ON";
    public static final String ACTION_SCO_OFF = "org.delta.scoutil.core.ACTION_SCO_OFF";

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.sco_widget);

        Intent onIntent = new Intent(context, SCOWidgetProvider.class).setAction(ACTION_SCO_ON);
        int onFlags = PendingIntent.FLAG_UPDATE_CURRENT;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) onFlags |= PendingIntent.FLAG_IMMUTABLE;
        PendingIntent onPI = PendingIntent.getBroadcast(context, 0, onIntent, onFlags);
        views.setOnClickPendingIntent(R.id.btnScoOn, onPI);

        Intent offIntent = new Intent(context, SCOWidgetProvider.class).setAction(ACTION_SCO_OFF);
        int offFlags = PendingIntent.FLAG_UPDATE_CURRENT;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) offFlags |= PendingIntent.FLAG_IMMUTABLE;
        PendingIntent offPI = PendingIntent.getBroadcast(context, 1, offIntent, offFlags);
        views.setOnClickPendingIntent(R.id.btnScoOff, offPI);

        ComponentName thisWidget = new ComponentName(context, SCOWidgetProvider.class);
        appWidgetManager.updateAppWidget(thisWidget, views);
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        super.onReceive(context, intent);
        if (ACTION_SCO_ON.equals(intent.getAction())) {
            Intent svc = new Intent(context, SCOService.class);
            svc.putExtra("action", "on");
            ContextCompat.startForegroundService(context, svc);
        } else if (ACTION_SCO_OFF.equals(intent.getAction())) {
            Intent svc = new Intent(context, SCOService.class);
            svc.putExtra("action", "off");
            ContextCompat.startForegroundService(context, svc);
        }
    }
}
