package com.example.hotelmanager.Fragments;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.EditText;

import com.example.hotelmanager.BaseFragment;
import com.example.hotelmanager.DB.DBHelper;
import com.example.hotelmanager.MainActivity;
import com.example.hotelmanager.Model.Guest;
import com.example.hotelmanager.OnBackPressed;
import com.example.hotelmanager.R;
import com.google.android.material.snackbar.Snackbar;

public class Edit extends BaseFragment {

    EditText name;
    EditText room;
    EditText price;

    CheckBox m;
    CheckBox f;

    AppCompatButton save;

    Guest guest;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_edit, container, false);

        name = view.findViewById(R.id.name);
        room = view.findViewById(R.id.room);
        price = view.findViewById(R.id.price);

        m = view.findViewById(R.id.male);
        f = view.findViewById(R.id.female);

        m.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!m.isChecked() || f.isChecked()) {
                    f.setChecked(false);
                    m.setChecked(true);
                }
            }
        });

        f.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!f.isChecked() || m.isChecked()) {
                    m.setChecked(false);
                    f.setChecked(true);
                }
            }
        });

        save = view.findViewById(R.id.save);

        save.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("WrongConstant")
            @Override
            public void onClick(View v) {
                String mName = name.getText().toString();
                String mRoom = room.getText().toString();
                String mPrice = price.getText().toString();

                boolean mM = m.isChecked();
                boolean mF = f.isChecked();

                if (mName.trim().equals("")
                        || mRoom.trim().equals("")
                        || mPrice.trim().equals("")
                        || (!mM && !mF)) {
                    Snackbar.make(view, "You haven't filled all data!", Snackbar.ANIMATION_MODE_SLIDE).show();
                    return;
                }

                DBHelper dbHelper = new DBHelper(getContext());

                Guest updGuest = new Guest(
                        guest.getId(),
                        mName,
                        Integer.valueOf(mRoom),
                        Double.valueOf(mPrice),
                        mM ? "M" : "F"
                );

                dbHelper.editGuest(updGuest);

                getActivity().getSupportFragmentManager().beginTransaction().remove(Edit.this).commit();

                dbHelper.close();

            }
        });

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        guest = (Guest) (getArguments().getSerializable(MainActivity.MSG_NAME));

        name.setText( guest.getName() );
        room.setText( guest.getRoom() + "" );
        price.setText(guest.getTotalPrice().toString());
        if (guest.getSex().toLowerCase().equals("m")) {
            m.setChecked(true);
            f.setChecked(false);
        } else {
            f.setChecked(true);
            m.setChecked(false);
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        ((MainActivity)getActivity()).update();
    }

    @Override
    public void onBackPressed(){
        getActivity().getSupportFragmentManager().beginTransaction().remove(Edit.this).commit();
    }
}