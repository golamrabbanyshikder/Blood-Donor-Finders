package com.bd.blooddonerfinder.service;

import com.bd.blooddonerfinder.model.Users;
import com.bd.blooddonerfinder.payload.request.UserRegistrationRequest;
import com.bd.blooddonerfinder.payload.response.RestApiResponse;
import com.bd.blooddonerfinder.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.BeanUtils;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class UserServiceImpl implements UserService {
	private final UserRepository userRepository;

	public UserServiceImpl(UserRepository userRepository) {
		this.userRepository = userRepository;
	}

	@Override
	public RestApiResponse<Users> registerUser(UserRegistrationRequest registrationRequest) {
		RestApiResponse<Users> apiResponse = new RestApiResponse<>();
		if (registrationRequest != null) {
			try {
				Optional<Users> optionalUser = userRepository.findByEmail(registrationRequest.getEmail());
				if (optionalUser.isEmpty()) {
					Users newUser = new Users(registrationRequest.getName(), registrationRequest.getEmail(),
							registrationRequest.getPhone(), registrationRequest.getBloodGroup(),
							registrationRequest.getRole(), registrationRequest.getLocation());
					newUser.setCreatedAt(LocalDateTime.now());
					newUser.setIsVerified(false);
					newUser.setIsAvailable(true);
					userRepository.save(newUser);
					log.debug("Registration successful");
					apiResponse.setData(newUser);
					apiResponse.setMessage("Registration successful");
					apiResponse.setStatus(HttpStatus.OK);
				} else {
					apiResponse.setData(optionalUser.get());
					apiResponse.setMessage("User already exists");
					apiResponse.setStatus(HttpStatus.CONFLICT);
				}
			} catch (Exception e) {
				log.error("Error while registering user : {}", e.getMessage());
				apiResponse.setMessage("Registration Failed");
				apiResponse.setStatus(HttpStatus.INTERNAL_SERVER_ERROR);
			}

		} else {
			apiResponse.setData(null);
			apiResponse.setStatus(HttpStatus.BAD_REQUEST);
			apiResponse.setMessage("Registration failed (reg request is null) ");
		}
		return apiResponse;
	}

	@Override
	public RestApiResponse<Users> getregisterUser(Long id) {
		RestApiResponse<Users> apiResponse = new RestApiResponse<>();
		Users getUser = userRepository.findById(id).orElse(null);

		if (getUser != null) {
			apiResponse.setData(getUser);
			apiResponse.setStatus(HttpStatus.OK);
			apiResponse.setMessage("User found");
		} else {
			apiResponse.setData(getUser);
			apiResponse.setStatus(HttpStatus.NOT_FOUND);
			apiResponse.setMessage("User not found");
		}

		return apiResponse;
	}

	@Override
	public RestApiResponse<Users> updateregisterUser(UserRegistrationRequest registrationRequest) {
		RestApiResponse<Users> apiResponse = new RestApiResponse<>();
		Users getUser = userRepository.findByEmail(registrationRequest.getEmail()).orElse(null);
		
		if (getUser != null && getUser.getName() != null) {
			Long countname = userRepository.checkUniqueName(getUser.getId(), getUser.getName());
			if (countname >0) {
				apiResponse.setMessage("Name is Already Exist");
				apiResponse.setStatus(HttpStatus.BAD_REQUEST);
				return apiResponse;
			}else {
				BeanUtils.copyProperties(registrationRequest,getUser, Users.class);
				userRepository.save(getUser);
				apiResponse.setData(getUser);
				apiResponse.setStatus(HttpStatus.NOT_FOUND);
				apiResponse.setMessage("User not found");
				return apiResponse;
			}					
		} 
		return null;
	}

	@Override
	public RestApiResponse<Users> getregisterUsers() {
		RestApiResponse<Users> apiResponse = new RestApiResponse<>();
		List<Users> getallusers = userRepository.findAll();
		if (getallusers != null) {
			apiResponse.setDatas(getallusers);
			apiResponse.setStatus(HttpStatus.FOUND);
			apiResponse.setMessage("All Users found");
			return apiResponse;
		}else {
			apiResponse.setMessage("No data found");
			apiResponse.setStatus(HttpStatus.NOT_FOUND);
			return apiResponse;
		}
			
	}

	@Override
	public RestApiResponse<Users> deleteregisterUser(Long id) {
		RestApiResponse<Users> apiResponse = new RestApiResponse<>();
		
		if ( userRepository.existsById(id)) {
			userRepository.deleteById(id);			
			apiResponse.setStatus(HttpStatus.FOUND);
			apiResponse.setMessage("SuccessFully Data Deleted FROM my DB where ID is = " + id);
			return apiResponse;
		}else {
			apiResponse.setMessage(id + " Number ID Didn't find in my DB");
			apiResponse.setStatus(HttpStatus.NOT_FOUND);
			return apiResponse;
		}
	}
}
