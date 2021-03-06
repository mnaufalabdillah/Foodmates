package com.rpl6.foodmates;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class ChefAdapter extends RecyclerView.Adapter<ChefAdapter.CardViewViewHolder> {
    private Context mCtx;
    private List<Chef> chefList;

    public ChefAdapter(Context mCtx, List<Chef> chefList){
        this.mCtx = mCtx;
        this.chefList =  chefList;
    }

    @NonNull
    @Override
    public CardViewViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.list_item_chef, viewGroup, false);
        return new CardViewViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final CardViewViewHolder holder, int position) {
        final Chef chef = chefList.get(position);

        holder.tvNama.setText(chef.getNama());
        holder.tvUmur.setText(Integer.toString(chef.getUmur()));
        holder.tvSpesialisasi.setText(chef.getSpesialisasi());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(holder.itemView.getContext(), "Kamu memilih " + chefList.get(holder.getAdapterPosition()).getNama(), Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(v.getContext(), ChefDetail.class);
                intent.putExtra("id", chefList.get(holder.getAdapterPosition()).getId());
                mCtx.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return chefList.size();
    }

    public class CardViewViewHolder extends RecyclerView.ViewHolder {
        TextView tvNama, tvUmur, tvSpesialisasi;

        public CardViewViewHolder(@NonNull View itemView) {
            super(itemView);
            tvNama = itemView.findViewById(R.id.textview_nama_chef);
            tvUmur = itemView.findViewById(R.id.textview_umur_chef);
            tvSpesialisasi = itemView.findViewById(R.id.textview_spesialisasi_chef);
        }
    }
}
