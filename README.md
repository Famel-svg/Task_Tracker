# 🚀 Rastreador de Tarefas (Task Tracker)

<p align="center">
  <a href="https://github.com/Famel-svg/Task_Tracker">
    <img alt="Repo size" src="https://img.shields.io/github/repo-size/Famel-svg/Task_Tracker?style=for-the-badge" />
  </a>
  <a href="https://github.com/Famel-svg/Task_Tracker/search?l=all">
    <img alt="Languages" src="https://img.shields.io/github/languages/count/Famel-svg/Task_Tracker?style=for-the-badge" />
  </a>
  <a href="https://github.com/Famel-svg/Task_Tracker/network/members">
    <img alt="Forks" src="https://img.shields.io/github/forks/Famel-svg/Task_Tracker?style=for-the-badge" />
  </a>
  <a href="https://github.com/Famel-svg/Task_Tracker/stargazers">
    <img alt="Stars" src="https://img.shields.io/github/stars/Famel-svg/Task_Tracker?style=for-the-badge" />
  </a>
  <a href="https://github.com/Famel-svg/Task_Tracker/issues">
    <img alt="Issues" src="https://img.shields.io/github/issues/Famel-svg/Task_Tracker?style=for-the-badge" />
  </a>
  <a href="https://roadmap.sh/projects/task-tracker">
    <img alt="Roadmap" src="https://img.shields.io/badge/roadmap-reference-blue?style=for-the-badge" />
  </a>
</p>

> Uma aplicação CLI interativa em **Java 25** para gerenciar tarefas localmente usando persistência em JSON. Projeto focado em **boas práticas**, **programação funcional** e arquitetura limpa — ideal para aprendizado avançado de Java moderno.

---

## ✨ Principais Funcionalidades

### 🎯 Gestão Completa de Tarefas
- ✅ **Criar** tarefas com nome, descrição e status
- ✅ **Atualizar** nome, descrição ou status individualmente
- ✅ **Deletar** tarefas com confirmação de segurança
- ✅ **Listar** todas as tarefas ou filtrar por status
- ✅ **Estatísticas** completas com percentual de progresso

### 🔧 Recursos Técnicos
- 🎨 Interface CLI interativa com menu navegável
- 💾 Persistência automática em `tasks.json`
- 🔍 Sistema de logging avançado com níveis (DEBUG, INFO, WARN, ERROR)
- ⚡ Validação robusta de entrada do usuário
- 🏗️ Arquitetura baseada em Repository Pattern
- 🎭 Builder Pattern para criação de tasks
- 📦 Zero dependências externas (apenas Java stdlib)

### 🚀 Recursos Modernos de Java
- Lambda expressions e method references
- Stream API para operações funcionais
- Optional para null-safety
- Text blocks para formatação
- Records e pattern matching (preparado para Java 25)
- Lazy evaluation com Suppliers

---

## 🧭 Roadmap de Desenvolvimento

- [x] CLI interativo com menu de opções
- [x] CRUD completo de tarefas
- [x] Persistência em JSON no diretório atual
- [x] Três status: Para fazer, Fazendo, Pronta
- [x] Filtros por status (pendente, em progresso, concluída)
- [x] Sistema de logging em arquivo
- [x] Validação de entrada com tratamento de erros
- [x] Timestamps automáticos (criação e finalização)
- [x] Estatísticas e métricas de progresso
- [x] Builder Pattern para criação de tasks
- [x] Repository Pattern para camada de dados
- [x] Refatoração funcional com lambdas e streams
- [ ] Busca por texto em nome/descrição
- [ ] Ordenação customizada (por data, nome, ID)
- [ ] Export/Import de tasks em diferentes formatos
- [ ] Testes unitários com JUnit 5
- [ ] Interface CLI com argumentos (modo batch)

---

## 🛠️ Tecnologias Utilizadas

| Tecnologia | Versão | Uso |
|------------|--------|-----|
| **Java** | 25 | Linguagem principal |
| **Maven** | 3.x | Gerenciamento de build |
| **JUnit** | 3.8.1 | Testes unitários |

### Recursos de Java Utilizados
- **Java 11+**: `Files.readString()`, `Files.writeString()`, text blocks
- **Java 14+**: Records (preparado), switch expressions
- **Java 16+**: Pattern matching para instanceof
- **Java 17+**: Sealed classes (preparado)
- **Java 21+**: String templates (preparado), virtual threads

---

## ⚙️ Requisitos

### Ambiente de Desenvolvimento
- **JDK 25** (ou superior) instalado e configurado
- **Maven 3.x** para build e gerenciamento de dependências
- **Git** para controle de versão
- Permissão de leitura/escrita no diretório de execução

### Verificar Instalação
```bash
java -version    # Deve mostrar versão 25+
mvn -version     # Deve mostrar Maven 3.x
```

---

## 🚀 Como Executar

### 1. Clonar o Repositório
```bash
git clone https://github.com/Famel-svg/Task_Tracker.git
cd Task_Tracker
```

### 2. Compilar o Projeto
```bash
mvn clean compile
```

