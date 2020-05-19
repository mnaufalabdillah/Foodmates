package com.rpl6.foodmates;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class OrderFragmentAdapter extends RecyclerView.Adapter<OrderFragmentAdapter.OrderViewHolder> {

    Context oContext;
    List<Chef> OrderData;

    public OrderFragmentAdapter(Context oContext, List<Chef> orderData) {
        this.oContext = oContext;
        OrderData = orderData;
    }

    @NonNull
    @Override
    public OrderViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v;
        v = LayoutInflater.from(oContext).inflate(R.layout.list_item_orders, parent, false);
        OrderViewHolder viewHolder = new OrderViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull OrderViewHolder holder, int position) {
        final Chef OrdChef = OrderData.get(position);

        holder.tvorder_nama.setText(OrdChef.getNama());
        holder.tvorder_umur.setText(Integer.toString(OrdChef.getUmur()));
        holder.tvorder_spesialisasi.setText(OrdChef.getSpesialisasi());
    }

    @Override
    public int getItemCount() {
        return OrderData.size();
    }

    public static class OrderViewHolder extends RecyclerView.ViewHolder {
        private TextView tvorder_nama, tvorder_umur, tvorder_spesialisasi;
        private AppCompatImageView imgorder_photo;
        private Button imgorder_call;

        public OrderViewHolder(View orderView) {
            super(orderView);

            tvorder_nama = (TextView) orderView.findViewById(R.id.order_namachef);
            tvorder_umur = (TextView) orderView.findViewById(R.id.order_umurchef);
            tvorder_spesialisasi = (TextView) orderView.findViewById(R.id.order_spesialisasichef);
            imgorder_call = orderView.findViewById(R.id.order_hubungichef);
            imgorder_photo = orderView.findViewById(R.id.order_photochef);

            imgorder_call.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent callIntent = new Intent(Intent.ACTION_CALL);
                    callIntent.setData(Uri.parse("tel:6038994210"));
                    startActivity(callIntent);
                }

                private void startActivity(Intent callIntent) {
                }
            });
        }


    }
}
