package com.example.projectfarrelmobileprogramming.data.model;

import com.google.gson.annotations.SerializedName;
import java.io.Serializable;

public class BahanKajian implements Serializable {
    @SerializedName("id_bahan_kajian")
    private int idBahanKajian;

    @SerializedName("uraian_bahan_kajian")
    private String uraianBahanKajian;

    public int getIdBahanKajian() {
        return idBahanKajian;
    }

    public String getUraianBahanKajian() {
        return uraianBahanKajian;
    }
}
