package saberliou.demo.profile.data

/**
 * A generic class that holds a value with its loading status.
 *
 * @param <T>
 */
sealed class Result<out R> {
    companion object {
        fun Result<*>.isSuccess() = this is Success && data != null
    }

    data class Success<out T>(val data: T) : Result<T>() {
        override fun equals(other: Any?): Boolean {
            if (this === other) return true
            if (javaClass != other?.javaClass) return false

            other as Success<*>

            if (data != other.data) return false

            return true
        }

        override fun hashCode(): Int {
            return data?.hashCode() ?: 0
        }
    }

    data class Error(val exception: Exception) : Result<Nothing>() {
        override fun equals(other: Any?): Boolean {
            if (this === other) return true
            if (javaClass != other?.javaClass) return false

            other as Error

            if (exception.message != other.exception.message) return false

            return true
        }

        override fun hashCode(): Int {
            return exception.hashCode()
        }
    }

    object Loading : Result<Nothing>()

    override fun toString(): String {
        return when (this) {
            is Success<*> -> "Success[data=$data]"
            is Error -> "Error[exception=$exception]"
            Loading -> "Loading"
        }
    }
}