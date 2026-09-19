package com.example.projectfarrelmobileprogramming.data.model;

import com.google.gson.annotations.SerializedName;
import java.io.Serializable;

public class Rtm implements Serializable {
    @SerializedName("id_rtm")
    private int idRtm;

    @SerializedName("judul_tugas")
    private String judulTugas;

    @SerializedName("deskripsi_tugas")
    private String deskripsiTugas;

    @SerializedName("waktu_pengerjaan")
    private String waktuPengerjaan;

    @SerializedName("bentuk_tugas")
    private String bentukTugas;

    public int getIdRtm() {
        return idRtm;
    }

    public String getJudulTugas() {
        return judulTugas;
    }

    public String getDeskripsiTugas() {
        return deskripsiTugas;
    }

    public String getWaktuPengerjaan() {
        return waktuPengerjaan;
    }

    public String getBentukTugas() {
        return bentukTugas;
    }
}
