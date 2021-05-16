package com.example.lab8;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.widget.RemoteViews;

/**
 * Implementation of App Widget functionality.
 */
public class ProductWidget extends AppWidgetProvider {

    static void updateAppWidget(Context context, AppWidgetManager appWidgetManager,
                                int appWidgetId) {

        Intent Inet = new Intent(context, MainActivity.class);

        Inet.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

        PendingIntent pIntentNetworkInfo = PendingIntent.getActivity(context, 2, Inet, 0);

        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.product_widget);

         views.setOnClickPendingIntent(R.id.widget, pIntentNetworkInfo);

        //RemoteViews Service needed to provide adapter for ListView
        Intent svcIntent = new Intent(context, ProductWidgetService.class);

        //passing app widget id to that RemoteViews Service
        svcIntent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetId);

        //setting a unique Uri to the intent
        //don't know its purpose to me right now
        svcIntent.setData(Uri.parse(
                svcIntent.toUri(Intent.URI_INTENT_SCHEME)));

        //setting adapter to listview of the widget
        views.setRemoteAdapter(R.id.product_list, svcIntent);

        //setting an empty view in case of no data

        appWidgetManager.updateAppWidget(appWidgetId, views);
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        // There may be multiple widgets active, so update all of them
        for (int appWidgetId : appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId);
        }
    }

    @Override
    public void onEnabled(Context context) {
        // Enter relevant functionality for when the first widget is created
    }

    @Override
    public void onDisabled(Context context) {
        // Enter relevant functionality for when the last widget is disabled
    }
}