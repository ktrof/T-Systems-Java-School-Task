package org.tsystems.javaschool.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.tsystems.javaschool.model.entity.RoleEntity;
import org.tsystems.javaschool.model.entity.UserEntity;
import org.tsystems.javaschool.repository.RoleRepository;
import org.tsystems.javaschool.repository.UserRepository;

import java.util.Collection;
import java.util.stream.Collectors;

/**
 * The type User details service.
 *
 * @author Trofim Kremen
 */
@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserRepository userRepository;

    private final RoleRepository roleRepository;

    @Override
    public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException {
        UserEntity userEntity = userRepository.findByLogin(login);
        if (userEntity.getLogin() == null) {
            throw new UsernameNotFoundException("Not found: " + login);
        }

        return new User(
                userEntity.getLogin(),
                userEntity.getPassword(),
                mapRolesToAuthorities(login)
        );
    }

    private Collection<? extends GrantedAuthority> mapRolesToAuthorities(String login) {
        Collection<RoleEntity> roleEntityCollection = roleRepository.findRolesByUserLogin(login);
        return roleEntityCollection.stream()
                .map(roleEntity -> new SimpleGrantedAuthority(roleEntity.getName()))
                .collect(Collectors.toList());
    }

}
