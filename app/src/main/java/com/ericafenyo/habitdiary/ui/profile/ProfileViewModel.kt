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

package com.ericafenyo.habitdiary.ui.profile

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ericafenyo.habitdiary.data
import com.ericafenyo.habitdiary.data.settings.ObserveThemeInteractor
import com.ericafenyo.habitdiary.data.settings.SetThemeUseCase
import com.ericafenyo.habitdiary.model.Theme
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class ProfileViewModel @ViewModelInject constructor(
  private val observeTheme: ObserveThemeInteractor,
  private val setTheme: SetThemeUseCase,
) : ViewModel() {
  private val _isDarkTheme = MutableLiveData<Boolean>()
  val isDarkTheme: LiveData<Boolean> get() = _isDarkTheme

  init {
    viewModelScope.launch {
      observeTheme(Unit).collect {
        _isDarkTheme.value = it.data == Theme.DARK
      }
    }
  }

  fun onEnableDarkThemeEvent(enabled: Boolean) {
    viewModelScope.launch {
      val theme = if (enabled) Theme.DARK else Theme.LIGHT
      setTheme(theme)
    }
  }
}
