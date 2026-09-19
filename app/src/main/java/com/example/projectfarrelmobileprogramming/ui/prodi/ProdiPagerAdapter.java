package com.example.projectfarrelmobileprogramming.ui.prodi;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

public class ProdiPagerAdapter extends FragmentStateAdapter {

    private final String idProdi;

    public ProdiPagerAdapter(@NonNull FragmentActivity fragmentActivity, String idProdi) {
        super(fragmentActivity);
        this.idProdi = idProdi;
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        if (position == 0) {
            return ProfilLulusanFragment.newInstance(idProdi);
        } else {
            return CplFragment.newInstance(idProdi);
        }
    }

    @Override
    public int getItemCount() {
        return 2;
    }
}
