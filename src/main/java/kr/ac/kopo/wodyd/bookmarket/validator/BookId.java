package kr.ac.kopo.wodyd.bookmarket.validator;

import jakarta.validation.Constraint;
import kr.ac.kopo.wodyd.bookmarket.domain.Book;

import java.lang.annotation.*;

@Constraint(validatedBy = BookValidator.class)
@Target({ElementType.METHOD, ElementType.FIELD, ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface BookId {
    String message() default  "";
    Class<?>[] groups() default  {};
    Class<?>[] payload() default  {};
}
