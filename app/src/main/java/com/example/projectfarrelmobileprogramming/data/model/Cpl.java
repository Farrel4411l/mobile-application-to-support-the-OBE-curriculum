package com.example.projectfarrelmobileprogramming.data.model;

import com.google.gson.annotations.SerializedName;

public class Cpl {
    @SerializedName("id_cpl")
    private String idCpl;

    @SerializedName("id_prodi")
    private String idProdi;

    @SerializedName("deskripsi_cpl")
    private String deskripsiCpl;

    public String getIdCpl() {
        return idCpl;
    }

    public String getIdProdi() {
        return idProdi;
    }

    public String getDeskripsiCpl() {
        return deskripsiCpl;
    }
}
