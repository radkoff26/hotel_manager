package com.example.hotelmanager;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.example.hotelmanager.Fragments.Edit;
import com.example.hotelmanager.Model.Guest;

import java.util.List;

public class GuestAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<Guest> guests;
    private Context ctx;
    private LayoutInflater inflater;

    public GuestAdapter(List<Guest> guests, Context context) {
        this.guests = guests;
        this.ctx = context;
        this.inflater = LayoutInflater.from(context);
    }

    public class NewViewHolder extends RecyclerView.ViewHolder {

        final TextView tv_name, tv_room, tv_total_price, tv_sex;

        final LinearLayout l_layout;

        public NewViewHolder(@NonNull View itemView) {
            super(itemView);
            l_layout = itemView.findViewById(R.id.ll);
            tv_name = itemView.findViewById(R.id.tv_name);
            tv_room = itemView.findViewById(R.id.tv_room);
            tv_total_price = itemView.findViewById(R.id.tv_tp);
            tv_sex = itemView.findViewById(R.id.tv_sex);
        }
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.guest_item, parent, false);
        return new NewViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        Guest guest = guests.get(position);

        ((NewViewHolder) holder).tv_room.setText("Room: " + guest.getRoom());
        ((NewViewHolder) holder).tv_total_price.setText("Total Price: " + guest.getTotalPrice() + "$");
        ((NewViewHolder) holder).tv_name.setText(guest.getName());
        ((NewViewHolder) holder).tv_sex.setText(guest.getSex().equals("M") ? "Male" : "Female");

        ((NewViewHolder) holder).itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Edit edit = new Edit();

                Bundle bundle = new Bundle();
                bundle.putSerializable(MainActivity.MSG_NAME, guests.get(position));
                edit.setArguments(bundle);

                ((AppCompatActivity)ctx).getSupportFragmentManager()
                        .beginTransaction()
                        .add(R.id.ll_main, edit)
                        .commit();
            }
        });
    }

    @Override
    public int getItemCount() {
        return guests.size();
    }
}
