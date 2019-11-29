package com.sample.home24;

import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.sample.home24.room.Article;
import com.sample.home24.utils.ActionListeners;
import com.sample.home24.utils.AppConstants;
import com.sample.home24.utils.GlideApp;
import com.sample.home24.utils.Utils;


public class ArticleFragment extends Fragment {
    private static final String ARTICLE = "article";
    private static final String TOTAL_COUNT = "totalCount";
    private static final String TOTAL_LIKE_DISLIKE_COUNT = "totalLikeDisLikeCount";
    private Article article;
    private ImageView articleImage,likeButton, unlikeButton;
    private TextView likedCountText;
    private TextView reviewButton;
    private ProgressBar imageLoader;
    private ActionListeners actionListeners;
    private int totalLikeCount;
    private int totalLikeDisLikeCount;

    public ArticleFragment() {
        // Required empty public constructor
    }

    public static ArticleFragment newInstance(Article article, int totalLikeCount,
                                              int totalLikeDisLikeCount,
                                              ActionListeners actionListeners) {
        ArticleFragment fragment = new ArticleFragment();
        Bundle args = new Bundle();
        args.putParcelable(ARTICLE, article);
        args.putInt(TOTAL_COUNT, totalLikeCount);
        args.putInt(TOTAL_LIKE_DISLIKE_COUNT, totalLikeDisLikeCount);
        fragment.setArguments(args);
        fragment.actionListeners = actionListeners;
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            article = getArguments().getParcelable(ARTICLE);
            totalLikeCount = getArguments().getInt(TOTAL_COUNT);
            totalLikeDisLikeCount = getArguments().getInt(TOTAL_LIKE_DISLIKE_COUNT);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_article, container, false);
        init(view);
        setValues();
        return view;
    }

    private void setValues() {
        if (getActivity() != null && article != null) {
            imageLoader.setVisibility(View.VISIBLE);
            Activity activity = getActivity();
            final int screenHeight = Utils.getScreenHeight(activity);
            final int screenWidth = Utils.getScreenWidth(activity);
            FrameLayout.LayoutParams params =
                    (FrameLayout.LayoutParams) articleImage.getLayoutParams();
            params.height = screenHeight / 2;
            articleImage.invalidate();
            GlideApp.with(activity)
                    .load(article.articleImageUrl)
                    .override(screenWidth, screenHeight / 2)
                    .listener(new RequestListener<Drawable>() {
                        @Override
                        public boolean onLoadFailed(@Nullable GlideException e, Object model,
                                                    Target<Drawable> target,
                                                    boolean isFirstResource) {
                            imageLoader.setVisibility(View.GONE);
                            return false;
                        }

                        @Override
                        public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                            imageLoader.setVisibility(View.GONE);
                            return false;
                        }
                    })
                    .into(articleImage);
            likeButton.setImageResource(article.isLiked?R.mipmap.liked:R.mipmap.like);
            unlikeButton.setImageResource(article.isDisliked?R.mipmap.disliked:R.mipmap.dislike);
            likeButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    actionListeners.onLikeClick(article);
                }
            });
            unlikeButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    actionListeners.onDisLikeClick(article);
                }
            });
            updateCount(article, totalLikeCount);
            enableButton(totalLikeDisLikeCount);
            reviewButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent=new Intent(getActivity(), ReviewActivity.class);
                    startActivity(intent);
                }
            });
        }
    }

    public void enableButton(int totalLikeDisLikeCount) {
        if (getActivity() != null) {
            if (totalLikeDisLikeCount == AppConstants.ARTICLE_TOTAL_COUNT) {
                reviewButton.setEnabled(true);
            } else {
                reviewButton.setEnabled(false);
            }
        }
    }

    private void init(View view) {
        articleImage = view.findViewById(R.id.iv_image_start);
        likedCountText = view.findViewById(R.id.vT_like_counter);
        likeButton = view.findViewById(R.id.iv_like);
        unlikeButton = view.findViewById(R.id.iv_dislike);
        reviewButton = view.findViewById(R.id.btn_review);
        imageLoader=view.findViewById(R.id.pb_image_loader);
    }

    public void updateCount(Article article, int count) {
        if (getActivity() != null) {
            likeButton.setImageResource(article.isLiked?R.mipmap.liked:R.mipmap.like);
            unlikeButton.setImageResource(article.isDisliked?R.mipmap.disliked:R.mipmap.dislike);
            likedCountText.setText(count + "/" + AppConstants.ARTICLE_TOTAL_COUNT);
        }
    }
}
