package com.example.mvvm;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.mvvm.databinding.FragmentMiEstadioBinding;

public class MiEstadioFragment extends Fragment {
    private FragmentMiEstadioBinding binding;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return (binding = FragmentMiEstadioBinding.inflate(inflater, container, false)).getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        final MiEstadioViewModel miEstadioViewModel = new ViewModelProvider(this).get(MiEstadioViewModel.class);

        binding.calcular.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                int filas = Integer.parseInt(binding.filas.getText().toString());
                int colus = Integer.parseInt(binding.colus.getText().toString());

                miEstadioViewModel.calcular(filas, colus);
            }
        });

        miEstadioViewModel.plazasMutableLiveData.observe(getViewLifecycleOwner(), new Observer<Integer>() {
            @Override
            public void onChanged(Integer pr) {
                binding.plazas.setText(""+pr);
            }
        });



        miEstadioViewModel.errorColumnas.observe(getViewLifecycleOwner(), new Observer<Integer>() {
            @Override
            public void onChanged(Integer columnas) {
                if (columnas != null) {
                    binding.colus.setError("Las columnas no pueden ser inferior a " + columnas);
                } else {
                    binding.colus.setError(null);
                }
            }
        });

        miEstadioViewModel.errorFilas.observe(getViewLifecycleOwner(), new Observer<Integer>() {
            @Override
            public void onChanged(Integer filas) {
                if (filas != null) {
                    binding.filas.setError("Las filas no pueden ser inferior a " + filas);
                } else {
                    binding.filas.setError(null);
                }
            }
        });
        miEstadioViewModel.calculando.observe(getViewLifecycleOwner(), new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean calculando) {
                if (calculando) {
                    binding.calculando.setVisibility(View.VISIBLE);
                    binding.plazas.setVisibility(View.GONE);
                } else {
                    binding.calculando.setVisibility(View.GONE);
                    binding.plazas.setVisibility(View.VISIBLE);
                }
            }
        });
    }
}