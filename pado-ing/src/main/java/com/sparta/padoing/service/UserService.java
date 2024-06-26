package com.sparta.padoing.service;

import com.sparta.padoing.repository.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    /*public UserResponseDto createUser(UserRequestDto requestDto) {
        Users users = new Users(requestDto);
        Users saveUser = userRepository.save(users);
        UserResponseDto userResponseDto = new UserResponseDto(users);

        return userResponseDto;
    }

    @Transactional
    public Long updateUser(Long userId, UserRequestDto requestDto) {
        Users user = findUser(userId);
        user.update(requestDto);
        return userId;
    }

    public Long deleteUser(Long userId) {
        Users user = findUser(userId);
        userRepository.delete(user);
        return userId;
    }

    private Users findUser(Long id) {
        return userRepository.findById(id).orElseThrow(() ->
                new IllegalArgumentException("선택한 회원은 존재하지 않습니다.")
        );
    }*/

}
