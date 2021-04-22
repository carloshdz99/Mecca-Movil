package com.multiclinicas.meccamovil.ui.usuario;

import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.multiclinicas.meccamovil.R;

public class UsuarioFragment extends Fragment {

    private static final String TAG = "Actualizando";
    private UsuarioViewModel mViewModel;

    /*public static UsuarioFragment newInstance() {
        return new UsuarioFragment();
    }*/

    //txt mostrando el usuario logueado
    private TextView TvUsuarioEmail, TvUsuarioName;
    //tomando campos de texto del fragment
    private EditText edtNombreUsuario;
    //tomando botones del fragment
    private Button btnActualizarNombre;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mViewModel = new ViewModelProvider(this).get(UsuarioViewModel.class);
        View root = inflater.inflate(R.layout.usuario_fragment, container, false);

        //tomando el txt de correo
       /* TvUsuarioEmail = root.findViewById(R.id.UsuarioEmailTV);
        //tomando el txt de nombre de usuario
        TvUsuarioName = root.findViewById(R.id.UsuarioNameTv);
        //tomando el input de texto
        edtNombreUsuario = root.findViewById(R.id.edtNameUsuario);
        //tomando boton
        btnActualizarNombre = root.findViewById(R.id.btnActualizarNombre);
        btnActualizarNombre.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateName();
            }
        });*/
        return root;
    }

    @Override
    public void onStart() {
        super.onStart();
        //tomando el usuario en sesion actual
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null){
            TvUsuarioEmail.setText(user.getEmail());
            TvUsuarioName.setText(user.getDisplayName());
        }
    }

    //metodo para actualizar nombre de usuario
    private void updateName(){
        //tomando el texto ingresado en el edittext
        String nombre;
        nombre = edtNombreUsuario.getText().toString();

        //tomando el usuario en sesion actual
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                .setDisplayName(nombre)
                .build();

        user.updateProfile(profileUpdates)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()){
                            Toast.makeText(getContext(), "Perfil de usuario actualizado",Toast.LENGTH_LONG).show();
                        }else {
                            Log.w(TAG, "error: " + task.getException());
                        }
                    }
                });
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(UsuarioViewModel.class);
        // TODO: Use the ViewModel
    }

}