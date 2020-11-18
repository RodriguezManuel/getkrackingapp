package com.example.getkracking.viewmodels;

import android.app.Application;
import android.content.res.Resources;
import android.os.CountDownTimer;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.example.getkracking.R;
import com.example.getkracking.entities.CycleVO;

import java.util.ArrayList;

public class RunRoutineViewModel extends AndroidViewModel {
    //Variables para la ejecucion de ejercicios
    ArrayList<CycleVO> cycles;
    int actualCycle, actualExercise;
    boolean timedExercise;
    MutableLiveData<String> sectionName = new MutableLiveData<>();
    MutableLiveData<String> exerciseName = new MutableLiveData<>();
    MutableLiveData<String> exerciseDesc = new MutableLiveData<>();
    MutableLiveData<String> exerciseType = new MutableLiveData<>();
    MutableLiveData<Boolean> finishedRoutine = new MutableLiveData<>(false);

    //Variables para el timer
    private CountDownTimer countDownTimer;
    private long timeLeftInMiliseconds; //en caso de que se corra con tiempo
    private boolean timerRunning;
    //Variables de botones
    MutableLiveData<String> timeLeftText = new MutableLiveData<>();
    MutableLiveData<String> buttonText = new MutableLiveData<>();

    public RunRoutineViewModel(@NonNull Application application) {
        super(application);
    }

    public MutableLiveData<Boolean> getFinishedRoutine() {
        return finishedRoutine;
    }

    public MutableLiveData<String> getSectionName() {
        return sectionName;
    }

    public MutableLiveData<String> getExerciseName() {
        return exerciseName;
    }

    public MutableLiveData<String> getExerciseDesc() {
        return exerciseDesc;
    }

    public MutableLiveData<String> getExerciseType() {
        return exerciseType;
    }

    public MutableLiveData<String> getTimeLeftText() {
        return timeLeftText;
    }

    public MutableLiveData<String> getButtonText() {
        return buttonText;
    }

    public void buttonSelected() {
        if (timedExercise)
            startStop();
        else
            runNextExercise();
    }

    public void setCycles(ArrayList<CycleVO> cycles) {
        this.cycles = cycles;
    }

    public void runNextExercise() {
        if (cycles == null || actualCycle == cycles.size()) {
            finishedRoutine.setValue(true);
            return;
        }
        sectionName.setValue(cycles.get(actualCycle).getName());
        exerciseName.setValue(cycles.get(actualCycle).getExercises().get(actualExercise).getName());
        exerciseDesc.setValue(cycles.get(actualCycle).getExercises().get(actualExercise).getDesc());
        if (cycles.get(actualCycle).getExercises().get(actualExercise).getDuration() > 0) {
            setCountDownTimer(cycles.get(actualCycle).getExercises().get(actualExercise).getDuration() * 1000);
            updateTimer();
            timedExercise = true;
            buttonText.setValue(getApplication().getResources().getString(R.string.start));
            exerciseType.setValue(getApplication().getResources().getString(R.string.time));
        } else {
            timeLeftText.setValue(String.valueOf(cycles.get(actualCycle).getExercises().get(actualExercise).getQuantity()));
            timedExercise = false;
            buttonText.setValue(getApplication().getResources().getString(R.string.continueText));
            exerciseType.setValue(getApplication().getResources().getString(R.string.repetitions));
        }

        actualExercise++;
        if (actualExercise == cycles.get(actualCycle).getExercises().size()) {
            actualExercise = 0;
            actualCycle++;
        }
    }

    //METODOS PARA EL TIMER
    public void startStop() {
        if (timerRunning) {
            stopTimer();
        } else {
            startTimer();
        }
    }

    public void setCountDownTimer(long exerciseDurationInMiliseconds) {
        timeLeftInMiliseconds = exerciseDurationInMiliseconds;
        timerRunning = false;
        countDownTimer = new CountDownTimer(exerciseDurationInMiliseconds, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                timeLeftInMiliseconds = millisUntilFinished; //actualizo el tiempo restante
                updateTimer();
            }

            @Override
            public void onFinish() {
                runNextExercise();
            }
        };
    }

    public void startTimer() {
        setCountDownTimer(timeLeftInMiliseconds); //retomo donde se dejo
        countDownTimer.start();
        buttonText.setValue(getApplication().getResources().getString(R.string.pause));
        timerRunning = true;
    }

    public void stopTimer() {
        countDownTimer.cancel();
        buttonText.setValue(getApplication().getResources().getString(R.string.continueText));
        timerRunning = false;
    }

    public void updateTimer() {
        int minutes = (int) timeLeftInMiliseconds / 60000;
        int seconds = (int) timeLeftInMiliseconds % 60000 / 1000;

        String timeLeftTextAux;
        timeLeftTextAux = "" + minutes + ":";
        if (seconds < 10)
            timeLeftTextAux += "0";

        timeLeftTextAux += "" + seconds;
        this.timeLeftText.setValue(timeLeftTextAux);
    }
}
