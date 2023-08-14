package com.distributedsystems.energyplatform.service;

import com.distributedsystems.energyplatform.dto.LoginRequestDto;
import com.distributedsystems.energyplatform.dto.LoginResponseDto;
import com.distributedsystems.energyplatform.dto.UserDto;
import com.distributedsystems.energyplatform.dto.UserEditDto;
import com.distributedsystems.energyplatform.entity.Role;
import com.distributedsystems.energyplatform.entity.User;
import com.distributedsystems.energyplatform.exception.EditUncreatedEntityException;
import com.distributedsystems.energyplatform.exception.LoginException;
import com.distributedsystems.energyplatform.exception.UsedUsernameException;
import com.distributedsystems.energyplatform.jwt.JwtProvider;
import com.distributedsystems.energyplatform.repository.UserRepository;
import com.distributedsystems.energyplatform.service.mapper.UserMapper;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final UserMapper userMapper;
    private final JwtProvider jwtProvider;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = getUserByUsername(username);
        return org.springframework.security.core.userdetails.User
                .withUsername(username)
                .password(user.getPassword())
                .authorities(user.getRoles())
                .accountExpired(false)
                .accountLocked(false)
                .credentialsExpired(false)
                .disabled(false)
                .build();
    }

    public LoginResponseDto login(LoginRequestDto loginRequestDto) {
        try {
            String username = loginRequestDto.getUsername();
            String password = loginRequestDto.getPassword();

            Authentication authentication =
                    authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
            SecurityContextHolder.getContext().setAuthentication(authentication);

            return LoginResponseDto.builder()
                    .username(username)
                    .token(jwtProvider.generateJwtToken(authentication))
                    .roles(getAuthenticationRoles(authentication))
                    .build();
        } catch (AuthenticationException e) {
            throw new LoginException("Invalid username/password");
        }
    }

    public Long register(UserDto userDto) {
        if (!userRepository.existsByUsername(userDto.getUsername())) {
            User user = userMapper.mapToEntity(userDto);
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            userRepository.save(user);
            return user.getId();
        }
        throw new UsedUsernameException("Username " + userDto.getUsername() + " already used by an account");
    }

    public Long editUser(UserEditDto userEditDto) {
        if (userRepository.existsById(userEditDto.getId())) {
            User user = userRepository.getById(userEditDto.getId());
            user.setName(userEditDto.getName());
            user.setAddress(userEditDto.getAddress());
            user.setBirthday(userEditDto.getBirthday());
            System.out.println(user);
            userRepository.save(user);
            return user.getId();
        }
        throw new EditUncreatedEntityException("User with id " + userEditDto.getId() + " does not exist!");
    }

    public Long deleteUser(Long id) {
        userRepository.deleteById(id);
        return id;
    }

    public List<UserDto> findAllClients() {
        List<User> clients = userRepository.findAllByRolesContains(Role.CLIENT);
        return clients.stream()
                .map(userMapper::mapToDto)
                .collect(Collectors.toList());
    }

    private Set<Role> getAuthenticationRoles(Authentication authentication) {
        return authentication.getAuthorities().stream()
                .map(r -> Role.valueOf(r.getAuthority()))
                .collect(Collectors.toSet());
    }

    public User getUserByUsername(String username) {
        User user = userRepository.getUserByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException("User '" + username + "' not found");
        }
        return user;
    }
}
