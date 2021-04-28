package com.multiclinicas.meccamovil.ui.slideshow;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
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
import com.google.firebase.firestore.QuerySnapshot;
import com.multiclinicas.meccamovil.R;

import java.util.HashMap;
import java.util.Map;

public class SlideshowFragment extends Fragment {

    private SlideshowViewModel slideshowViewModel;

    private Button btnprueba;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        slideshowViewModel =
                new ViewModelProvider(this).get(SlideshowViewModel.class);
        View root = inflater.inflate(R.layout.fragment_slideshow, container, false);

        btnprueba = root.findViewById(R.id.btnprueba);
        btnprueba.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NuevaCita();
            }
        });
        return root;
    }

    public void NuevaCita(){
        Map<String, Object> cita = new HashMap<>();
        cita.put("nombre_paciente", "josue");
        cita.put("edad", 21);
        cita.put("enfermedad", "estudiar ing");

        //instancial de la base
        FirebaseFirestore db= FirebaseFirestore.getInstance();
        db.collection("citas")
                .document()
                .set(cita)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    private static final String TAG = "firestore" ;

                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()){
                            Toast.makeText(getContext(), "Se guardo el dato", Toast.LENGTH_LONG).show();
                        }else {
                            Log.w(TAG, task.getException());
                        }
                    }
                });
    }
}