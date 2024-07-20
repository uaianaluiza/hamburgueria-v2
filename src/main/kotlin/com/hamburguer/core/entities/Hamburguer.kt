package com.hamburguer.core.entities

import com.Lanchonete.hamburguer.core.enuns.Carne
import com.Lanchonete.hamburguer.core.enuns.Queijo
import com.Lanchonete.hamburguer.core.enuns.Salada
import com.Lanchonete.hamburguer.core.enuns.TipoDePao

data class Hamburguer(

        val id: Int?,
        var tipoDePao: TipoDePao,
        var carne: Carne,
        var queijo: Queijo,
        var salada: Salada
)
