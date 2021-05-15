package com.multiclinicas.meccamovil.ui.slideshow;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
//import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.google.android.gms.tasks.OnCanceledListener;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.multiclinicas.meccamovil.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class SlideshowFragment extends Fragment{

    private static final String TAG = "Firestore";
    private SlideshowViewModel slideshowViewModel;

    private Button btnprueba;

    //tomando elementos de la pantalla
    private EditText edtFecha, edtPrescripcionMedica, edtDiagnosticoCita;
    private String NombrePaciente;

    //declarando lista del fragment
    private ListView listViewPacientes;
    private ArrayList<String> arrayListPacientes;
    private ArrayAdapter<String> arrayAdapterPacientes;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        slideshowViewModel =
                new ViewModelProvider(this).get(SlideshowViewModel.class);
        View root = inflater.inflate(R.layout.fragment_slideshow, container, false);

        btnprueba = root.findViewById(R.id.btnRegistrarCita);
        btnprueba.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NuevaCita();
            }
        });

        //tomando elementos
        edtFecha = root.findViewById(R.id.edtFecha);
        edtPrescripcionMedica = root.findViewById(R.id.edtPrescripcionMedica);
        edtDiagnosticoCita = root.findViewById(R.id.edtDiagnosticoCita);

        //tomando la lista
        arrayListPacientes = new ArrayList<String>();

        //tomando la listview
        listViewPacientes = root.findViewById(R.id.listPacientes);

        //ejecutando evento
        getPacientes();

        //pasando valores del listview
        listViewPacientes.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                FirebaseFirestore db= FirebaseFirestore.getInstance();
                String paciente= parent.getItemAtPosition(position).toString();
                NombrePaciente = paciente;
                db.collection("paciente").whereEqualTo("nombre_paciente",paciente)
                        .get()
                        .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                if (task.isSuccessful()){
                                    for(QueryDocumentSnapshot document: task.getResult()){
                                        Log.d(TAG, document.getData().get("prescripcion").toString());
                                        edtPrescripcionMedica.setText(document.getData().get("prescripcion").toString());
                                    }
                                }
                            }
                        });
            }
        });

        return root;
    }


    //metodo para tomar pacientes
    public void getPacientes(){
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("paciente").get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()){
                            for (QueryDocumentSnapshot document: task.getResult()){
                                Log.d(TAG, document.getId() + " => " + document.getData());
                                //pasando datos a la lista creada
                                arrayListPacientes.add(document.getData().get("nombre_paciente").toString());
                                arrayAdapterPacientes = new ArrayAdapter<String>(getContext(), android.R.layout.simple_list_item_1, arrayListPacientes);
                                listViewPacientes.setAdapter(arrayAdapterPacientes);
                            }
                        }else {
                            Log.w(TAG, "error al llamar los datos ", task.getException());
                        }
                    }
                });
    }

    public void NuevaCita(){
        //tomando el texto registrado en diagnostico
        String diagnostico = edtDiagnosticoCita.getText().toString();
        String fecha = edtFecha.getText().toString();

        Map<String, Object> cita = new HashMap<>();
        cita.put("nombre_paciente", NombrePaciente);
        cita.put("prescripcion", edtPrescripcionMedica.getText().toString());
        cita.put("diagnostico", diagnostico);
        cita.put("fecha_cita",fecha);

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