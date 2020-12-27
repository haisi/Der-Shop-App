package li.selman.dershop.tech.db

import androidx.room.Database
import androidx.room.RoomDatabase
import li.selman.dershop.business.popular.ArticleCacheEntity
import li.selman.dershop.business.popular.ArticleDao

 @Database(entities = [ArticleCacheEntity::class], version = 1)
 abstract class DerShopDatabase : RoomDatabase() {

    companion object {
        const val DATABASE_NAME: String = "dershop_db"
    }

    abstract fun articleDao(): ArticleDao
}
