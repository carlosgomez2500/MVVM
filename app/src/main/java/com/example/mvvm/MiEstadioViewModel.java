package com.example.mvvm;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class MiEstadioViewModel extends AndroidViewModel {

    Executor executor;

    CalculadorEstadios calculadorEstadios;

    MutableLiveData<Integer> plazasMutableLiveData = new MutableLiveData<>();
    MutableLiveData<Integer> errorColumnas = new MutableLiveData<>();
    MutableLiveData<Integer> errorFilas = new MutableLiveData<>();
    MutableLiveData<Boolean> calculando = new MutableLiveData<>();


    public MiEstadioViewModel(@NonNull Application application) {
        super(application);

        executor = Executors.newSingleThreadExecutor();
        calculadorEstadios = new CalculadorEstadios();
    }

    public void calcular(final int filas, int colus) {

        final CalculadorEstadios.Solicitud solicitud = new CalculadorEstadios.Solicitud(filas, colus);

        executor.execute(new Runnable() {
            @Override
            public void run() {

                calculadorEstadios.calcular(solicitud, new CalculadorEstadios.Callback() {

                    @Override
                    public void cuandoEsteCalculadasLasPlazas(int p) {
                        plazasMutableLiveData.postValue(p);
                    }

                    @Override
                    public void cuandoHayaErrorDecolumnasInferiorAlMinimo(int columnas) {
                        errorColumnas.postValue(columnas);
                    }

                    @Override
                    public void cuandoHayaErrorDefilasInferiorAlMinimo(int filas) {
                        errorFilas.postValue(filas);
                    }


                    @Override
                    public void cuandoEmpieceElCalculo() {
                        calculando.postValue(true);
                    }

                    @Override
                    public void cuandoFinaliceElCalculo() {
                        calculando.postValue(false);
                    }

                });
            }
        });


    }
}