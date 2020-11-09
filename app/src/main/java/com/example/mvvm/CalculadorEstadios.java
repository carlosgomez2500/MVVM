package com.example.mvvm;

public class CalculadorEstadios {


    public static class Solicitud {
        public int columnas;
        public int filas;

        public Solicitud(int columnas, int filas) {
            this.columnas = columnas;
            this.filas = filas;
        }
    }

    interface Callback {
        void cuandoEsteCalculadasLasPlazas(int cuota);
        void cuandoHayaErrorDecolumnasInferiorAlMinimo(int columnas);
        void cuandoHayaErrorDefilasInferiorAlMinimo(int filas);
        void cuandoEmpieceElCalculo();
        void cuandoFinaliceElCalculo();
    }


    public void calcular(Solicitud solicitud, Callback callback) {
        int minColus = 0;
        int minFilas = 0;

        callback.cuandoEmpieceElCalculo();

        try {
            Thread.sleep(2500);  // long run operation
            minColus = 1;
            minFilas = 1;
        } catch (InterruptedException e) {}

        boolean error = false;
        if (solicitud.columnas < minColus) {
            callback.cuandoHayaErrorDecolumnasInferiorAlMinimo(minColus);
            error = true;
        }

        if (solicitud.filas < minFilas) {
            callback.cuandoHayaErrorDefilasInferiorAlMinimo(minFilas);
            error = true;
        }

        if(!error) {
            callback.cuandoEsteCalculadasLasPlazas(solicitud.columnas * solicitud.filas);
        }

        callback.cuandoFinaliceElCalculo();
    }
}
