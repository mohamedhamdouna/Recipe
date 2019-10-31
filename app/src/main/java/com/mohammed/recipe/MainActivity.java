package com.mohammed.recipe;

import android.os.Bundle;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.mohammed.recipe.ui.addFragment;
import com.mohammed.recipe.ui.chifFragment;
import com.mohammed.recipe.ui.dashFragment;
import com.mohammed.recipe.ui.globelFragment;
import com.mohammed.recipe.ui.searchFragment;

import androidx.appcompat.app.AppCompatActivity;
import androidx.annotation.NonNull;

import android.view.MenuItem;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_dash:
                    getSupportFragmentManager().beginTransaction().replace(R.id.mainContainer, new dashFragment()).commit();
                    return true;
                case R.id.navigation_magnifier:
                    getSupportFragmentManager().beginTransaction().replace(R.id.mainContainer, new searchFragment()).commit();

                    return true;
                case R.id.navigation_add:
                    getSupportFragmentManager().beginTransaction().replace(R.id.mainContainer, new addFragment()).commit();

                    return true;
                case R.id.navigation_global:
                    getSupportFragmentManager().beginTransaction().replace(R.id.mainContainer, new globelFragment()).commit();

                    return true;
                case R.id.navigation_chif:
                    getSupportFragmentManager().beginTransaction().replace(R.id.mainContainer, new chifFragment()).commit();

                    return true;
            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        BottomNavigationView navView = findViewById(R.id.nav_view);
        navView.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        navView.setSelectedItemId(R.id.navigation_dash);
    }

}
