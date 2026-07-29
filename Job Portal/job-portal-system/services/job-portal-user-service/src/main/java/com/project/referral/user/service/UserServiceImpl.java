package com.project.referral.user.service;

import org.springframework.stereotype.Service;

import com.project.referral.exception.ResourceNotFoundException;
import com.project.referral.user.entity.User;
import com.project.referral.user.repository.UserRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserServiceImpl implements UserService{

    private final UserRepository repository;

    @Override
    public User findByEmail(String email){

        log.info("Finding user {}",email);

        return repository.findByEmail(email)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "User not found"));

    }
    @Override
    public boolean existsByEmail(String email){
    	
    	log.info("checking  user exists {}",email);
    	
    	return repository.existsByEmail(email)
    			.orElseThrow(() ->
    			new ResourceNotFoundException(
    					"User not found"));
    	
    }

    @Override
    public User save(User user){

        log.info("Saving {}",user.getEmail());

        return repository.save(user);

    }

}