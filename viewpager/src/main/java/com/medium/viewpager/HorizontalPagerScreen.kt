package com.medium.viewpager

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.google.accompanist.pager.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@OptIn(ExperimentalPagerApi::class)
@Composable
fun HorizontalPagerScreen() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(30.dp)
            .background(Color.LightGray)
    ) {
        val items = createItems()
        val pagerState = rememberPagerState()
        val coroutineScope = rememberCoroutineScope()

        /*   HorizontalTabs(
               items = items,
               pagerState = pagerState,
               scope = coroutineScope
           )*/

        HorizontalPager(
            count = items.size,
            state = pagerState,
            modifier = Modifier.weight(1f)
        ) { currentPage ->
            Column(
                modifier = Modifier.fillMaxSize()
            ) {
                Text(
                    text = items[currentPage].title,
                    style = MaterialTheme.typography.h2
                )
                Spacer(modifier = Modifier.height(10.dp))
                Text(
                    text = items[currentPage].subtitle,
                    style = MaterialTheme.typography.h4
                )
                Spacer(modifier = Modifier.height(10.dp))
                Text(
                    text = items[currentPage].description,
                    style = MaterialTheme.typography.body1
                )
            }
        }

        HorizontalPagerIndicator(
            pagerState = pagerState,
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .padding(16.dp)
                .align(Alignment.CenterHorizontally),
            indicatorHeight = 8.dp,
            activeColor = Color.White,
            inactiveColor = Color.DarkGray,
            indicatorWidth = ChangeSizeOfShape(currentPage = pagerState.currentPage),
            pageCount = 2,
            indicatorShape = RoundedCornerShape(corner = CornerSize(40.dp))
        )

        Button(
            onClick = {
                coroutineScope.launch {
                    pagerState.animateScrollToPage(page = 2)
                }
            },
            modifier = Modifier.align(Alignment.CenterHorizontally)
        ) {
            Text(text = "Scroll to the third page")
        }
    }
}

@Composable
fun CorrectShape(currentPage: Int): Shape {
    var shapeToReturn: Shape? = null
    when (currentPage) {
        0 -> {
            shapeToReturn = RoundedCornerShape(corner = CornerSize(50.dp))
        }
        1 -> {
            shapeToReturn = RoundedCornerShape(corner = CornerSize(15.dp))
        }
    }

    return shapeToReturn!!
}

@Composable
fun ChangeSizeOfShape(currentPage: Int): Dp {
    when (currentPage) {
        0 -> {
            return 8.dp
        }
        1 -> {
            return 16.dp
        }
    }
    return 8.dp
}

@OptIn(ExperimentalPagerApi::class)
@Composable
fun HorizontalTabs(
    items: List<HorizontalPagerContent>,
    pagerState: PagerState,
    scope: CoroutineScope
) {
    TabRow(
        selectedTabIndex = pagerState.currentPage,
        indicator = { tabPositions ->
            TabRowDefaults.Indicator(
                modifier = Modifier.pagerTabIndicatorOffset(pagerState, tabPositions)
            )
        }
    ) {
        items.forEachIndexed { index, item ->
            Tab(
                selected = pagerState.currentPage == index,
                onClick = {
                    scope.launch {
                        pagerState.animateScrollToPage(page = index)
                    }
                }
            ) {
                Text(text = item.title)
            }
        }
    }
}

private fun createItems() = listOf(
    HorizontalPagerContent(title = "Title1", subtitle = "Subtitle1", description = "Description1"),
    HorizontalPagerContent(title = "Title2", subtitle = "Subtitle2", description = "Description2"),
    HorizontalPagerContent(title = "Title3", subtitle = "Subtitle3", description = "Description3"),
    HorizontalPagerContent(title = "Title4", subtitle = "Subtitle4", description = "Description4"),
    HorizontalPagerContent(title = "Title5", subtitle = "Subtitle5", description = "Description5")
)

data class HorizontalPagerContent(
    val title: String,
    val subtitle: String,
    val description: String
)
