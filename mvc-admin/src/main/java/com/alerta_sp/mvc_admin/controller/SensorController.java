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

@Controller
@RequestMapping("/admin/sensores")
public class SensorController {

    private final SensorService sensorService;
    private final CorregoService corregoService;

    public SensorController(SensorService sensorService, CorregoService corregoService) {
        this.sensorService = sensorService;
        this.corregoService = corregoService;
    }

    @GetMapping
    public String listarSensores(Model model) {
        List<SensorView> todos = sensorService.listarTodos();
        model.addAttribute("sensores", todos);
        return "gestao_sensores";
    }

    @GetMapping("/novo")
    public String exibirFormularioCadastro(Model model) {
        model.addAttribute("sensorForm", new SensorFormDTO());
        model.addAttribute("corregos", corregoService.listarTodos());
        return "formulario_sensores";
    }

    @PostMapping
    public String salvarSensor(
            @Valid @ModelAttribute("sensorForm") SensorFormDTO formDto,
            BindingResult bindingResult,
            Model model
    ) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("corregos", corregoService.listarTodos());
            return "formulario_sensores";
        }

        sensorService.salvar(formDto);
        return "redirect:/admin/sensores";
    }

    @GetMapping("/editar/{id}")
    public String exibirFormularioEdicao(@PathVariable("id") Long id, Model model) {
        return sensorService.buscarPorId(id)
                .map(sensor -> {
                    SensorFormDTO form = new SensorFormDTO();
                    form.setCodigo(sensor.codigo());
                    form.setDataInstalacao(sensor.dataInstalacao());
                    form.setStatus(sensor.status());
                    form.setIdCorrego(sensor.idCorrego());

                    model.addAttribute("sensorForm", form);
                    model.addAttribute("idSensor", id);
                    model.addAttribute("corregos", corregoService.listarTodos());
                    return "editar_sensor";
                })
                .orElse("redirect:/admin/sensores");
    }

    @PostMapping("/{id}")
    public String atualizar(
            @PathVariable("id") Long id,
            @Valid @ModelAttribute("sensorForm") SensorFormDTO formDto,
            BindingResult bindingResult,
            Model model
    ) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("idSensor", id);
            model.addAttribute("corregos", corregoService.listarTodos());
            return "editar_sensor";
        }

        sensorService.atualizar(id, formDto);
        return "redirect:/admin/sensores";
    }

    @GetMapping("/remover/{id}")
    public String removerSensor(@PathVariable("id") Long id) {
        sensorService.deletarPorId(id);
        return "redirect:/admin/sensores";
    }
}
