package com.hamburguer.repository

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface HamburguerRepository : JpaRepository<HamburguerEntity,Int>{
}