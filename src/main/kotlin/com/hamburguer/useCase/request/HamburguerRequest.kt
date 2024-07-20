package com.hamburguer.useCase.request

data class HamburguerRequest(
    var tipoDePao: Int,
    var carne: Int,
    var queijo: Int,
    var salada: Int
)