### 3. Executar a Aplicação
```bash
mvn exec:java -Dexec.mainClass="App"
```

Ou compile e execute diretamente:
```bash
java src/main/java/App.java
```

### 4. Executar Testes
```bash
mvn test
```

---

## 💻 Guia de Uso

### Menu Principal
Ao executar, você verá o menu interativo:

```
╔═══════════════════════════════════════╗
║            MENU PRINCIPAL            ║
╠═══════════════════════════════════════╣
║ (1) Adicionar nova Task              ║
║ (2) Atualizar uma Task               ║
║ (3) Apagar uma Task                  ║
║ (4) Listar todas as Tasks            ║
║ (5) Listar Tasks prontas             ║
║ (6) Listar Tasks em progresso        ║
║ (7) Ver estatísticas                 ║
║ (0) Sair e salvar                    ║
╚═══════════════════════════════════════╝
```

### Exemplos de Uso

#### Adicionar uma Task
1. Selecione opção `1`
2. Digite o nome: `Estudar Java Streams`
3. Digite a descrição: `Aprender map, filter e reduce`
4. Escolha o status: `2` (Fazendo)

#### Atualizar uma Task
1. Selecione opção `2`
2. Visualize as tasks disponíveis
3. Digite o ID da task
4. Escolha o que atualizar (Nome, Descrição ou Status)
5. Forneça o novo valor

#### Ver Estatísticas
```
╔════════════════════════════════════════╗
║           ESTATÍSTICAS               ║
╠════════════════════════════════════════╣
║ Total de tasks:        4              ║
║ Para fazer:            1              ║
║ Em progresso:          2              ║
║ Prontas:               1              ║
║ Progresso:             25.0%          ║
╚════════════════════════════════════════╝
```

---

## 🗂️ Estrutura do Projeto

```
Task_Tracker/
├── src/
│   ├── main/
│   │   └── java/
│   │       ├── App.java                          # Ponto de entrada
│   │       ├── br/com/famel/model/
│   │       │   ├── entities/
│   │       │   │   ├── Task.java                 # Modelo de dados
│   │       │   │   ├── Status.java               # Enum de status
│   │       │   │   ├── TaskRepository.java       # Camada de dados
│   │       │   │   ├── JsonManager.java          # Persistência JSON
│   │       │   │   ├── InputValidator.java       # Validação de entrada
│   │       │   │   ├── OpcoesDaCase.java         # Lógica do menu
│   │       │   │   └── Textos.java               # Constantes de UI
│   │       │   └── util/
│   │       │       └── Logger.java               # Sistema de logging
│   │       └── examples/
│   │           └── ExemploUsoBuilder.java        # Exemplos de uso
│   └── test/
│       └── java/
│           └── br/com/famel/
│               └── AppTest.java                  # Testes unitários
├── tasks.json                                    # Dados persistidos
├── task_tracker.log                              # Log da aplicação
├── pom.xml                                       # Configuração Maven
└── README.md                                     # Este arquivo
```

---

## 📊 Formato do Arquivo JSON

O arquivo `tasks.json` armazena um array de objetos Task:

```json
[
    {
        "Id": 1,
        "Nome": "Comprar mantimentos",
        "Descricao": "Ir ao mercado e comprar itens da lista",
        "Status": "Para fazer",
        "DataDeCriacao": "2025-01-02",
        "DataDeFinalizacao": null
    },
    {
        "Id": 2,
        "Nome": "Finalizar relatório mensal",
        "Descricao": "Completar análise de vendas de dezembro",
        "Status": "Pronta",
        "DataDeCriacao": "2024-12-20",
        "DataDeFinalizacao": "2024-12-22"
    },
    {
        "Id": 3,
        "Nome": "Estudar Java",
        "Descricao": "Revisar conceitos de streams e lambdas",
        "Status": "Fazendo",
        "DataDeCriacao": "2025-01-01",
        "DataDeFinalizacao": null
    }
]
```

### Campos Explicados
- **Id**: Identificador único auto-incrementado
- **Nome**: Título curto da task
- **Descricao**: Detalhamento da tarefa
- **Status**: `Para fazer`, `Fazendo` ou `Pronta`
- **DataDeCriacao**: Data de criação (ISO-8601)
- **DataDeFinalizacao**: Data de conclusão (null se não finalizada)

---

## 🎨 Padrões e Arquitetura

### Design Patterns Implementados

#### 1️⃣ Repository Pattern
```java
TaskRepository repo = new TaskRepository();
repo.adicionar(task);
repo.buscarPorId(1);
repo.listarProntas();
```

#### 2️⃣ Builder Pattern
```java
Task task = Task.builder()
    .nome("Estudar Java")
    .descricao("Revisar conceitos")
    .emProgresso()
    .build();
```

#### 3️⃣ Singleton Logger
```java
Logger.info("Operação realizada");
Logger.error("Erro", exception);
Logger.debug("Detalhes técnicos");
```

