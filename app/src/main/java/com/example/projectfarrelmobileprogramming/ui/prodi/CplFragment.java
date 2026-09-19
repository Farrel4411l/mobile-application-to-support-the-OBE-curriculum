package com.example.projectfarrelmobileprogramming.ui.prodi;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.projectfarrelmobileprogramming.R;
import com.example.projectfarrelmobileprogramming.data.model.ApiResponse;
import com.example.projectfarrelmobileprogramming.data.model.Cpl;
import com.example.projectfarrelmobileprogramming.data.network.ApiClient;
import com.example.projectfarrelmobileprogramming.data.network.ApiService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Kelas CplFragment merupakan turunan dari Fragment yang merepresentasikan sebagian dari antarmuka pengguna
 * dalam sebuah Activity. Tujuan utamanya adalah untuk memuat dan menampilkan daftar data Capaian
 * Pembelajaran Lulusan (CPL) menggunakan RecyclerView dalam konteks Fragment, dengan berkomunikasi ke backend API.
 */
public class CplFragment extends Fragment {

    // Variabel untuk menyimpan parameter ID Program Studi (idProdi)
    private String idProdi;
    // Variabel komponen antarmuka pengguna: RecyclerView untuk menampilkan daftar list CPL
    private RecyclerView rv;
    // Variabel komponen antarmuka pengguna: ProgressBar untuk menunjukkan bahwa data sedang diambil
    private ProgressBar progressBar;
    // Variabel komponen antarmuka pengguna: TextView untuk menampilkan teks informasi apabila data kosong atau gagal diakses
    private TextView tvEmpty;
    // Adapter untuk RecyclerView yang mengatur pemetaan antara data CPL dan elemen view individual
    private CplAdapter adapter;

    /**
     * Metode factory pattern untuk membuat instance baru dari CplFragment.
     * Menggunakan pendekatan ini sangat disarankan untuk mengirimkan parameter (argumen) ke Fragment.
     *
     * @param idProdi ID dari program studi yang datanya perlu diambil.
     * @return Instance baru dari CplFragment dengan argumen yang di-bundle.
     */
    public static CplFragment newInstance(String idProdi) {
        // Membuat objek fragment baru
        CplFragment fragment = new CplFragment();
        // Menggunakan bundle untuk menyimpan parameter string
        Bundle args = new Bundle();
        // Menyisipkan nilai idProdi ke dalam bundle dengan kunci "ID_PRODI"
        args.putString("ID_PRODI", idProdi);
        // Memasukkan bundle ke argumen di fragment tersebut
        fragment.setArguments(args);
        // Mengembalikan objek fragment yang siap digunakan
        return fragment;
    }

    /**
     * Metode ini dipanggil saat Fragment dibuat (inisialisasi awal).
     * Di sini kita membaca nilai bundle (argumen) yang dikirim saat instansiasi.
     */
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Memeriksa apakah argumen yang dimasukkan (bundle) ada (tidak null)
        if (getArguments() != null) {
            // Mengambil string idProdi berdasarkan kunci "ID_PRODI"
            idProdi = getArguments().getString("ID_PRODI");
        }
    }

    /**
     * Metode ini memanggil saat sistem memanifestasikan antarmuka grafis (UI) Fragment untuk pertama kalinya.
     * Kita melakukan operasi "inflate" layout fragment dan menginisialisasi komponen view di dalamnya.
     */
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // Mengubah (inflate) file layout fragment_list.xml menjadi objek View Java
        View view = inflater.inflate(R.layout.fragment_list, container, false);
        
        // Memetakan ID pada komponen UI ke dalam variabel Java di kelas ini
        rv = view.findViewById(R.id.recyclerView);
        progressBar = view.findViewById(R.id.progressBar);
        tvEmpty = view.findViewById(R.id.tvEmpty);
        
        // Mengatur LayoutManager RecyclerView dengan bentuk list vertikal/linear
        rv.setLayoutManager(new LinearLayoutManager(getContext()));
        // Inisialisasi adapter baru untuk CPL
        adapter = new CplAdapter();
        // Memasangkan adapter dengan RecyclerView
        rv.setAdapter(adapter);

        // Memanggil fungsi untuk memuat data CPL dari API
        loadData();
        // Mengembalikan View yang utuh kepada activity pembungkus
        return view;
    }

    /**
     * Meminta data capaian pembelajaran dari web API (server) melalui Retrofit (ApiService).
     */
    private void loadData() {
        // Mengaktifkan (memunculkan) loading bar saat awal pemanggilan API
        progressBar.setVisibility(View.VISIBLE);
        // Menyembunyikan pesan kosong dan recycler view agar area tetap bersih
        tvEmpty.setVisibility(View.GONE);
        rv.setVisibility(View.GONE);

        // Membentuk layanan (interface) API dari instance Retrofit client
        ApiService apiService = ApiClient.getClient().create(ApiService.class);
        
        // Melakukan panggilan asynchronous ke server (via enqueue) mengambil data CPL (limit 100)
        apiService.getCpl(idProdi, 100).enqueue(new Callback<ApiResponse<Cpl>>() {
            
            /**
             * Dipanggil ketika request mencapai server dan server memberikan balasan (baik sukses/gagal).
             */
            @Override
            public void onResponse(Call<ApiResponse<Cpl>> call, Response<ApiResponse<Cpl>> response) {
                // Menghilangkan loading bar
                progressBar.setVisibility(View.GONE);
                
                // Jika permintaan HTTP status sukses (200-300 range) dan tidak ada data null
                if (response.isSuccessful() && response.body() != null && response.body().getData() != null) {
                    
                    // Jika data CPL dari server kosong array list-nya (tidak ada list), maka tampilkan teks 'tvEmpty'
                    if (response.body().getData().isEmpty()) {
                        tvEmpty.setVisibility(View.VISIBLE);
                        tvEmpty.setText("Tidak ada CPL untuk prodi ini.");
                    } else {
                        // Jika data tersedia, munculkan RecyclerView
                        rv.setVisibility(View.VISIBLE);
                        // Lempar data ke adapter agar adapter dapat memutarnya di layar UI
                        adapter.setCplList(response.body().getData());
                    }
                } else {
                    // Beri tahu user jika kondisi HTTP request error
                    tvEmpty.setVisibility(View.VISIBLE);
                    tvEmpty.setText("Gagal mengambil data CPL.");
                }
            }

            /**
             * Dipanggil saat proses permintaan gagal karena kendala teknis dari sisi jaringan seperti timeout atau diskonek.
             */
            @Override
            public void onFailure(Call<ApiResponse<Cpl>> call, Throwable t) {
                // Menghilangkan loading bar
                progressBar.setVisibility(View.GONE);
                // Memunculkan pesan kegagalan ke elemen UI
                tvEmpty.setVisibility(View.VISIBLE);
                tvEmpty.setText("Koneksi Error: " + t.getMessage());
            }
        });
    }
}
