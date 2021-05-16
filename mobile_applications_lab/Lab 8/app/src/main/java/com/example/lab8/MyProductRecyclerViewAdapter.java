package com.example.lab8;

import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

/**
 * {@link RecyclerView.Adapter} that can display a {@link Product}.
 * TODO: Replace the implementation with code for your data type.
 */
public class MyProductRecyclerViewAdapter extends RecyclerView.Adapter<MyProductRecyclerViewAdapter.ViewHolder> {

    private final List<Product> mValues;

    public MyProductRecyclerViewAdapter(List<Product> items) {
        mValues = items;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_product_list_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.mItem = mValues.get(position);
        holder.mIdView.setText("" + mValues.get(position).get_id());
        holder.mNameView.setText(mValues.get(position).get_name());
        holder.mMRPView.setText("" + mValues.get(position).get_mrp());
        holder.mPriceView.setText("" + mValues.get(position).get_price());
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final TextView mIdView;
        public final TextView mNameView;
        public final TextView mMRPView;
        public final TextView mPriceView;
        public Product mItem;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            mIdView = (TextView) view.findViewById(R.id.product_id);
            mNameView = (TextView) view.findViewById(R.id.product_name);
            mMRPView = (TextView) view.findViewById(R.id.product_mrp);
            mPriceView = (TextView) view.findViewById(R.id.product_price);
        }

        @Override
        public String toString() {
            return super.toString() + " '" + mNameView.getText() + "'";
        }
    }
}