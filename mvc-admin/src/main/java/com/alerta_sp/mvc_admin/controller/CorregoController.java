package com.alerta_sp.mvc_admin.controller;

import com.alerta_sp.mvc_admin.dto.CorregoGestao;
import com.alerta_sp.mvc_admin.dto.CorregoView;
import com.alerta_sp.mvc_admin.dto.CorregoFormDTO;
import com.alerta_sp.mvc_admin.service.CorregoService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controller responsável pelas operações CRUD de Córregos
 * (listagem, criação, edição, remoção, exibição de mapa).
 */
@Controller
@RequestMapping("/admin/corregos")
public class CorregoController {

    private final CorregoService corregoService;

    public CorregoController(CorregoService corregoService) {
        this.corregoService = corregoService;
    }

    /**
     * 1) GET  /admin/corregos
     *      => Exibe a página “Gestão de Córregos” (gestao_corrego.html)
     *      => Precisamos colocar no model o atributo “corregos” (lista de CorregoView).
     */
    @GetMapping
    public String listarCorregos(Model model) {
        List<CorregoView> todos = corregoService.listarTodos();

        /*
         * OBSERVAÇÃO IMPORTANTE:
         * O seu template “gestao_corrego.html” faz th:each="corrego : ${corregos}"
         * e espera, para cada item, propriedades:
         *   corrego.id
         *   corrego.nome
         *   corrego.localizacao    ← que não existe em CorregoView, mas pode ser formado a partir de latitude+longitude
         *   corrego.nivelAtual     ← você pode ter que buscar, por exemplo, a última leitura (se não tiver, mantenha 0.0)
         *   corrego.nivelAlerta
         *   corrego.nivelCritico
         *
         * Por enquanto, este exemplo assume que você quer usar diretamente “latitude” e “longitude”
         * dentro do template, e que “nivelAtual” não existe ainda (ficará nulo ou zero).
         * Depois, você pode ajustar:
         *   • No CorregoView, inclua um método getLocalizacao() que concatene latitude + ", " + longitude
         *   • Inclua um campo “nivelAtual” em CorregoView, se já tiver disponível.
         *
         * Mas, para que o Thymeleaf não quebre, vamos criar um DTO auxiliar “CorregoGestao” em tempo de execução:
         */
        var listaParaTela = todos.stream().map(c -> {
            String localizacao = c.latitude() + ", " + c.longitude();
            Double nivelAtual = 0.0; // stub: você pode substituir pela última leitura real
            return new CorregoGestao(
                    c.id(),
                    c.nome(),
                    localizacao,
                    nivelAtual,
                    c.nivelAlerta(),
                    c.nivelCritico()
            );
        }).toList();

        model.addAttribute("corregos", listaParaTela);
        return "gestao_corrego";
    }

    /**
     * 2) GET  /admin/corregos/novo
     *      => Exibe o formulário para cadastrar um novo córrego
     *      => Esse formulário deve mapear um objeto CorregoFormDTO
     */
    @GetMapping("/novo")
    public String exibirFormularioNovo(Model model) {
        model.addAttribute("corregoForm", new CorregoFormDTO());
        return "formulario_corrego";
    }

    /**
     * 3) POST /admin/corregos
     *      => Recebe o CorregoFormDTO submetido do formulário e tenta salvar
     */
    @PostMapping
    public String salvarNovo(
            @Valid @ModelAttribute("corregoForm") CorregoFormDTO formDto,
            BindingResult bindingResult,
            Model model
    ) {
        if (bindingResult.hasErrors()) {
            // se houver erros de validação, retorna ao mesmo formulário mostrando mensagens
            return "formulario_corrego";
        }

        corregoService.salvar(formDto);
        return "redirect:/admin/corregos";
    }

    /**
     * 4) GET  /admin/corregos/editar/{id}
     *      => Carrega o córrego existente no formulário para edição
     */
    @GetMapping("/editar/{id}")
    public String exibirFormularioEdicao(@PathVariable("id") Long id, Model model) {
        var opt = corregoService.buscarPorId(id);

        if (opt.isEmpty()) {
            // você pode exibir uma página de “não encontrado” ou redirecionar com mensagem de erro
            return "redirect:/admin/corregos";
        }

        CorregoView c = opt.get();
        // Preencher o DTO de edição com os dados existentes
        CorregoFormDTO form = new CorregoFormDTO();
        form.setNome(c.nome());
        form.setLatitude(c.latitude());
        form.setLongitude(c.longitude());
        form.setNivelAlerta(c.nivelAlerta());
        form.setNivelCritico(c.nivelCritico());
        // (se tiver mais campos, copie aqui)

        model.addAttribute("corregoForm", form);
        model.addAttribute("idCorregoEdicao", id);
        return "formulario_corrego";
    }

    /**
     * 5) POST /admin/corregos/{id}
     *      => Recebe o CorregoFormDTO preenchido para atualizar o córrego de {id}
     */
    @PostMapping("/{id}")
    public String atualizar(
            @PathVariable("id") Long id,
            @Valid @ModelAttribute("corregoForm") CorregoFormDTO formDto,
            BindingResult bindingResult,
            Model model
    ) {
        if (bindingResult.hasErrors()) {
            // volta para o formulário de edição
            model.addAttribute("idCorregoEdicao", id);
            return "formulario_corrego";
        }

        corregoService.atualizar(id, formDto);
        return "redirect:/admin/corregos";
    }

    /**
     * 6) GET  /admin/corregos/remover/{id}
     *      => Remove o córrego de id={id} e redireciona para lista
     */
    @GetMapping("/remover/{id}")
    public String remover(@PathVariable("id") Long id) {
        corregoService.deletarPorId(id);
        return "redirect:/admin/corregos";
    }

    /**
     * 7) GET /admin/corregos/mapa/{id}
     *      => Exibe uma tela de mapa para o córrego de id={id}.
     *      => Se você ainda não tiver o template “mapa_corrego.html”, crie‐o ou redirecione de volta.
     */
    @GetMapping("/mapa/{id}")
    public String exibirMapa(@PathVariable("id") Long id, Model model) {
        // Caso queira mostrar algum detalhe no mapa, coloque “id” ou entidade no model
        model.addAttribute("idCorrego", id);
        return "mapa_corrego";
        // (se ainda não tiver “mapa_corrego.html”, crie esse arquivo em /templates/ ou altere para onde quiser)
    }
}
