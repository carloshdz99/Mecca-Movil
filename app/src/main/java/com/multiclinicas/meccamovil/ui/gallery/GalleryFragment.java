package com.multiclinicas.meccamovil.ui.gallery;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
//import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.multiclinicas.meccamovil.R;

import java.util.HashMap;
import java.util.Map;

public class GalleryFragment extends Fragment {

    private GalleryViewModel galleryViewModel;
    private EditText etNombrePaciente, etApellidoPaciente, etTelefonoPaciente, etPrescripcion;
    private Button btnRegistrarPaciente;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        galleryViewModel =
                new ViewModelProvider(this).get(GalleryViewModel.class);
        View root = inflater.inflate(R.layout.fragment_gallery, container, false);

        //final TextView textView = root.findViewById(R.id.text_gallery);
        /*galleryViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });*/

        etNombrePaciente = root.findViewById(R.id.edtNombrePaciente);
        etApellidoPaciente = root.findViewById(R.id.edtApellidoPaciente);
        etTelefonoPaciente = root.findViewById(R.id.edtTelefonoPaciente);
        etPrescripcion = root.findViewById(R.id.edtPrescripcion);
        btnRegistrarPaciente = root.findViewById(R.id.btnRegPaciente);

        btnRegistrarPaciente.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NuevoPaciente();
            }
        });

        return root;
    }

    public void NuevoPaciente(){

        //validando que no vayan campos vacios
        if(TextUtils.isEmpty(etNombrePaciente.getText().toString())){
            Toast.makeText(getContext(),"No se permiten campos vacios", Toast.LENGTH_LONG).show();
            return;
        }
        if(TextUtils.isEmpty(etApellidoPaciente.getText().toString())){
            Toast.makeText(getContext(),"No se permiten campos vacios", Toast.LENGTH_LONG).show();
            return;
        }
        if(TextUtils.isEmpty(etTelefonoPaciente.getText().toString())){
            Toast.makeText(getContext(),"No se permiten campos vacios", Toast.LENGTH_LONG).show();
            return;
        }
        if(TextUtils.isEmpty(etPrescripcion.getText().toString())){
            Toast.makeText(getContext(),"No se permiten campos vacios", Toast.LENGTH_LONG).show();
            return;
        }


        Map<String, Object> paciente = new HashMap<>();
        paciente.put("nombre_paciente", etNombrePaciente.getText().toString());
        paciente.put("apellido_paciente", etApellidoPaciente.getText().toString());
        paciente.put("telefono_paciente", etTelefonoPaciente.getText().toString());
        paciente.put("prescripcion", etPrescripcion.getText().toString());

        //instancia de la base
        FirebaseFirestore db= FirebaseFirestore.getInstance();
        db.collection("paciente")
                .document()
                .set(paciente)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    private static final String TAG = "firestore" ;

                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()){
                            Toast.makeText(getContext(), "Se guardo el paciente", Toast.LENGTH_LONG).show();
                            etNombrePaciente.setText("");
                            etApellidoPaciente.setText("");
                            etTelefonoPaciente.setText("");
                            etPrescripcion.setText("");
                        }else {
                            Log.w(TAG, task.getException());
                        }
                    }
                });
    }
}