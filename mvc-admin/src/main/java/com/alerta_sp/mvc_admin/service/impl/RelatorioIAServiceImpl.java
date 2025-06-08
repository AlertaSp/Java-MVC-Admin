package com.alerta_sp.mvc_admin.service.impl;

import com.alerta_sp.mvc_admin.model.LeituraSensor;
import com.alerta_sp.mvc_admin.repository.LeituraSensorRepository;
import com.alerta_sp.mvc_admin.service.RelatorioIAService;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class RelatorioIAServiceImpl implements RelatorioIAService {

    private final LeituraSensorRepository leituraSensorRepository;
    private final ChatClient chatClient;

    public RelatorioIAServiceImpl(LeituraSensorRepository leituraSensorRepository, ChatClient chatClient) {
        this.leituraSensorRepository = leituraSensorRepository;
        this.chatClient = chatClient;
    }

    @Override
    public String gerarRelatorio() {
        LocalDateTime inicio = LocalDateTime.now().minusHours(12);
        List<LeituraSensor> leituras = leituraSensorRepository.findByDataHoraAfterOrderByDataHoraAsc(inicio);

        DateTimeFormatter fmt = DateTimeFormatter.ofPattern("HH:mm");
        String dados = leituras.stream()
                .map(l -> String.format("[%s | %.2f m às %s]",
                        l.getSensor().getCorrego().getNome(),
                        l.getNivel(),
                        l.getDataHora().format(fmt)))
                .collect(Collectors.joining(", "));

        String prompt = String.format("""
                Gere um resumo técnico baseado nas seguintes leituras:
                %s
                Destaque quais córregos ultrapassaram nível de alerta ou crítico e se há tendência de risco.
                """, dados);

        return chatClient.prompt()
                .user(prompt)
                .call()
                .content();
    }

    @Override
    public String gerarRelatorioUltimasLeituras() {
        return gerarRelatorio();
    }
}
