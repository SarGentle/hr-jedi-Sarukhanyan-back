package ru.lanit.bpm.jedu.hrjedi.app.impl.employee;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import ru.lanit.bpm.jedu.hrjedi.app.api.employee.SetEmployeeAvatarOutbound;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Paths;

/**
 * todo Document type SetEmployeeAvatarUseCase
 */
@Component
@RequiredArgsConstructor
public class SetEmployeeAvatarUseCase implements SetEmployeeAvatarOutbound {
    private static final Logger LOGGER = LoggerFactory.getLogger(SetEmployeeAvatarUseCase.class);

    public void execute(String userLogin, MultipartFile file){
        try {
            byte[] avatar = file.getBytes();
            BufferedOutputStream stream =
                new BufferedOutputStream(new FileOutputStream(new File(String.valueOf(Paths.get("target", "classes", "images", userLogin + ".png")))));
            stream.write(avatar);
            stream.close();
        } catch (IOException e) {
            LOGGER.error("Unable to set employee avatar. Message - {}", e.getMessage());
        }
    }
}
