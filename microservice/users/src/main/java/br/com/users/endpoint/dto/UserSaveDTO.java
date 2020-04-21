package br.com.users.endpoint.dto;

import br.com.core.model.UserEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class UserSaveDTO {

    private String username;
    private String password;
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public UserEntity parseUserEntity(){
        UserEntity user = new UserEntity();
        user.setUsername(this.getUsername());
        user.setPassword(new BCryptPasswordEncoder().encode(this.getPassword()));
        user.setRole("USER");
        return user;
    }

    public UserSaveDTO() {
    }
}
