package com.example.aplicacionrobo.ui.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.aplicacionrobo.R;
import com.example.aplicacionrobo.databinding.FragmentHomeBinding;
import com.example.aplicacionrobo.ui.dashboard.DashboardFragment;
import com.example.aplicacionrobo.ui.notifications.NotificationsFragment;
import com.example.aplicacionrobo.ui.slideshow.SlideshowFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class HomeFragment extends Fragment {

    private FragmentHomeBinding binding;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        // Initialize BottomNavigationView
        BottomNavigationView bottomNavigationView = root.findViewById(R.id.bottom_navigation);

        // Set up navigation logic
        bottomNavigationView.setOnItemSelectedListener(item -> {
            Fragment selectedFragment = null;

            if (item.getItemId() == R.id.nav_home){
                selectedFragment = new NotificationsFragment(); // Define este Fragment
            }else if(item.getItemId() == R.id.nav_home){
                selectedFragment = new DashboardFragment(); // Define este Fragment
            }else if(item.getItemId() == R.id.nav_profile){
                selectedFragment = new SlideshowFragment(); // Define este Fragment
            }
            if (selectedFragment != null) {
                getChildFragmentManager().beginTransaction()
                        .replace(R.id.fragment_container, selectedFragment)
                        .commit();
            }
            return true;
        });

        // Set default selection
        bottomNavigationView.setSelectedItemId(R.id.nav_home);

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}