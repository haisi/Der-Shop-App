package li.selman.dershop.business.popular

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface ArticleDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(articleEntity: ArticleCacheEntity): Long

    @Query("SELECT * FROM article")
    suspend fun get(): List<ArticleCacheEntity>
}
