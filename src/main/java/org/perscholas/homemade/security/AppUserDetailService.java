package org.perscholas.homemade.security;

import org.perscholas.homemade.dao.AuthGroupRepoI;
import org.perscholas.homemade.dao.ChefRepoI;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class AppUserDetailService implements UserDetailsService {
    ChefRepoI chefRepoI;
    AuthGroupRepoI authGroupRepoI;

    @Autowired
    public AppUserDetailService(ChefRepoI chefRepoI, AuthGroupRepoI authGroupRepoI) {
        this.chefRepoI = chefRepoI;
        this.authGroupRepoI = authGroupRepoI;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        return new AppUserPrincipal(
                chefRepoI.findByEmail(username)
                        .orElseThrow(() -> new UsernameNotFoundException("Email Not Found"))
                , authGroupRepoI.findByEmail(username));
    }
}
