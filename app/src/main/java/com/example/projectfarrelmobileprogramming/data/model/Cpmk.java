package com.example.projectfarrelmobileprogramming.data.model;

import com.google.gson.annotations.SerializedName;

public class Cpmk {
    @SerializedName("id_cpmk")
    private String idCpmk;

    @SerializedName("kode_cpmk")
    private String kodeCpmk;

    @SerializedName("deskripsi_cpmk")
    private String deskripsiCpmk;

    @SerializedName("id_cpl")
    private String idCpl;

    @SerializedName("id_mk")
    private String idMk;

    public String getIdCpmk() {
        return idCpmk;
    }

    public String getKodeCpmk() {
        return kodeCpmk;
    }

    public String getDeskripsiCpmk() {
        return deskripsiCpmk;
    }

    public String getIdCpl() {
        return idCpl;
    }

    public String getIdMk() {
        return idMk;
    }
}
