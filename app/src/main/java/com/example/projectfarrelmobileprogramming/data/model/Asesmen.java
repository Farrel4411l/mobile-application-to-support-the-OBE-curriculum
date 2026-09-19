package com.example.projectfarrelmobileprogramming.data.model;

import com.google.gson.annotations.SerializedName;
import java.io.Serializable;

public class Asesmen implements Serializable {
    @SerializedName("id_hasil_asesmen")
    private int idHasilAsesmen;

    @SerializedName("jenis_asesmen")
    private String jenisAsesmen;

    @SerializedName("nim")
    private String nim;

    @SerializedName("nama")
    private String nama;

    @SerializedName("bobot_cpl")
    private int bobotCpl;

    public int getIdHasilAsesmen() {
        return idHasilAsesmen;
    }

    public String getJenisAsesmen() {
        return jenisAsesmen;
    }

    public String getNim() {
        return nim;
    }

    public String getNama() {
        return nama;
    }

    public int getBobotCpl() {
        return bobotCpl;
    }
}
