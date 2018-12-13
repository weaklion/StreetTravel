package com.example.dita_8.travel;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;

public class RecycleAdapter extends RecyclerView.Adapter<RecycleAdapter.ViewHolder> {

    private ArrayList<MapActivity.Item> mDataset;
  private Context mcontext;

    public RecycleAdapter(ArrayList<MapActivity.Item> mDataset,Context mcontext) {
        this.mDataset = mDataset;
        this.mcontext = mcontext;
    }


    public static class ViewHolder extends RecyclerView.ViewHolder{
       TextView mLocation;
       TextView mLat;
      TextView mLng;
      LinearLayout Parentlayout;

      public ViewHolder(View itemView) {
          super(itemView);
          mLocation = (TextView)itemView.findViewById(R.id.text_location);
          mLat = (TextView)itemView.findViewById(R.id.textLat);
          mLng = (TextView)itemView.findViewById(R.id.textLng);
         Parentlayout=(LinearLayout) itemView.findViewById(R.id.parentlayout);

      }
  }

    @NonNull
    @Override
    public RecycleAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
      //새로운 뷰 생성
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item,parent,false);
        //뷰의 사이즈,마진,패딩과 layout parameters 정의
        ViewHolder holder = new ViewHolder(v);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecycleAdapter.ViewHolder holder, final int position) {
        //element 정의


        holder.mLocation.setText(mDataset.get(position).getLocation());
        holder.mLat.setText(Double.toString(mDataset.get(position).getLat()));
        holder.mLng.setText(Double.toString(mDataset.get(position).getLng()));
        holder.Parentlayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(mcontext,streetview.class);
                 intent.putExtra("lat",Double.toString(mDataset.get(position).getLat()));
                 intent.putExtra("lng",Double.toString(mDataset.get(position).getLng()));
                 mcontext.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mDataset.size();
    }
}
