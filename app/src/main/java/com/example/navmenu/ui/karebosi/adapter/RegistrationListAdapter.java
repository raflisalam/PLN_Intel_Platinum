package com.example.navmenu.ui.karebosi.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.navmenu.ui.karebosi.DetailKarebosiActivity;
import com.example.navmenu.R;
import com.example.navmenu.datamodel.RegistrationModel;

import java.util.ArrayList;

public class RegistrationListAdapter extends RecyclerView.Adapter<RegistrationListAdapter.itemHolder> implements Filterable {

    Context context;
    private ArrayList<RegistrationModel> registrationModels;
    private ArrayList<RegistrationModel> registrationModelsFull;

    public RegistrationListAdapter(Context context, ArrayList<RegistrationModel> list) {
        this.context = context;
        this.registrationModels = list;
        registrationModelsFull = new ArrayList<>(list);
//        this.filteredData = list;
    }

    @NonNull
    @Override
    public itemHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.user_list, parent, false);
        return new itemHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull itemHolder holder, int position) {
        RegistrationModel model = registrationModels.get(position);
        holder.tvIdpel.setText(model.getIdpel());
        holder.tvUnit.setText(model.getUnit());
        holder.tvNama.setText(model.getNama());
        holder.tvAlamat.setText(model.getAlamat());
        holder.tvNoHp.setText(model.getNo_hp());
        holder.tvTarif.setText(model.getTarif());
        holder.tvDaya.setText(model.getDaya());
        holder.tvIndikasi.setText(model.getIndikasi());
        holder.tvStandRek.setText(model.getStandRek());
        holder.tagLokasi.setText(model.getTagLokasi());
        holder.fotoMeter.setText(model.getFotoMeter());
        holder.fotoRumah.setText(model.getFotoRumah());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(holder.itemView.getContext(), DetailKarebosiActivity.class);
                intent.putExtra(DetailKarebosiActivity.DETAIL_DATA, registrationModels.get(position));
                holder.itemView.getContext().startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return registrationModels.size();
    }

//    public void filterList (ArrayList<RegistrationModel> filteredList) {
//        registrationModels = filteredList;
//        notifyDataSetChanged();
//    }

//    public Filter getFilter() {
//        return new Filter() {
//            @Override
//            protected FilterResults performFiltering(CharSequence constraint) {
//                String key = constraint.toString();
//                if (key.isEmpty()) {
//                    filteredData = registrationModels;
//                } else {
//                    ArrayList<RegistrationModel> listFilter = new ArrayList<>();
//                    for (RegistrationModel row: registrationModels) {
//                        if (row.getIdpel().toLowerCase().contains(key.toLowerCase())) {
//                            listFilter.add(row);
//                        }
//                    }
//
//                    filteredData = listFilter;
//                }
//                FilterResults filterResults = new FilterResults();
//                filterResults.values = filteredData;
//                return filterResults;
//            }
//
//            @Override
//            protected void publishResults(CharSequence constraint, FilterResults results) {
//                filteredData = (ArrayList<RegistrationModel>)results.values;
//                notifyDataSetChanged();
//            }
//        };
//    }

    public class itemHolder extends RecyclerView.ViewHolder {
        TextView tvIdpel, tvUnit, tvNama, tvAlamat, tvNoHp, tvTarif, tvDaya, tvIndikasi, tvStandRek, tagLokasi, fotoMeter, fotoRumah;

        public itemHolder(@NonNull View v) {
            super(v);
            tvIdpel = v.findViewById(R.id.tvIdpel);
            tvUnit = v.findViewById(R.id.tvUnit);
            tvNama = v.findViewById(R.id.tvNama);
            tvAlamat = v.findViewById(R.id.tvAlamat);
            tvNoHp = v.findViewById(R.id.tvNoHp);
            tvTarif = v.findViewById(R.id.tvTarif);
            tvDaya = v.findViewById(R.id.tvDaya);
            tvIndikasi = v.findViewById(R.id.tvIndikasi);
            tvStandRek = v.findViewById(R.id.tvStandRek);
            tagLokasi = v.findViewById(R.id.tagLokasi);
            fotoMeter = v.findViewById(R.id.fotoMeter);
            fotoRumah = v.findViewById(R.id.fotoRumah);
        }
    }

    @Override
    public Filter getFilter() {
        return karebosiFilter;
    }

    private Filter karebosiFilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            ArrayList<RegistrationModel> filteredList = new ArrayList<>();

            if (constraint == null || constraint.length() == 0) {
                filteredList.addAll(registrationModelsFull);
            } else {
                String filterPattern = constraint.toString().toLowerCase().trim();
                for (RegistrationModel item :  registrationModelsFull) {
                    if (item.getIdpel().toLowerCase().contains(filterPattern)) {
                        filteredList.add(item);
                    }
                }
            }
            FilterResults results = new FilterResults();
            results.values = filteredList;

            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            registrationModels.clear();
            registrationModels.addAll((ArrayList) results.values);
            notifyDataSetChanged();
        }
    };
}
