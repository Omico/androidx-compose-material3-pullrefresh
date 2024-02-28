package me.omico.lux.compose.material3.pullrefresh.test

data class MainViewState(
    val isLoading: Boolean = false,
    val items: List<String> = emptyList(),
) {
    companion object {
        val Empty = MainViewState()
    }
}
