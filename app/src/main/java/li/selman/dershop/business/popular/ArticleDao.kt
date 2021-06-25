package li.selman.dershop.business.popular

import androidx.lifecycle.LiveData
import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface ArticleDao {

    @Query("SELECT * FROM article WHERE id=:id")
    suspend fun findById(id: Long): ArticleCacheEntity?

    @Query("SELECT * FROM article WHERE id IN (:ids)")
    fun findAllById(ids: List<Long>): Flow<List<ArticleCacheEntity>>

    @Update
    suspend fun update(entity: ArticleCacheEntity)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(articleEntity: ArticleCacheEntity): Long

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertAll(vararg articleEntity: ArticleCacheEntity): Array<Long>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertAll(articleEntities: List<ArticleCacheEntity>): Array<Long>

    @Query("SELECT * FROM article")
    fun getAll(): LiveData<List<ArticleCacheEntity>>

    @Query("SELECT * FROM article WHERE favourite = 1")
    suspend fun getAllFavourites(): List<ArticleCacheEntity>
}
