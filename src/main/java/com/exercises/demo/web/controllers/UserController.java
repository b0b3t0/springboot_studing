package com.exercises.demo.web.controllers;

import com.exercises.demo.domain.entities.User;
import com.exercises.demo.domain.models.bindings.RegisterUserBindingModel;
import com.exercises.demo.repository.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;

@Controller
public class UserController extends BaseController{

    private final UserRepository userRepository;
    private final ModelMapper modelMapper;

    @Autowired
    public UserController(UserRepository userRepository, ModelMapper modelMapper) {
        this.userRepository = userRepository;
        this.modelMapper = modelMapper;
    }

    @GetMapping("/login")
    public ModelAndView login() {
        return this.view("login");
    }

    @GetMapping("/register")
    public ModelAndView register(RedirectAttributes redirectAttributes, ModelAndView model) {
        if(redirectAttributes.asMap().size() == 0) {
            model.addObject("model", new RegisterUserBindingModel());
        } else {
            redirectAttributes.mergeAttributes(model.getModel());
        }
        
        return this.view("register");
    }

    @PostMapping("/register")
        public ModelAndView registerConfirm(@ModelAttribute @Valid RegisterUserBindingModel bindingModel,
                                            BindingResult bindingResult, RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors() ||
                (bindingModel.getConfirmPassword().equals(bindingModel.getPassword()))) {
            redirectAttributes.addFlashAttribute("user", bindingModel);
            return this.redirect("/register");
        }

        User user = this.modelMapper.map(bindingResult, User.class);
        this.userRepository.save(user);

        return this.redirect("/login");
    }
}
