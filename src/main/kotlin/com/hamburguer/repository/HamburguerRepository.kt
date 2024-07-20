package com.santa.hamburgueria.repository

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import repository.HamburguerEntity

@Repository
interface HamburguerRepository : JpaRepository<HamburguerEntity,Int>{
}