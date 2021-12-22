package com.example.socialhub.model

data class Ong(
    val uid: String = "",
    val name: String = "",
    val email: String= "",
    val logo : String= "",
    val endereco: String = "",
    val telefones : Telefones = Telefones(),
    val setor_id : Int= 0,
    val link_site : String= "",
    val redes : Redes = Redes()
    )
