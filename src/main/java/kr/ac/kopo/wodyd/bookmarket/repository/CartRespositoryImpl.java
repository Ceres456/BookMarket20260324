package kr.ac.kopo.wodyd.bookmarket.repository;

import kr.ac.kopo.wodyd.bookmarket.domain.Cart;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;

@Repository
public class CartRespositoryImpl implements CartRepository{
    private Map<String, Cart> listOfCarts;

    public CartRespositoryImpl() {
        listOfCarts = new HashMap<>();
    }

    @Override
    public Cart create(Cart cart) {
        if (listOfCarts.keySet().contains(cart.getCartId())){
            throw new IllegalArgumentException(String.format("장바구니를 새로 생성할 수 없습니다. 장바구니 %s id가 이미 존재합니다.", cart.getCartId()));
        }

        listOfCarts.put(cart.getCartId(), cart);

        return cart;
    }

    @Override
    public Cart read(String cartId) {
        return listOfCarts.get(cartId);
    }
}
