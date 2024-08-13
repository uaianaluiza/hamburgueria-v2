package com.hamburguer.useCase

import com.hamburguer.core.entities.Hamburguer
import com.hamburguer.core.enuns.Carne
import com.hamburguer.core.enuns.Queijo
import com.hamburguer.core.enuns.Salada
import com.hamburguer.core.enuns.TipoDePao
import com.hamburguer.core.gateway.HamburguerGateway
import com.hamburguer.repository.HamburguerRepository
import com.hamburguer.useCase.exceptions.HamburguerException
import com.hamburguer.useCase.request.HamburguerRequest
import com.hamburguer.useCase.translator.toEntity
import com.hamburguer.useCase.translator.toHamburguer
import org.springframework.stereotype.Service

@Service
class HamburguerService(val repository: HamburguerRepository) :
    HamburguerGateway {

    override fun criarHamburguer(request: HamburguerRequest): Hamburguer {

        val hamburguer = Hamburguer(escolherPao(request.tipoDePao),
            escolherCarne(request.carne),
            escolherQueijo(request.queijo),
            escolherSalada(request.salada))

        val hamburguerEntity = toEntity(hamburguer)

        repository.save(hamburguerEntity)

        return hamburguer;
    }

    override fun listarHamburguer(): Iterable<Hamburguer> {
        val hamburguersEntities = repository.findAll()
        return hamburguersEntities.map { entity -> toHamburguer(entity) }
    }

    override fun buscarHamburguerPorId(id: Int): Hamburguer {
        val optionalHamburger = repository.findById(id)

        if (optionalHamburger.isPresent) {
            val hamburguer = optionalHamburger.get()
            return toHamburguer(hamburguer)
        } else {
            throw HamburguerException("Hamburguer não encontrado com o id: $id")
        }
    }

    override fun alterarHamburguer(id: Int, request: HamburguerRequest): Hamburguer {
        val hamburguer = buscarHamburguerPorId(id)

        hamburguer.tipoDePao = escolherPao(request.tipoDePao)
        hamburguer.carne = escolherCarne(request.carne)
        hamburguer.queijo = escolherQueijo(request.queijo)
        hamburguer.salada = escolherSalada(request.salada)

        repository.save(toEntity(hamburguer))

        return hamburguer;
    }

    override fun deletarHamburguer(id: Int) {
        val hamburguer = buscarHamburguerPorId(id)
        repository.deleteById(hamburguer.id!!)
    }

    override fun deletarHamburgueres() {
        repository.deleteAll()
    }

    private fun escolherPao(escolha: Int): TipoDePao {

        val tipoDePao = when (escolha) {
            1 -> TipoDePao.AUSTRALIANO
            2 -> TipoDePao.BRIOCHE
            3 -> TipoDePao.ARTESANAL
            4 -> TipoDePao.SEM_GLUTEM
            else -> throw HamburguerException("Pão - Opção inválida")
        }
        return tipoDePao
    }

    private fun escolherCarne(escolha: Int): Carne {
        val carne = when (escolha) {
            1 -> Carne.AO_PONTO
            2 -> Carne.BEM_PASSADA
            3 -> Carne.MAL_PASSADA
            4 -> Carne.VEGANA
            else -> throw HamburguerException("Carne - Opção inválida")
        }
        return carne
    }

    private fun escolherQueijo(escolha: Int): Queijo {
        val queijo = when (escolha) {
            1 -> Queijo.CHEDDAR
            2 -> Queijo.MINAS
            3 -> Queijo.MUSSARELA
            4 -> Queijo.ZERO_LACTOSE
            5 -> Queijo.SEM_QUEIJO
            else -> throw HamburguerException("Queijo - Opção inválida")
        }
        return queijo
    }

    private fun escolherSalada(escolha: Int): Salada {
        val salada = when (escolha) {
            1 -> Salada.ALFACE
            2 -> Salada.RÚCULA
            3 -> Salada.TOMATE
            4 -> Salada.SEM_SALADA
            else -> throw HamburguerException("Salada - Opção inválida")
        }
        return salada
    }
}