package kr.ac.kopo.wodyd.bookmarket.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import kr.ac.kopo.wodyd.bookmarket.domain.Cart;
import kr.ac.kopo.wodyd.bookmarket.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping(value = "/cart")
public class CartController {
    @Autowired
    private CartService cartService;

    @GetMapping
    public String requestCartId(HttpServletRequest request){
        String sessionId = request.getSession().getId();
        return "redirect:/cart/"+sessionId;
    }

    @PostMapping
    public @ResponseBody Cart create(@RequestBody Cart cart){
        return cartService.create(cart);
    }

    @PutMapping("/{cartId}")
    public @ResponseBody Cart read(@PathVariable(value =  "cartId") String cartId){
        return cartService.read(cartId);
    }
}
