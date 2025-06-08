package com.alerta_sp.mvc_admin.controller;

import com.alerta_sp.mvc_admin.service.RelatorioIAService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin/relatorio")
public class RelatorioIAController {

    private final RelatorioIAService relatorioIAService;

    public RelatorioIAController(RelatorioIAService relatorioIAService) {
        this.relatorioIAService = relatorioIAService;
    }

    @PostMapping("/gerar")
    public String gerar(Model model) {
        String texto = relatorioIAService.gerarRelatorio();
        model.addAttribute("relatorio", texto);
        return "relatorio_gerado";
    }
}
