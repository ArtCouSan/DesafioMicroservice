package br.com.users.service;

import br.com.core.model.UserEntity;
import br.com.core.repository.UserRepository;
import br.com.users.endpoint.dto.UserSaveDTO;
import br.com.users.endpoint.service.impl.UserServiceImpl;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import static org.mockito.Mockito.when;

import org.mockito.junit.MockitoJUnitRunner;
import java.util.ArrayList;
import java.util.Optional;

@RunWith(MockitoJUnitRunner.class)
public class UserServiceTest {

    @Mock
    UserRepository userRepository;

    @InjectMocks
    private UserServiceImpl userService;

    @Test
    public void testFindAll() {
        ArrayList<UserEntity> userEntities = new ArrayList<UserEntity>();
        userEntities.add(getUserEntityMock());
        when(userRepository.findAll()).thenReturn(userEntities);
        Iterable<UserEntity> result = userService.listUser();
        Assert.assertNotNull(result);
    }

    @Test
    public void testValidateUserExist() {
        Optional<UserEntity> userEntityOptional  = Optional.of(getUserEntityMock());
        when(userRepository.findById(1l)).thenReturn(userEntityOptional);
        UserEntity result = userService.validateUserExist(1l);
        Assert.assertNotNull(result);
    }

    private UserEntity getUserEntityMock() {
        UserEntity userEntity = new UserEntity();
        userEntity.setId(1l);
        userEntity.setUsername("MockUser");
        userEntity.setPassword("MockPassword");
        userEntity.setRole("USER");
        return userEntity;
    }

    private UserSaveDTO getUserSaveDTOMock() {
        UserSaveDTO userSaveDTO = new UserSaveDTO();
        userSaveDTO.setUsername("MockUser");
        userSaveDTO.setUsername("MockPassword");
        return userSaveDTO;
    }

}
