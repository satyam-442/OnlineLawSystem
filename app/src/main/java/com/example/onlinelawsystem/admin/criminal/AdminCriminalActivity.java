package com.example.onlinelawsystem.admin.criminal;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentPagerAdapter;

import android.os.Bundle;

import com.example.onlinelawsystem.R;
import com.example.onlinelawsystem.admin.adapter.CourtAdapter;
import com.example.onlinelawsystem.admin.fragments.AddCourtFragment;
import com.example.onlinelawsystem.admin.fragments.AddCriminalFragment;
import com.example.onlinelawsystem.admin.fragments.ViewCourtFragment;
import com.example.onlinelawsystem.admin.fragments.ViewCriminalFragment;
import com.example.onlinelawsystem.databinding.ActivityAdminCriminalBinding;

public class AdminCriminalActivity extends AppCompatActivity {

    ActivityAdminCriminalBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAdminCriminalBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Connect the ViewPager and TabLayout
        binding.tabLayout.setupWithViewPager(binding.viewPager);

        // Create an instance of your custom FragmentPagerAdapter
        CourtAdapter adapter = new CourtAdapter(getSupportFragmentManager(), FragmentPagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        adapter.addFragment(new AddCriminalFragment(),"Add Criminal");
        adapter.addFragment(new ViewCriminalFragment(),"View Criminal");
        binding.viewPager.setAdapter(adapter);
    }
}