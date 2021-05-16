package com.example.lab8;

import android.appwidget.AppWidgetManager;
import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import java.util.ArrayList;
import java.util.List;

/**
 * If you are familiar with Adapter of ListView,this is the same as adapter
 * with few changes
 *
 */
public class ProductListViewProvider implements RemoteViewsService.RemoteViewsFactory {
    private List<Product> listItemList;
    private Context context = null;
    private int appWidgetId;

    DatabaseHandler mDatabaseHandler;

    public ProductListViewProvider(Context context, Intent intent) {
        this.context = context;
        appWidgetId = intent.getIntExtra(AppWidgetManager.EXTRA_APPWIDGET_ID,
                AppWidgetManager.INVALID_APPWIDGET_ID);

        mDatabaseHandler = new DatabaseHandler(context);

        populateListItem();
    }

    private void populateListItem() {
        listItemList = mDatabaseHandler.getProducts();
    }

    @Override
    public void onCreate() {

    }

    @Override
    public void onDataSetChanged() {

    }

    @Override
    public void onDestroy() {

    }

    @Override
    public int getCount() {
        return listItemList.size();
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    /*
     *Similar to getView of Adapter where instead of View
     *we return RemoteViews
     *
     */
    @Override
    public RemoteViews getViewAt(int position) {
        final RemoteViews remoteView = new RemoteViews(
                context.getPackageName(), R.layout.fragment_product_list_item);

        Product p = listItemList.get(position);
        remoteView.setTextViewText(R.id.product_id, p.get_id() + "");
        remoteView.setTextViewText(R.id.product_name, p.get_name());
        remoteView.setTextViewText(R.id.product_mrp, p.get_mrp() + "");
        remoteView.setTextViewText(R.id.product_price, p.get_price() + "");

        return remoteView;
    }

    @Override
    public RemoteViews getLoadingView() {
        return null;
    }

    @Override
    public int getViewTypeCount() {
        return 1;
    }
}