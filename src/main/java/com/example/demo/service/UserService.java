package com.example.demo.service;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.dto.UserDto;
import com.example.demo.entities.UserEntity;
import com.example.demo.exceptions.ResourceNotFoundException;
import com.example.demo.repositories.UserRepository;

@Service
public class UserService {
	@Autowired private UserRepository userRepository;
    @Autowired private ModelMapper modelMapper;

    public UserDto createUser(UserDto userDto) {
        UserEntity userEntity = modelMapper.map(userDto, UserEntity.class);
        UserEntity savedUser = userRepository.save(userEntity);
        return modelMapper.map(savedUser, UserDto.class);
    }
    public List<UserDto> getAllUsers() {
        return userRepository.findAll()
                .stream()
                .map(user -> modelMapper.map(user, UserDto.class))
                .collect(Collectors.toList());
    }

   
    public UserDto getUserById(Long id) {
        UserEntity user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + id));
        return modelMapper.map(user, UserDto.class);
    }

}
