package org.perscholas.homemade.exception;

import org.springframework.ui.Model;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import java.nio.file.FileAlreadyExistsException;

@ControllerAdvice
@Slf4j
public class AdviceController {
    @ExceptionHandler(Exception.class)
    public RedirectView catchExceptions(Exception e){
        log.warn(e.getMessage().toString());
        RedirectView mv= new RedirectView("/index");
        return mv;
    }
    @ExceptionHandler(MaxUploadSizeExceededException.class)
    public String handleMaxSizeException(Model model, MaxUploadSizeExceededException e) {
        model.addAttribute("error_message", "File is too large!");
        return "/addNewProcuct";
    }

}
