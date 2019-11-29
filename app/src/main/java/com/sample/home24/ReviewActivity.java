package com.sample.home24;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.sample.home24.adapter.ArticleGridAdapter;
import com.sample.home24.adapter.ArticleListAdapter;
import com.sample.home24.beans.ArticlesProcessor;
import com.sample.home24.room.Article;
import com.sample.home24.viewmodel.ArticleViewModel;

import java.util.ArrayList;
import java.util.List;

public class ReviewActivity extends AppCompatActivity implements View.OnClickListener {

    private RecyclerView recyclerVieArticles;
    private ArticleGridAdapter articleGridAdapter;
    private ArticleListAdapter articleListAdapter;
    private TextView viewList, viewGrid;
    private List<Article> articleList;
    private ArticleViewModel mArticleViewModel;
    private GridLayoutManager gridLayoutManager;
    private LinearLayoutManager linearLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_review);
        init();
        setValues();
    }

    private void setValues() {
        mArticleViewModel.getArticlesFromDB().observe(this, new Observer<ArticlesProcessor>() {
            @Override
            public void onChanged(ArticlesProcessor articlesProcessor) {
                articleList.clear();
                articleList.addAll(articlesProcessor.getArticleList());
                articleGridAdapter.notifyDataSetChanged();
                articleListAdapter.notifyDataSetChanged();
            }
        });
    }

    private void init() {
        articleList = new ArrayList<>();
        mArticleViewModel = ViewModelProviders.of(this).get(ArticleViewModel.class);
        articleListAdapter = new ArticleListAdapter(this, articleList);
        articleGridAdapter = new ArticleGridAdapter(this, articleList);
        gridLayoutManager = new GridLayoutManager(this, 2);
        linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        gridLayoutManager.setOrientation(GridLayoutManager.VERTICAL);
        recyclerVieArticles = findViewById(R.id.rv_articles);
        viewList = findViewById(R.id.btn_list);
        viewGrid = findViewById(R.id.btn_grid);
        viewList.setOnClickListener(this);
        viewGrid.setOnClickListener(this);
        switchToList();

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.btn_list:
                switchToList();
                break;

            case R.id.btn_grid:
                switchToGrid();
                break;


            default:
                break;
        }
    }


    private void switchToList() {
        recyclerVieArticles.setLayoutManager(linearLayoutManager);
        recyclerVieArticles.setAdapter(articleListAdapter);
    }

    // Display the Grid
    private void switchToGrid() {
        recyclerVieArticles.setLayoutManager(gridLayoutManager);
        recyclerVieArticles.setAdapter(articleGridAdapter);
    }


}

