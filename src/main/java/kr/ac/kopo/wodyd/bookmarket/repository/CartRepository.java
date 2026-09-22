package kr.ac.kopo.wodyd.bookmarket.repository;

import kr.ac.kopo.wodyd.bookmarket.domain.Cart;

public interface CartRepository {
    Cart create(Cart cart);
    Cart read(String cartId);
    void update(String cartId, Cart cart);
    void delete(String cartId);
}
