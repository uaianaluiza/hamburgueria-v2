package com.hamburguer.useCase.translator

import com.Lanchonete.hamburguer.core.entities.Hamburguer
import repository.HamburguerEntity

fun toHamburguer(entity: HamburguerEntity): Hamburguer {
    return Hamburguer(entity.id, entity.tipoDePao, entity.carne, entity.queijo, entity.salada)
}

fun toEntity(hamburguer: Hamburguer): HamburguerEntity {
    return HamburguerEntity(
        hamburguer.id!!,
        hamburguer.tipoDePao,
        hamburguer.carne,
        hamburguer.queijo,
        hamburguer.salada
    )
}

