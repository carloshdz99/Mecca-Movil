package com.multiclinicas.meccamovil.ui.usuario;

import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.text.Layout;
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
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.EmailAuthProvider;
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
    private TextView TvUsuarioEmail;
    //tomando campos de texto del fragment
    private EditText edtNombreUsuario, edtContraseñaUsuario, edtContraseñaUsuarioAntigua;
    //tomando botones del fragment
    private Button btnActualizarNombre, btnActualizarContraseña, btnCancelUpdate, btnVsLtPass;

    //tomando el layout vertical oculto
    private View ltUpdatePassword;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mViewModel = new ViewModelProvider(this).get(UsuarioViewModel.class);
        View root = inflater.inflate(R.layout.usuario_fragment, container, false);

        //tomando el txt de correo
        TvUsuarioEmail = root.findViewById(R.id.UsuarioEmailTV);
        //tomando el input de texto
        edtNombreUsuario = root.findViewById(R.id.edtNameUsuario);
        //tomando el input de contraseña
        edtContraseñaUsuario = root.findViewById(R.id.edtContraseñaUsuarioPut);
        edtContraseñaUsuarioAntigua = root.findViewById(R.id.edtContraseñaUsuarioAntiguaPut);
        //tomando boton para actualizar nombre
        btnActualizarNombre = root.findViewById(R.id.btnActualizarNombre);
        btnActualizarNombre.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateName();
            }
        });

        //tomando boton para actualizar contraseña
        btnActualizarContraseña = root.findViewById(R.id.btnUpdatePassword);
        btnActualizarContraseña.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UpdatePassword();
            }
        });
        //tomando el layout que muestra los campos para actualizar contraseña
        ltUpdatePassword = root.findViewById(R.id.ltUpdatePass);
        //tomando boton para cancelar actualizacion y ocultar el layout
        btnCancelUpdate = root.findViewById(R.id.btnCancelUpdate);
        btnCancelUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ltUpdatePassword.setVisibility(View.INVISIBLE);
            }
        });
        //tomando el boton que pone visible el layout para contraseña
        btnVsLtPass = root.findViewById(R.id.btnVsltPass);
        btnVsLtPass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ltUpdatePassword.setVisibility(View.VISIBLE);
            }
        });
        return root;
    }

    @Override
    public void onStart() {
        super.onStart();
        //tomando el usuario en sesion actual
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null){
            TvUsuarioEmail.setText(user.getEmail());
            edtNombreUsuario.setText(user.getDisplayName());
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

    //metodo para actualizar contraseña
    private void UpdatePassword(){
        String contraseñanueva, contraseñaantigua;
        contraseñanueva = edtContraseñaUsuario.getText().toString();
        contraseñaantigua = edtContraseñaUsuarioAntigua.getText().toString();

        //tomando el usuario en sesion actual
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        //reatutenticando el usuario para que no de error al cambiar contraseña
        AuthCredential credential = EmailAuthProvider.getCredential(user.getEmail(), contraseñaantigua);
        user.reauthenticate(credential).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    Log.d(TAG, "Usuario reautenticado");
                }else {
                    Log.w(TAG, "error al reautenticar: " + task.getException());
                }
            }
        });

        //despues de reautenticar el usuario cambiamos la contraseña
        user.updatePassword(contraseñanueva).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()){
                    Log.d(TAG, "contraseña cambiada");
                }else {
                    Log.w(TAG, "error al actualizar contraseña: " + task.getException());
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