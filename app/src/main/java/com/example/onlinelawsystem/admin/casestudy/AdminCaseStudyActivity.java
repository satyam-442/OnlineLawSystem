package com.example.onlinelawsystem.admin.casestudy;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentPagerAdapter;

import android.os.Bundle;

import com.example.onlinelawsystem.R;
import com.example.onlinelawsystem.admin.adapter.CourtAdapter;
import com.example.onlinelawsystem.admin.fragments.AddCaseStudyFragment;
import com.example.onlinelawsystem.admin.fragments.AddCourtFragment;
import com.example.onlinelawsystem.admin.fragments.ViewCaseStudyFragment;
import com.example.onlinelawsystem.admin.fragments.ViewCourtFragment;
import com.example.onlinelawsystem.databinding.ActivityAdminCaseStudyBinding;

public class AdminCaseStudyActivity extends AppCompatActivity {

    ActivityAdminCaseStudyBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAdminCaseStudyBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Connect the ViewPager and TabLayout
        binding.tabLayout.setupWithViewPager(binding.viewPager);

        // Create an instance of your custom FragmentPagerAdapter
        CourtAdapter adapter = new CourtAdapter(getSupportFragmentManager(), FragmentPagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        adapter.addFragment(new AddCaseStudyFragment(),"Add Case Study");
        adapter.addFragment(new ViewCaseStudyFragment(),"View Case Study");
        binding.viewPager.setAdapter(adapter);
    }
}