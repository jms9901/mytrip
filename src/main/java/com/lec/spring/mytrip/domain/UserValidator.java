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

//        // 사용자명 검증
//        String username = user.getUsername();
//        if (username == null || username.trim().isEmpty()) {
//            errors.rejectValue("username", "username.required", "username 은 필수입니다.");
//        } else if (userService.findByUsername(username) != null) {
//            errors.rejectValue("username", "username.duplicate", "이미 존재하는 아이디(username) 입니다.");
//        }

        // 이메일 검증
        String email = user.getEmail();
        if (email == null || email.trim().isEmpty()) {
            errors.rejectValue("email", "email.required", "email 은 필수입니다.");
        } else if (userService.findByEmail(email) != null) {
            errors.rejectValue("email", "email.duplicate", "이미 존재하는 이메일입니다.");
        } else if (!email.matches("^[\\w._%+-]+@[\\w.-]+\\.[a-zA-Z]{2,6}$")) {
            errors.rejectValue("email", "email.invalid", "유효하지 않은 이메일 형식입니다.");
        }

        // 비밀번호와 비밀번호 확인 일치 여부 검증
        if (!user.getPassword().equals(user.getRe_password())) {
            errors.rejectValue("re_password", "password.mismatch", "비밀번호와 비밀번호 확인 입력값은 같아야 합니다.");
        }

        // 기타 필드 검증
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "name", "name.required", "name 은 필수입니다.");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "password", "password.required", "password 은 필수입니다.");

        // 생일 또는 사업자 번호 검증
        if ((user.getBirthday() == null || user.getBirthday().trim().isEmpty()) &&
                (user.getCompanyNumber() == null || user.getCompanyNumber().trim().isEmpty())) {
            errors.rejectValue("birthday", "birthday.or.companyNumber.required", "생일 또는 사업자 번호 중 하나는 필수입니다.");
            errors.rejectValue("companyNumber", "birthday.or.companyNumber.required", "생일 또는 사업자 번호 중 하나는 필수입니다.");
        }

        // 생일이 있을 때 사업자 번호 검증 생략
        if (user.getBirthday() != null && !user.getBirthday().trim().isEmpty()) {
            // 생일 형식 검증 로직 추가 가능
        }

        // 사업자 번호가 있을 때 생일 검증 생략
        if (user.getCompanyNumber() != null && !user.getCompanyNumber().trim().isEmpty()) {
            if (!user.getCompanyNumber().matches("^(\\d{3})-?(\\d{2})-?(\\d{5})$")) {
                errors.rejectValue("companyNumber", "companyNumber.invalid", "유효하지 않는 사업자 번호 형식입니다.");
            }
        }
    }
}
