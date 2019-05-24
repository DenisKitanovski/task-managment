package com.example.springwebproektna.controller;

import com.example.springwebproektna.config.JwtTokenUtil;
import com.example.springwebproektna.domains.AuthToken;
import com.example.springwebproektna.domains.LoginUser;
import com.example.springwebproektna.domains.RegistrationForm;
import com.example.springwebproektna.domains.UserId;
import com.example.springwebproektna.model.Dashboard;
import com.example.springwebproektna.model.User;
import com.example.springwebproektna.service.DashboardService;
import com.example.springwebproektna.service.UserService;
import com.example.springwebproektna.utils.SecurityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@RestController
public class UserController {
    private static final Logger log = LoggerFactory.getLogger(UserController.class);

    private UserService userService;
    private DashboardService dashboardService;
    private SecurityUtils utils;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;




    @Autowired
    public UserController(UserService userService, DashboardService dashboardService) {
        this.userService = userService;
        this.dashboardService = dashboardService;
    }

    @PostMapping("/sign-up")
    @ResponseStatus(HttpStatus.CREATED)
    public UserId createNewUser(@RequestBody RegistrationForm form) {
        return userService.createNewUser(form);
    }

    @GetMapping("/me")
   // @PreAuthorize("hasRole('ROLE_USER')")
    public User getCurrentUser() {
        return userService.getCurrentUser();
    }


   // @PreAuthorize("hasRole('USER')")
    @PostMapping("/login")
    public AuthToken login(@RequestBody LoginUser loginUser) throws AuthenticationException{
          final Authentication authentication = authenticationManager.authenticate(
                  new UsernamePasswordAuthenticationToken(loginUser.getUsername(),loginUser.getPassword())
          );

      /*  SecurityContextHolder.getContext().setAuthentication(authentication);
        final User user = userService.getCurrentUser();
        Dashboard dashboard = dashboardService.findById(user.getDashboardId()).isPresent() ? dashboardService.findById(user.getDashboardId()).get() : null;
        HashMap<String, Object> map = new HashMap<>();
        map.put("companyName", dashboard.getCompanyName());
        map.put("user", user);
        return map;*/

        SecurityContextHolder.getContext().setAuthentication(authentication);
        final User user = userService.getCurrentUser();
        final String token = jwtTokenUtil.generateToken(user);
        final Optional<Dashboard> dashboard = dashboardService.findById(user.getDashboardId());
        final String companyName = dashboard.isPresent() ? dashboard.get().getCompanyName() : "";
        return new AuthToken(token, user, companyName);
    }

  /*  @PreAuthorize("hasRole('USER')")
    @RequestMapping("/logout")
    public ResponseEntity logout() {
        return new ResponseEntity(HttpStatus.OK);
    }*/

    @GetMapping("/user")
    public User user() {
        return userService.getCurrentUser();
    }


}
