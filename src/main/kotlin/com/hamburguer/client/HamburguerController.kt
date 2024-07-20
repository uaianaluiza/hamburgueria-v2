package com.hamburguer.client


import com.hamburguer.core.entities.Hamburguer
import com.hamburguer.useCase.HamburguerService
import com.hamburguer.useCase.request.HamburguerRequest
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*
import java.util.*

@RestController
@RequestMapping("/hamburguer/")
class HamburguerController(
    private val service: HamburguerService
){

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    fun criarHamburguer(@RequestBody request: HamburguerRequest): Hamburguer {
        return service.criarHamburguer(request)
    }

    @GetMapping("listarTodos/")
    @ResponseStatus(HttpStatus.OK)
    fun listarHamburguer() : Iterable<Hamburguer>{
        return service.listarHamburguer()
    }

    @GetMapping("listar/{id}")
    @ResponseStatus(HttpStatus.OK)
    fun buscarHamburguerPorId(@PathVariable id: Int): Hamburguer {
        return service.buscarHamburguerPorId(id)
    }

    @PutMapping("alterar/{id}")
    @ResponseStatus(HttpStatus.ACCEPTED)
    fun alterarHamburguer(@PathVariable id: Int,
                          @RequestBody request: HamburguerRequest
    ): Hamburguer {
        return service.alterarHamburguer(id,request)
    }

    @DeleteMapping("deletar/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun deletarHamburguer(@PathVariable id: Int){
        service.deletarHamburguer(id)
    }
    @DeleteMapping("deletarTodos/")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun deletarHamburgueres(){
        service.deletarHamburgueres()
    }
}