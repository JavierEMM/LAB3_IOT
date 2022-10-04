package com.example.lab3_iot.models;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class ContadorViewModel extends ViewModel {
    private MutableLiveData<String> contador = new MutableLiveData<>();
    private Thread thread = null;


    public void iniciarContador(float segundos){
        thread = new Thread(new Runnable() {
            @Override
            public void run() {
                float contadorLocal = segundos;
                while (contadorLocal >= 0){
                    float parteDecimal = contadorLocal/60 % 1;
                    float parteEntera =  (contadorLocal/60 - parteDecimal);
                    int parteDecimal1 = (int) (60*parteDecimal);
                    int parteEntera1=(int)parteEntera;
                    String horas_mostrar = "";
                    if(parteDecimal1 < 10){
                        horas_mostrar =parteEntera1+":"+"0"+parteDecimal1;
                    }else{
                        horas_mostrar =parteEntera1+":"+parteDecimal1;
                    }
                    contador.postValue(horas_mostrar);
                    contadorLocal--;
                    try {
                        Thread.sleep(1000);
                    }catch (Exception e){
                        e.printStackTrace();
                        break;
                    }
                }
            }
        });
        thread.start();
    }

    public MutableLiveData<String> getContador() {
        return contador;
    }

    public void setContador(MutableLiveData<String> contador) {
        this.contador = contador;
    }
}
