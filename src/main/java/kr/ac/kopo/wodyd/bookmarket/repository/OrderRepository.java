package kr.ac.kopo.wodyd.bookmarket.repository;

import kr.ac.kopo.wodyd.bookmarket.domain.Order;

public interface OrderRepository {
    Long saveOrder(Order order);

}
