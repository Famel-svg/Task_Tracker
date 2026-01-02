package br.com.famel.model.entities;

/**
 * Enum que representa os possíveis status de uma Task
 */
public enum Status {
    PARA_FAZER("Para fazer", "1"),
    FAZENDO("Fazendo", "2"),
    PRONTA("Pronta", "3");

    private final String descricao;
    private final String codigo;

    /**
     * Construtor do Enum
     */
    Status(String descricao, String codigo) {
        this.descricao = descricao;
        this.codigo = codigo;
    }

    /**
     * Retorna a descrição do status
     */
    public String getDescricao() {
        return descricao;
    }

    /**
     * Retorna o código do status
     */
    public String getCodigo() {
        return codigo;
    }

    /**
     * Converte código ou descrição para o Enum correspondente
     * @param input Código (1, 2, 3) ou descrição ("Para fazer", etc)
     * @return Status correspondente
     * @throws IllegalArgumentException se o input for inválido
     */
    public static Status fromString(String input) {
        if (input == null || input.trim().isEmpty()) {
            throw new IllegalArgumentException("Status não pode ser vazio");
        }

        String inputLimpo = input.trim();

        // Tenta por código primeiro
        for (Status s : values()) {
            if (s.codigo.equals(inputLimpo)) {
                return s;
            }
        }

        // Tenta por descrição (case insensitive)
        for (Status s : values()) {
            if (s.descricao.equalsIgnoreCase(inputLimpo)) {
                return s;
            }
        }

        throw new IllegalArgumentException("Status inválido: " + input +
                ". Use: 1 (Para fazer), 2 (Fazendo), 3 (Pronta)");
    }

    /**
     * Retorna uma string com todas as opções de status formatadas
     */
    public static String listarOpcoes() {
        StringBuilder sb = new StringBuilder();
        sb.append("Opções de Status:\n");
        for (Status s : values()) {
            sb.append("  (").append(s.codigo).append(") ").append(s.descricao).append("\n");
        }
        return sb.toString();
    }

    /**
     * Verifica se um status está completo (Pronta)
     */
    public boolean isCompleta() {
        return this == PRONTA;
    }

    /**
     * Verifica se um status está em progresso
     */
    public boolean isEmProgresso() {
        return this == FAZENDO;
    }

    /**
     * Verifica se um status está pendente
     */
    public boolean isPendente() {
        return this == PARA_FAZER;
    }

    @Override
    public String toString() {
        return descricao;
    }
}