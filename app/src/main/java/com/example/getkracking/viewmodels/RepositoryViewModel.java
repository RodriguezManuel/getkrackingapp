package com.example.getkracking.viewmodels;

import androidx.lifecycle.ViewModel;

public class RepositoryViewModel<T> extends ViewModel {
    protected T repository;

    public RepositoryViewModel(T repository) {
        this.repository = repository;
    }

}
