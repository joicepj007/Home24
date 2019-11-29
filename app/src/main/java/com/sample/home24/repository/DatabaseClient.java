package com.sample.home24.repository;

import android.content.Context;

import androidx.room.Room;

import com.sample.home24.room.AppDatabase;

class DatabaseClient {
    static AppDatabase getDatabase(Context context) {
        return Room.databaseBuilder(context,
                AppDatabase.class, "ArticleDB").build();
    }
}