#### 4️⃣ Strategy Pattern (Atualização)
```java
Map<Integer, BiFunction<TaskRepository, Integer, Boolean>> estrategias = Map.of(
    1, (repo, id) -> repo.atualizarNome(id, novoNome),
    2, (repo, id) -> repo.atualizarDescricao(id, novaDesc),
    3, (repo, id) -> repo.atualizarStatus(id, novoStatus)
);
```

### Princípios SOLID Aplicados
- ✅ **S**ingle Responsibility: Cada classe tem uma responsabilidade única
- ✅ **O**pen/Closed: Extensível via herança e composição
- ✅ **L**iskov Substitution: Interfaces e contratos bem definidos
- ✅ **I**nterface Segregation: Interfaces focadas e específicas
- ✅ **D**ependency Inversion: Uso de abstrações (Repository)

---

## 🧪 Testes

### Executar Testes
```bash
mvn test
```

### Cobertura de Testes (Planejado)
- [ ] Testes unitários de Task
- [ ] Testes de TaskRepository
- [ ] Testes de JsonManager
- [ ] Testes de InputValidator
- [ ] Testes de integração

---

## 🔧 Configuração Avançada

### Ativar Modo Debug
Edite `src/main/java/br/com/famel/model/util/Logger.java`:
```java
private static final boolean DEBUG_MODE = true; // Ativa logs detalhados
```

### Desativar Log em Arquivo
```java
private static final boolean LOG_TO_FILE = false;
```

### Customizar Arquivo de Dados
```java
TaskRepository repo = new TaskRepository("minhas_tasks.json");
```

---

## 🧩 Boas Práticas Implementadas

### Código Limpo
- ✅ Nomes descritivos e auto-explicativos
- ✅ Métodos pequenos com responsabilidade única
- ✅ Comentários JavaDoc em métodos públicos
- ✅ Constantes para valores mágicos
- ✅ Tratamento robusto de exceções

### Programação Funcional
- ✅ Lambda expressions para operações
- ✅ Stream API para transformações
- ✅ Method references quando apropriado
- ✅ Optional para null-safety
- ✅ Immutability onde possível

### Validação e Segurança
- ✅ Validação de todas as entradas do usuário
- ✅ Tratamento de InputMismatchException
- ✅ Confirmação antes de deletar
- ✅ Escape de caracteres especiais no JSON
- ✅ Validação de IDs antes de operações

---

## 📚 Aprendizados do Projeto

### Conceitos de Java Avançado
1. **Streams e Lambdas**: Operações funcionais em coleções
2. **Optional**: Tratamento elegante de valores nulos
3. **Enums**: Tipos seguros para status
4. **Builder Pattern**: Criação fluente de objetos
5. **Repository Pattern**: Separação de lógica de dados
6. **Text Blocks**: Formatação limpa de strings multi-linha
7. **Try-with-resources**: Gerenciamento automático de recursos
8. **Method References**: Código mais conciso

### Boas Práticas de Desenvolvimento
- Separação de responsabilidades (SoC)
- Validação de entrada robusta
- Logging estruturado
- Tratamento de erros consistente
- Código auto-documentado

---

## 🤝 Como Contribuir

### 1. Fork o Projeto
```bash
# Via GitHub UI ou:
gh repo fork Famel-svg/Task_Tracker
```

### 2. Crie um Branch de Feature
```bash
git checkout -b feat/nova-funcionalidade
# ou
git checkout -b fix/correcao-bug
```

### 3. Faça Commits Semânticos
```bash
git commit -m "feat: adiciona busca por texto nas tasks"
git commit -m "fix: corrige validação de data de finalização"
git commit -m "refactor: simplifica lógica de atualização usando lambdas"
git commit -m "docs: atualiza README com instruções de build"
```

#### Convenção de Commits
- `feat`: Nova funcionalidade
- `fix`: Correção de bug
- `refactor`: Refatoração sem mudança de comportamento
- `docs`: Atualização de documentação
- `test`: Adição ou correção de testes
- `style`: Formatação, ponto e vírgula, etc
- `perf`: Melhoria de performance

### 4. Push e Pull Request
```bash
git push origin feat/nova-funcionalidade
```

Abra um Pull Request no GitHub descrevendo:
- 📝 O que foi alterado
- 🎯 Por que foi alterado
- ✅ Como testar
- 📸 Screenshots (se aplicável)

---

## 📄 Licença

Este projeto é de código aberto e está disponível sob a licença MIT.

---

## 👨‍💻 Autor

**Famel**
- GitHub: [@Famel-svg](https://github.com/Famel-svg)
- Projeto inspirado em: [roadmap.sh/projects/task-tracker](https://roadmap.sh/projects/task-tracker)

---

## 🙏 Agradecimentos

- [roadmap.sh](https://roadmap.sh) pela ideia do projeto

---

## 📞 Suporte

Encontrou um bug? Tem uma sugestão?

1. Verifique se já existe uma [issue](https://github.com/Famel-svg/Task_Tracker/issues) relacionada
2. Se não, [abra uma nova issue](https://github.com/Famel-svg/Task_Tracker/issues/new)
3. Descreva o problema ou sugestão detalhadamente

---

<p align="center">
  Feito com ❤️ e ☕ em Java
</p>