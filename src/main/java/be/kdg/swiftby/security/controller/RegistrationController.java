package be.kdg.swiftby.security.controller;

import be.kdg.swiftby.domain.exception.AlreadyExistsException;
import be.kdg.swiftby.domain.testEnv.User;
import be.kdg.swiftby.email.EmailService;
import be.kdg.swiftby.security.ProfileDto;
import be.kdg.swiftby.security.service.ProfileServiceInt;
import com.postmarkapp.postmark.client.exception.PostmarkException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import java.io.IOException;

@Controller
public class RegistrationController {
    private final ProfileServiceInt profileService;
    private final EmailService emailService;

    public RegistrationController(ProfileServiceInt profileService, EmailService emailService) {
        this.profileService = profileService;
        this.emailService = emailService;
    }

    @GetMapping("/registration")
    public String showRegistrationForm(Model model) {
        model.addAttribute("user", new ProfileDto());
        return "registration";
    }

    //Idk if I should be handling errors here-refactor
    @PostMapping("/registration")
    public ModelAndView registerUserAccount(@ModelAttribute("user") @Valid ProfileDto userDto,
                                            BindingResult result,
                                            HttpServletRequest request) throws IOException, PostmarkException {
        ModelAndView mav = new ModelAndView();

        if (result.hasErrors()) {
            mav.setViewName("registration");
            return mav;
        }

        try {
            User registered = profileService.registerNewUserAccount(userDto);
        } catch (AlreadyExistsException ex) {
            result.rejectValue("username", "user.exists", "An account for that email already exists.");
            mav.setViewName("registration");
            return mav;
        }

        emailService.sendAccountRegistrationEmail(userDto.getUsername(), userDto.getFirstName(), "www.swiftby.be");
        return new ModelAndView("successRegister", "user", userDto);
    }
}
