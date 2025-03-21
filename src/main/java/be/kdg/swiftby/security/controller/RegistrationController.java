package be.kdg.swiftby.security.controller;

import be.kdg.swiftby.domain.exception.AlreadyExistsException;
import be.kdg.swiftby.domain.testEnv.User;
import be.kdg.swiftby.security.ProfileDto;
import be.kdg.swiftby.security.service.ProfileServiceInt;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.ModelAndView;

import java.util.Collections;
import java.util.Locale;

@Controller
public class RegistrationController {
    private final ProfileServiceInt profileService;


    public RegistrationController(ProfileServiceInt profileService) {
        this.profileService = profileService;
    }

    @GetMapping("/registration")
    public String showRegistrationForm(Model model) {
        model.addAttribute("user", new ProfileDto());
        return "registration";
    }

    //Idk if I should be handling errors here-refactor
    @PostMapping("/registration")
    public ModelAndView registerUserAccount( @Valid @ModelAttribute("user")ProfileDto userDto,
                                            HttpServletRequest request, Errors errors,
                                            ModelAndView mav) {
        if (errors.hasErrors()) {
            for (ObjectError error : errors.getAllErrors()) {
                System.out.println("Error: " + error.getDefaultMessage());
            }
            mav.setViewName("registration");
            return mav;
        }

        try {
            User registered = profileService.registerNewUserAccount(userDto);
        } catch (AlreadyExistsException uaEX) {
            mav.addObject("message", "An account for that username/email already exists.");
            return mav;
        }

        return new ModelAndView("successRegister", "user", userDto);
    }
}
