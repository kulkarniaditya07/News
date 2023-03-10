package com.example.news3;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class CategoryRVAdapter extends RecyclerView.Adapter<CategoryRVAdapter.ViewHolder> {

    private ArrayList<CategoryRVModel>categoryRVModels;
    private Context context;

    private CategoryClickinterface categoryClickinterface;

    public CategoryRVAdapter(ArrayList<CategoryRVModel> categoryRVModels, Context context, CategoryClickinterface categoryClickinterface) {
        this.categoryRVModels = categoryRVModels;
        this.context = context;
        this.categoryClickinterface = categoryClickinterface;
    }

    @NonNull
    @Override
    public CategoryRVAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
      View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.categories_rv_item,parent,false);
      return  new CategoryRVAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CategoryRVAdapter.ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        CategoryRVModel categoryRVModel=categoryRVModels.get(position);
        holder.CategoryTV.setText(categoryRVModel.getCategory());
        Picasso.get().load(categoryRVModel.getCategoryImageUrl()).into(holder.CategoryIV);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                categoryClickinterface.onCategoryclick(position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return categoryRVModels.size();
    }
    public interface CategoryClickinterface{
        void onCategoryclick(int position);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
     private TextView CategoryTV;
        private ImageView CategoryIV;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            CategoryTV=itemView.findViewById(R.id.idTVcategory);
            CategoryIV=itemView.findViewById(R.id.idIVCategory);

        }
    }
}
