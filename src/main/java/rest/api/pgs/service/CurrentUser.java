package rest.api.pgs.service;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

public class CurrentUser extends User implements UserDetails {


    private final rest.api.pgs.entity.User user;

    public CurrentUser(String username, String password, Collection<? extends GrantedAuthority> authorities, rest.api.pgs.entity.User user)
    {
        super(username, password, authorities);
        this.user = user;
    }
    public  rest.api.pgs.entity.User getUser() {
        return user;
    }


}
