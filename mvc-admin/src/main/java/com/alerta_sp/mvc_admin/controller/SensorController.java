package com.alerta_sp.mvc_admin.controller;

import com.alerta_sp.mvc_admin.dto.SensorFormDTO;
import com.alerta_sp.mvc_admin.dto.SensorView;
import com.alerta_sp.mvc_admin.service.SensorService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/admin/sensores")
public class SensorController {

    private final SensorService sensorService;

    public SensorController(SensorService sensorService) {
        this.sensorService = sensorService;
    }

    /**
     * Lista todos os sensores.
     */
    @GetMapping
    public String listar(Model model) {
        // Agora sensorService.listarTodos() retorna List<SensorView>
        List<SensorView> sensores = sensorService.listarTodos();
        model.addAttribute("sensores", sensores);
        return "gestao_sensores"; // nome do template Thymeleaf
    }

    /**
     * Abre o formulário para cadastrar novo sensor.
     */
    @GetMapping("/novo")
    public String abrirFormulario(Model model) {
        model.addAttribute("sensorForm", new SensorFormDTO());
        // Para popular o <select> de córregos:
        model.addAttribute("corregos", sensorService.listarCorregosDisponiveis());
        return "formulario_sensor";
    }

    /**
     * Recebe o POST do formulário de cadastro e salva um novo sensor.
     */
    @PostMapping
    public String salvar(@Valid @ModelAttribute("sensorForm") SensorFormDTO dto,
                         BindingResult result,
                         RedirectAttributes redirect,
                         Model model) {
        if (result.hasErrors()) {
            // Em caso de erro de validação, volta ao formulário
            model.addAttribute("corregos", sensorService.listarCorregosDisponiveis());
            return "formulario_sensor";
        }

        try {
            sensorService.salvarSensor(dto);
            redirect.addFlashAttribute("sucesso", "Sensor cadastrado com sucesso!");
        } catch (IllegalArgumentException ex) {
            // Exibe erro de negócio (por exemplo, código duplicado)
            result.rejectValue("codigo", "error.sensorForm", ex.getMessage());
            model.addAttribute("corregos", sensorService.listarCorregosDisponiveis());
            return "formulario_sensor";
        }

        return "redirect:/admin/sensores";
    }

    /**
     * Abre o formulário para editar um sensor existente.
     */
    @GetMapping("/editar/{id}")
    public String abrirEditar(@PathVariable("id") Long id,
                              Model model,
                              RedirectAttributes redirect) {
        // Agora buscarPorId retorna Optional<SensorView>
        SensorView view = sensorService.buscarPorId(id)
                .orElse(null);

        if (view == null) {
            redirect.addFlashAttribute("erro", "Sensor não encontrado.");
            return "redirect:/admin/sensores";
        }

        // Preenche o SensorFormDTO com os dados de SensorView
        SensorFormDTO dto = new SensorFormDTO();
        dto.setCodigo(view.codigo());
        dto.setDataInstalacao(view.dataInstalacao());
        dto.setStatus(view.status());
        dto.setIdCorrego(view.idCorrego());

        model.addAttribute("sensorForm", dto);
        model.addAttribute("corregos", sensorService.listarCorregosDisponiveis());
        model.addAttribute("idSensor", id);
        return "formulario_sensor";
    }

    /**
     * Recebe o POST para atualizar um sensor.
     */
    @PostMapping("/editar/{id}")
    public String atualizar(@PathVariable("id") Long id,
                            @Valid @ModelAttribute("sensorForm") SensorFormDTO dto,
                            BindingResult result,
                            RedirectAttributes redirect,
                            Model model) {
        if (result.hasErrors()) {
            model.addAttribute("corregos", sensorService.listarCorregosDisponiveis());
            return "formulario_sensor";
        }

        try {
            sensorService.atualizarSensor(id, dto);
            redirect.addFlashAttribute("sucesso", "Sensor atualizado com sucesso!");
        } catch (IllegalArgumentException ex) {
            result.rejectValue("codigo", "error.sensorForm", ex.getMessage());
            model.addAttribute("corregos", sensorService.listarCorregosDisponiveis());
            return "formulario_sensor";
        }

        return "redirect:/admin/sensores";
    }

    /**
     * Remove um sensor pelo ID.
     */
    @GetMapping("/remover/{id}")
    public String remover(@PathVariable("id") Long id, RedirectAttributes redirect) {
        try {
            sensorService.deletarPorId(id);
            redirect.addFlashAttribute("sucesso", "Sensor removido com sucesso!");
        } catch (IllegalArgumentException ex) {
            redirect.addFlashAttribute("erro", ex.getMessage());
        }
        return "redirect:/admin/sensores";
    }
}
