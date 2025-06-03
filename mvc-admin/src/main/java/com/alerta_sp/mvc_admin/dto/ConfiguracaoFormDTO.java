package com.alerta_sp.mvc_admin.dto;

public class ConfiguracaoFormDTO {

    private String apiKey;
    private String parametros;
    private boolean emailNotificacao;
    private boolean pushNotificacao;

    // Getters e setters
    public String getApiKey() {
        return apiKey;
    }

    public void setApiKey(String apiKey) {
        this.apiKey = apiKey;
    }

    public String getParametros() {
        return parametros;
    }

    public void setParametros(String parametros) {
        this.parametros = parametros;
    }

    public boolean isEmailNotificacao() {
        return emailNotificacao;
    }

    public void setEmailNotificacao(boolean emailNotificacao) {
        this.emailNotificacao = emailNotificacao;
    }

    public boolean isPushNotificacao() {
        return pushNotificacao;
    }

    public void setPushNotificacao(boolean pushNotificacao) {
        this.pushNotificacao = pushNotificacao;
    }
}
