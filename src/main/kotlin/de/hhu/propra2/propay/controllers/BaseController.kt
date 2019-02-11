package de.hhu.propra2.propay.controllers

import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping

@Controller
class BaseController {
    @GetMapping("/")
    fun home(): String {
        return "redirect:/swagger-ui.html"
    }
}