package com.hamburguer.service

import com.hamburguer.core.entities.Hamburguer
import com.hamburguer.core.enuns.Carne
import com.hamburguer.core.enuns.Queijo
import com.hamburguer.core.enuns.Salada
import com.hamburguer.core.enuns.TipoDePao
import com.hamburguer.repository.HamburguerEntity
import com.hamburguer.repository.HamburguerRepository
import com.hamburguer.useCase.HamburguerService
import com.hamburguer.useCase.exceptions.HamburguerException
import com.hamburguer.useCase.request.HamburguerRequest
import com.hamburguer.useCase.translator.toHamburguer
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.DynamicTest
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestFactory
import org.junit.jupiter.api.assertThrows
import org.mockito.ArgumentMatchers.any
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito.*
import org.springframework.boot.test.context.SpringBootTest
import java.util.*
import java.util.stream.Stream

@SpringBootTest
class HamburguerServiceTest {

    @Mock
    private lateinit var repository: HamburguerRepository

    @InjectMocks
    private lateinit var service: HamburguerService

    private val hamburguerEntityTeste =
        HamburguerEntity(14, TipoDePao.AUSTRALIANO, Carne.BEM_PASSADA, Queijo.MUSSARELA, Salada.ALFACE)
    private val hamburguerEntityTeste2 =
        HamburguerEntity(15, TipoDePao.SEM_GLUTEM, Carne.MAL_PASSADA, Queijo.ZERO_LACTOSE, Salada.ALFACE)
    private val hamburguerTeste = toHamburguer(hamburguerEntityTeste)
    private val hamburguerTeste2 = toHamburguer(hamburguerEntityTeste2)
    private val hamburguerList: Iterable<Hamburguer> = listOf(hamburguerTeste, hamburguerTeste2)
    private val request = HamburguerRequest(1, 2, 3, 1)

    internal data class TabelaTeste(
        val hamburguerRequest: HamburguerRequest,
        val hamburguerEntity: HamburguerEntity? = null,
        val expected: String
    )

    @Test
    fun `teste criar hamburguer`() {

        `when`(repository.save(any(HamburguerEntity::class.java))).thenReturn(hamburguerEntityTeste)

        val result = service.criarHamburguer(request)

        assertNotNull(result)

        verify(repository, times(1)).save(any(HamburguerEntity::class.java))
    }

    @Test
    fun `teste buscar hamburguer por id`() {

        `when`(repository.findById(hamburguerEntityTeste.id)).thenReturn(Optional.of(hamburguerEntityTeste))

        val result = service.buscarHamburguerPorId(hamburguerEntityTeste.id)

        assertEquals(hamburguerTeste, result)

        verify(repository, times(1)).findById(hamburguerEntityTeste.id)
    }

    @Test
    fun `teste excecao buscar hamburguer por id`() {

        `when`(repository.findById(hamburguerEntityTeste.id)).thenReturn(Optional.of(hamburguerEntityTeste))

        assertThrows<HamburguerException> { service.buscarHamburguerPorId(20) }

        verify(repository, times(1)).findById(20)
    }

    @Test
    fun `teste listar hamburgueres`() {
        val hamburguerEntityList = listOf(hamburguerEntityTeste, hamburguerEntityTeste2)

        `when`(repository.findAll()).thenReturn(hamburguerEntityList)

        val result = service.listarHamburguer()

        assertEquals(hamburguerList, result)

        verify(repository, times(1)).findAll()

    }

    @Test
    fun `teste alterar hamburguer`() {

        `when`(repository.findById(hamburguerEntityTeste2.id)).thenReturn(Optional.of(hamburguerEntityTeste2))
        `when`(repository.save(any(HamburguerEntity::class.java))).thenReturn(hamburguerEntityTeste2)

        service.alterarHamburguer(15, request)

        verify(repository, times(1)).findById(hamburguerEntityTeste2.id)
        verify(repository, times(1)).save(any(HamburguerEntity::class.java))

    }

