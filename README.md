# 🚨 Projeto Alerta SP – Painel Administrativo (MVC)

![Java](https://img.shields.io/badge/-Java-orange?logo=java\&logoColor=white)
![Spring MVC](https://img.shields.io/badge/-Spring%20MVC-green?logo=spring\&logoColor=white)
![Thymeleaf](https://img.shields.io/badge/-Thymeleaf-blue?logo=thymeleaf\&logoColor=white)
![RabbitMQ](https://img.shields.io/badge/-RabbitMQ-ff6600?logo=rabbitmq\&logoColor=white)

## 👥 Equipe do Projeto

| Matrícula  | Nome                              |
|------------|-----------------------------------|
| **553568** | Sabrina da Motta Café            |
| **552692** | Luís Henrique Oliveira Da Silva  |
| **554199** | Matheus Duarte Oliveira          |

## 📌 Descrição Geral

O **Alerta SP** é um sistema inteligente de monitoramento em tempo real, focado na prevenção e alerta sobre enchentes e desastres naturais. Utiliza sensores IoT instalados em áreas de risco para coletar dados de nível dos córregos e gerar alertas em caso de perigo. O painel administrativo em Java permite acompanhar as leituras, registrar valores manualmente e gerar relatórios automatizados com auxílio de IA.

## 🚀 Papel do Java no Projeto

A linguagem **Java** é responsável pelo backend da aplicação administrativa, utilizando o framework **Spring MVC**:

* ✅ Controla a navegação e renderização das páginas.
* ✅ Processa formulários (login, cadastro, edição).
* ✅ Comunicação com o banco de dados via JPA/Hibernate.
* ✅ Segurança robusta com autenticação via OAuth2 (GitHub).
* ✅ Gestão das regras de negócio (córregos, sensores, alertas).

## 📌 Links Importantes 

* 🔜 Diagrama UML (\[adicionar link aqui])
* 🎥 Vídeo de Explicação (\[adicionar link aqui])
* 🎬 Vídeo Pitch (\[adicionar link aqui])

## 🛠️ Funcionalidades do Painel Admin (`/admin/**`)

* 🌊 **Gerenciar Córregos**:

    * Cadastro, edição e remoção de córregos
    * Visualização geolocalizada em mapas

* 📟 **Associar Sensores**:

    * Vinculação dos sensores físicos aos córregos cadastrados

* 📊 **Visualizar e Registrar Leituras**:

    * Consulta das últimas medições de cada sensor
    * Registro manual de leituras para testes ou ajustes
    * Gráficos com tendências de níveis e alertas

* 🚨 **Emitir Alertas**:

    * Controle e gerenciamento dos alertas em casos críticos

* 🔧 **Verificar Serviços**:

    * Status de RabbitMQ e serviços de inteligência artificial (Spring AI)

* 🧠 **Gerar Relatórios com IA**:

    * Síntese automatizada das últimas leituras usando Spring AI

## 🔑 Tecnologias Utilizadas

* **Java 17**
* **Spring Boot 3.5.0**
* **Spring Security** com OAuth2
* **Thymeleaf** para templates HTML dinâmicos
* **Spring AI com Azure OpenAI**
* **RabbitMQ** para mensageria em tempo real
* **MySQL** como banco de dados

## 🚧 Pré-requisitos

* Java JDK 17
* Maven 3.x
* MySQL (Banco de dados)
* RabbitMQ

## 🖥️ Como Executar o Projeto

1. Clone o repositório:

   ```bash
   git clone <link_do_repositório>
   ```

2. Acesse o sistema:

   ```
   http://localhost:8080/admin
   ```

## 🧪 Testes Unitários e de Integração

Este projeto conta com uma suíte de testes criada com o **Spring Boot Test** e
o banco de dados em memória **H2**. Eles servem para garantir que as regras de
negócio e as operações dos repositórios estejam funcionando corretamente sem a
necessidade de acessar o MySQL real.

* **Testes unitários** – utilizam o Mockito para simular dependências das
  classes de serviço, verificando comportamentos como validações e tratamento de
  erros.
* **Testes de integração** – executados com o H2 apenas durante os testes,
  validam consultas e persistência nos repositórios de forma isolada.

Para rodar todos os testes, basta executar:

```bash
mvn test
```

