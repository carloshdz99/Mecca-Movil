package com.multiclinicas.meccamovil.ui.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.multiclinicas.meccamovil.R;

public class HomeFragment extends Fragment {

    private HomeViewModel homeViewModel;

    //variable para textview donde se mostrara el nombre del usuario
    private TextView TvNombreUsuario;
    private FirebaseAuth mAuth;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        homeViewModel =
                new ViewModelProvider(this).get(HomeViewModel.class);
        View root = inflater.inflate(R.layout.fragment_home, container, false);

        //instanciando firebase
        mAuth = FirebaseAuth.getInstance();
        //tomando el nombre del usuario en la vista
        TvNombreUsuario = root.findViewById(R.id.NombreUsuarioTv);

        return root;
    }

    @Override
    public void onStart() {
        super.onStart();
        //pasando el nombre de usuario al fragment
        //tomando el usuario actual
        FirebaseUser currentUser = mAuth.getCurrentUser();
        TvNombreUsuario.setText(currentUser.getDisplayName());
    }
}