package com.alerta_sp.mvc_admin.controller;

import com.alerta_sp.mvc_admin.dto.SensorFormDTO;
import com.alerta_sp.mvc_admin.dto.SensorView;
import com.alerta_sp.mvc_admin.dto.CorregoView;
import com.alerta_sp.mvc_admin.service.SensorService;
import com.alerta_sp.mvc_admin.service.CorregoService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controller responsável pelas operações CRUD de Sensores:
 *   • listar todos (gestao_sensores.html)
 *   • criar (formulario_sensor.html)
 *   • editar (formulario_sensor.html)
 *   • remover
 */
@Controller
@RequestMapping("/admin/sensores")
public class SensorController {

    private final SensorService sensorService;
    private final CorregoService corregoService;

    public SensorController(SensorService sensorService,
                            CorregoService corregoService) {
        this.sensorService  = sensorService;
        this.corregoService = corregoService;
    }

    /**
     * 1) GET  /admin/sensores
     *    => Página de listagem de sensores (gestao_sensores.html).
     *    => Espera um atributo “sensores” contendo List<SensorView>.
     */
    @GetMapping
    public String listarSensores(Model model) {
        List<SensorView> todos = sensorService.listarTodos();
        model.addAttribute("sensores", todos);
        return "gestao_sensores";
    }

    /**
     * 2) GET  /admin/sensores/novo
     *    => Exibe o formulário de cadastro (formulario_sensor.html).
     *    => No model, adiciona:
     *         • “sensorForm” → um SensorFormDTO vazio
     *         • “corregos”   → lista de CorregoView para popular o <select>
     */
    @GetMapping("/novo")
    public String exibirFormularioCadastro(Model model) {
        model.addAttribute("sensorForm", new SensorFormDTO());
        List<CorregoView> listaCorregos = corregoService.listarTodos();
        model.addAttribute("corregos", listaCorregos);
        return "formulario_sensor";
    }

    /**
     * 3) POST /admin/sensores
     *    => Recebe o SensorFormDTO do formulário de criação, valida e salva.
     *    => Se houver erros, volta ao “formulario_sensor.html” mostrando mensagens.
     *    => Se tudo OK, redireciona para GET /admin/sensores (listagem).
     */
    @PostMapping
    public String salvarSensor(
            @Valid @ModelAttribute("sensorForm") SensorFormDTO formDto,
            BindingResult bindingResult,
            Model model
    ) {
        // Se houver erros de validação, reabastece lista de córregos e retorna ao formulário:
        if (bindingResult.hasErrors()) {
            List<CorregoView> listaCorregos = corregoService.listarTodos();
            model.addAttribute("corregos", listaCorregos);
            return "formulario_sensor";
        }

        sensorService.salvar(formDto);
        return "redirect:/admin/sensores";
    }

    /**
     * 4) GET  /admin/sensores/editar/{id}
     *    => Carrega o sensor existente no formulário para edição (formulario_sensor.html).
     *    => No model, adiciona:
     *         • “sensorForm”     → SensorFormDTO preenchido com dados do sensor
     *         • “idSensor”       → o ID do sensor (para que o template saiba exibir o botão “Atualizar”)
     *         • “corregos”       → lista de CorregoView (para popular o <select>)
     */
    @GetMapping("/editar/{id}")
    public String exibirFormularioEdicao(
            @PathVariable("id") Long id,
            Model model
    ) {
        var optView = sensorService.buscarPorId(id);
        if (optView.isEmpty()) {
            // Se não encontrar, redireciona de volta para a lista
            return "redirect:/admin/sensores";
        }

        SensorView s = optView.get();
        // Monta o DTO de edição usando os valores do SensorView:
        SensorFormDTO form = new SensorFormDTO();
        form.setCodigo(s.codigo());
        form.setDataInstalacao(s.dataInstalacao());
        form.setStatus(s.status());
        form.setIdCorrego(s.idCorrego());
        // (caso tenha mais campos no SensorFormDTO, preencha aqui)

        model.addAttribute("sensorForm", form);
        model.addAttribute("idSensor", id);

        List<CorregoView> listaCorregos = corregoService.listarTodos();
        model.addAttribute("corregos", listaCorregos);

        return "formulario_sensor";
    }

    /**
     * 5) POST /admin/sensores/editar/{id}
     *    => Recebe o SensorFormDTO do formulário de edição, valida e atualiza.
     *    => Se houver erros, volta ao formulário de edição;
     *    => Senão, redireciona para a lista de sensores.
     */
    @PostMapping("/editar/{id}")
    public String atualizarSensor(
            @PathVariable("id") Long id,
            @Valid @ModelAttribute("sensorForm") SensorFormDTO formDto,
            BindingResult bindingResult,
            Model model
    ) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("idSensor", id);
            List<CorregoView> listaCorregos = corregoService.listarTodos();
            model.addAttribute("corregos", listaCorregos);
            return "formulario_sensor";
        }

        sensorService.atualizar(id, formDto);
        return "redirect:/admin/sensores";
    }

    /**
     * 6) GET /admin/sensores/remover/{id}
     *    => Remove o sensor e redireciona para a lista de sensores.
     */
    @GetMapping("/remover/{id}")
    public String removerSensor(@PathVariable("id") Long id) {
        sensorService.deletarPorId(id);
        return "redirect:/admin/sensores";
    }

}
