package com.ttknpdev.backend.services.secure;

/*import com.ttknpdev.understandjwth2databasehelloworld.dao.UserDao;
import com.ttknpdev.understandjwth2databasehelloworld.entities.User;*/

import com.ttknpdev.backend.entities.secure.User;
import com.ttknpdev.backend.log.Logback;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.List;

// @Service // I don't need this annotation because I called this class on my configuration class
// (Meaning I set any things for in this class But I will build it / convert it to beans I did it on my configuration class)
public class JwtUserDetailsService implements UserDetailsService {
    private Logback logback;
    private UserService userService;

    public JwtUserDetailsService() {
        logback = new Logback(JwtUserDetailsService.class);
    }

    @Autowired // I need to use SDI. I think it works after generate constructor
    public void setUserDao(UserService userService) {
        this.userService = userService;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        List<SimpleGrantedAuthority> roles = null; // for storing some roles from database
        User user = userService.read(username); // search by username
        if (user == null) {
            logback.log.warn("User is not found (username) {} " , username);
            throw new UsernameNotFoundException("User is not found (username : " + username + ")");
        }
        // same name class just called it like below
        roles = List.of(new SimpleGrantedAuthority(user.getRoles()));
        return new org.springframework.security.core.userdetails.User(
                user.getUsername(), //  username
                user.getPassword(), // password
                roles
        ); // roles
    }

}
