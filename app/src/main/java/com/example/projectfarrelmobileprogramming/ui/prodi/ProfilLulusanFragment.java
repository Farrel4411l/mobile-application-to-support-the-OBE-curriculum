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
import com.example.projectfarrelmobileprogramming.data.model.ProfilLulusan;
import com.example.projectfarrelmobileprogramming.data.network.ApiClient;
import com.example.projectfarrelmobileprogramming.data.network.ApiService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Kelas ProfilLulusanFragment
 * <p>
 * Fragment ini berfungsi untuk menampilkan daftar profil lulusan berdasarkan ID Prodi tertentu.
 * Digunakan sebagai salah satu tab yang ada di dalam ProdiDetailActivity.
 * Terhubung ke backend/API melalui Retrofit untuk mengambil data ProfilLulusan.
 */
public class ProfilLulusanFragment extends Fragment {

    // Menyimpan ID Prodi untuk dijadikan parameter dalam pencarian data
    private String idProdi;
    
    // UI Komponen: Daftar bergulir untuk menampilkan data lulusan
    private RecyclerView rv;
    // UI Komponen: Lingkaran pemuatan (loading bar)
    private ProgressBar progressBar;
    // UI Komponen: Teks yang muncul bila data kosong atau gagal diambil
    private TextView tvEmpty;
    
    // Adapter untuk mengatur tampilan setiap item profil lulusan di dalam RecyclerView
    private PlAdapter adapter;

    /**
     * Method factory untuk membuat instance baru dari fragment ini beserta mengirimkan argument 'idProdi'.
     * Ini pola standar agar fragment bisa menerima data pada saat dibuat.
     * 
     * @param idProdi ID Prodi yang ingin dicari profil lulusannya
     * @return ProfilLulusanFragment
     */
    public static ProfilLulusanFragment newInstance(String idProdi) {
        ProfilLulusanFragment fragment = new ProfilLulusanFragment();
        // Bundle digunakan untuk membungkus data parameter
        Bundle args = new Bundle();
        args.putString("ID_PRODI", idProdi);
        fragment.setArguments(args);
        return fragment;
    }

    /**
     * Dipanggil pada saat inisialisasi awal fragment.
     */
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Membaca argumen yang dikirim melalui newInstance
        if (getArguments() != null) {
            idProdi = getArguments().getString("ID_PRODI");
        }
    }

    /**
     * Dipanggil untuk membuat / inflate layout UI dari Fragment.
     */
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // Inflate file XML fragment_list ke dalam View
        View view = inflater.inflate(R.layout.fragment_list, container, false);
        
        // Menghubungkan variabel Java dengan ID yang ada di layout XML
        rv = view.findViewById(R.id.recyclerView);
        progressBar = view.findViewById(R.id.progressBar);
        tvEmpty = view.findViewById(R.id.tvEmpty);
        
        // Mempersiapkan RecyclerView dengan LayoutManager (membuat data tampil secara vertikal/list linear)
        rv.setLayoutManager(new LinearLayoutManager(getContext()));
        
        // Menginisialisasi adapter PlAdapter
        adapter = new PlAdapter();
        // Memasang adapter ke RecyclerView
        rv.setAdapter(adapter);

        // Memanggil fungsi untuk mulai mengunduh data
        loadData();
        return view;
    }

    /**
     * Method untuk melakukan request jaringan melalui Retrofit dan API Service
     * guna mendapatkan data Profil Lulusan.
     */
    private void loadData() {
        // Tampilkan loading saat proses dimulai
        progressBar.setVisibility(View.VISIBLE);
        // Sembunyikan status kosong / pesan error sementara data sedang dimuat
        tvEmpty.setVisibility(View.GONE);
        // Sembunyikan daftar RecyclerView sampai ada kepastian datanya masuk
        rv.setVisibility(View.GONE);

        // Membuat instance dari antarmuka API menggunakan Retrofit yang telah dikonfigurasi
        ApiService apiService = ApiClient.getClient().create(ApiService.class);
        
        // Memanggil endpoint getProfilLulusan, mem-passing parameter ID Prodi dan jumlah maksimal (100)
        // enqueue() digunakan untuk menjalankan request secara Asinkron agar tidak memblokir UI Thread (Main Thread)
        apiService.getProfilLulusan(idProdi, 100).enqueue(new Callback<ApiResponse<ProfilLulusan>>() {
            
            // Callback jika server memberikan respon (bisa respon sukses atau respon gagal ber-code misal 404/500)
            @Override
            public void onResponse(Call<ApiResponse<ProfilLulusan>> call, Response<ApiResponse<ProfilLulusan>> response) {
                // Sembunyikan loading karena sudah ada respon
                progressBar.setVisibility(View.GONE);
                
                // Cek apakah HTTP response code-nya adalah 2xx (sukses) dan datanya tidak null
                if (response.isSuccessful() && response.body() != null && response.body().getData() != null) {
                    
                    // Jika daftar data yang diterima bernilai kosong, tampilkan teks empty state
                    if (response.body().getData().isEmpty()) {
                        tvEmpty.setVisibility(View.VISIBLE);
                        tvEmpty.setText("Tidak ada Profil Lulusan untuk prodi ini.");
                    } else {
                        // Jika ada datanya, tampilkan RecyclerView, lalu pasang daftar datanya ke adapter
                        rv.setVisibility(View.VISIBLE);
                        adapter.setPlList(response.body().getData());
                    }
                } else {
                    // Jika code bukan 2xx, atau data balikan ternyata null, tampilkan error
                    tvEmpty.setVisibility(View.VISIBLE);
                    tvEmpty.setText("Gagal mengambil data Profil Lulusan.");
                }
            }

            // Callback jika tidak ada balasan dari server (contoh: internet putus, timeout)
            @Override
            public void onFailure(Call<ApiResponse<ProfilLulusan>> call, Throwable t) {
                // Sembunyikan loading
                progressBar.setVisibility(View.GONE);
                // Tampilkan pesan error kepada user
                tvEmpty.setVisibility(View.VISIBLE);
                tvEmpty.setText("Koneksi Error: " + t.getMessage());
            }
        });
    }
}
