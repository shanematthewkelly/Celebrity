package com.example.spendcelebsmoney.LocalDatabase;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

public class UserViewModel extends AndroidViewModel {

    private Repository repository;
    private LiveData<List<User>> users;

    public UserViewModel(@NonNull Application application) {
        super(application);
        repository = new Repository(application);
        users = repository.getAllUsers();
    }

    public void createUser(User user) {
        repository.createUser(user);
    }
    public void updateUser(User user) {
        repository.updateUser(user);
    }
    public void deleteUser(User user) {
        repository.deleteUser(user);
    }
    public void bulkDeletion() {
        repository.bulkDeletion();
    }

    public LiveData<List<User>> getUsers() {
        return users;
    }
}
