package com.example.getkracking;

import android.content.Intent;
import android.os.Bundle;

import com.example.getkracking.fragments.WelcomeLoginFragment;
import com.example.getkracking.fragments.WelcomeRegisterFragment;
import com.example.getkracking.viewmodels.WelcomeViewModel;
import com.google.android.material.tabs.TabLayout;

import androidx.annotation.NonNull;
import androidx.annotation.StringRes;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import com.example.getkracking.ui.welcomeTabs.SectionsPagerAdapter;
import com.google.android.material.tabs.TabLayoutMediator;

public class WelcomeActivity extends AppCompatActivity {
    @StringRes
    private static final int[] TAB_TITLES = new int[]{R.string.tab_text_1, R.string.tab_text_2};

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

        SectionsPagerAdapter adapter = new SectionsPagerAdapter(getSupportFragmentManager(), getLifecycle());
        ViewPager2 viewPager = this.findViewById(R.id.vpwelcome);

        adapter.addFragment(new WelcomeLoginFragment());
        adapter.addFragment(new WelcomeRegisterFragment());

        viewPager.setAdapter(adapter);
        TabLayout tabLayout = this.findViewById(R.id.tabs);
        new TabLayoutMediator(tabLayout, viewPager, (tab, position) -> {
            switch (position){
                case 0:
                    tab.setText(R.string.tab_text_1);
                    break;
                case 1:
                    tab.setText(R.string.tab_text_2);
                    break;
                default:
                    tab.setText("ERROR");
            }
        }
        ).attach();

        Intent intent = getIntent();
        String argument = intent.getStringExtra("routineId");
    }
}