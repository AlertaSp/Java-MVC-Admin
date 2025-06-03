package com.alerta_sp.mvc_admin.controller;

import com.alerta_sp.mvc_admin.dto.ConfiguracaoFormDTO;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin")
public class ConfiguracaoController {

    @GetMapping("/configuracoes")
    public String exibirConfiguracoes(Model model) {
        model.addAttribute("config", new ConfiguracaoFormDTO());
        return "configuracoes";
    }

    @PostMapping("/config/salvar")
    public String salvarConfiguracoes(@ModelAttribute("config") ConfiguracaoFormDTO config) {
        System.out.println("API KEY: " + config.getApiKey());
        System.out.println("Parâmetros: " + config.getParametros());
        System.out.println("Notificar por Email? " + config.isEmailNotificacao());
        System.out.println("Notificar por Push? " + config.isPushNotificacao());

        return "redirect:/admin/configuracoes";
    }
}
