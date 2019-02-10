package com.coopra.database;

import com.coopra.database.entities.User;
import com.google.gson.Gson;

import androidx.room.TypeConverter;

public class Converters {
    @TypeConverter
    public String fromUser(User user) {
        Gson gson = new Gson();
        String json = gson.toJson(user);
        return json;
    }

    @TypeConverter
    public User toUser(String userJson) {
        Gson gson = new Gson();
        User user = gson.fromJson(userJson, User.class);
        return user;
    }
}
