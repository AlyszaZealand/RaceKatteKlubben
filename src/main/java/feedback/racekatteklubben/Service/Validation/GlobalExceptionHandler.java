package feedback.racekatteklubben.Controller;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

import java.io.IOException;

@ControllerAdvice
public class GlobalExceptionHandler {

    // Handles IOException from image uploads
    @ExceptionHandler(IOException.class)
    public String handleIOException(IOException e, Model model) {
        model.addAttribute("error", "Der skete en fejl ved upload af billede: " + e.getMessage());
        return "error";
    }

    // Handles any other unexpected exception
    @ExceptionHandler(Exception.class)
    public String handleGeneralException(Exception e, Model model) {
        model.addAttribute("error", "Der skete en uventet fejl: " + e.getMessage());
        return "error";
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public String handleBadRequestException(IllegalArgumentException ex, HttpServletRequest request) {
        ModelAndView modelAndView = new ModelAndView("error");
        modelAndView.addObject("errors", ex.getMessage());
        modelAndView.addObject("previousUrl", request.getHeader("Referer"));
        return "redirect:/error";
    }

}