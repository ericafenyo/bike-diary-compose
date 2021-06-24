/*
 * The MIT License (MIT)
 *
 * Copyright (C) 2021 Eric Afenyo
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package com.ericafenyo.tracker.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.ericafenyo.tracker.database.dao.AdventureDao
import com.ericafenyo.tracker.database.dao.ConfigDao
import com.ericafenyo.tracker.database.dao.GuestDao
import com.ericafenyo.tracker.database.dao.UserInfoDao
import com.ericafenyo.tracker.database.dao.WeightDao
import com.ericafenyo.tracker.database.entities.AdventureEntity
import com.ericafenyo.tracker.database.entities.Config
import com.ericafenyo.tracker.database.entities.GuestEntity
import com.ericafenyo.tracker.database.entities.UserInfo
import com.ericafenyo.tracker.database.entities.WeightEntity

@Database(
  entities = [AdventureEntity::class, GuestEntity::class, UserInfo::class, WeightEntity::class, Config::class],
  version = 1,
  exportSchema = false
)
abstract class CacheDatabase : RoomDatabase() {
  abstract fun getAdventureDao(): AdventureDao
  abstract fun getGuestDao(): GuestDao
  abstract fun getUserInfoDao(): UserInfoDao
  abstract fun getWeightDao(): WeightDao
  abstract fun getConfigDao(): ConfigDao

  companion object {
    private const val DB_NAME = "cache-db"

    @Volatile
    private var INSTANCE: CacheDatabase? = null

    @JvmStatic
    fun getInstance(context: Context): CacheDatabase {
      return INSTANCE ?: synchronized(this) {
        INSTANCE ?: createCacheDatabase(context)
          .also { INSTANCE = it }
      }
    }

    private fun createCacheDatabase(context: Context): CacheDatabase {
      return Room.databaseBuilder(
        context,
        CacheDatabase::class.java,
        DB_NAME
      ).build()
    }
  }
}