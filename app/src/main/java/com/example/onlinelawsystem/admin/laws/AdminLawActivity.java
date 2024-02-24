package com.example.onlinelawsystem.admin.laws;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentPagerAdapter;

import android.os.Bundle;

import com.example.onlinelawsystem.R;
import com.example.onlinelawsystem.admin.adapter.CourtAdapter;
import com.example.onlinelawsystem.admin.fragments.AddCourtFragment;
import com.example.onlinelawsystem.admin.fragments.AddLawFragment;
import com.example.onlinelawsystem.admin.fragments.AddLawyerFragment;
import com.example.onlinelawsystem.admin.fragments.ViewCourtFragment;
import com.example.onlinelawsystem.admin.fragments.ViewLawFragment;
import com.example.onlinelawsystem.databinding.ActivityAdminLawBinding;

public class AdminLawActivity extends AppCompatActivity {

    ActivityAdminLawBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAdminLawBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Connect the ViewPager and TabLayout
        binding.tabLayout.setupWithViewPager(binding.viewPager);

        // Create an instance of your custom FragmentPagerAdapter
        CourtAdapter adapter = new CourtAdapter(getSupportFragmentManager(), FragmentPagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        adapter.addFragment(new AddLawFragment(),"Add Law");
        adapter.addFragment(new ViewLawFragment(),"View Law");
        binding.viewPager.setAdapter(adapter);
    }
}