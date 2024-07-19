package com.example.authorbookrest.security;

import com.example.authorbookrest.entity.User;
import org.springframework.security.core.authority.AuthorityUtils;

public class CurrentUser extends org.springframework.security.core.userdetails.User {

    private User user;

    public CurrentUser(User user) {
        super(user.getEmail(), user.getPassword(),
                user.getUserType() != null ?
                        AuthorityUtils.createAuthorityList(user.getUserType().name()) :
                        AuthorityUtils.createAuthorityList("USER"));
        this.user = user;
    }


    public User getUser() {
        return user;
    }

}