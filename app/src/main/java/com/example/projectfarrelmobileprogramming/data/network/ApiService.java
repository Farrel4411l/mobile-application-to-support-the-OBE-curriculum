package com.example.projectfarrelmobileprogramming.data.network;

import com.example.projectfarrelmobileprogramming.data.model.ApiResponse;
import com.example.projectfarrelmobileprogramming.data.model.Prodi;
import com.example.projectfarrelmobileprogramming.data.model.Cpl;
import com.example.projectfarrelmobileprogramming.data.model.MataKuliah;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ApiService {

    @GET("prodi")
    Call<ApiResponse<Prodi>> getProdi(@Query("per_page") int perPage);

    @GET("profil-lulusan")
    Call<ApiResponse<com.example.projectfarrelmobileprogramming.data.model.ProfilLulusan>> getProfilLulusan(@Query("id_prodi") String idProdi, @Query("per_page") int perPage);

    @GET("cpl")
    Call<ApiResponse<Cpl>> getCpl(@Query("id_prodi") String idProdi, @Query("per_page") int perPage);

    @GET("cpmk")
    Call<ApiResponse<com.example.projectfarrelmobileprogramming.data.model.Cpmk>> getCpmk(@Query("id_prodi") String idProdi, @Query("per_page") int perPage);

    @GET("mata-kuliah")
    Call<ApiResponse<MataKuliah>> getMataKuliah(@Query("per_page") int perPage);

    @GET("bahan-kajian")
    Call<ApiResponse<com.example.projectfarrelmobileprogramming.data.model.BahanKajian>> getBahanKajian(@Query("per_page") int perPage);

    @GET("rtm")
    Call<ApiResponse<com.example.projectfarrelmobileprogramming.data.model.Rtm>> getRtm(@Query("per_page") int perPage);

    @GET("rps-hasil-asesmen")
    Call<ApiResponse<com.example.projectfarrelmobileprogramming.data.model.Asesmen>> getHasilAsesmen(@Query("per_page") int perPage);

    @GET("pemetaan-bk-mk")
    Call<ApiResponse<Object>> getPemetaanBkMk(@Query("per_page") int perPage);
    
    @GET("pemetaan-bk-cpl")
    Call<ApiResponse<Object>> getPemetaanBkCpl(@Query("per_page") int perPage);
    
    @GET("pemetaan-cpmk-mk")
    Call<ApiResponse<Object>> getPemetaanCpmkMk(@Query("per_page") int perPage);
    
    @GET("rps-pemetaan-cpl-subcpmk")
    Call<ApiResponse<Object>> getPemetaanCplSubCpmk(@Query("per_page") int perPage);

    @GET("rps")
    Call<ApiResponse<com.example.projectfarrelmobileprogramming.data.model.Rps>> getRps(@Query("per_page") int perPage);

    @GET("rps-subcpmk")
    Call<ApiResponse<Object>> getSubCpmk(@Query("id_rps") int idRps, @Query("per_page") int perPage);

    @GET("rps-rencana-asesmen")
    Call<ApiResponse<Object>> getRencanaAsesmen(@Query("id_subcpmk") int idSubCpmk, @Query("per_page") int perPage);

}
