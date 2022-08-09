package ru.lanit.bpm.jedu.hrjedi.app.api.employee;

import org.springframework.web.multipart.MultipartFile;

/**
 * todo Document type SetEmployeeAvatarOutbound
 */
public interface SetEmployeeAvatarOutbound {
    void execute(String userLogin, MultipartFile file);
}
