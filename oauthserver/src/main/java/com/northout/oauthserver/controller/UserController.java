package com.northout.oauthserver.controller;

import java.security.Principal;
import java.util.HashMap;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {
    //@PreAuthorize("hasAuthority('can_call_tansUnion')")
    @GetMapping(value="/test/{name}")
    @PreAuthorize("hasRole('ROLE_admin')")
    public ResponseEntity<String> user(@PathVariable String name) {

        return new ResponseEntity<>(name+", you can access protected Resource..!!", HttpStatus.OK);

    }
}
