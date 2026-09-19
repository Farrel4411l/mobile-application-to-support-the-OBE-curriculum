package com.example.projectfarrelmobileprogramming.data.model;

import com.google.gson.annotations.SerializedName;
import java.io.Serializable;

public class MataKuliah implements Serializable {
    @SerializedName("id_mk")
    private String idMk;

    @SerializedName("nama_mk")
    private String namaMk;

    @SerializedName("sks")
    private int sks;

    @SerializedName("semester")
    private String semester;

    public String getIdMk() {
        return idMk;
    }

    public String getNamaMk() {
        return namaMk;
    }

    public int getSks() {
        return sks;
    }

    public String getSemester() {
        return semester;
    }
}
