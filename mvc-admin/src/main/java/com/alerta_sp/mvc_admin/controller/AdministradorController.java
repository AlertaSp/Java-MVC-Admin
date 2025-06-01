package com.alerta_sp.mvc_admin.controller;

import com.alerta_sp.mvc_admin.dto.AdministradorFormDTO;
import com.alerta_sp.mvc_admin.dto.AdministradorView;
import com.alerta_sp.mvc_admin.service.AdministradorService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/admin/administradores")
public class AdministradorController {

    private final AdministradorService administradorService;

    public AdministradorController(AdministradorService administradorService) {
        this.administradorService = administradorService;
    }

    @GetMapping
    public String listar(Model model) {
        List<AdministradorView> admins = administradorService.listarTodos();
        model.addAttribute("admins", admins);
        return "gestao_administradores"; // template Thymeleaf de lista
    }

    @GetMapping("/novo")
    public String abrirFormulario(Model model) {
        model.addAttribute("adminForm", new AdministradorFormDTO());
        return "formulario_administrador"; // template Thymeleaf de form
    }

    @PostMapping
    public String salvar(@Valid @ModelAttribute("adminForm") AdministradorFormDTO dto,
                         BindingResult result,
                         RedirectAttributes redirect) {
        if (result.hasErrors()) {
            return "formulario_administrador";
        }

        try {
            administradorService.salvar(dto);
            redirect.addFlashAttribute("sucesso", "Administrador cadastrado com sucesso!");
        } catch (IllegalArgumentException ex) {
            result.rejectValue("username", "error.adminForm", ex.getMessage());
            return "formulario_administrador";
        }

        return "redirect:/admin/administradores";
    }

    @GetMapping("/remover/{id}")
    public String remover(@PathVariable("id") Long id, RedirectAttributes redirect) {
        try {
            administradorService.deletarPorId(id);
            redirect.addFlashAttribute("sucesso", "Administrador removido com sucesso!");
        } catch (IllegalArgumentException ex) {
            redirect.addFlashAttribute("erro", ex.getMessage());
        }
        return "redirect:/admin/administradores";
    }
}
