package com.example.digitacioncaja.Adaptador;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.example.digitacioncaja.DatosDestinatarioFragment;
import com.example.digitacioncaja.DatosRemitenteFragment;

public class PagerAdapater extends FragmentPagerAdapter {
    int tabs;
    public PagerAdapater(@NonNull FragmentManager fm, int behavior) {
        super(fm, behavior);
        this.tabs = behavior;
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0:
                return new DatosRemitenteFragment();
            case 1:
                return  new DatosDestinatarioFragment();
            default:
                return null;

        }
    }

    @Override
    public int getCount() {
        return this.tabs;
    }
}
