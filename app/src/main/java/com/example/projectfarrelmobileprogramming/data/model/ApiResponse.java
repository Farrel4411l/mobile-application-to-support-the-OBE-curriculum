package com.example.projectfarrelmobileprogramming.data.model;

import com.google.gson.annotations.SerializedName;
import java.util.List;

public class ApiResponse<T> {
    @SerializedName("data")
    private List<T> data;

    public List<T> getData() {
        return data;
    }
}
