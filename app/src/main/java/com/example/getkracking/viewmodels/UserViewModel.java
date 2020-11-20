package com.example.getkracking.viewmodels;

import android.graphics.Bitmap;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.getkracking.entities.UserVO;
import com.example.getkracking.repository.UserRepository;
import com.example.getkracking.vo.Resource;

public class UserViewModel extends RepositoryViewModel<UserRepository> {
    private boolean editingName = false, editingEmail = false;
    private String username, email;
    private int idUser;
    private MutableLiveData<Bitmap> imageBM = new MutableLiveData<>();

    public UserViewModel(UserRepository repository) {
        super(repository);
    }

    public LiveData<Resource<UserVO>> getCurrent() {
        return repository.getCurrent();
    }

    public LiveData<Resource<UserVO>> updateUser(String fullname, String image) {
        return repository.updateUser(fullname, image, fullname, email, idUser);
    }

    public LiveData<Resource<String>> login(String username, String password){ return repository.login(username, password); }

    public LiveData<Resource<UserVO>> register(String username, String email, String password){ return repository.register(username, email, password); }

    public LiveData<Resource<Void>> logout(){
        return repository.logout();
    }

    public MutableLiveData<Bitmap> getImageBM() {
        return imageBM;
    }

    public void setImageBM(Bitmap imageBM) {
        this.imageBM.setValue(imageBM);
    }

    public boolean isEditingName() {
        return editingName;
    }

    public void setEditingName(boolean editingName) {
        this.editingName = editingName;
    }

    public boolean isEditingEmail() {
        return editingEmail;
    }

    public void setEditingEmail(boolean editingEmail) {
        this.editingEmail = editingEmail;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getIdUser() {
        return idUser;
    }

    public void setIdUser(int idUser) {
        this.idUser = idUser;
    }
}
