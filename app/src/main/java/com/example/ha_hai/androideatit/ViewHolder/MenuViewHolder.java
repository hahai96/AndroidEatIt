package com.example.ha_hai.androideatit.ViewHolder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.ha_hai.androideatit.Interface.ItemClickListener;
import com.example.ha_hai.androideatit.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by ha_hai on 4/8/2018.
 */

public class MenuViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    @BindView(R.id.menu_name)
    public TextView menu_name;

    @BindView(R.id.menu_image)
    public ImageView menu_image;

    private ItemClickListener itemClickListener;

    public void setItemClickListener(ItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }

    public MenuViewHolder(View itemView) {
        super(itemView);

        //bind with butter knife
        ButterKnife.bind(this, itemView);

        itemView.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        itemClickListener.onClick(v, getAdapterPosition(), false);
    }
}
