package com.avaliacao.estoque

class Estoque {
    private val storage: MutableList<Peca> = mutableListOf<Peca>()
    private var lastId: Int = 0

    fun addPeca(nome: String, quantidade: Int) {
        storage.add(Peca(++lastId, nome, quantidade))
    }

    fun set(selectedItem: String, nome: String, quantidade: Int) {
        val index = selectedItem.toInt() - 1
        val itemToUpdate = storage[index]
        storage[index] = Peca(itemToUpdate.id, nome, quantidade)
    }

    fun size(): Int {
        return storage.size
    }

    override fun toString(): String {
        var asString = ""
        storage.forEach {
            asString += it.toString() + "\n"
        }

        return asString
    }

    fun getAvailableItems(): String {
        var asString = ""
        storage.forEach {
            if (it.quantidade != 0)
                asString += it.toString() + "\n"
        }

        return asString
    }
}
