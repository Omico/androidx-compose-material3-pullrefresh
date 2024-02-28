package me.omico.lux.compose.material3.pullrefresh.test

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import java.util.concurrent.atomic.AtomicInteger

class MainViewModel : ViewModel() {
    private val increment: AtomicInteger = AtomicInteger(0)

    private val isLoading: MutableStateFlow<Boolean> = MutableStateFlow(false)
    private val items: MutableStateFlow<List<String>> = MutableStateFlow(emptyList())

    val state: StateFlow<MainViewState> =
        combine(
            isLoading,
            items,
        ) {
                isLoading,
                items,
            ->
            MainViewState(
                isLoading = isLoading,
                items = items,
            )
        }.stateIn(
            scope = viewModelScope,
            started = SharingStarted.Lazily,
            initialValue = MainViewState.Empty,
        )

    init {
        refresh()
    }

    fun refresh() {
        viewModelScope.launch {
            isLoading.value = true
            // Simulate a network request
            delay(1500)
            val times = increment.incrementAndGet()
            items.value = (times * 5 downTo 0).map { "Item $it" }
            isLoading.value = false
        }
    }
}
