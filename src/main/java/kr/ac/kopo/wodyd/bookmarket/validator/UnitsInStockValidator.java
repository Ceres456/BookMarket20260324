package kr.ac.kopo.wodyd.bookmarket.validator;

import kr.ac.kopo.wodyd.bookmarket.domain.Book;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import java.math.BigDecimal;

@Component
public class UnitsInStockValidator implements Validator {
    @Override
    public boolean supports(Class<?> clazz) {
        return Book.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        Book book = (Book) target;
        BigDecimal unitPrice = book.getUnitPrice();
        long unitInStock = book.getUnitsInStock();
        if(unitPrice != null && unitPrice.intValue() >= 10000 && unitInStock > 99 ){
            errors.rejectValue("unitsInStock", "UnitsInStockValidator.message", "가격 10000 쓰껄쓰껄");
        }
    }
}
