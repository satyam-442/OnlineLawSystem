package com.example.onlinelawsystem.admin.lawyer;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentPagerAdapter;

import android.os.Bundle;

import com.example.onlinelawsystem.R;
import com.example.onlinelawsystem.admin.adapter.CourtAdapter;
import com.example.onlinelawsystem.admin.adapter.LawyerAdapter;
import com.example.onlinelawsystem.admin.fragments.AddCourtFragment;
import com.example.onlinelawsystem.admin.fragments.AddLawyerFragment;
import com.example.onlinelawsystem.admin.fragments.ViewCourtFragment;
import com.example.onlinelawsystem.admin.fragments.ViewLawyerFragment;
import com.example.onlinelawsystem.databinding.ActivityAdminLawyerBinding;

public class AdminLawyerActivity extends AppCompatActivity {

    ActivityAdminLawyerBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityAdminLawyerBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Connect the ViewPager and TabLayout
        binding.tabLayout.setupWithViewPager(binding.viewPager);

        // Create an instance of your custom FragmentPagerAdapter
        LawyerAdapter adapter = new LawyerAdapter(getSupportFragmentManager(), FragmentPagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        adapter.addFragment(new AddLawyerFragment(),"Add Lawyer");
        adapter.addFragment(new ViewLawyerFragment(),"View Lawyer");
        binding.viewPager.setAdapter(adapter);

    }
}