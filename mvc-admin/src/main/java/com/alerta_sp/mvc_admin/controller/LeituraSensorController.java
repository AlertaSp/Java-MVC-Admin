package com.alerta_sp.mvc_admin.controller;

import com.alerta_sp.mvc_admin.dto.CorregoView;
import com.alerta_sp.mvc_admin.dto.LeituraFormDTO;
import com.alerta_sp.mvc_admin.dto.SensorView;
import com.alerta_sp.mvc_admin.dto.LeituraDTO;
import com.alerta_sp.mvc_admin.service.CorregoService;
import com.alerta_sp.mvc_admin.service.LeituraSensorService;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

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

    @GetMapping("/leitura/novo")
    public String novaLeituraForm(Model model) {
        List<SensorView> sensores = leituraSensorService.listarSensoresDisponiveis();
        model.addAttribute("leitura", new LeituraFormDTO());
        model.addAttribute("sensores", sensores);
        return "formulario_leitura";
    }

    @PostMapping("/leitura")
    public String salvarNovaLeitura(@ModelAttribute("leitura") LeituraFormDTO leitura) {
        leituraSensorService.salvar(leitura);
        return "redirect:/admin/dashboard";
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

    // Endpoint REST para fornecer as leituras ao gráfico via fetch()
    @GetMapping("/leituras/dados")
    public @ResponseBody List<LeituraDTO> obterLeituras(@RequestParam("idCorrego") Long idCorrego) {
        return leituraSensorService.buscarUltimasLeituras(idCorrego);
    }

}
