package kr.ac.kopo.wodyd.bookmarket.validator;

import jakarta.validation.ConstraintViolation;
import kr.ac.kopo.wodyd.bookmarket.domain.Book;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import java.util.HashSet;
import java.util.Set;
// @ 어노테이션(Bean Validation 과 사용자 정의)
// Validator 구현체
// 위 두가지를 통합
@Component
public class BookValidator implements Validator {
    @Autowired
    private jakarta.validation.Validator beanValidator;
    public Set<Validator> springValidators;

    public BookValidator() {
        springValidators = new HashSet<Validator>();
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return Book.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
    //    Bean Validation 관련
        Set<ConstraintViolation<Object>> violations = beanValidator.validate(target);
        for (ConstraintViolation<Object> violation: violations) {
            String propertyPath = violation.getPropertyPath().toString();
            String message = violation.getMessage();
            errors.rejectValue(propertyPath, "", message);
        }
    //    Validator 구현체 관련
        for (Validator validator : springValidators) {
            validator.validate(target, errors);
        }
    }
}
