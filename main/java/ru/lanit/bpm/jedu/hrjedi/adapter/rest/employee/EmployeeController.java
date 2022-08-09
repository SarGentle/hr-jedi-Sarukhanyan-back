/*
 * Copyright (c) 2008-2020
 * LANIT
 * All rights reserved.
 *
 * This product and related documentation are protected by copyright and
 * distributed under licenses restricting its use, copying, distribution, and
 * decompilation. No part of this product or related documentation may be
 * reproduced in any form by any means without prior written authorization of
 * LANIT and its licensors, if any.
 *
 * $
 */
package ru.lanit.bpm.jedu.hrjedi.adapter.rest.employee;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.lanit.bpm.jedu.hrjedi.adapter.rest.employee.dto.SignUpFormDto;
import ru.lanit.bpm.jedu.hrjedi.app.impl.employee.EmailValidator;
import ru.lanit.bpm.jedu.hrjedi.app.api.employee.*;
import ru.lanit.bpm.jedu.hrjedi.app.api.security.GenerateSecurePasswordInbound;
import ru.lanit.bpm.jedu.hrjedi.domain.employee.Employee;
import ru.lanit.bpm.jedu.hrjedi.domain.employee.EmployeeAvatar;

import javax.servlet.ServletContext;
import java.util.List;

@RestController
@RequestMapping("/hr-rest/employees")
@RequiredArgsConstructor
public class EmployeeController {
    private final UpdateEmployeeEmailInbound updateEmployeeEmailInbound;
    private final CreateEmployeeInbound createEmployeeInbound;
    private final FindEmployeeByLoginInbound findEmployeeByLoginInbound;
    private final FindAllEmployeesInbound findAllEmployeesInbound;
    private final GetEmployeeFullNameByLoginInbound getEmployeeFullNameByLoginInbound;
    private final GetEmployeeAvatarInbound getEmployeeAvatarInbound;
    private final GenerateSecurePasswordInbound generateSecurePasswordInbound;
    private final ServletContext servletContext;
    private final SetEmployeeAvatarOutbound setEmployeeAvatarOutbound;

    @PostMapping("/current/update-email")
    public ResponseEntity<String> updateEmail(@RequestAttribute String currentUser, @RequestBody String email) {
        if (EmailValidator.emailValidate(email)) {
            updateEmployeeEmailInbound.execute(currentUser, email);
            return ResponseEntity.ok("Email changed!");
        } else {
            return ResponseEntity.badRequest().body("Incorrect email");
        }
    }



    @PostMapping()
    @PreAuthorize("hasRole('OMNI') or hasRole('ADMIN')")
    public ResponseEntity<String> createEmployee(@RequestBody SignUpFormDto signUpRequest) {
        try {
            createEmployeeInbound.execute(
                signUpRequest.getLogin(),
                signUpRequest.getFirstName(),
                signUpRequest.getPatronymic(),
                signUpRequest.getLastName(),
                signUpRequest.getPassword(),
                signUpRequest.getEmail(),
                signUpRequest.getRoles());
        } catch (EmployeeRegistrationException ex) {
            return ResponseEntity.badRequest().body(ex.getMessage());
        }

        return ResponseEntity.ok("Employee registered successfully!");
    }

    @GetMapping("/{login}")
    public Employee findEmployee(@PathVariable String login) {
        return findEmployeeByLoginInbound.execute(login);
    }

    @GetMapping()
    @PreAuthorize("hasRole('OMNI') or hasRole('ADMIN')")
    public List<Employee> getAllEmployees() {
        return findAllEmployeesInbound.execute();
    }

    @GetMapping("/{employeeLogin}/fullName")
    @PreAuthorize("hasRole('OMNI') or hasRole('ADMIN') or hasRole('HR') or hasRole('USER')")
    public String getEmployeeFullNameByLogin(@PathVariable("employeeLogin") String employeeLogin) {
        return getEmployeeFullNameByLoginInbound.execute(employeeLogin);
    }

    @GetMapping("/current/avatar")
    public ResponseEntity<byte[]> getAvatar(@RequestAttribute String currentUser) {
        EmployeeAvatar employeeAvatar = getEmployeeAvatarInbound.execute(currentUser);
        if (employeeAvatar != null) {
            return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(servletContext.getMimeType(employeeAvatar.getAvatarPath().toAbsolutePath().toString())))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + employeeAvatar.getAvatarPath().getFileName() + "\"")
                .body(employeeAvatar.getAvatar());
        } else {
            return ResponseEntity.ok().body(null);
        }
    }

    @PostMapping(value="/current/avatar", consumes=MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<String> uploadAvatar(@RequestAttribute String currentUser, @RequestParam("file") MultipartFile file) {
        setEmployeeAvatarOutbound.execute(currentUser, file);
        return ResponseEntity.ok("Avatar Changed!");
    }

    @GetMapping("/generate-pass")
    public String generateSecurePassword() {
        return generateSecurePasswordInbound.execute();
    }
}
