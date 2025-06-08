package com.alerta_sp.mvc_admin.controller;

import com.alerta_sp.mvc_admin.dto.CorregoDashboardView;
import com.alerta_sp.mvc_admin.dto.AlertaView;
import com.alerta_sp.mvc_admin.service.CorregoService;
import com.alerta_sp.mvc_admin.service.AlertaService;
import com.alerta_sp.mvc_admin.service.RelatorioIAService;
import org.springframework.amqp.AmqpException;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/admin")
public class DashboardController {

    private final CorregoService corregoService;
    private final AlertaService alertaService;
    private final RabbitTemplate rabbitTemplate;
    private final RelatorioIAService relatorioIAService;

    public DashboardController(CorregoService corregoService,
                               AlertaService alertaService,
                               RabbitTemplate rabbitTemplate,
                               RelatorioIAService relatorioIAService) {
        this.corregoService = corregoService;
        this.alertaService = alertaService;
        this.rabbitTemplate = rabbitTemplate;
        this.relatorioIAService = relatorioIAService;
    }

    @GetMapping("/dashboard")
    public String dashboard(Model model) {
        // 1) Lista de córregos com nível atual + status
        List<CorregoDashboardView> listaCorregos = corregoService.listarTodosComStatus();
        model.addAttribute("corregos", listaCorregos);

        // 2) Lista com os últimos 5 alertas
        List<AlertaView> listaAlertas = alertaService.listarUltimosAlertas(5);
        model.addAttribute("alertas", listaAlertas);

        // 3) Status dos serviços
        model.addAttribute("rabbitStatus", verificarRabbitmq() ? "OK" : "OFFLINE");
        model.addAttribute("aiStatus", "OK"); // Pode alterar depois se integrar com IA

        // 4) Dados para o gráfico (fictício)
        model.addAttribute("labelsHorarios", List.of("00h", "04h", "08h", "12h", "16h", "20h"));
        model.addAttribute("historicoNiveis", List.of(1.1, 1.3, 1.5, 1.7, 1.8, 2.1));

        return "dashboard";
    }

    @PostMapping("/ia/gerar-relatorio")
    public String gerarRelatorioIA(Model model) {
        String relatorio = relatorioIAService.gerarRelatorioUltimasLeituras();
        model.addAttribute("relatorioIA", relatorio);
        model.addAttribute("corregos", corregoService.listarTodosComStatus());
        model.addAttribute("alertas", alertaService.listarUltimosAlertas(5));
        return "dashboard";
    }

    private boolean verificarRabbitmq() {
        try {
            rabbitTemplate.convertAndSend("health-check-queue", "ping");
            return true;
        } catch (AmqpException e) {
            return false;
        }
    }
}
