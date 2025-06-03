package com.alerta_sp.mvc_admin.controller;

import com.alerta_sp.mvc_admin.dto.CorregoGestao;
import com.alerta_sp.mvc_admin.dto.CorregoView;
import com.alerta_sp.mvc_admin.dto.CorregoFormDTO;
import com.alerta_sp.mvc_admin.service.CorregoService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controller responsável pelas operações CRUD de Córregos
 * (listagem, criação, edição, remoção, exibição de mapa).
 */
@Controller
@RequestMapping("/admin/corregos")
public class CorregoController {

    private final CorregoService corregoService;

    public CorregoController(CorregoService corregoService) {
        this.corregoService = corregoService;
    }

    @GetMapping
    public String listarCorregos(Model model) {
        List<CorregoView> todos = corregoService.listarTodos();

        var listaParaTela = todos.stream().map(c -> {
            String localizacao = c.latitude() + ", " + c.longitude();
            Double nivelAtual = 0.0; // stub: você pode substituir pela última leitura real
            return new CorregoGestao(
                    c.id(),
                    c.nome(),
                    localizacao,
                    nivelAtual,
                    c.nivelAlerta(),
                    c.nivelCritico()
            );
        }).toList();

        model.addAttribute("corregos", listaParaTela);
        return "gestao_corrego";
    }

    @GetMapping("/novo")
    public String exibirFormularioNovo(Model model) {
        model.addAttribute("corregoForm", new CorregoFormDTO());
        return "formulario_corrego";
    }

    @PostMapping
    public String salvarNovo(
            @Valid @ModelAttribute("corregoForm") CorregoFormDTO formDto,
            BindingResult bindingResult,
            Model model
    ) {
        if (bindingResult.hasErrors()) {
            // se houver erros de validação, retorna ao mesmo formulário mostrando mensagens
            return "formulario_corrego";
        }

        corregoService.salvar(formDto);
        return "redirect:/admin/corregos";
    }

    @GetMapping("/editar/{id}")
    public String exibirFormularioEdicao(@PathVariable("id") Long id, Model model) {
        var opt = corregoService.buscarPorId(id);

        if (opt.isEmpty()) {
            return "redirect:/admin/corregos";
        }

        CorregoView c = opt.get();
        // Preencher o DTO de edição com os dados existentes
        CorregoFormDTO form = new CorregoFormDTO();
        form.setNome(c.nome());
        form.setLatitude(c.latitude());
        form.setLongitude(c.longitude());
        form.setNivelAlerta(c.nivelAlerta());
        form.setNivelCritico(c.nivelCritico());
        // (se tiver mais campos, copie aqui)

        model.addAttribute("corregoForm", form);
        model.addAttribute("idCorregoEdicao", id);
        return "formulario_corrego";
    }

    @PostMapping("/{id}")
    public String atualizar(
            @PathVariable("id") Long id,
            @Valid @ModelAttribute("corregoForm") CorregoFormDTO formDto,
            BindingResult bindingResult,
            Model model
    ) {
        if (bindingResult.hasErrors()) {
            // volta para o formulário de edição
            model.addAttribute("idCorregoEdicao", id);
            return "formulario_corrego";
        }

        corregoService.atualizar(id, formDto);
        return "redirect:/admin/corregos";
    }

    @GetMapping("/remover/{id}")
    public String remover(@PathVariable("id") Long id) {
        corregoService.deletarPorId(id);
        return "redirect:/admin/corregos";
    }

    @GetMapping("/mapa/{id}")
    public String exibirMapa(@PathVariable("id") Long id, Model model) {

        model.addAttribute("idCorrego", id);
        return "mapa_corrego";

    }
}
