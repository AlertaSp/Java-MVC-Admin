package com.alerta_sp.mvc_admin.controller;

import com.alerta_sp.mvc_admin.dto.AlertaDTO;
import com.alerta_sp.mvc_admin.dto.AlertaView;
import com.alerta_sp.mvc_admin.messaging.AlertaProducer;
import com.alerta_sp.mvc_admin.service.AlertaService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/admin/alertas")
public class AlertaController {

    private final AlertaService alertaService;
    private final AlertaProducer alertaProducer;

    public AlertaController(AlertaService alertaService, AlertaProducer alertaProducer) {
        this.alertaService = alertaService;
        this.alertaProducer = alertaProducer;
    }

    @GetMapping
    public String listarAlertas(Model model) {
        List<AlertaView> alertas = alertaService.listarUltimosAlertas(50);
        model.addAttribute("alertas", alertas);
        return "alertas";
    }

    @GetMapping("/emitir")
    public String exibirFormularioEmitir(Model model) {
        model.addAttribute("alertaDTO", new AlertaDTO());
        return "emitir_alerta";
    }

    @PostMapping("/emitir")
    public String emitirAlerta(@ModelAttribute AlertaDTO alertaDTO) {
        alertaService.salvar(alertaDTO); // opcional
        alertaProducer.enviarAlerta(alertaDTO);
        return "redirect:/admin/alertas";
    }
}
