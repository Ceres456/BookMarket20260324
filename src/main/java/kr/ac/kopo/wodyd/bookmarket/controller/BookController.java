package kr.ac.kopo.wodyd.bookmarket.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import kr.ac.kopo.wodyd.bookmarket.domain.Book;
import kr.ac.kopo.wodyd.bookmarket.domain.Cart;
import kr.ac.kopo.wodyd.bookmarket.domain.CartItem;
import kr.ac.kopo.wodyd.bookmarket.exception.BookIdException;
import kr.ac.kopo.wodyd.bookmarket.exception.CategoryException;
import kr.ac.kopo.wodyd.bookmarket.service.BookService;
import kr.ac.kopo.wodyd.bookmarket.service.CartService;
import kr.ac.kopo.wodyd.bookmarket.validator.BookValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.FileCopyUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Controller
@RequestMapping("/books")
public class BookController {
    @Autowired
    private BookService bookService;

    @Autowired
    private BookValidator bookValidator;

    @Value("${file.uploadDir}")
    String fileDir;

    @RequestMapping(method = RequestMethod.GET)
    public String requestBookList(Model model) {
        List<Book> listOfBooks = bookService.getAllBookList();
        model.addAttribute("bookList", listOfBooks);
        return "books";
    }

    @GetMapping("/book")
    public String requestBookById(@RequestParam("id") String bookId, Model model) {
        Book book = bookService.getBookById(bookId);
        model.addAttribute("book", book);
        return "book";
    }

    @GetMapping("/{category}")
    public String requestBooksByCategory(@PathVariable("category") String bookCategory, Model model) {
        List<Book> booksByCategory = bookService.getBookListByCategory(bookCategory);
        if (booksByCategory == null || booksByCategory.isEmpty())
            throw new CategoryException();

        model.addAttribute("bookList", booksByCategory);
        return "books";
    }

    @GetMapping("/filter/{bookFilter}")
    public String requestBooksByFilter(@MatrixVariable(pathVar = "bookFilter") Map<String, List<String>> bookFilter, Model model) {
        Set<Book> booksByFilter = bookService.getBookListByFilter(bookFilter);
        model.addAttribute("bookList", booksByFilter);
        return "books";
    }

    @GetMapping("/add")
    public String requestAddBookForm(Model model) {
        model.addAttribute("book", new Book());
        return "addBook";
    }

    @PostMapping("/add")
    public String submitAddNewBook(@Valid @ModelAttribute Book book, BindingResult bindingResult) {
        if (bindingResult.hasErrors())
            return "addBook";

        MultipartFile bookImage = book.getBookImage();
        if (bookImage != null && !bookImage.isEmpty()) {
            String saveName = bookImage.getOriginalFilename();
            File saveFile = new File(fileDir, saveName);
            try {
                bookImage.transferTo(saveFile);
            } catch (IOException e) {
                throw new RuntimeException("이미지가 업로드되지 않았습니다.");
            }
            book.setFileName(saveName);
        }
        bookService.setNewBook(book);
        return "redirect:/books";
    }

    @ModelAttribute
    public void addAttributes(Model model) {
        model.addAttribute("addTitle", "신규 도서 등록");
    }

    @GetMapping("/download")
    public void downloadBookImage(@RequestParam("file") String paramKey, HttpServletResponse response) {
        File imgFile = new File(fileDir + paramKey);
        response.setContentType("application/download");
        response.setContentLength((int)imgFile.length());
        response.setHeader("Content-Disposition", "attachment;filename=\"" + paramKey + "\"");

        try {
            OutputStream out = response.getOutputStream();
            FileInputStream fileIn = new FileInputStream(imgFile);
            FileCopyUtils.copy(fileIn, out);
            fileIn.close();
            out.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        binder.setValidator(bookValidator);
    }

    @GetMapping("/all")
    public ModelAndView requestAllBooks() {
      ModelAndView modelAndView = new ModelAndView();
      List<Book> list = bookService.getAllBookList();
      modelAndView.addObject("bookList", list);
      modelAndView.setViewName("books");
      return modelAndView;
    }

    @ExceptionHandler(value = {BookIdException.class})
    public ModelAndView handleError(HttpServletRequest request, BookIdException exception) {
        ModelAndView mav = new ModelAndView();
        mav.addObject("invalidBookId", exception.getBookId());
        mav.addObject("exception", exception);
        String queryString = request.getQueryString();
        mav.addObject("url", request.getRequestURL() + (queryString != null ? "?" + queryString : ""));
        mav.setViewName("errorBookId");
        return mav;
    }

    @Controller
    @RequestMapping(value = "/cart")
    public static class CartController {
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

        @GetMapping("/{cartId}")
        public String requestCartList(@PathVariable(value = "cartId") String cartId, Model model){
            Cart cart = cartService.read(cartId);
            model.addAttribute("cart", cart);
            model.addAttribute("cartId", cartId);
            return "cart";
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

            cartService.update(sessionId, cart);
        }

        @DeleteMapping("/book/{bookId}")
        @ResponseStatus(value = HttpStatus.NO_CONTENT)
        public void removeCartByItem(@PathVariable("bookId") String bookId, HttpServletRequest request){
            String sessionId = request.getSession(true).getId();

            Cart cart = cartService.read(sessionId);

            if (cart == null)
                cart = cartService.create(new Cart(sessionId));

            Book book = bookService.getBookById(bookId);

            if (book == null)
                throw new IllegalArgumentException(new BookIdException(bookId));

            cart.removeCartItem(new CartItem(book));

            cartService.update(sessionId, cart);
        }

    }
}
