package com.example.projectfarrelmobileprogramming.data.model;

import com.google.gson.annotations.SerializedName;
import java.io.Serializable;

public class Prodi implements Serializable {
    @SerializedName("id_prodi")
    private String idProdi;

    @SerializedName("nama_prodi")
    private String namaProdi;

    @SerializedName("jenjang")
    private String jenjang;

    @SerializedName("id_fakultas")
    private int idFakultas;

    public String getIdProdi() {
        return idProdi;
    }

    public String getNamaProdi() {
        return namaProdi;
    }

    public String getJenjang() {
        return jenjang;
    }

    public int getIdFakultas() {
        return idFakultas;
    }
}
