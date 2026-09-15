package kr.ac.kopo.wodyd.bookmarket.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import kr.ac.kopo.wodyd.bookmarket.domain.Book;
import kr.ac.kopo.wodyd.bookmarket.domain.Cart;
import kr.ac.kopo.wodyd.bookmarket.domain.CartItem;
import kr.ac.kopo.wodyd.bookmarket.eception.BookIdException;
import kr.ac.kopo.wodyd.bookmarket.service.BookService;
import kr.ac.kopo.wodyd.bookmarket.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping(value = "/cart")
public class CartController {
    @Autowired
    private CartService cartService;

    @Autowired
    private BookService bookService;

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

    @PutMapping("/book/{bookId}")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void addCartByNewItem(@PathVariable("bookId") String bookId, HttpServletRequest request){
        String sessionId = request.getSession(true).getId();

        Cart cart = cartService.read(sessionId);

        if (cart == null)
            cart = cartService.create(new Cart(sessionId));

        Book book = bookService.getBookById(bookId);

        if (book == null)
            throw new IllegalArgumentException(new BookIdException(bookId));

        cart.addCartItem(new CartItem(book));

        cartService.update();
    }

    @DeleteMapping("/book/{bookId}")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void removeCartByItem(@PathVariable("BookId") String bookId, HttpServletRequest request){
        String sessionId = request.getSession(true).getId();

        Cart cart
    }

}