    @Test
    fun `teste deletar hamburgueres`() {
        doNothing().`when`(repository).deleteAll()

        service.deletarHamburgueres()

        verify(repository, times(1)).deleteAll()
    }

    @Test
    fun `teste deletar hamburguer por id`() {

        `when`(repository.findById(hamburguerEntityTeste2.id)).thenReturn(Optional.of(hamburguerEntityTeste2))

        doNothing().`when`(repository).deleteById(hamburguerEntityTeste2.id)

        service.deletarHamburguer(hamburguerEntityTeste2.id)

        verify(repository, times(1)).deleteById(hamburguerEntityTeste2.id)
        verify(repository, times(1)).findById(hamburguerEntityTeste2.id)
    }

    @TestFactory
    fun testeExcecaoOpcaoIngredientes(): Stream<DynamicTest> {
        val testeCenarios = listOf(
            TabelaTeste(HamburguerRequest(1, 2, 3, 8), null, "Salada - Opção inválida"),
            TabelaTeste(HamburguerRequest(9, 2, 3, 1), null, "Pão - Opção inválida"),
            TabelaTeste(HamburguerRequest(2, 9, 3, 1), null, "Carne - Opção inválida"),
            TabelaTeste(HamburguerRequest(1, 2, 7, 2), null, "Queijo - Opção inválida"),
        )

        return testeCenarios.map { test ->
            DynamicTest.dynamicTest("Teste para ${test.expected}") {
                val exception = assertThrows<HamburguerException> {
                    service.criarHamburguer(test.hamburguerRequest)
                }
                assertEquals(test.expected, exception.message)
            }
        }.stream()
    }

    @TestFactory
    fun testeCriarHamburguerTodosIngredientes(): Stream<DynamicTest> {

        val testeCenariosOk = listOf(
            TabelaTeste(
                HamburguerRequest(1, 1, 1, 1),
                HamburguerEntity(22, TipoDePao.AUSTRALIANO, Carne.AO_PONTO, Queijo.CHEDDAR, Salada.ALFACE),
                "teste 1"
            ),
            TabelaTeste(
                HamburguerRequest(2, 2, 2, 2),
                HamburguerEntity(23, TipoDePao.BRIOCHE, Carne.BEM_PASSADA, Queijo.MINAS, Salada.RÚCULA),
                "teste 2"
            ),
            TabelaTeste(
                HamburguerRequest(3, 3, 3, 3),
                HamburguerEntity(24, TipoDePao.ARTESANAL, Carne.MAL_PASSADA, Queijo.MUSSARELA, Salada.TOMATE),
                "teste 3"
            ),
            TabelaTeste(
                HamburguerRequest(4, 4, 4, 4),
                HamburguerEntity(25, TipoDePao.SEM_GLUTEM, Carne.VEGANA, Queijo.ZERO_LACTOSE, Salada.SEM_SALADA),
                "teste 4"
            ),
            TabelaTeste(
                HamburguerRequest(4, 4, 5, 4),
                HamburguerEntity(26, TipoDePao.SEM_GLUTEM, Carne.VEGANA, Queijo.SEM_QUEIJO, Salada.SEM_SALADA),
                "teste 5"
            )
        )
        `when`(repository.save(any(HamburguerEntity::class.java))).thenAnswer { invocation ->
            invocation.getArgument(0) as HamburguerEntity
        }

        return testeCenariosOk.map { test ->
            DynamicTest.dynamicTest(test.expected) {
                val resultado = service.criarHamburguer(test.hamburguerRequest)

                assertEquals(test.hamburguerEntity!!.carne, resultado.carne)
                assertEquals(test.hamburguerEntity!!.tipoDePao, resultado.tipoDePao)
                assertEquals(test.hamburguerEntity!!.queijo, resultado.queijo)
                assertEquals(test.hamburguerEntity!!.salada, resultado.salada)
            }
        }.stream()
    }
}
