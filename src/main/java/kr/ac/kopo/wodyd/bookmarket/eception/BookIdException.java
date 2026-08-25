package kr.ac.kopo.wodyd.bookmarket.eception;

import lombok.Data;

@Data
public class BookIdException extends RuntimeException{
    private String bookId;

    public BookIdException(String bookId) {
        this.bookId = bookId;
    }
}

