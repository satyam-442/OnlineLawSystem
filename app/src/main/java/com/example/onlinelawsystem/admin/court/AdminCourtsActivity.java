package com.example.onlinelawsystem.admin.court;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentPagerAdapter;

import android.os.Bundle;

import com.example.onlinelawsystem.R;
import com.example.onlinelawsystem.admin.adapter.CourtAdapter;
import com.example.onlinelawsystem.admin.fragments.AddCourtFragment;
import com.example.onlinelawsystem.admin.fragments.ViewCourtFragment;
import com.example.onlinelawsystem.databinding.ActivityAdminCourtsBinding;

public class AdminCourtsActivity extends AppCompatActivity {

    ActivityAdminCourtsBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAdminCourtsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Connect the ViewPager and TabLayout
        binding.tabLayout.setupWithViewPager(binding.viewPager);

        // Create an instance of your custom FragmentPagerAdapter
        CourtAdapter adapter = new CourtAdapter(getSupportFragmentManager(), FragmentPagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        adapter.addFragment(new AddCourtFragment(),"Add Court");
        adapter.addFragment(new ViewCourtFragment(),"View Court");
        binding.viewPager.setAdapter(adapter);

    }
}