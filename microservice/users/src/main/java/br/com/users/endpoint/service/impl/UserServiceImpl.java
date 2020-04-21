package br.com.users.endpoint.service.impl;

import br.com.core.model.UserEntity;
import br.com.core.repository.UserRepository;
import br.com.users.endpoint.dto.UserSaveDTO;
import br.com.users.endpoint.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public Iterable<UserEntity> listUser() {
        return userRepository.findAll();
    }

    @Override
    public UserEntity saveUser(UserSaveDTO userSaveDTO) {
        UserEntity userEntity = userSaveDTO.parseUserEntity();
        return userRepository.save(userEntity);
    }

    @Override
    public UserEntity validateUserExist(Long idUser) {
        Optional<UserEntity> user = this.userRepository.findById(idUser);
        if (user.isPresent()) {
            return user.get();
        } else {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "Client not found");
        }
    }

}
