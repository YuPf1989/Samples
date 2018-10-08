package com.rain.justtestsample.constraintlayout;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.rain.justtestsample.R;

import java.util.ArrayList;

/**
 * Author:rain
 * Date:2018/9/26 17:56
 * Description:
 * constraintLayout布局练习
 */
public class ConstraintLayoutActivity extends AppCompatActivity {

    private RecyclerView mRecycler;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_constraint);

        mRecycler = findViewById(R.id.recycler);
        mRecycler.setLayoutManager(new LinearLayoutManager(this));
        MyAdapter myAdapter = new MyAdapter(20);
        myAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onClick(String content) {
                Toast.makeText(ConstraintLayoutActivity.this, content, Toast.LENGTH_SHORT).show();
            }
        });
        mRecycler.setAdapter(myAdapter);
    }

    class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> {
        private OnItemClickListener listener;
        ArrayList<String> list = new ArrayList<>();

        public MyAdapter(int size) {
            for (int i = 0; i < size; i++) {
                list.add("item" + i);
            }
        }

        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View inflate = LayoutInflater.from(ConstraintLayoutActivity.this).inflate(R.layout.activity_constraint_item, parent,false);
            return new ViewHolder(inflate);
        }

        @Override
        public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
            holder.mTitle.setText(list.get(position));
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener != null) {
                        listener.onClick(list.get(position));
                    }
                }
            });
        }

        @Override
        public int getItemCount() {
            return list.size();
        }

        class ViewHolder extends RecyclerView.ViewHolder {

            private TextView mTitle;

            private ViewHolder(View itemView) {
                super(itemView);
                mTitle = itemView.findViewById(R.id.item_title);
            }
        }

        public void setOnItemClickListener(OnItemClickListener listener){
            this.listener = listener;
        }
    }

    interface OnItemClickListener {
        void onClick(String content);
    }
}
