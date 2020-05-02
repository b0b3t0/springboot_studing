package com.exercises.demo.web.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;


@Controller
public class HomeController extends com.exercises.demo.web.controllers.BaseController {
    @GetMapping("/")
    public ModelAndView index() {

        return this.view("index");
    }

    @GetMapping(value = "/home", produces = "text/html")
    public ModelAndView home(HttpSession session, RedirectAttributes attributes, ModelAndView model) {
        if (!this.isLoggedIn(session)) {
            return this.redirect("/login");
        }

        attributes.getFlashAttributes().entrySet().forEach(x ->
                model.addObject(x.getKey(), x.getValue()));

        return this.view("home", model);
    }
}
