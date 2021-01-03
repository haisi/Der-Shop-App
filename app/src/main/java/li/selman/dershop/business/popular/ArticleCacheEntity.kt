package li.selman.dershop.business.popular

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "article")
class ArticleCacheEntity(
    @PrimaryKey(autoGenerate = false)
    @ColumnInfo(name = "id")
    val id: Long,
    @ColumnInfo(name = "title")
    val title: String,
    @ColumnInfo(name = "article_url")
    val articleUrl: String,
    @ColumnInfo(name = "image_url")
    val imageUrl: String,
    @ColumnInfo(name = "read")
    var isRead: Boolean = false,
    @ColumnInfo(name = "favourite")
    var isFavourite: Boolean = false
) {

    override fun toString(): String {
        return "ArticleCacheEntity(id=$id, title='$title', articleUrl='$articleUrl', imageUrl='$imageUrl', isRead=$isRead, isFavourite=$isFavourite)"
    }
}
