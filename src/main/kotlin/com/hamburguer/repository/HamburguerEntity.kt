package repository


import com.Lanchonete.hamburguer.core.enuns.Carne
import com.Lanchonete.hamburguer.core.enuns.Queijo
import com.Lanchonete.hamburguer.core.enuns.Salada
import com.Lanchonete.hamburguer.core.enuns.TipoDePao
import jakarta.persistence.*
import jakarta.validation.constraints.NotEmpty
import jakarta.validation.constraints.NotNull


@Entity
@Table(name="hamburguer")
class HamburguerEntity(

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", unique = true, nullable = false)
    val id: Int = 0,

    @NotNull(message = "Pao não pode ser nulo")
    @NotEmpty(message = "Pao não pode ser vazio")
    @Column(name = "tipoDePao")
    @Enumerated(EnumType.STRING)
    var tipoDePao: TipoDePao,

    @NotNull(message = "Carne não pode ser nulo")
    @NotEmpty(message = "Carne não pode ser vazio")
    @Column(name = "carne")
    @Enumerated(EnumType.STRING)
    var carne: Carne,

    @NotNull(message = "Queijo não pode ser nulo")
    @NotEmpty(message = "Queijo não pode ser vazio")
    @Column(name = "queijo")
    @Enumerated(EnumType.STRING)
    var queijo: Queijo,

    @NotNull(message = "Salada não pode ser nulo")
    @NotEmpty(message = "Salada não pode ser vazio")
    @Column(name = "salada")
    @Enumerated(EnumType.STRING)
    var salada: Salada

)

