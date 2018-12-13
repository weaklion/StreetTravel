package com.example.dita_8.travel;

import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.View;

public class RecycleViewDecoration extends RecyclerView.ItemDecoration {
  public final int divHeight;

    public RecycleViewDecoration(int divHeight){
        this.divHeight=divHeight;
    }
    @Override
    public void getItemOffsets(Rect outRect, View view,RecyclerView parent,RecyclerView.State state){
        super.getItemOffsets(outRect,view,parent,state);
        outRect.top=divHeight;
    }
}
