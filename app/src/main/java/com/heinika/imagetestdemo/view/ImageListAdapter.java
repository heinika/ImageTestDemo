package com.heinika.imagetestdemo.view;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.heinika.imagetestdemo.ImageActivity;
import com.heinika.imagetestdemo.R;
import com.heinika.imagetestdemo.entity.ImageEntity;

import java.util.List;

public class ImageListAdapter extends RecyclerView.Adapter<ImageListAdapter.ViewHolder> {
    private List<ImageEntity.ListBean> listBeans;
    private Context mContext;

    public ImageListAdapter(Context context){
        mContext = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
       return new ViewHolder(LayoutInflater.from(mContext).inflate(R.layout.image_item,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final ImageEntity.ListBean listBean = listBeans.get(position);
        Resources resources = mContext.getResources();
        DisplayMetrics dm = resources.getDisplayMetrics();
        int width = dm.widthPixels/3;
        int height = (int) (width*(Float.parseFloat(listBean.getHeight())/Float.parseFloat(listBean.getWidth())));
        RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) holder.imageView.getLayoutParams();
        layoutParams.height = height;
        holder.imageView.setLayoutParams(layoutParams);
        Glide.with(mContext).load(listBeans.get(position).getImg()).into(holder.imageView);
        holder.imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, ImageActivity.class);
                intent.putExtra("imageSrc",listBean.getImg());
                mContext.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return listBeans == null ? 0 : listBeans.size();
    }

    public void setListBeans(List<ImageEntity.ListBean> listBeans) {
        this.listBeans = listBeans;
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;

        public ViewHolder(View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.imageView_item);
        }
    }
}
