package com.fasta.stuntguard.news

import com.fasta.stuntguard.api.ApiConfig
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class NewsRepository {
    fun getHealthNews(
        onSuccess: (List<NewsItem>) -> Unit,
        onFailure: (String) -> Unit
    ) {
        ApiConfig.newsApiService.searchHealthNews("cancer", "health", "en", "b4d8bbbffb0e45af991afa94c8a42f81")
            .enqueue(object : Callback<NewsResponse> {
                override fun onResponse(call: Call<NewsResponse>, response: Response<NewsResponse>) {
                    if (response.isSuccessful) {
                        val articles = response.body()?.articles ?: emptyList()
                        val newsList = articles.mapNotNull { article ->
                            if (!article.title.isNullOrEmpty() && !article.urlToImage.isNullOrEmpty() && !article.url.isNullOrEmpty()) {
                                NewsItem(article.title, article.urlToImage, article.url)
                            } else {
                                null
                            }
                        }
                        onSuccess(newsList)
                    } else {
                        onFailure("Failed to fetch news")
                    }
                }

                override fun onFailure(call: Call<NewsResponse>, t: Throwable) {
                    onFailure(t.message ?: "Unknown error")
                }
            })
    }
}