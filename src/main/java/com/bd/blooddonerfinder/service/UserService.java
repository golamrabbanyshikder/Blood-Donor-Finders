package com.bd.blooddonerfinder.service;

import com.bd.blooddonerfinder.model.Users;
import com.bd.blooddonerfinder.payload.request.UserRegistrationRequest;
import com.bd.blooddonerfinder.payload.response.RestApiResponse;

public interface UserService {
    RestApiResponse<Users> registerUser(UserRegistrationRequest registrationRequest);
    RestApiResponse<Users> getregisterUser(Long id);
    RestApiResponse<Users> updateregisterUser(UserRegistrationRequest registrationRequest);
    RestApiResponse<Users> getregisterUsers();
    RestApiResponse<Users> deleteregisterUser(Long id);

}
