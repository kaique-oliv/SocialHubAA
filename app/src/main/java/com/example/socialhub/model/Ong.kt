package com.example.socialhub.model

data class Ong(
    var uid: String = "",
    var name: String = "",
    var email: String= "",
    var logo : String= "",
    var endereco: String = "",
    var telefones : Telefones = Telefones(),
    var setor_id : Int= 0,
    var link_site : String= "",
    var redes : Redes = Redes()
    )
