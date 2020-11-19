package com.example.getkracking.viewmodels;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;

import com.example.getkracking.API.model.UserModel;
import com.example.getkracking.R;
import com.example.getkracking.entities.UserVO;
import com.example.getkracking.repository.UserRepository;
import com.example.getkracking.room.entities.UserTable;
import com.example.getkracking.vo.Resource;

public class UserViewModel extends RepositoryViewModel<UserRepository> {

    private MediatorLiveData<Resource<UserVO>> userData;

    public UserViewModel(UserRepository repository) {
        super(repository);
    }

    public LiveData<Resource<UserVO>> getCurrent() {
        return repository.getCurrent();
    }

    public LiveData<Resource<UserVO>> updateUser(String fullname, String image) {
        return repository.updateUser(fullname, image);
    }

    public LiveData<Resource<Void>> logout(){
        return repository.logout();
    }


}
