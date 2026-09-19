package com.example.projectfarrelmobileprogramming.data.model;

import com.google.gson.annotations.SerializedName;
import java.io.Serializable;

public class Rps implements Serializable {
    @SerializedName("id_rps")
    private int idRps;

    @SerializedName("id_mk")
    private String idMk;

    @SerializedName("nama_dosen")
    private String namaDosen;

    @SerializedName("deskripsi_mk")
    private String deskripsiMk;

    @SerializedName("tanggal_penyusunan")
    private String tanggalPenyusunan;

    @SerializedName("status")
    private String status;

    public int getIdRps() {
        return idRps;
    }

    public String getIdMk() {
        return idMk;
    }

    public String getNamaDosen() {
        return namaDosen;
    }

    public String getDeskripsiMk() {
        return deskripsiMk;
    }

    public String getTanggalPenyusunan() {
        return tanggalPenyusunan;
    }

    public String getStatus() {
        return status;
    }
}
