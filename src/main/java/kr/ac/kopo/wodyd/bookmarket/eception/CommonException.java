package kr.ac.kopo.wodyd.bookmarket.eception;

import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

@ControllerAdvice
public class CommonException {
    @ExceptionHandler(value = {RuntimeException.class})
    public ModelAndView handleError(HttpServletResponse request, BookIdException exception){
        ModelAndView mav = new ModelAndView();
        mav.addObject("exception", exception);
        mav.addObject("url", request.getRequestURL());
        mav.SetViewName("errorCommom");
        return mav;
    }
}
