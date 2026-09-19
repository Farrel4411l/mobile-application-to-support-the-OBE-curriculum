package com.example.projectfarrelmobileprogramming.data.model;

import com.google.gson.annotations.SerializedName;

public class ProfilLulusan {
    @SerializedName("id_pl")
    private int idPl;

    @SerializedName("id_prodi")
    private String idProdi;

    @SerializedName("deskripsi_pl")
    private String deskripsiPl;

    public int getIdPl() {
        return idPl;
    }

    public String getIdProdi() {
        return idProdi;
    }

    public String getDeskripsiPl() {
        return deskripsiPl;
    }
}
