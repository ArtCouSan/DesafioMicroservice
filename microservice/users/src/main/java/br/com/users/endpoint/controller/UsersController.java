package br.com.users.endpoint.controller;

import br.com.core.model.UserEntity;
import br.com.users.endpoint.dto.UserSaveDTO;
import br.com.users.endpoint.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "v1/user")
@Api(value = "Endpoint to users")
public class UsersController {

    private UserService userService;

    public UsersController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    @ApiOperation(value = "List all users")
    public ResponseEntity<Iterable<UserEntity>> listUsers() {
        Iterable<UserEntity> userEntities = userService.listUser();
        return new ResponseEntity<Iterable<UserEntity>>(userEntities, HttpStatus.OK);
    }

    @PostMapping
    @ApiOperation(value = "Save user")
    public ResponseEntity<UserEntity> saveUser(@RequestBody UserSaveDTO userSaveDTO) {
        UserEntity userSaved = userService.saveUser(userSaveDTO);
        return new ResponseEntity<UserEntity>(userSaved, HttpStatus.CREATED);
    }
}
