package br.com.users.endpoint.service;

import br.com.core.model.UserEntity;
import br.com.users.endpoint.dto.UserSaveDTO;

public interface UserService {

    public Iterable<UserEntity> listUser();
    public UserEntity saveUser(UserSaveDTO user);
    public UserEntity validateUserExist(Long id);

}
