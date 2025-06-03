package com.alerta_sp.mvc_admin.controller;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/config")
public class ConfiguracaoIAController {

    private final ChatClient chatClient;

    public ConfiguracaoIAController(ChatClient chatClient) {
        this.chatClient = chatClient;
    }

    @PostMapping("/explicar")
    public String explicarParametros(@RequestBody String parametros) {
        String prompt = String.format("""
                Você é um assistente de configuração de sistemas.
                Explique de forma simples o significado e a função dos seguintes parâmetros técnicos:

                %s

                A explicação deve ser objetiva e clara, com uma frase por parâmetro.
                """, parametros);

        return chatClient.prompt()
                .user(prompt)
                .call()
                .content();
    }
}
