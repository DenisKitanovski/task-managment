package com.example.springwebproektna.service;

import com.example.springwebproektna.domains.RegistrationForm;
import com.example.springwebproektna.domains.UserId;
import com.example.springwebproektna.model.Task;
import com.example.springwebproektna.model.User;

public interface UserService {
    UserId createNewUser(RegistrationForm form);
    User getCurrentUser();

}
