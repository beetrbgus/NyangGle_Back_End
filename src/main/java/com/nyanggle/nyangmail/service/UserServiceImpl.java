package com.nyanggle.nyangmail.service;

import com.nyanggle.nyangmail.exception.user.CannotFindUser;
import com.nyanggle.nyangmail.persistence.entity.User;
import com.nyanggle.nyangmail.persistence.repository.CustomUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService{
    private final CustomUserRepository customUserRepository;

    @Transactional
    public void modifyNickname(String userId, String nickName) {
        User user = customUserRepository.findByUserUid(userId)
                .orElseThrow(CannotFindUser::new);
        user.nicknameChange(nickName);
    }
}
