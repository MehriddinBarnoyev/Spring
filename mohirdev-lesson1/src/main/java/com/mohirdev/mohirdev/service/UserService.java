package com.mohirdev.mohirdev.service;

import com.mohirdev.mohirdev.domain.User;
import com.mohirdev.mohirdev.domain.UserCreateDto;
import com.mohirdev.mohirdev.repository.WorkersRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    private final WorkersRepository workersRepository;
    private final ModelMapper modelMapper;

    public UserService(WorkersRepository workersRepository, ModelMapper modelMapper) {
        this.workersRepository = workersRepository;
        this.modelMapper = modelMapper;
    }

    public static User save(User user) {
        return user;
    }

    public User addClient(UserCreateDto userCreateDto) {
        User map = modelMapper.map(userCreateDto, User.class);
        User save = workersRepository.save(map);
        return save;
    }
}
