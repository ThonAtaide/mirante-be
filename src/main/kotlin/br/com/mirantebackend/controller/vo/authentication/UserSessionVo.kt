package br.com.mirantebackend.controller.vo.authentication

import br.com.mirantebackend.annotations.NoArgsConstructor

@NoArgsConstructor
data class UserSessionVo(
    val nickname: String,
    val jwtToken: String
)