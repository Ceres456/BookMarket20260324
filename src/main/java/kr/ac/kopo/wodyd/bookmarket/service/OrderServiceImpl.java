package kr.ac.kopo.wodyd.bookmarket.service;

import kr.ac.kopo.wodyd.bookmarket.domain.Book;
import kr.ac.kopo.wodyd.bookmarket.domain.Order;
import kr.ac.kopo.wodyd.bookmarket.repository.BookRepository;
import kr.ac.kopo.wodyd.bookmarket.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OrderServiceImpl implements OrderService{
    @Autowired
    private BookRepository BookRepository;

    private OrderRepository orderRepository;


    @Override
    public void confirmOrder(String bookId, long quantity){
        Book bookById = BookRepository.getBookById(bookId);
        if (bookById.getUnitsInStock() < quantity)
            throw new IllegalArgumentException("도서 수량이 부족합니다. 구입 가능 수량: " + bookById.getUnitsInStock());

        bookById.setUnitsInStock(bookById.getUnitsInStock() - quantity);
    }

    @Override
    public Long saveOrder(Order order) {
        Long orderId = orderRepository.saveOrder(order);
        return orderId;
    }
}
