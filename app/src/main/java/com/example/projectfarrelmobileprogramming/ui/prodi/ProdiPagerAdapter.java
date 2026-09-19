package com.example.projectfarrelmobileprogramming.ui.prodi;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

/**
 * Kelas ProdiPagerAdapter
 * <p>
 * Ini adalah kelas adapter untuk mengatur fragment yang ditampilkan dalam ViewPager2 
 * pada ProdiDetailActivity.
 * Adapter ini mengelola dua fragment: ProfilLulusanFragment dan CplFragment.
 */
public class ProdiPagerAdapter extends FragmentStateAdapter {

    // Variabel untuk menyimpan ID Prodi yang akan dikirim ke fragment
    private final String idProdi;

    /**
     * Konstruktor adapter.
     * 
     * @param fragmentActivity Activity yang mewadahi ViewPager2
     * @param idProdi ID Program Studi untuk digunakan di dalam setiap Fragment (mengambil data API spesifik)
     */
    public ProdiPagerAdapter(@NonNull FragmentActivity fragmentActivity, String idProdi) {
        // Memanggil konstruktor dari superclass (FragmentStateAdapter)
        super(fragmentActivity);
        // Menyimpan ID prodi
        this.idProdi = idProdi;
    }

    /**
     * Method untuk membuat/memilih fragment berdasarkan posisi tab (0 atau 1).
     * 
     * @param position Urutan dari tab yang aktif (dimulai dari 0)
     * @return Fragment yang sesuai dengan posisi tab
     */
    @NonNull
    @Override
    public Fragment createFragment(int position) {
        // Jika posisi 0 (Tab Pertama)
        if (position == 0) {
            // Mengembalikan ProfilLulusanFragment, dengan membawa data idProdi
            return ProfilLulusanFragment.newInstance(idProdi);
        } else {
            // Jika posisi selain 0 (berarti 1, Tab Kedua)
            // Mengembalikan CplFragment, dengan membawa data idProdi
            return CplFragment.newInstance(idProdi);
        }
    }

    /**
     * Menentukan jumlah total tab / fragment yang ada di ViewPager2 ini.
     * 
     * @return Jumlah fragment (dalam kasus ini ada 2)
     */
    @Override
    public int getItemCount() {
        // Karena kita hanya memiliki Tab 'Profil Lulusan' dan Tab 'CPL', maka return 2
        return 2;
    }
}
