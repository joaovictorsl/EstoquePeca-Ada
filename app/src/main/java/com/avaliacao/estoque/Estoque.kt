package com.avaliacao.estoque

class Estoque {
    val storage: MutableList<Peca>
    var lastId: Int

    constructor() {
        this.storage = mutableListOf<Peca>()
        lastId = 0
    }

    fun addPeca(nome: String, quantidade: Int) {
        validQuantity(quantidade)

        storage.add(Peca(++lastId, nome, quantidade))
    }

    fun validQuantity(quantidade: Int) {
        if (quantidade > 999)
            throw LimiteEstoqueMaxException("Não podemos armazenar mais do que 999 unidades de cada peça.")
    }

    fun set(selectedItem: String, nome: String, quantidade: Int) {
        validQuantity(quantidade)

        val index = selectedItem.toInt() - 1
        val itemToUpdate = storage.get(index)
        storage.set(index, Peca(itemToUpdate.id, nome, quantidade))
    }

    fun size(): Int {
        return storage.size
    }

    override fun toString(): String {
        var asString = ""
        storage.forEach {
            asString += it.id.toString() + " - " + it.nome + " | " + it.quantidade + "\n"
        }

        return asString
    }

    fun getAvailableItems(): String {
        var asString = ""
        storage.forEach {
            if (it.quantidade != 0)
                asString += it.id.toString() + " - " + it.nome + " | " + it.quantidade + "\n"
        }

        return asString
    }
}

class LimiteEstoqueMaxException(msg: String) : Exception(msg)