package com.sample.home24.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.sample.home24.R;
import com.sample.home24.room.Article;
import com.sample.home24.utils.GlideApp;

import java.util.List;


public class ArticleListAdapter extends RecyclerView.Adapter<ArticleListAdapter.ViewHolder>{

    List<Article> articleList ;
    Context context;

    public ArticleListAdapter(Context context,List<Article> articleList) {
        this.articleList = articleList;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.list_item,parent,false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Article article = articleList.get(position);
        GlideApp.with(context)
                .load(article.articleImageUrl)
                .into(holder.imageView);
        holder.tittle.setText(article.tittle);
        if (article.isLiked) {
            holder.watermark.setVisibility(View.VISIBLE);
        } else {
            holder.watermark.setVisibility(View.GONE);
        }
    }

    @Override
    public int getItemCount() {
        return articleList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{

        ImageView imageView,watermark;
        TextView tittle;

        public ViewHolder(View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.iv_artcle);
            tittle = itemView.findViewById(R.id.vT_tittle);
            watermark = itemView.findViewById(R.id.iv_watermark);
        }
    }

}