package life.league.challenge.kotlin.model

import java.io.Serializable

data class User(var id: Int? = null, var name: String? = null, var avatar: String? = null,) :
    Serializable