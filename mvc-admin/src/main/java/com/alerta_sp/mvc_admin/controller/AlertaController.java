package com.alerta_sp.mvc_admin.controller;

import com.alerta_sp.mvc_admin.dto.AlertaDTO;
import com.alerta_sp.mvc_admin.dto.AlertaView;
import com.alerta_sp.mvc_admin.messaging.AlertaProducer;
import com.alerta_sp.mvc_admin.service.AlertaService;
import com.alerta_sp.mvc_admin.service.CorregoService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/admin/alertas")
public class AlertaController {

    private final AlertaService alertaService;
    private final AlertaProducer alertaProducer;
    private final CorregoService corregoService;

    public AlertaController(AlertaService alertaService,
                            AlertaProducer alertaProducer,
                            CorregoService corregoService) {
        this.alertaService = alertaService;
        this.alertaProducer = alertaProducer;
        this.corregoService = corregoService;
    }

    // Lista todos os alertas
    @GetMapping
    public String listarAlertas(Model model) {
        List<AlertaView> alertas = alertaService.listarUltimosAlertas(50);
        model.addAttribute("alertas", alertas);
        return "alertas";
    }

    // Exibe o formulário de emissão de alerta
    @GetMapping("/emitir")
    public String exibirFormularioEmitir(Model model) {
        model.addAttribute("alertaDTO", new AlertaDTO());
        model.addAttribute("corregos", corregoService.listarTodos()); // lista para dropdown
        return "emitir_alerta";
    }

    // Processa o envio do alerta
    @PostMapping("/emitir")
    public String emitirAlerta(@ModelAttribute AlertaDTO alertaDTO) {
        alertaService.salvar(alertaDTO);
        alertaProducer.enviarAlerta(alertaDTO);
        return "redirect:/admin/alertas";
    }
}
