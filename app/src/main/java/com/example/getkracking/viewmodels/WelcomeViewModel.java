package com.example.getkracking.viewmodels;

import androidx.lifecycle.ViewModel;

public class WelcomeViewModel extends ViewModel {
    String argument;

    public String getArgument() {
        return argument;
    }

    public void setArgument(String argument) {
        this.argument = argument;
    }
}
