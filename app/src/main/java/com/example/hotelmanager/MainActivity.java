package com.example.hotelmanager;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;

import com.example.hotelmanager.DB.DBHelper;
import com.example.hotelmanager.Fragments.Add;
import com.example.hotelmanager.Fragments.Edit;
import com.example.hotelmanager.Model.Guest;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    public static final String MSG_NAME = "msg";

    Add add;
    Edit edit;

    FragmentTransaction transaction;
    LinearLayout layout;
    RecyclerView rv;

    List<Guest> guests;

    DBHelper dbHelper;
    GuestAdapter adapter;

    ItemTouchHelper.SimpleCallback callback;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        dbHelper = new DBHelper(this);
        guests = dbHelper.getGuests();

        rv = (RecyclerView) findViewById(R.id.rv);

        add = new Add();
        edit = new Edit();

        FragmentManager fm = getSupportFragmentManager();
        fm.addOnBackStackChangedListener(new FragmentManager.OnBackStackChangedListener() {
            @Override
            public void onBackStackChanged() {
                if (getFragmentManager().getBackStackEntryCount() == 0) finish();
            }
        });

        rv = findViewById(R.id.rv);
        adapter = new GuestAdapter(guests, this);
        rv.setAdapter(adapter);

        for (int i = 0; i < guests.size(); i++) {
            Log.d("DEBUG", guests.get(i).getId() + "\n" +
                    guests.get(i).getName() + "\n" + guests.get(i).getRoom() + "\n" + guests.get(i).getTotalPrice() +
                    "\n" + guests.get(i).getSex());
        }

        layout = findViewById(R.id.ll_main);

        AppCompatButton addBtn = findViewById(R.id.add);

        addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                transaction = getSupportFragmentManager().beginTransaction();
                transaction.add(R.id.ll_main, add);
                transaction.commit();
            }
        });

        callback = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                int position = viewHolder.getAdapterPosition();

                dbHelper.deleteGuest(guests.get(position));
                guests.remove(position);
                adapter.notifyDataSetChanged();
            }
        };

        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(callback);
        itemTouchHelper.attachToRecyclerView(rv);

    }

    @Override
    public void onBackPressed() {
        tellFragments();
    }

    //------------------
    //This code was taken from the site https://medium.com
    //Code's creator is Daniel Wilson
    private void tellFragments(){
        List<Fragment> fragments = getSupportFragmentManager().getFragments();
        if (fragments.size() == 0) {
            finish();
        } else {
            for(Fragment f : fragments){
                if(f instanceof BaseFragment)
                    ((BaseFragment)f).onBackPressed();
            }
        }
    }
    //------------------

    @Override
    protected void onResume() {
        super.onResume();
    }

    public void update() {
        guests.clear();
        guests.addAll(dbHelper.getGuests());
        adapter.notifyDataSetChanged();
    }

    @Override
    protected void onDestroy() {
        dbHelper.close();
        super.onDestroy();
    }
}