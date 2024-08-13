package com.hamburguer.core.entities

import com.hamburguer.core.enuns.Carne
import com.hamburguer.core.enuns.Queijo
import com.hamburguer.core.enuns.Salada
import com.hamburguer.core.enuns.TipoDePao

data class Hamburguer(

        val id: Int = 0,
        var tipoDePao: TipoDePao,
        var carne: Carne,
        var queijo: Queijo,
        var salada: Salada
) {
        constructor(tipoDePao: TipoDePao, carne: Carne, queijo: Queijo, salada: Salada) :
                this(0, tipoDePao, carne, queijo, salada)
}

