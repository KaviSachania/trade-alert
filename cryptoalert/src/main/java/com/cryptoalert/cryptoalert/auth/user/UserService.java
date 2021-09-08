package com.cryptoalert.cryptoalert.auth.user;

import com.cryptoalert.cryptoalert.auth.registration.token.ConfirmationToken;
import com.cryptoalert.cryptoalert.auth.registration.token.ConfirmationTokenService;
import com.cryptoalert.cryptoalert.controllers.exceptions.ResourceNotFoundException;
import com.cryptoalert.cryptoalert.services.AlertsService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class UserService implements UserDetailsService {

    private final static String USER_NOT_FOUND_MSG =
            "user with email %s not found";

    private final AlertsService alertsService;
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final ConfirmationTokenService confirmationTokenService;

    public UserService(AlertsService alertsService,
                       UserRepository userRepository,
                       BCryptPasswordEncoder bCryptPasswordEncoder,
                       ConfirmationTokenService confirmationTokenService) {
        this.alertsService = alertsService;
        this.userRepository = userRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.confirmationTokenService = confirmationTokenService;
    }

    @Override
    public UserDetails loadUserByUsername(String email)
            throws UsernameNotFoundException {
        return userRepository.findByEmail(email)
                .orElseThrow(() ->
                        new UsernameNotFoundException(
                                String.format(USER_NOT_FOUND_MSG, email)));
    }

    public AppUser getUserByEmail(String email) {
        Optional<AppUser> appUser = userRepository.findByEmail(email);
        return appUser.orElse(null);
    }

    public String signUpUser(AppUser appUser) {
        Optional<AppUser> existingUser = userRepository
                .findByEmail(appUser.getEmail());

        if (existingUser.isPresent()) {
            AppUser user = existingUser.get();
            ConfirmationToken confirmationToken = confirmationTokenService.getConfirmationTokenByUserId(user.getId());
            if (confirmationToken == null) {
                throw new ResourceNotFoundException("Confirmation Token not found.");
            }

            if ((confirmationToken.getConfirmedAt() == null) &&
                    (LocalDateTime.now().isAfter(confirmationToken.getExpiresAt()))) {

                alertsService.deleteAlertsByUserId(user.getId());
                confirmationTokenService.deleteConfirmationToken(confirmationToken);
                userRepository.delete(user);
            } else {
                throw new IllegalStateException("email already taken");
            }
        }

        String encodedPassword = bCryptPasswordEncoder
                .encode(appUser.getPassword());

        appUser.setPassword(encodedPassword);

        userRepository.save(appUser);

        String token = UUID.randomUUID().toString();

        ConfirmationToken confirmationToken = new ConfirmationToken(
                token,
                LocalDateTime.now(),
                LocalDateTime.now().plusMinutes(15),
                appUser
        );

        confirmationTokenService.saveConfirmationToken(confirmationToken);

        return token;
    }

    public int enableUser(String email) {
        return userRepository.enableUser(email);
    }

    public List<AppUser> getEnabledUsers() {
        return userRepository.getEnabledUsers();
    }

    @Transactional
    public void deleteUser(AppUser appUser) {
        ConfirmationToken confirmationToken = confirmationTokenService.getConfirmationTokenByUserId(appUser.getId());
        if (confirmationToken == null) {
            throw new ResourceNotFoundException("Confirmation Token not found.");
        }

        alertsService.deleteAlertsByUserId(appUser.getId());
        confirmationTokenService.deleteConfirmationToken(confirmationToken);
        userRepository.delete(appUser);
    }
}