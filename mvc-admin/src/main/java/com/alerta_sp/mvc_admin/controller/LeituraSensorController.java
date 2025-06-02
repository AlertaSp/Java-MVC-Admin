package com.alerta_sp.mvc_admin.controller;

import com.alerta_sp.mvc_admin.dto.CorregoView;
import com.alerta_sp.mvc_admin.service.CorregoService;
import com.alerta_sp.mvc_admin.service.LeituraSensorService;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDate;
import java.util.List;

@Controller
@RequestMapping("/admin")
public class LeituraSensorController {

    private final CorregoService corregoService;
    private final LeituraSensorService leituraSensorService;

    public LeituraSensorController(
            CorregoService corregoService,
            LeituraSensorService leituraSensorService
    ) {
        this.corregoService       = corregoService;
        this.leituraSensorService = leituraSensorService;
    }

    /**
     * 1) GET /admin/leituras
     *    → Exibe a página de visualização de leituras (gestao_leitura_sensores.html).
     *    → Popula apenas a lista de córregos no <select>.
     */
    @GetMapping("/leituras")
    public String mostrarPaginaDeLeituras(Model model) {
        List<CorregoView> listaCorregos = corregoService.listarTodos();
        model.addAttribute("corregos", listaCorregos);
        return "gestao_leitura_sensores";
    }

    /**
     * 2) GET /admin/leituras/filtro
     *    → Recebe os parâmetros corrego, inicio e fim.
     *    → (Por enquanto) só recarrega a lista de córregos para que o form volte a aparecer
     *      sem 404/500. No futuro, você pode usar leituraSensorService para buscar
     *      as leituras filtradas e enviar listas “labels” e “valores” para o gráfico.
     */
    @GetMapping("/leituras/filtro")
    public String filtrarLeituras(
            @RequestParam("corrego") Long corregoId,
            @RequestParam("inicio")
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate inicio,
            @RequestParam("fim")
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fim,
            Model model
    ) {
        // 1. Recarrega a lista de córregos para manter o <select> populado:
        List<CorregoView> listaCorregos = corregoService.listarTodos();
        model.addAttribute("corregos", listaCorregos);

        return "gestao_leitura_sensores";
    }

}
