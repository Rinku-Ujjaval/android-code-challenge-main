package life.league.challenge.kotlin.model

import java.io.Serializable


data class Posts(
    var id: Int? = null,
    val userId: Int? = null,
    val title: String? = null,
    val body: String? = null
) :
    Serializable {
}