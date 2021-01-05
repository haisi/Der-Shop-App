package li.selman.dershop.tech.network

import li.selman.dershop.business.popular.MostViewOf
import retrofit2.Converter
import retrofit2.Retrofit
import java.lang.reflect.Type

/**
 * Allows us to use our custom enums, such as [MostViewOf], as Retrofit path variables.
 *
 * @see [li.selman.dershop.business.popular.MostPopularApi] for an example.
 * @see <a href="https://medium.com/tedpark-developer/how-to-use-enum-with-retrofit-query-path-d9c14b93d68f">This blog article
 * for a more generic approach for enums</a>
 */
class CustomPathTypeConverterFactory : Converter.Factory() {

    private val converter = Converter<MostViewOf, String> { value -> value.days.toString() }

    override fun stringConverter(type: Type, annotations: Array<Annotation>, retrofit: Retrofit): Converter<*, String>? {
        return if (type is Class<*> && type == MostViewOf::class.java) {
            converter
        } else {
            super.stringConverter(type, annotations, retrofit)
        }
    }
}
