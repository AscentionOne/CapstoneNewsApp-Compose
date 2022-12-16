package com.kenchen.capstonenewsappcompose.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.kenchen.capstonenewsappcompose.database.converters.SourceConverter
import com.kenchen.capstonenewsappcompose.database.dao.ArticleDao
import com.kenchen.capstonenewsappcompose.database.dao.SourceDao
import com.kenchen.capstonenewsappcompose.model.Article
import com.kenchen.capstonenewsappcompose.model.Source

const val DATABASE_VERSION = 1

@Database(
    entities = [Article::class, Source::class],
    version = DATABASE_VERSION
)
@TypeConverters(SourceConverter::class)
abstract class NewsDatabase() : RoomDatabase() {
//    companion object {
//        private const val DATABASE_NAME = "News"
//        fun buildDatabase(context: Context): NewsDatabase {
//            return Room.databaseBuilder(
//                context,
//                NewsDatabase::class.java,
//                DATABASE_NAME
//            )
//                .build()
//        }
//    }

    abstract fun articleDao(): ArticleDao

    //
    abstract fun sourceDao(): SourceDao
}
