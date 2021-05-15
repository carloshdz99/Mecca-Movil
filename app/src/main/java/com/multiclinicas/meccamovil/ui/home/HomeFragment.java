package com.multiclinicas.meccamovil.ui.home;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.multiclinicas.meccamovil.R;

import java.util.ArrayList;

public class HomeFragment extends Fragment {

    private static final String TAG = "Firestore";
    private HomeViewModel homeViewModel;

    //variable para textview donde se mostrara el nombre del usuario
    private TextView TvNombreUsuario;
    private FirebaseAuth mAuth;

    //declarando listas
    private ListView listViewCitas, listViewFechas;
    private ArrayList<String> arrayListCitas, arrayListFechas;
    private ArrayAdapter<String> arrayAdapterCitas, arrayAdapterFechas;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        homeViewModel =
                new ViewModelProvider(this).get(HomeViewModel.class);
        View root = inflater.inflate(R.layout.fragment_home, container, false);

        //instanciando firebase
        mAuth = FirebaseAuth.getInstance();
        //tomando el nombre del usuario en la vista
        TvNombreUsuario = root.findViewById(R.id.NombreUsuarioTv);

        //tomando la lista
        listViewCitas = root.findViewById(R.id.listCitas);
        arrayListCitas = new ArrayList<String>();
        listViewFechas = root.findViewById(R.id.listFechas);
        arrayListFechas = new ArrayList<String>();

        getCitas();

        return root;
    }

    //tomando las citas registradas
    public void getCitas(){
        FirebaseFirestore db= FirebaseFirestore.getInstance();
        db.collection("citas").get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()){
                            for (QueryDocumentSnapshot document: task.getResult()){
                                arrayListCitas.add(document.getData().get("nombre_paciente").toString());
                                arrayAdapterCitas = new ArrayAdapter<String>(getContext(), android.R.layout.simple_list_item_1, arrayListCitas);
                                listViewCitas.setAdapter(arrayAdapterCitas);
                                arrayListFechas.add(document.getData().get("fecha_cita").toString());
                                arrayAdapterFechas = new ArrayAdapter<String>(getContext(), android.R.layout.simple_list_item_1, arrayListFechas);
                                listViewFechas.setAdapter(arrayAdapterFechas);
                            }
                        }else {
                            Log.w(TAG, "error al llamar los datos ", task.getException());
                        }
                    }
                });
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