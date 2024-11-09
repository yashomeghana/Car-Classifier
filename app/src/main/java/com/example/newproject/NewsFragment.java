package com.example.newproject;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import java.util.List;
import android.widget.Toast;

import com.google.android.material.progressindicator.LinearProgressIndicator;
import com.kwabenaberko.newsapilib.NewsApiClient;
import com.kwabenaberko.newsapilib.models.request.TopHeadlinesRequest;
import com.kwabenaberko.newsapilib.models.response.ArticleResponse;
import com.kwabenaberko.newsapilib.models.Article;

import java.util.ArrayList;


public class NewsFragment extends Fragment {

    RecyclerView recyclerView;
    List<Article> articleList = new ArrayList<>();
    NewsRecyclerAdapter adapter;
    LinearProgressIndicator progressIndicator;




    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_news, container, false);

        recyclerView = view.findViewById(R.id.news_recycle_view);
        progressIndicator = view.findViewById(R.id.progress_bar);

        setupRecyclerView();
        getNews();

        return view;
    }


    void setupRecyclerView(){
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new NewsRecyclerAdapter(articleList);
        recyclerView.setAdapter(adapter);
    }

    void changeInProgress(boolean show){
        if(show)
            progressIndicator.setVisibility(View.VISIBLE);
        else
            progressIndicator.setVisibility(View.INVISIBLE);
    }

    void getNews(){
        changeInProgress(true);
        NewsApiClient newsApiClient = new NewsApiClient("92905c06019d4fe8b05b3eba86105bb8");
        newsApiClient.getTopHeadlines(
                new TopHeadlinesRequest.Builder()
                        .language("en")
                        .build(),
                new NewsApiClient.ArticlesResponseCallback() {
                    @Override
                    public void onSuccess(ArticleResponse response) {
                        if(isAdded()) {
                            getActivity().runOnUiThread(() -> {
                                changeInProgress(false);
                                articleList = response.getArticles();
                                adapter.updateData(articleList);
                                adapter.notifyDataSetChanged();
                            });
                        }
                    }


                    @Override
                    public void onFailure(Throwable throwable) {
                        getActivity().runOnUiThread(() -> {
                            changeInProgress(false);
                            // Show an error message (e.g., a Toast or a Snackbar)
                            Toast.makeText(getContext(), "Failed to fetch news: " + throwable.getMessage(), Toast.LENGTH_LONG).show();
                        });
                        Log.i("GOT FAILURE", throwable.getMessage());
                    }

                }
        );
    }
}