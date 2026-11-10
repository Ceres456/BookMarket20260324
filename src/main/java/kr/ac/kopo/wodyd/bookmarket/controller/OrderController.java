package kr.ac.kopo.wodyd.bookmarket.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import kr.ac.kopo.wodyd.bookmarket.domain.*;
import kr.ac.kopo.wodyd.bookmarket.service.BookService;
import kr.ac.kopo.wodyd.bookmarket.service.CartService;
import kr.ac.kopo.wodyd.bookmarket.service.OrderProService;
import kr.ac.kopo.wodyd.bookmarket.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping(value = "/order")
public class OrderController {
    @Autowired
    private OrderService orderService;

    @Autowired
    private CartService cartService;

    @Autowired
    private OrderProService orderProService;

    @Autowired
    private BookService bookService;

    List<Book> listOfBooks;
    Order order;

    @GetMapping("/{cartId}")
    public String requestCartList(@PathVariable(value = "cartId") String cartId, Model model){
        Cart cart = cartService.read(cartId);
        order = new Order();
        listOfBooks = new ArrayList<Book>();

        for (CartItem item : cart.getCartItems().values()){
            OrderItem orderItem = new OrderItem();

            Book book = item.getBook();

            listOfBooks.add(book);

            orderItem.setBookId(book.getBookId());
            orderItem.setQuantity(item.getQuantity());
            orderItem.setTotalPrice(item.getTotalPrice());

            order.getOrderItems().put(book.getBookId(), orderItem);
        }

        order.setCustomer(new Customer());
        order.setShipping(new Shipping());
        order.setGrandTotal(cart.getGrandTotal());

        return "redirect:/order/orderCustomerInfo";
    }

    @GetMapping("/orderCustomerInfo")
    public String requestCustomerInfoForm(Model model){
        model.addAttribute("customer", order.getCustomer());
        return "orderCustomerInfo";
    }

    @PostMapping("/orderCustomerInfo")
    public String requestCustomerInfo(@ModelAttribute Customer customer){
       order.setCustomer(customer);
       return "redirect:/order/orderShippingInfo";
    }

    @GetMapping("/orderShippingInfo")
    public String requestShippingInfoForm(Model model){
        model.addAttribute("shipping", order.getShipping());
        return "orderShippingInfo";
    }

    @PostMapping("/orderShippingInfo")
    public String requestShippingInfo(@Valid @ModelAttribute Shipping shipping, BindingResult bindingResult, Model model){
        if (bindingResult.hasErrors())
            return "orderShippingInfo";
        order.setShipping(shipping);
        model.addAttribute("order", order);
        return "redirect:/order/orderConfirmation";
    }

    @GetMapping("/orderConfirmation")
    public String requestConfirmation(Model model){
        model.addAttribute("bookList", listOfBooks);
        model.addAttribute("order", order);
        return "orderConfirmation";
    }

    @GetMapping("/orderFinished")
    public String requestOrderFinished(HttpServletRequest request, Model model){
        orderService.saveOrder(order);
        model.addAttribute("order", order);
        HttpSession session = request.getSession();

        if (session != null)
            session.invalidate();

        return "orderFinished";
    }

    @GetMapping("/orderCancelled")
    public String requestCancelled(HttpServletRequest request){
        HttpSession session = request.getSession();

        if(session != null)
            session.invalidate();

        return "orderCancelled";
    }

    @GetMapping("/list")
    public String viewHomePage(Model model){
        return viewPage(1, "orderId", "asc", model);
    }

    @GetMapping("/page")
    public String viewPage(@RequestParam("pageNum") int pageNu, @RequestParam("sortField") String sortField, @RequestParam("sortDir") String sortDir, Model model){
        Page<Order> page = orderProService.listAll(pageNu, sortField, sortDir);
        List<Order> listOrders = page.getContent();
        model.addAttribute("currentPage", pageNu);
        model.addAttribute("totalPages", page.getTotalPages());
        model.addAttribute("totalItems", page.getTotalElements());
        model.addAttribute("sortField", sortField);
        model.addAttribute("sortDir", sortDir);
        model.addAttribute("reverseSortDir", sortDir.equals("asc") ? "desc" : "asc");
        model.addAttribute("orderList", listOrders);

        return "orderList";
    }
    @GetMapping("/view/{id}")
    public ModelAndView viewOrder(@PathVariable(name="id")Long id){
        Order order = orderProService.get(id);
        List<Book> listOfBooks = new ArrayList<Book>();
        for (OrderItem item : order.getOrderItems().values()){
            String bookId = item.getBookId();
            Book book = bookService.getBookById(bookId);
            listOfBooks.add(book);
        }
        ModelAndView modelAndView = new ModelAndView("orderView");
        modelAndView.addObject("order", order);
        modelAndView.addObject("bookList", listOfBooks);

        return modelAndView;
    }
}
