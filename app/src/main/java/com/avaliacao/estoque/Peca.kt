package com.avaliacao.estoque

import java.lang.IllegalArgumentException

data class Peca(
    val id: Int,
    val nome: String,
    var quantidade: Int,
) {
    init {
        if (quantidade > 999)
            throw LimiteEstoqueMaxException("Não podemos armazenar mais do que 999 unidades de cada peça.")
        else if (nome.isBlank() or nome.isEmpty()) {
            throw IllegalArgumentException("Nome não pode ser vazio ou em branco.")
        }
    }

    override fun toString(): String {
        return "$id - $nome | $quantidade"
    }
}