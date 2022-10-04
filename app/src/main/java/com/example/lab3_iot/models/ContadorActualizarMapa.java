package com.example.lab3_iot.models;


import android.util.Log;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class ContadorActualizarMapa extends ViewModel {
    private MutableLiveData<Integer> contador = new MutableLiveData<>();
    private Thread thread = null;


    public void iniciarContador(Integer segundos){
        thread = new Thread(new Runnable() {
            @Override
            public void run() {
                Integer contadorLocal = segundos;
                while (contadorLocal >= 0){
                    contador.postValue(contadorLocal);
                    contadorLocal = contadorLocal - 5;
                    try {
                        Thread.sleep(20000);
                    }catch (Exception e){
                        e.printStackTrace();
                        break;
                    }
                }
            }
        });
        thread.start();
    }

    public MutableLiveData<Integer> getContador() {
        return contador;
    }

    public void setContador(MutableLiveData<Integer> contador) {
        this.contador = contador;
    }
}
