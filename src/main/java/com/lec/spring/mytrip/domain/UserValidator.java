package com.lec.spring.mytrip.domain;
import com.lec.spring.mytrip.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

@Component
public class UserValidator implements Validator {

    private UserService userService;

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        // 검증할 객체의 클래스 타입 확인
        return User.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        User user = (User) target;

        // username 검증
        String username = user.getUsername();
        if (username == null || username.trim().isEmpty()) {
            errors.rejectValue("username", "username.required", "username 은 필수입니다.");
        } else if (userService.isExist(username)) {
            errors.rejectValue("username", "username.duplicate", "이미 존재하는 아이디(username) 입니다.");
        }

        // name 검증
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "name", "name.required", "name 은 필수입니다.");

        // password 검증
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "password", "password.required", "password 은 필수입니다.");

        // email 검증 (정규표현식 패턴 확인)
        String email = user.getEmail();
        if (email == null || email.trim().isEmpty()) {
            errors.rejectValue("email", "email.required", "email 은 필수입니다.");
        } else if (!email.matches("^[\\w._%+-]+@[\\w.-]+\\.[a-zA-Z]{2,6}$")) {
            errors.rejectValue("email", "email.invalid", "유효하지 않은 이메일 형식입니다.");
        }

        // 비밀번호와 비밀번호 확인 일치 여부 검증
        if (!user.getPassword().equals(user.getRe_password())) {
            errors.rejectValue("re_password", "password.mismatch", "비밀번호와 비밀번호 확인 입력값은 같아야 합니다.");
        }
    }
}
