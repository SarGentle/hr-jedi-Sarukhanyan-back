package ru.lanit.bpm.jedu.hrjedi.app.impl.security;

import org.passay.CharacterRule;
import org.passay.EnglishCharacterData;
import org.passay.PasswordGenerator;
import org.springframework.stereotype.Component;
import ru.lanit.bpm.jedu.hrjedi.app.api.security.GenerateSecurePasswordInbound;

import java.util.Arrays;
import java.util.List;


@Component
public class GenerateSecurePasswordUseCase implements GenerateSecurePasswordInbound {
    /**
     * Legacy code used to load classes by reflection
     *
     * @return secure password
     */
    @Override
    public String execute() {
        List<CharacterRule> rules = Arrays.asList(
            new CharacterRule(EnglishCharacterData.UpperCase, 2),
            new CharacterRule(EnglishCharacterData.LowerCase, 4),
            new CharacterRule(EnglishCharacterData.Digit, 2));
        PasswordGenerator generator = new PasswordGenerator();
        return generator.generatePassword(8, rules);
    }
}
