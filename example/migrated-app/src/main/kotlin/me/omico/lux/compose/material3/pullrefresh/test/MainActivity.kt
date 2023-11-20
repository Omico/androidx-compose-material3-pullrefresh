package me.omico.lux.compose.material3.pullrefresh.test

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.pulltorefresh.PullToRefreshContainer
import androidx.compose.material3.pulltorefresh.rememberPullToRefreshState
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.delay
import me.omico.lux.compose.material3.pullrefresh.test.theme.AppTheme

class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AppTheme {
                // From https://cs.android.com/androidx/platform/frameworks/support/+/androidx-main:compose/material3/material3/samples/src/main/java/androidx/compose/material3/samples/PullToRefreshSamples.kt
                var itemCount by remember { mutableIntStateOf(15) }
                val state = rememberPullToRefreshState()
                if (state.isRefreshing) {
                    LaunchedEffect(Unit) {
                        delay(1500)
                        itemCount += 5
                        state.endRefresh()
                    }
                }
                Box(modifier = Modifier.nestedScroll(connection = state.nestedScrollConnection)) {
                    LazyColumn(
                        modifier = Modifier.fillMaxSize(),
                        verticalArrangement = Arrangement.spacedBy(space = 8.dp),
                    ) {
                        if (!state.isRefreshing) {
                            items(itemCount) {
                                Card {
                                    Box(modifier = Modifier.padding(all = 8.dp)) {
                                        Text(text = "Item ${itemCount - it}")
                                    }
                                }
                            }
                        }
                    }
                    PullToRefreshContainer(
                        modifier = Modifier.align(alignment = Alignment.TopCenter),
                        state = state,
                    )
                }
            }
        }
    }
}
