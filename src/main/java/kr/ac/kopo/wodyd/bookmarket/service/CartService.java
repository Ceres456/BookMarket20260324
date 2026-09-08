package kr.ac.kopo.wodyd.bookmarket.service;

import kr.ac.kopo.wodyd.bookmarket.domain.Cart;

public interface CartService {
    Cart create(Cart cart);
    Cart read(String cartId);

}
