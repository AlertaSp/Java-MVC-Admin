package com.alerta_sp.mvc_admin.controller;

import com.alerta_sp.mvc_admin.dto.LeituraFormDTO;
import com.alerta_sp.mvc_admin.dto.LeituraView;
import com.alerta_sp.mvc_admin.service.LeituraSensorService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/admin/leitura-sensores")
public class LeituraSensorController {

    private final LeituraSensorService leituraService;

    public LeituraSensorController(LeituraSensorService leituraService) {
        this.leituraService = leituraService;
    }

    @GetMapping
    public String listar(Model model) {
        List<LeituraView> leituras = leituraService.listarTodas();
        model.addAttribute("leituras", leituras);
        return "gestao_leitura_sensores"; // template Thymeleaf de lista
    }

    @GetMapping("/novo")
    public String abrirFormulario(Model model) {
        model.addAttribute("leituraForm", new LeituraFormDTO());
        model.addAttribute("sensores", leituraService.listarSensoresDisponiveis());
        return "formulario_leitura_sensor"; // template Thymeleaf de form
    }

    @PostMapping
    public String salvar(@Valid @ModelAttribute("leituraForm") LeituraFormDTO dto,
                         BindingResult result,
                         RedirectAttributes redirect,
                         Model model) {
        if (result.hasErrors()) {
            model.addAttribute("sensores", leituraService.listarSensoresDisponiveis());
            return "formulario_leitura_sensor";
        }

        try {
            leituraService.salvar(dto);
            redirect.addFlashAttribute("sucesso", "Leitura cadastrada com sucesso!");
        } catch (IllegalArgumentException ex) {
            result.rejectValue("nivel", "error.leituraForm", ex.getMessage());
            model.addAttribute("sensores", leituraService.listarSensoresDisponiveis());
            return "formulario_leitura_sensor";
        }

        return "redirect:/admin/leitura-sensores";
    }

    @GetMapping("/remover/{id}")
    public String remover(@PathVariable("id") Long id, RedirectAttributes redirect) {
        try {
            leituraService.deletarPorId(id);
            redirect.addFlashAttribute("sucesso", "Leitura removida com sucesso!");
        } catch (IllegalArgumentException ex) {
            redirect.addFlashAttribute("erro", ex.getMessage());
        }
        return "redirect:/admin/leitura-sensores";
    }
}
