package life.league.challenge.kotlin.model

data class UserPost(
    var id: Int? = null,
    var name: String? = null,
    var avatar: String? = null,
    val title: String? = null,
    val body: String? = null
) {
}