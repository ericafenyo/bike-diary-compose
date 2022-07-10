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

package com.ericafenyo.bikediary.repositories.adventure

import com.ericafenyo.bikediary.model.Adventure
import kotlinx.coroutines.flow.Flow

/**
 * Repository implementation serving as a single point of access to adventure data.
 */
interface AdventureRepository {

  /**
   * Returns available observable adventures
   */
  fun adventures(): Flow<List<Adventure>>

  /**
   * Returns a specific adventure
   *
   * @param id unique string identifying the adventure.
   */

  fun adventure(id: String): Flow<Adventure>

  /**
   * Replace the local database with fresh data from the remote source.
   *
   * @param refresh force remote data fetching.
   *
   * @return 'true' if the update was successful.
   */
  suspend fun updateAdventures(refresh: Boolean): Boolean

  suspend fun synchronizeAdventures()
}
