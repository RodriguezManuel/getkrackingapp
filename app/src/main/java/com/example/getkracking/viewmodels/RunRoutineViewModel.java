package com.example.getkracking.viewmodels;

import android.os.CountDownTimer;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.getkracking.entities.CycleVO;

import java.util.ArrayList;

public class RunRoutineViewModel extends ViewModel {
    ArrayList<CycleVO> cycles = new ArrayList<>();
    //Variables para el timer
    private CountDownTimer countDownTimer;
    private long timeLeftInMiliseconds; //en caso de que se corra con tiempo
    private boolean timerRunning, finished;
    MutableLiveData<Integer> seconds;

    public MutableLiveData<Integer> getSeconds() {
        return seconds;
    }

    public void setCycles(ArrayList<CycleVO> cycles) {
        this.cycles = cycles;
    }

    public ArrayList<CycleVO> getCycles() {
        return cycles;
    }

    //METODOS PARA EL TIMER
    public void startStop(){
        if(timerRunning){
            stopTimer();
        }
        else{
            startTimer();
        }
    }

    public void setCountDownTimer(long exerciseDurationInMiliseconds) {
        timeLeftInMiliseconds = exerciseDurationInMiliseconds;
        countDownTimer = new CountDownTimer(exerciseDurationInMiliseconds, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                timeLeftInMiliseconds = millisUntilFinished; //actualizo el tiempo restante
                seconds.setValue((int)timeLeftInMiliseconds / 1000);
                updateTimer();
            }

            @Override
            public void onFinish() {
                finished = true;
            }
        };
    }

    public void startTimer(){
        countDownTimer.start(); //lo empieza de inmediato
//        countDownButton.setText("Pausar");
        timerRunning = true;
        finished = false;
    }

    public void stopTimer(){
        countDownTimer.cancel();
//        countDownButton.setText("Continuar");
        timerRunning = false;
    }

    public void updateTimer(){
        int minutes = (int) timeLeftInMiliseconds / 60000;
        int seconds = (int) timeLeftInMiliseconds % 60000 / 1000;

        String timeLeftText;

        timeLeftText = "" + minutes;
        timeLeftText += ":";
        if(seconds < 10){
            timeLeftText += "0";
        }
        timeLeftText += ""+seconds;
//        countDownText.setText(timeLeftText);
    }
}
