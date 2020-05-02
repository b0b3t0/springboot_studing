package com.exercises.demo.web.controllers;

import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;

public abstract class BaseController {

    protected boolean isLoggedIn(HttpSession session) {
        return session.getAttribute("user") != null;
    }


    protected ModelAndView view(String view, ModelAndView modelAndView) {
        modelAndView.setViewName(view);

        return modelAndView;
    }

    protected ModelAndView view(String view) {
        return this.view(view, new ModelAndView());
    }

    protected ModelAndView redirect(String url) {
        return this.view("redirect:" + url);
    }
}
