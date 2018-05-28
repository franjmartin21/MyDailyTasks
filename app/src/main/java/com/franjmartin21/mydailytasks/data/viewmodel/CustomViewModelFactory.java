package com.franjmartin21.mydailytasks.data.viewmodel;

import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;
import android.support.annotation.NonNull;

import com.franjmartin21.mydailytasks.data.repository.TaskRepository;

public class CustomViewModelFactory implements ViewModelProvider.Factory{

    private final TaskRepository repository;

    public CustomViewModelFactory(TaskRepository repository) {
        this.repository = repository;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if(modelClass.isAssignableFrom(TaskOccurrenceListViewModel.class))
            return (T) new TaskOccurrenceListViewModel(repository);
        else
            throw new IllegalArgumentException("ViewModel not found");
    }
}
