package kr.ac.kopo.wodyd.bookmarket.domain;

import jakarta.validation.constraints.*;
import kr.ac.kopo.wodyd.bookmarket.validator.BookId;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;

@Data
@Setter
@Getter
public class Book {
    @BookId
    @Pattern(regexp = "^isbn[1-9][0-9]{3}$", message = "{Pattern.book.booId}")
    private String bookId; //도서 ID
    @Size(min = 4, max = 50, message = "{Size.book.name}")
    private String name; //도서 이름
    @Min(value = 0, message = "{Min.book.unitPrice}")
    @Digits(integer = 8, fraction = 2, message = "{Digits.book.unitPrice}")
    @NotNull(message = "{NotNull.book.unitPrice}")
    private BigDecimal unitPrice; //도서 가격
    private String author; //저자
    private String description; //설명
    private String publisher; //출판사
    private String category; //분류
    private long unitsInStock; //재고수
    private String releaseDate; //출판일
    private String condition; //신규도서, 중고도서, E-Book
    private String fileName; //도서이미지 파일이름
    private MultipartFile bookImage; //도서이미지 파일객체
}
