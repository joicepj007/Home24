package com.sample.home24.adapter;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import androidx.recyclerview.widget.RecyclerView;

import com.sample.home24.R;
import com.sample.home24.room.Article;
import com.sample.home24.utils.GlideApp;
import com.sample.home24.utils.Utils;

import java.util.List;

public class ArticleGridAdapter extends RecyclerView.Adapter<ArticleGridAdapter.ViewHolder> {

    List<Article> articleList;
    Context context;

    public ArticleGridAdapter(Context context, List<Article> articleList) {
        this.articleList = articleList;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.grid_item, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Article article = articleList.get(position);
        int width= Utils.getScreenWidth(context);
        RelativeLayout.LayoutParams params =
                (RelativeLayout.LayoutParams) holder.imageView.getLayoutParams();
        params.height = width / 2;
        params.width = width / 2;
        holder.imageView.invalidate();
        GlideApp.with(context)
                .load(article.articleImageUrl)
                .override(width/2,width/2)
                .into(holder.imageView);
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

    public static class ViewHolder extends RecyclerView.ViewHolder {

        ImageView imageView, watermark;

        public ViewHolder(View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.iv_artcle);
            watermark = itemView.findViewById(R.id.iv_watermark);
        }
    }

}