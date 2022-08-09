package ru.lanit.bpm.jedu.hrjedi.app.impl.employee;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * todo Document type EmailValidate
 */
public class EmailValidator {

    public static boolean emailValidate(String email){
        Pattern pattern = Pattern.compile("^[A-zА-я0-9\\.-]+@[A-zА-я0-9\\.-]+$");
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }
}
