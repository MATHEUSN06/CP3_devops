# Checkpoint 3 — DevOps & Cloud Computing

**Curso:** Análise e Desenvolvimento de Sistemas (ADS)
**Turma:** 1TDSPV

### 👥 Integrantes do Grupo
* **Matheus Nascimento Corregio** | RM 563765
* **Erick de Faria Gama** | RM 561951

---

## 📋 Descrição do Projeto

Esta é uma API REST desenvolvida em **Spring Boot** para o gerenciamento de brinquedos (DimDimApp). O objetivo deste Checkpoint foi aplicar boas práticas de DevOps e Cloud Computing para conteinerizar a aplicação e o banco de dados, garantindo isolamento de rede, persistência de dados e segurança, com deploy automatizado em uma Máquina Virtual na nuvem da **Microsoft Azure**.

---

## 🛠️ Arquitetura e Tecnologias

* **Backend:** Java 21 | Spring Boot 4.0.6 | Spring Data JPA | Maven
* **Banco de Dados:** PostgreSQL (imagem oficial `postgres:latest`)
* **Conteinerização:** Docker com *Multi-stage Build* e usuário seguro (não-root)
* **Persistência:** Volume Nomeado do Docker
* **Infraestrutura em Nuvem:** Máquina Virtual (Ubuntu Server) na Microsoft Azure

---

## 🔒 Boas Práticas de Segurança e Otimização implementadas

1. **Docker Multi-stage Build:** O processo de build foi dividido em duas etapas no `Dockerfile`. A primeira utiliza uma imagem robusta com Maven para compilar o código gerando o `.jar`. A segunda etapa utiliza uma imagem JRE limpa e leve (`eclipse-temurin:21-jre-jammy`) para rodar o app, reduzindo drasticamente o tamanho final da imagem e a superfície de vulnerabilidades.
2. **Usuário Não-Root Seguro:** Seguindo as diretrizes de segurança, a aplicação não roda com privilégios de `root`. Foi criado um usuário dedicado no container que possui permissão de execução restrita à pasta `/app`.
3. **Isolamento de Rede:** Ambos os containers comunicam-se de forma privada e isolada através de uma rede bridge customizada no Docker, expondo para a internet apenas a porta `8080` da API.
4. **Volume Nomeado:** O banco de dados PostgreSQL utiliza um volume nomeado associado ao diretório `/var/lib/postgresql`, garantindo que todas as inserções sejam persistidas mesmo em caso de reinicialização ou remoção dos containers.

---

### 🚀 Como Executar a Solução na VM Azure

Siga o passo a passo abaixo para reproduzir o ambiente configurado na Máquina Virtual Azure.

### 📌 Informações da VM
- **IP Público:** `172.200.160.24`
- **Tecnologias utilizadas:** Docker, PostgreSQL e Spring Boot

---

## 1️⃣ Clonar o Repositório

Clone o repositório do projeto e acesse a pasta principal:

```bash
git clone <URL_DO_SEU_REPOSITORIO>
cd <NOME_DA_PASTA_DO_PROJETO>
```

---

## 2️⃣ Criar a Rede Docker

Crie uma rede Docker isolada para permitir a comunicação entre os containers da aplicação e do banco de dados:

```bash
docker network create rede-brinquedos
```

---

## 3️⃣ Criar a Imagem Docker da Aplicação

A partir do `Dockerfile` localizado na raiz do projeto, execute o build da aplicação Spring Boot:

```bash
docker build -t app-brinquedos-img .
```

---

## 4️⃣ Inicializar o Container PostgreSQL

Execute o comando abaixo para criar e iniciar o container do banco de dados PostgreSQL:

```bash
docker run -d \
  --name db-563765 \
  --network rede-brinquedos \
  -p 5432:5432 \
  -v vol-dados-563765:/var/lib/postgresql \
  -e POSTGRES_DB=brinquedos \
  -e POSTGRES_USER=admin \
  -e POSTGRES_PASSWORD=admin \
  postgres:latest
  
  ```

### 🔎 Explicação
- `--network rede-brinquedos` → conecta o container à rede criada.
- `-v vol-dados-563765:/var/lib/postgresql/data` → mantém os dados persistidos.
- `POSTGRES_DB` → cria automaticamente o banco `brinquedos`.
- `POSTGRES_USER` e `POSTGRES_PASSWORD` → credenciais de acesso.

---

## 5️⃣ Inicializar o Container da Aplicação Spring Boot

Execute o comando abaixo para iniciar a API:

```bash
docker run -d \
  --name app-563765 \
  --network rede-brinquedos \
  -p 8080:8080 \
  -e SPRING_DATASOURCE_URL=jdbc:postgresql://db-563765:5432/brinquedos \
  -e SPRING_DATASOURCE_USERNAME=admin \
  -e SPRING_DATASOURCE_PASSWORD=admin \
  -e SPRING_JPA_HIBERNATE_DDL_AUTO=update \
  app-brinquedos-img
```

### 🔎 Explicação
- A aplicação Spring Boot será disponibilizada na porta `8080`.
- A conexão com o PostgreSQL é realizada utilizando o nome do container `db-563765`.
- `SPRING_JPA_HIBERNATE_DDL_AUTO=update` → atualiza automaticamente as tabelas do banco.

---

# ✅ Comandos Úteis de Validação

## Verificar Containers Ativos

```bash
docker ps
```

---

## Validar Usuário Não-Root da Aplicação

```bash
docker exec -it app-563765 whoami
```

### ✔️ Retorno Esperado

```bash
matheus
```

---

## Executar Query Diretamente no PostgreSQL

```bash
docker exec -it db-563765 \
psql -U admin -d brinquedos \
-c "SELECT * FROM tds_tb_brinquedos;"
```

---

# 🌐 Acesso à Aplicação

Após a inicialização dos containers, a aplicação poderá ser acessada através do endereço:

```bash
http://172.200.160.24:8080
```