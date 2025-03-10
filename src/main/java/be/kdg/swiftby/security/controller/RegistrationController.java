package be.kdg.swiftby.security.controller;

import be.kdg.swiftby.domain.testEnv.User;
import be.kdg.swiftby.security.ProfileDto;
import be.kdg.swiftby.security.UserAlreadyExistsException;
import be.kdg.swiftby.security.service.ProfileServiceInt;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class RegistrationController {
    private final ProfileServiceInt profileService;

    public RegistrationController(ProfileServiceInt profileService) {
        this.profileService = profileService;
    }

    @GetMapping("/user/registration")
    public String showRegistrationForm(WebRequest request, Model model) {
        ProfileDto profileDto = new ProfileDto();
        model.addAttribute("user", profileDto);
        return "registration";
    }

    @PostMapping("/user/registration")
    public ModelAndView registerUserAccount(@ModelAttribute("user") @Valid ProfileDto userDto,
                                            HttpServletRequest request, Errors errors,
                                            ModelAndView mav) {
      try{
          User registered = profileService.registerNewUserAccount(userDto);
      }catch (UserAlreadyExistsException uaEX) {
          mav.addObject("message", "An account for that username/email already exists.");
          return mav;
      }

        return new ModelAndView("successRegister", "user", userDto);
    }

}