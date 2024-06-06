// Generated by view binder compiler. Do not edit!
package com.example.stuntguard.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.widget.NestedScrollView;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewbinding.ViewBinding;
import androidx.viewbinding.ViewBindings;
import com.example.stuntguard.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import java.lang.NullPointerException;
import java.lang.Override;
import java.lang.String;

public final class ActivityMainBinding implements ViewBinding {
  @NonNull
  private final ConstraintLayout rootView;

  @NonNull
  public final ConstraintLayout addChildButton;

  @NonNull
  public final BottomNavigationView bottomNavigationMain;

  @NonNull
  public final TextView day;

  @NonNull
  public final TextView hallo;

  @NonNull
  public final TextView news;

  @NonNull
  public final TextView newsLatest;

  @NonNull
  public final ProgressBar progressBar;

  @NonNull
  public final RecyclerView rvNews;

  @NonNull
  public final RecyclerView rvNewsLatest;

  @NonNull
  public final NestedScrollView scrollView;

  @NonNull
  public final TextView seeAll;

  @NonNull
  public final TextView seeMore;

  @NonNull
  public final ConstraintLayout top;

  @NonNull
  public final TextView username;

  private ActivityMainBinding(@NonNull ConstraintLayout rootView,
      @NonNull ConstraintLayout addChildButton, @NonNull BottomNavigationView bottomNavigationMain,
      @NonNull TextView day, @NonNull TextView hallo, @NonNull TextView news,
      @NonNull TextView newsLatest, @NonNull ProgressBar progressBar, @NonNull RecyclerView rvNews,
      @NonNull RecyclerView rvNewsLatest, @NonNull NestedScrollView scrollView,
      @NonNull TextView seeAll, @NonNull TextView seeMore, @NonNull ConstraintLayout top,
      @NonNull TextView username) {
    this.rootView = rootView;
    this.addChildButton = addChildButton;
    this.bottomNavigationMain = bottomNavigationMain;
    this.day = day;
    this.hallo = hallo;
    this.news = news;
    this.newsLatest = newsLatest;
    this.progressBar = progressBar;
    this.rvNews = rvNews;
    this.rvNewsLatest = rvNewsLatest;
    this.scrollView = scrollView;
    this.seeAll = seeAll;
    this.seeMore = seeMore;
    this.top = top;
    this.username = username;
  }

  @Override
  @NonNull
  public ConstraintLayout getRoot() {
    return rootView;
  }

  @NonNull
  public static ActivityMainBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, null, false);
  }

  @NonNull
  public static ActivityMainBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup parent, boolean attachToParent) {
    View root = inflater.inflate(R.layout.activity_main, parent, false);
    if (attachToParent) {
      parent.addView(root);
    }
    return bind(root);
  }

  @NonNull
  public static ActivityMainBinding bind(@NonNull View rootView) {
    // The body of this method is generated in a way you would not otherwise write.
    // This is done to optimize the compiled bytecode for size and performance.
    int id;
    missingId: {
      id = R.id.add_child_button;
      ConstraintLayout addChildButton = ViewBindings.findChildViewById(rootView, id);
      if (addChildButton == null) {
        break missingId;
      }

      id = R.id.bottom_navigation_main;
      BottomNavigationView bottomNavigationMain = ViewBindings.findChildViewById(rootView, id);
      if (bottomNavigationMain == null) {
        break missingId;
      }

      id = R.id.day;
      TextView day = ViewBindings.findChildViewById(rootView, id);
      if (day == null) {
        break missingId;
      }

      id = R.id.hallo;
      TextView hallo = ViewBindings.findChildViewById(rootView, id);
      if (hallo == null) {
        break missingId;
      }

      id = R.id.news;
      TextView news = ViewBindings.findChildViewById(rootView, id);
      if (news == null) {
        break missingId;
      }

      id = R.id.news_latest;
      TextView newsLatest = ViewBindings.findChildViewById(rootView, id);
      if (newsLatest == null) {
        break missingId;
      }

      id = R.id.progress_bar;
      ProgressBar progressBar = ViewBindings.findChildViewById(rootView, id);
      if (progressBar == null) {
        break missingId;
      }

      id = R.id.rv_news;
      RecyclerView rvNews = ViewBindings.findChildViewById(rootView, id);
      if (rvNews == null) {
        break missingId;
      }

      id = R.id.rv_news_latest;
      RecyclerView rvNewsLatest = ViewBindings.findChildViewById(rootView, id);
      if (rvNewsLatest == null) {
        break missingId;
      }

      id = R.id.scrollView;
      NestedScrollView scrollView = ViewBindings.findChildViewById(rootView, id);
      if (scrollView == null) {
        break missingId;
      }

      id = R.id.see_all;
      TextView seeAll = ViewBindings.findChildViewById(rootView, id);
      if (seeAll == null) {
        break missingId;
      }

      id = R.id.see_more;
      TextView seeMore = ViewBindings.findChildViewById(rootView, id);
      if (seeMore == null) {
        break missingId;
      }

      id = R.id.top;
      ConstraintLayout top = ViewBindings.findChildViewById(rootView, id);
      if (top == null) {
        break missingId;
      }

      id = R.id.username;
      TextView username = ViewBindings.findChildViewById(rootView, id);
      if (username == null) {
        break missingId;
      }

      return new ActivityMainBinding((ConstraintLayout) rootView, addChildButton,
          bottomNavigationMain, day, hallo, news, newsLatest, progressBar, rvNews, rvNewsLatest,
          scrollView, seeAll, seeMore, top, username);
    }
    String missingId = rootView.getResources().getResourceName(id);
    throw new NullPointerException("Missing required view with ID: ".concat(missingId));
  }
}
