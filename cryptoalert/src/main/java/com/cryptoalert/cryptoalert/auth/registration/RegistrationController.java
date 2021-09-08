package com.cryptoalert.cryptoalert.auth.registration;

import com.cryptoalert.cryptoalert.auth.user.AppUser;
import com.cryptoalert.cryptoalert.auth.user.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestController
public class RegistrationController {

    private final RegistrationService registrationService;
    private final UserService userService;

    public RegistrationController(RegistrationService registrationService, UserService userService) {
        this.registrationService = registrationService;
        this.userService = userService;
    }

    @PostMapping(path = "registration")
    public String register(@RequestBody RegistrationRequest request) {
        return registrationService.register(request);
    }

    @GetMapping(path = "registration/confirm")
    public String confirm(@RequestParam("token") String token) {
        return registrationService.confirmToken(token);
    }

    @GetMapping(path = "loggedin")
    public HttpStatus confirmLoggedIn() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if ((authentication != null) && (!authentication.getName().equals("anonymousUser"))) {
            AppUser user = userService.getUserByEmail(authentication.getName());
            if (user != null) {
                return HttpStatus.OK;
            }
        }

        throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);
    }

    @DeleteMapping(path = "registration")
    public HttpStatus deleteAccount() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if ((authentication == null) || (authentication.getName().equals("anonymousUser"))){
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);
        }

        AppUser user = userService.getUserByEmail(authentication.getName());
        if (user == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }

        userService.deleteUser(user);
        return HttpStatus.OK;
    }

}