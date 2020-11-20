package com.example.getkracking;

import android.content.Intent;
import android.os.Bundle;

import com.example.getkracking.fragments.WelcomeLoginFragment;
import com.example.getkracking.fragments.WelcomeRegisterFragment;
import com.google.android.material.tabs.TabLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import com.example.getkracking.ui.welcomeTabs.SectionsPagerAdapter;
import com.google.android.material.tabs.TabLayoutMediator;

public class WelcomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_welcome);

        SectionsPagerAdapter adapter = new SectionsPagerAdapter(getSupportFragmentManager(), getLifecycle());
        ViewPager2 viewPager = this.findViewById(R.id.vpwelcome);

        Intent intent = getIntent();
        String argument = intent.getStringExtra("routineId");

        adapter.addFragment(new WelcomeLoginFragment(argument));
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
    }
}