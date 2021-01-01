package li.selman.dershop.util

sealed class ActionResult<out R> {
    data class Success<out T>(val data: T) : ActionResult<T>()
    data class Error(val exception: Exception) : ActionResult<Nothing>()
}
