package org.tsystems.javaschool.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.tsystems.javaschool.model.entity.RoleEntity;
import org.tsystems.javaschool.model.entity.UserEntity;
import org.tsystems.javaschool.repository.RoleRepository;
import org.tsystems.javaschool.repository.UserRepository;

import java.util.ArrayList;
import java.util.Collection;
import java.util.stream.Collectors;

/**
 * The type User details service.
 *
 * @author Trofim Kremen
 */
@Service
@Slf4j
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException {
        UserEntity userEntity = new UserEntity();
        try {
            userEntity = userRepository.findByLogin(login);
        } catch (Exception e) {
            log.error("Error getting user by login", e);
        }

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
        Collection<RoleEntity> roleEntityCollection = new ArrayList<>();
        try {
            roleEntityCollection = roleRepository.findRolesByUserLogin(login);
        } catch (Exception e) {
            log.error("Error getting user roles");
        }
        return roleEntityCollection.stream()
                .map(roleEntity -> new SimpleGrantedAuthority(roleEntity.getName()))
                .collect(Collectors.toList());
    }

}
