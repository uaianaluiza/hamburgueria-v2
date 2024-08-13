package com.hamburguer.core.gateway

import com.hamburguer.core.entities.Hamburguer
import com.hamburguer.useCase.request.HamburguerRequest

interface HamburguerGateway {

    fun criarHamburguer (request: HamburguerRequest): Hamburguer

    fun listarHamburguer(): Iterable<Hamburguer>

    fun buscarHamburguerPorId(id: Int): Hamburguer

    fun alterarHamburguer(id: Int, request: HamburguerRequest): Hamburguer

    fun deletarHamburguer(id: Int)

    fun deletarHamburgueres ()
}