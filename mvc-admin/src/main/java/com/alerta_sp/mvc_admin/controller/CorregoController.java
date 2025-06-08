package com.alerta_sp.mvc_admin.controller;

import com.alerta_sp.mvc_admin.dto.CorregoFormDTO;
import com.alerta_sp.mvc_admin.dto.CorregoGestao;
import com.alerta_sp.mvc_admin.dto.CorregoView;
import com.alerta_sp.mvc_admin.service.CorregoService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
        List<CorregoGestao> listaParaTela = todos.stream().map(c -> {
            String localizacao = c.latitude() + ", " + c.longitude();
            Double nivelAtual = corregoService.buscarNivelAtual(c.id());
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
        model.addAttribute("formCorrego", new CorregoFormDTO());
        return "formulario_corrego"; // tela de cadastro
    }

    @PostMapping
    public String salvarNovo(@Valid @ModelAttribute("formCorrego") CorregoFormDTO formDto,
                             BindingResult result,
                             Model model) {
        if (result.hasErrors()) {
            return "formulario_corrego";
        }
        corregoService.salvar(formDto);
        return "redirect:/admin/corregos";
    }

    @GetMapping("/editar/{id}")
    public String exibirFormularioEdicao(@PathVariable Long id, Model model) {
        return corregoService.buscarPorId(id)
                .map(c -> {
                    CorregoFormDTO dto = new CorregoFormDTO();
                    dto.setNome(c.nome());
                    dto.setLatitude(c.latitude());
                    dto.setLongitude(c.longitude());
                    dto.setNivelAtual(0.0); // ou valor real se houver
                    dto.setNivelAlerta(c.nivelAlerta());
                    dto.setNivelCritico(c.nivelCritico());

                    model.addAttribute("formCorrego", dto);
                    model.addAttribute("idCorregoEdicao", id);
                    return "editar_corrego"; // tela separada para edição
                })
                .orElse("redirect:/admin/corregos");
    }

    @PostMapping("/{id}")
    public String atualizar(@PathVariable Long id,
                            @Valid @ModelAttribute("formCorrego") CorregoFormDTO formDto,
                            BindingResult result,
                            Model model) {
        if (result.hasErrors()) {
            model.addAttribute("idCorregoEdicao", id);
            return "editar_corrego";
        }

        corregoService.atualizar(id, formDto);
        return "redirect:/admin/corregos";
    }

    @GetMapping("/remover/{id}")
    public String remover(@PathVariable Long id) {
        corregoService.deletarPorId(id);
        return "redirect:/admin/corregos";
    }
}
