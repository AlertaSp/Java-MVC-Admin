package com.alerta_sp.mvc_admin.controller;

import com.alerta_sp.mvc_admin.dto.CorregoFormDTO;
import com.alerta_sp.mvc_admin.dto.CorregoView;
import com.alerta_sp.mvc_admin.service.CorregoService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/admin/corregos")
public class CorregoController {

    private final CorregoService corregoService;

    public CorregoController(CorregoService corregoService) {
        this.corregoService = corregoService;
    }

    /**
     * Lista todos os córregos.
     */
    @GetMapping
    public String listar(Model model) {
        List<CorregoView> corregoList = corregoService.listarTodos();
        model.addAttribute("corregos", corregoList);
        return "gestao_corrego"; // nome do template Thymeleaf (ex: gestao_corrego.html)
    }

    /**
     * Exibe o formulário para cadastrar novo córrego.
     */
    @GetMapping("/novo")
    public String abrirFormulario(Model model) {
        model.addAttribute("corregoForm", new CorregoFormDTO());
        return "formulario_corrego"; // ex: formulario_corrego.html
    }

    /**
     * Recebe POST do formulário e salva um novo córrego.
     */
    @PostMapping
    public String salvar(@Valid @ModelAttribute("corregoForm") CorregoFormDTO dto,
                         BindingResult result,
                         RedirectAttributes redirect) {
        if (result.hasErrors()) {
            return "formulario_corrego";
        }

        try {
            corregoService.salvar(dto);
            redirect.addFlashAttribute("sucesso", "Córrego cadastrado com sucesso!");
        } catch (IllegalArgumentException ex) {
            // Em caso de duplicidade ou erro de negócio
            result.rejectValue("nome", "error.corregoForm", ex.getMessage());
            return "formulario_corrego";
        }

        return "redirect:/admin/corregos";
    }

    /**
     * Exibe o formulário para editar um córrego existente.
     */
    @GetMapping("/editar/{id}")
    public String abrirEditar(@PathVariable("id") Long id, Model model, RedirectAttributes redirect) {
        CorregoView view = corregoService.buscarPorId(id)
                .orElse(null);

        if (view == null) {
            redirect.addFlashAttribute("erro", "Córrego não encontrado.");
            return "redirect:/admin/corregos";
        }

        // Preenche o CorregoFormDTO com os dados atuais
        CorregoFormDTO dto = new CorregoFormDTO();
        dto.setNome(view.nome());
        dto.setLatitude(view.latitude());
        dto.setLongitude(view.longitude());
        dto.setNivelAlerta(view.nivelAlerta());
        dto.setNivelCritico(view.nivelCritico());

        model.addAttribute("corregoForm", dto);
        model.addAttribute("idCorrego", id);
        return "formulario_corrego";
    }

    /**
     * Recebe o POST para atualizar um córrego.
     */
    @PostMapping("/editar/{id}")
    public String atualizar(@PathVariable("id") Long id,
                            @Valid @ModelAttribute("corregoForm") CorregoFormDTO dto,
                            BindingResult result,
                            RedirectAttributes redirect) {
        if (result.hasErrors()) {
            return "formulario_corrego";
        }

        try {
            corregoService.atualizar(id, dto);
            redirect.addFlashAttribute("sucesso", "Córrego atualizado com sucesso!");
        } catch (IllegalArgumentException ex) {
            result.rejectValue("nome", "error.corregoForm", ex.getMessage());
            return "formulario_corrego";
        }

        return "redirect:/admin/corregos";
    }

    /**
     * Remove um córrego pelo ID.
     */
    @GetMapping("/remover/{id}")
    public String remover(@PathVariable("id") Long id, RedirectAttributes redirect) {
        try {
            corregoService.deletarPorId(id);
            redirect.addFlashAttribute("sucesso", "Córrego removido com sucesso!");
        } catch (IllegalArgumentException ex) {
            redirect.addFlashAttribute("erro", ex.getMessage());
        }
        return "redirect:/admin/corregos";
    }
}
