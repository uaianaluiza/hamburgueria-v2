package com.hamburguer.client

import com.hamburguer.core.entities.Hamburguer
import com.hamburguer.core.enuns.Carne
import com.hamburguer.core.enuns.Queijo
import com.hamburguer.core.enuns.Salada
import com.hamburguer.core.enuns.TipoDePao
import com.hamburguer.useCase.HamburguerService
import com.hamburguer.useCase.request.HamburguerRequest
import com.hamburguer.useCase.translator.toHamburguer
import com.fasterxml.jackson.databind.ObjectMapper
import com.hamburguer.repository.HamburguerEntity
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito.doNothing
import org.mockito.Mockito.`when`
import org.mockito.junit.jupiter.MockitoExtension
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultMatchers
import org.springframework.test.web.servlet.setup.MockMvcBuilders


@ExtendWith(MockitoExtension::class)
class HamburguerControllerTest {

    @Mock
    private lateinit var service: HamburguerService

    @InjectMocks
    private lateinit var controller: HamburguerController

    private val hamburguerEntityTeste =
        HamburguerEntity(14, TipoDePao.AUSTRALIANO, Carne.BEM_PASSADA, Queijo.MUSSARELA, Salada.ALFACE)
    private val hamburguerEntityTeste2 =
        HamburguerEntity(15, TipoDePao.SEM_GLUTEM, Carne.MAL_PASSADA, Queijo.ZERO_LACTOSE, Salada.ALFACE)
    private val hamburguerTeste = toHamburguer(hamburguerEntityTeste)
    private val hamburguerTeste2 = toHamburguer(hamburguerEntityTeste2)
    private val iterable: Iterable<Hamburguer> = listOf(hamburguerTeste, hamburguerTeste2)
    private val request = HamburguerRequest(1, 2, 3, 1)

    @Test
    fun `teste criar hamburguer`() {
        val mockMvc = MockMvcBuilders.standaloneSetup(controller).build()

        `when`(service.criarHamburguer(request)).thenReturn(hamburguerTeste)

        mockMvc.perform(
            MockMvcRequestBuilders.post("/hamburguer/").contentType(MediaType.APPLICATION_JSON)
                .content(ObjectMapper().writeValueAsBytes(request))
        ).andExpect(MockMvcResultMatchers.status().isCreated)
            .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
    }

    @Test
    fun `teste listar todos os hamburgueres`() {
        val mockMvc = MockMvcBuilders.standaloneSetup(controller).build()

        `when`(service.listarHamburguer()).thenReturn(iterable)

        mockMvc.perform(
            MockMvcRequestBuilders.get("/hamburguer/listarTodos/")
        ).andExpect(MockMvcResultMatchers.status().isOk)
            .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(MockMvcResultMatchers.content().json(ObjectMapper().writeValueAsString(iterable)))
    }

    @Test
    fun `teste listar hamburguer por id`() {

        val mockMvc = MockMvcBuilders.standaloneSetup(controller).build()

        `when`(service.buscarHamburguerPorId(14)).thenReturn(hamburguerTeste)

        mockMvc.perform(
            MockMvcRequestBuilders.get("/hamburguer/listar/${hamburguerEntityTeste.id}")
        ).andExpect(MockMvcResultMatchers.status().isOk)
            .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(MockMvcResultMatchers.content().json(ObjectMapper().writeValueAsString(hamburguerEntityTeste)))
    }

    @Test
    fun `teste alterar hamburguer`() {

        val mockMvc = MockMvcBuilders.standaloneSetup(controller).build()

        `when`(service.alterarHamburguer(15, HamburguerRequest(3, 2, 3, 1))).thenReturn(hamburguerTeste2)

        mockMvc.perform(
            MockMvcRequestBuilders.put("/hamburguer/alterar/${hamburguerEntityTeste2.id}")
                .contentType(MediaType.APPLICATION_JSON)
                .content(ObjectMapper().writeValueAsBytes(HamburguerRequest(3, 2, 3, 1)))
        )
            .andExpect(MockMvcResultMatchers.status().isAccepted)
            .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(MockMvcResultMatchers.content().json(ObjectMapper().writeValueAsString(hamburguerEntityTeste2)))
    }

    @Test
    fun `teste deletar todos os hamburgueres`() {

        val mockMvc = MockMvcBuilders.standaloneSetup(controller).build()

        doNothing().`when`(service).deletarHamburgueres()

        mockMvc.perform(
            MockMvcRequestBuilders.delete("/hamburguer/deletarTodos/")
        )

            .andExpect(MockMvcResultMatchers.status().isNoContent)
    }

    @Test
    fun `teste deletar hamburguer por id`() {
        val mockMvc = MockMvcBuilders.standaloneSetup(controller).build()

        doNothing().`when`(service).deletarHamburguer(hamburguerEntityTeste2.id)

        mockMvc.perform(
            MockMvcRequestBuilders.delete("/hamburguer/deletar/${hamburguerEntityTeste2.id}")
        )

            .andExpect(MockMvcResultMatchers.status().isNoContent)
    }
}