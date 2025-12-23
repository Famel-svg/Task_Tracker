# 🚀 Rastreador de Tarefas (Task Tracker)

<p align="center">
  <!-- Badges clicáveis como imagens (devem sempre aparecer) -->
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

> Um aplicativo CLI simples para gerenciar tarefas localmente usando um arquivo JSON — ideal para aprendizado e uso rápido sem dependências externas. (Referência: [Task Tracker — roadmap.sh](https://roadmap.sh/projects/task-tracker))

---

## ✨ Principais funcionalidades
- Criar, atualizar e deletar tarefas.
- Marcar status: `pendente`, `andamento`, `concluída`.
- Listar tarefas (todas / por status).
- Persistência local em `tasks.json` (criado automaticamente).
- IDs únicos e timestamps (`createdAt`, `updatedAt`).
- Projeto sem dependências externas — somente módulos nativos.

## 🖼️ Captura rápida
<!-- Use um placeholder público para garantir que a imagem apareça -->
<p align="center">
  <img alt="Screenshot exemplo" src="https://via.placeholder.com/900x260.png?text=Task+Tracker+-+CLI" />
</p>

---

## 🧭 Roadmap (status)
- [x] CLI básico: adicionar, listar, remover
- [x] Armazenamento em JSON no diretório atual
- [x] Suporte a status (pendente, andamento, concluída)
- [x] Filtros básicos (por status)

---

## ⚙️ Requisitos
- Interpretador/ambiente correspondente à linguagem escolhida (ex.: Node.js 14+ / Python 3.8+).
- Permissão de leitura/escrita no diretório de execução.
- Nenhuma dependência externa necessária (projeto ensina uso de APIs nativas de arquivos).

## ☕ Uso (CLI — exemplos)
A CLI usa argumentos posicionais. Substitua `./task-tracker` pelo seu executável/script.

Adicionar tarefa:
```bash
./task-tracker add "Comprar mantimentos"
```

Atualizar tarefa (descrição e/ou status):
```bash
./task-tracker update <id> "Nova descrição" andamento
# exemplo
./task-tracker update 3 "Finalizar relatório mensal" concluída
```

Deletar tarefa:
```bash
./task-tracker delete <id>
```

Listar tarefas:
```bash
./task-tracker list            # todas
./task-tracker list pendente   # somente pendentes
./task-tracker list andamento  # somente em andamento
./task-tracker list concluída   # somente concluídas
```

Buscar texto (se implementado):
```bash
./task-tracker search "relatório"
```

Ajuda:
```bash
./task-tracker help
```

---

## 🗂️ Formato do arquivo (tasks.json)
O arquivo `tasks.json` é um array de objetos. Exemplo:
```json
[
  {
    "id": 1,
    "description": "Comprar mantimentos",
    "status": "pendente",
    "createdAt": "2025-12-23T10:00:00.000Z",
    "updatedAt": "2025-12-23T10:00:00.000Z"
  },
  {
    "id": 2,
    "description": "Finalizar relatório mensal",
    "status": "concluída",
    "createdAt": "2025-12-20T09:30:00.000Z",
    "updatedAt": "2025-12-22T16:45:00.000Z"
  }
]

---

## 🧩 Boas práticas de implementação
- Normalizar status (`andamento` ⇄ `em andamento`).
- Atualizar `updatedAt` em todas as modificações.
- Permitir ordenação por `createdAt`, `updatedAt`, `id`.
- Mensagens claras com exit codes apropriados.
- Suportar `--dry-run` para simular alterações (opcional).
- Incluir `tasks.example.json` para testes.

---

## 🛠️ Estrutura sugerida do projeto
- task-tracker (executável / script principal)
- README (este arquivo)
- tasks.json (gerado em runtime)
- tasks.example.json (opcional, com exemplos)
- CONTRIBUTING.md (opcional)

---

## 🧾 Como contribuir
1. Faça um fork do repositório.
2. Crie um branch: `git checkout -b feat/nome-da-caracteristica`
3. Faça commits claros: `git commit -m "Descrição da mudança"`
4. Envie: `git push origin feat/nome-da-caracteristica`
5. Abra um Pull Request descrevendo alterações e testes.

Dicas:
- Abra uma issue para propostas maiores.
- Inclua testes quando possível.

---

## 📄 Licença
Adicione um arquivo `LICENSE` (ex.: MIT) para definir os termos. Atualmente, inclua a licença desejada no repositório ao publicar.

---

## 📬 Contato
Se quiser, eu posso:
- Gerar a versão em inglês do README.
- Adaptar exemplos para uma linguagem específica (Node.js / Python / Go).
- Criar um `tasks.example.json`.
- Gerar um esqueleto inicial do CLI (arquivo executável) na linguagem que preferir.
