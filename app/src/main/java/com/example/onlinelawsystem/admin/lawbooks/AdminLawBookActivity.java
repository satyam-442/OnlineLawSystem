package com.example.onlinelawsystem.admin.lawbooks;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentPagerAdapter;

import android.os.Bundle;

import com.example.onlinelawsystem.R;
import com.example.onlinelawsystem.admin.adapter.CourtAdapter;
import com.example.onlinelawsystem.admin.fragments.AddCaseStudyFragment;
import com.example.onlinelawsystem.admin.fragments.AddLawBooksFragment;
import com.example.onlinelawsystem.admin.fragments.ViewCaseStudyFragment;
import com.example.onlinelawsystem.admin.fragments.ViewLawBooksFragment;
import com.example.onlinelawsystem.databinding.ActivityAdminLawBookBinding;

public class AdminLawBookActivity extends AppCompatActivity {

    ActivityAdminLawBookBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAdminLawBookBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Connect the ViewPager and TabLayout
        binding.tabLayout.setupWithViewPager(binding.viewPager);

        // Create an instance of your custom FragmentPagerAdapter
        CourtAdapter adapter = new CourtAdapter(getSupportFragmentManager(), FragmentPagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        adapter.addFragment(new AddLawBooksFragment(),"Add Law Book");
        adapter.addFragment(new ViewLawBooksFragment(),"View Law Book");
        binding.viewPager.setAdapter(adapter);
    }
}