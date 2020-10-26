package com.example.test_store.list;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.test_store.R;

import java.util.List;

public class MyRecyclerViewAdapter extends RecyclerView.Adapter<MyRecyclerViewAdapter.ViewHolder> {

    private List<ItemDetails> mData;
    private LayoutInflater mInflater;
    private ItemClickListener mClickListener;

    // data is passed into the constructor
    public MyRecyclerViewAdapter(Context context, List<ItemDetails> data) {
        this.mInflater = LayoutInflater.from(context);
        this.mData = data;
    }

    // inflates the row layout from xml when needed
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.recyclerview_post_row, parent, false);
        return new ViewHolder(view);
    }

    // binds the data to the TextView in each row
    @SuppressLint("UseCompatLoadingForDrawables")
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        String title = mData.get(position).getTitle();
        String date = mData.get(position).getDate();
        int likes = mData.get(position).getLikes();
        int comments = mData.get(position).getComments();
        holder.post_title.setText(title);
        holder.post_date.setText(date);
        holder.post_comments.setText(String.valueOf(comments));
        holder.post_likes.setText(String.valueOf(likes));

        final String[] categories = holder.itemView.getResources().getStringArray(R.array.category);
        String cat = mData.get(position).getCategory();
        if(cat.equals(categories[0]))
            holder.post_cat_image.setBackground(holder.itemView.getResources().getDrawable(R.drawable.category_life_style));
        else if(cat.equals(categories[1]))
            holder.post_cat_image.setBackground(holder.itemView.getResources().getDrawable(R.drawable.category_food));
        else if(cat.equals(categories[2]))
            holder.post_cat_image.setBackground(holder.itemView.getResources().getDrawable(R.drawable.category_technology));
        else if(cat.equals(categories[3]))
            holder.post_cat_image.setBackground(holder.itemView.getResources().getDrawable(R.drawable.category_social_media));
        else if(cat.equals(categories[4]))
            holder.post_cat_image.setBackground(holder.itemView.getResources().getDrawable(R.drawable.category_news));
        else if(cat.equals(categories[5]))
            holder.post_cat_image.setBackground(holder.itemView.getResources().getDrawable(R.drawable.category_fashion));
        else
            holder.post_cat_image.setBackground(holder.itemView.getResources().getDrawable(R.drawable.category_education));

    }



    // total number of rows
    @Override
    public int getItemCount() {
        return mData.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView post_title, post_likes, post_comments, post_date, post_author;
        ImageView post_cat_image;
        ViewHolder(View itemView) {
            super(itemView);
            post_title = itemView.findViewById(R.id.post_row_title);
            post_comments = itemView.findViewById(R.id.post_row_comments_count);
            post_likes = itemView.findViewById(R.id.post_row_likes_count);
            post_date = itemView.findViewById(R.id.post_row_date);
            post_author = itemView.findViewById(R.id.post_row_author);
            post_cat_image = itemView.findViewById(R.id.post_category_image);

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if (mClickListener != null) mClickListener.onItemClick(view, getAdapterPosition());
        }
    }


    public String getItem(int id) {
        return mData.get(id).getPostID();
    }

    // allows clicks events to be caught
    public void setClickListener(ItemClickListener itemClickListener) {
        this.mClickListener = itemClickListener;
    }

    // parent activity will implement this method to respond to click events
    public interface ItemClickListener {
        void onItemClick(View view, int position);
    }
}
