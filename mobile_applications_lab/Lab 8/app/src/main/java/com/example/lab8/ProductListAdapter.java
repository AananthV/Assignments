package com.example.lab8;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

public class ProductListAdapter extends ArrayAdapter<Product> {

    private int resourceLayout;
    private Context mContext;

    public ProductListAdapter(Context context, int resource, List<Product> items) {
        super(context, resource, items);
        this.resourceLayout = resource;
        this.mContext = context;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View view = convertView;

        if (view == null) {
            LayoutInflater vi;
            vi = LayoutInflater.from(mContext);
            view = vi.inflate(resourceLayout, null);
        }

        Product p = getItem(position);

        if (p != null) {
            TextView mIdView = (TextView) view.findViewById(R.id.product_id);
            TextView mNameView = (TextView) view.findViewById(R.id.product_name);
            TextView mMRPView = (TextView) view.findViewById(R.id.product_mrp);
            TextView mPriceView = (TextView) view.findViewById(R.id.product_price);

            mIdView.setText("" + p.get_id());
            mNameView.setText(p.get_name());
            mMRPView.setText("" + p.get_mrp());
            mPriceView.setText("" + p.get_price());
        }

        return view;
    }

}