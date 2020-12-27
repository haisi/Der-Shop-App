package li.selman.dershop.tech.db

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import li.selman.dershop.business.popular.ArticleDao
import javax.inject.Singleton

@Module
@InstallIn(ApplicationComponent::class)
object DbModule {

    @Singleton
    @Provides
    fun provideStoryDb(@ApplicationContext context: Context): DerShopDatabase {
        return Room
            .databaseBuilder(
                context,
                DerShopDatabase::class.java,
                DerShopDatabase.DATABASE_NAME
            )
            .fallbackToDestructiveMigration()
            .build()
    }

    @Singleton
    @Provides
    fun provideArticleDao(derShopDatabase: DerShopDatabase): ArticleDao = derShopDatabase.articleDao()
}
