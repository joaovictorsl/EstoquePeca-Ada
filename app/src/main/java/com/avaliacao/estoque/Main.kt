package com.avaliacao.estoque

import java.lang.NumberFormatException
import kotlin.system.exitProcess

val menu = """1 - ADICIONAR ITEM
    |2 - EDITAR ITEM
    |3 - EXIBIR ITENS EM ESTOQUE
    |4 - EXIBIR TODOS OS ITENS
    |0 - FECHAR SISTEMA""".trimMargin()
val estoque = Estoque()

fun main() {
    while (true) {
        val input = getUserInput(
            prompt = {println(menu)},
            isInputValid = { input -> checkIfInputInRange(input, 0..4)})

        when(input) {
            "1" -> handleAddItem()
            "2" -> handleEditItem()
            "3" -> handleShowAvailableItems()
            "4" -> handleShowAllItems()
            "0" -> {
                println("Tchau tchau :)")
                exitProcess(0)
            }
        }
    }
}

fun handleAddItem() {
        val nome = getUserInput(prompt = { println("Digite o nome da peça:")}, isInputValid = { it.isNotBlank() and it.isNotEmpty()})
        val quantidade = getUserInput(
            prompt = { println("Digite a quantidade disponível da peça:")},
            isInputValid = { it.isNotBlank() and it.isNotEmpty() and checkIfInputInRange(it, 0..999)},
            errorMsg = "Apenas valores de 0 até 999 são válidos para quantidade de itens.")

        try {
            estoque.addPeca(nome, quantidade.toInt())
            println("Adição realizada com sucesso.")
        } catch (e: LimiteEstoqueMaxException) {
            println(e.message)
        }

}

fun handleEditItem() {
    if (!emptyEstoque()) {
        val selectedItem = getUserInput({ println("Escolha a peça para ser editada: \n${estoque.toString()}")}, {it.isNotBlank() and it.isNotEmpty() and checkIfInputInRange(it, 1..estoque.size())})
        val nome = getUserInput({ println("Digite o novo nome da peça:")}, { it.isNotBlank() and it.isNotEmpty()})
        val quantidade = getUserInput({ println("Digite a nova quantidade disponível da peça:")}, { it.isNotBlank() and it.isNotEmpty() and checkIfInputInRange(it, 0..999)})

        try {
            estoque.set(selectedItem, nome, quantidade.toInt())
            println("Edição realizada com sucesso.")
        } catch (e: LimiteEstoqueMaxException) {
            println(e.message)
        }
    } else {
        println("Estoque vazio.")
    }

}

fun emptyEstoque(): Boolean {
    return estoque.size() == 0
}

fun handleShowAllItems() {
    if (!emptyEstoque()) {
        println("Lista de todos os itens: \n$estoque")
    } else {
        println("Estoque vazio.")
    }
}

fun handleShowAvailableItems() {
    if (!emptyEstoque()) {
        println("Lista de itens em estoque: \n${estoque.getAvailableItems()}")
    } else {
        println("Estoque vazio.")
    }
}

fun checkIfInputInRange(v: String, validRange: IntRange): Boolean {
    return try {
        v.toInt() in validRange
    } catch(e: NumberFormatException) {
        false
    }
}

fun getUserInput(prompt: () -> Unit, isInputValid: (String) -> Boolean, errorMsg: String = "Entrada inválida, tente novamente."): String {
    prompt()
    var entrada = readln()

    while(!isInputValid(entrada)) {
        prompt()
        println(errorMsg)
        entrada = readln()
    }

    return entrada
}
