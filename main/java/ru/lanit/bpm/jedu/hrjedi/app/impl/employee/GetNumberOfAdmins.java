package ru.lanit.bpm.jedu.hrjedi.app.impl.employee;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import ru.lanit.bpm.jedu.hrjedi.app.api.employee.EmployeeRepository;

import static ru.lanit.bpm.jedu.hrjedi.domain.security.RoleName.ROLE_ADMIN;

@Component
@RequiredArgsConstructor
public class GetNumberOfAdmins {
    private final EmployeeRepository employeeRepository;

    @Transactional(readOnly = true)
    public long execute() {
        return employeeRepository.findAll().stream()
            .filter(user ->
                user.getRoles().stream()
                    .anyMatch(role -> ROLE_ADMIN.equals(role.getName())))
            .count();
    }
}
