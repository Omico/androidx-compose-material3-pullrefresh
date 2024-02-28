package me.omico.lux.compose.material3.pullrefresh.test

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.pulltorefresh.PullToRefreshContainer
import androidx.compose.material3.pulltorefresh.rememberPullToRefreshState
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.unit.dp
import me.omico.lux.compose.material3.pullrefresh.test.theme.AppTheme

class MainActivity : ComponentActivity() {
    private val viewModel: MainViewModel by viewModels()

    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AppTheme {
                val state by viewModel.state.collectAsState()

                val pullToRefreshState = rememberPullToRefreshState()
                // If the pull-to-refresh state is refreshing, then trigger the refresh data.
                if (pullToRefreshState.isRefreshing) {
                    LaunchedEffect(Unit) {
                        viewModel.refresh()
                    }
                }

                // Observe the loading state, when "state.isLoading" is false,
                // which means the loading is finished,
                // then tell the pull-to-refresh state to end the refreshing.
                LaunchedEffect(state.isLoading) {
                    if (state.isLoading) return@LaunchedEffect
                    pullToRefreshState.endRefresh()
                }

                Box(modifier = Modifier.nestedScroll(connection = pullToRefreshState.nestedScrollConnection)) {
                    LazyColumn(
                        modifier = Modifier.fillMaxSize(),
                        verticalArrangement = Arrangement.spacedBy(space = 8.dp),
                    ) {
                        item {
                            Button(
                                onClick = { pullToRefreshState.startRefresh() },
                                content = { Text(text = "Refresh") },
                            )
                        }
                        if (!pullToRefreshState.isRefreshing) {
                            items(state.items) { item ->
                                Card {
                                    Box(modifier = Modifier.padding(all = 8.dp)) {
                                        Text(text = item)
                                    }
                                }
                            }
                        }
                    }
                    PullToRefreshContainer(
                        modifier = Modifier.align(alignment = Alignment.TopCenter),
                        state = pullToRefreshState,
                    )
                }
            }
        }
    }
}
