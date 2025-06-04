package com.alerta_sp.mvc_admin.controller;

import com.alerta_sp.mvc_admin.dto.AlertaDTO;
import com.alerta_sp.mvc_admin.dto.AlertaView;
import com.alerta_sp.mvc_admin.messaging.AlertaProducer;
import com.alerta_sp.mvc_admin.model.Corrego;
import com.alerta_sp.mvc_admin.repository.CorregoRepository;
import com.alerta_sp.mvc_admin.service.AlertaService;
import com.alerta_sp.mvc_admin.service.CorregoService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Locale;

@Controller
@RequestMapping("/admin/alertas")
public class AlertaController {

    private final AlertaService alertaService;
    private final AlertaProducer alertaProducer;
    private final CorregoService corregoService;
    private final MessageSource messageSource;
    private final CorregoRepository corregoRepository;

    public AlertaController(
            AlertaService alertaService,
            AlertaProducer alertaProducer,
            CorregoService corregoService,
            MessageSource messageSource,
            CorregoRepository corregoRepository) {
        this.alertaService = alertaService;
        this.alertaProducer = alertaProducer;
        this.corregoService = corregoService;
        this.messageSource = messageSource;
        this.corregoRepository = corregoRepository;
    }

    // Lista os últimos 50 alertas
    @GetMapping
    public String listarAlertas(Model model, Locale locale) {
        List<AlertaView> alertas = alertaService.listarUltimosAlertas(50);

        alertas.forEach(alerta -> {
            String key = (alerta.getTipo() != null)
                    ? "alertas.tipo." + alerta.getTipo().name().toLowerCase()
                    : "alertas.tipo.indefinido";
            alerta.setTipoDescricao(messageSource.getMessage(key, null, locale));
        });

        model.addAttribute("alertas", alertas);
        return "alertas";
    }

    // Exibe o formulário de emissão
    @GetMapping("/emitir")
    public String exibirFormularioEmitir(Model model) {
        model.addAttribute("alertaDTO", new AlertaDTO());
        model.addAttribute("corregos", corregoService.listarTodos());
        return "emitir_alerta";
    }

    // Processa o envio do alerta
    @PostMapping("/emitir")
    public String emitirAlerta(@ModelAttribute AlertaDTO alertaDTO) {
        Corrego corrego = corregoRepository.findById(alertaDTO.getIdCorrego())
                .orElseThrow(() -> new EntityNotFoundException(
                        "Córrego não encontrado: " + alertaDTO.getIdCorrego()));

        alertaDTO.setCorrego(corrego.getNome());
        alertaProducer.enviarAlerta(alertaDTO);

        return "redirect:/admin/alertas";
    }
}
