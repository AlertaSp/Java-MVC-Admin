package com.alerta_sp.mvc_admin.controller;

import com.alerta_sp.mvc_admin.dto.AlertaView;
import com.alerta_sp.mvc_admin.service.AlertaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/admin/alertas")
public class AlertaController {

    private final AlertaService alertaService;

    @Autowired
    public AlertaController(AlertaService alertaService) {
        this.alertaService = alertaService;
    }

    @GetMapping
    public String listarAlertas(Model model) {
        // Aqui estou pegando os 50 mais recentes, ajuste conforme seu contexto
        List<AlertaView> alertas = alertaService.listarUltimosAlertas(50);
        model.addAttribute("alertas", alertas);
        return "alertas";
    }

    @GetMapping("/{id}")
    public String verDetalhes(@PathVariable Long id, Model model) {
        // TODO: implementar buscarPorId ou similar no serviço
        model.addAttribute("alerta", new AlertaView(id, "Detalhes fictícios ainda não implementados."));
        return "detalhe_alerta"; // futura página de detalhes
    }

    @GetMapping("/encerrar/{id}")
    public String encerrarAlerta(@PathVariable Long id) {
        // TODO: implementar lógica de encerramento no serviço
        System.out.println("Encerrando alerta ID: " + id); // placeholder
        return "redirect:/admin/alertas";
    }
}
