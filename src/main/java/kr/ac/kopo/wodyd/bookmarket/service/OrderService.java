package kr.ac.kopo.wodyd.bookmarket.service;

import kr.ac.kopo.wodyd.bookmarket.domain.Order;

public interface OrderService {
    void confirmOrder(String bookId, long quantity);
    Long saveOrder(Order order);
}
