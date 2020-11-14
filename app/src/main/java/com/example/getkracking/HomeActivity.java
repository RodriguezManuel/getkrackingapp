package com.example.getkracking;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.content.res.AppCompatResources;
import androidx.appcompat.widget.Toolbar;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.Toast;

import com.google.android.material.bottomappbar.BottomAppBar;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Objects;
import java.util.Stack;

public class HomeActivity extends AppCompatActivity {
    BottomAppBar bottomAppBar;
    BottomNavigationView bottomNavigationView;
    FloatingActionButton homeButton;
    Stack<Integer> iconSelectedStack;
    Deque<Integer> dequeSelection = new ArrayDeque<>(5);      //double ended queue

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        bottomAppBar = findViewById(R.id.bottomAppBar);
        bottomNavigationView = findViewById(R.id.bottomNavigationView);

        Toolbar toolbar = findViewById(R.id.homeTopBar);
        setSupportActionBar(toolbar);

        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(R.id.socialFragment, R.id.searchFragment,
                R.id.homeFragment, R.id.statsFragment, R.id.perfilFragment)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(bottomNavigationView, navController);

        homeButton = findViewById(R.id.fabBottomAppBar);
        homeButton.setBackgroundTintList(AppCompatResources.getColorStateList(this, R.color.primary));
        iconSelectedStack = new Stack<>();

        bottomNavigationView.setBackground(null);   //saca la sombra que se previsualiza en la bottom bar
        bottomNavigationView.getMenu().getItem(2).setEnabled(false);    //desactiva el boton placeholder del medio que ayuda en la alineacion

        goToHome( findViewById(android.R.id.content).getRootView() );   // home es el defaultfragment
        bottomNavigationView.setOnNavigationItemSelectedListener(item -> {
            int id = item.getItemId();
            switchFabHomeColor(false); //por si estaba prendido

            if(!dequeSelection.isEmpty() && id == dequeSelection.peek())
                return false;     // me encuentro en el mismo fragmento

            // no uso switch por un tema de retrocompatibilidad
            if (id == R.id.menu_social) {
                showSelectedFragment(R.id.socialFragment, R.id.menu_social);
            } else if (id == R.id.menu_search) {
                showSelectedFragment(R.id.searchFragment, R.id.menu_search);
            } else if (id == R.id.menu_stats) {
                showSelectedFragment(R.id.statsFragment, R.id.menu_stats);
            } else if (id == R.id.menu_perfil) {
                showSelectedFragment(R.id.perfilFragment, R.id.menu_perfil);
            }
            return true;
        });
    }

    public void goToHome(View view) {
        if(!iconSelectedStack.isEmpty() && iconSelectedStack.peek() == R.id.homeFragment)
            //me encuentro en este fragment
            return;

        showSelectedFragment(R.id.homeFragment, R.id.menu_placeholder);
        bottomNavigationView.getMenu().getItem(2).setChecked(true); //desactivo todos los otros activando el placeholder
        switchFabHomeColor(true);
    }

    private void showSelectedFragment(int id, int menuId) {
        NavController controller = Navigation.findNavController(this, R.id.nav_host_fragment);
        boolean poppedFragment = controller.popBackStack(id, false);

        if (!poppedFragment) {
            controller.navigate(id);    //lo creo por que no existe
        }

        if(dequeSelection.contains(menuId)){
            //intente usar el poppedFragment pero anda mal
            int aux;
            for (int i = dequeSelection.size(); i > 0 ; i--) {
                aux = dequeSelection.pop();
                if (aux == menuId)
                    break;
            }
        }

        dequeSelection.push(menuId);    //nueva instancia

            /*
            VER SI SE PUEDE ARREGLAR LA SITUACION EN LA QUE TENGO
            A-> B-> C-> D
            voy a A y me queda
            A
            estaria bueno que quede B-> C-> D-> A
             */

    }

    @Override
    public void onBackPressed() {
        //saco el fragment actual
        dequeSelection.pop();
        if (!dequeSelection.isEmpty()) {
            int fragmentid = dequeSelection.peek();
            int iconIndex = 2;  //default home
            if (fragmentid == R.id.menu_social) {
                iconIndex = 0;
            } else if (fragmentid == R.id.menu_search) {
                iconIndex = 1;
            } else if (fragmentid == R.id.menu_stats) {
                iconIndex = 3;
            } else if (fragmentid == R.id.menu_perfil) {
                iconIndex = 4;
            }
            switchFabHomeColor(fragmentid == R.id.menu_placeholder);
            bottomNavigationView.getMenu().getItem(iconIndex).setChecked(true);
            super.onBackPressed();
        }else
            finish();

    }

    private void switchFabHomeColor(boolean flag) {
        if (flag)
            homeButton.setBackgroundTintList(AppCompatResources.getColorStateList(this, R.color.orange));
        else
            homeButton.setBackgroundTintList(AppCompatResources.getColorStateList(this, R.color.primary));
    }
}