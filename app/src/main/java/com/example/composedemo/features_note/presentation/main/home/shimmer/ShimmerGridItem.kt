package com.example.composedemo.features_note.presentation.main.home.shimmer

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.composedemo.features_note.domain.model.Packages
import com.example.composedemo.features_note.presentation.main.home.HomeItem
import com.example.composedemo.features_note.presentation.main.home.HomeListEvent
import com.example.composedemo.features_note.presentation.main.home.HomeViewModel

@Composable
fun ShimmerGridItem(
    brush: Brush,
    showLoading: Boolean,
    packages: Packages,
    viewModel: HomeViewModel
) {

    HomeItem(
        packages = packages,
        onEvent = viewModel::onEvent,
        modifier = Modifier
            .fillMaxSize()
            .clickable { viewModel.onEvent(HomeListEvent.OnPackageClick(packages)) }
            .padding(16.dp)
    )
    if (showLoading) {
        Row(
            modifier = Modifier
                .padding(8.dp)
                .fillMaxSize()
                .padding(4.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {

            Spacer(
                modifier = Modifier
                    .size(60.dp)
                    .clip(CircleShape)
                    .padding(4.dp)
                    .background(brush)
            )

            Spacer(modifier = Modifier.width(10.dp))

            Column(
                modifier = Modifier
                    .wrapContentHeight()
                    .padding(4.dp)
                    .weight(1f),
                verticalArrangement = Arrangement.Center
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Spacer(
                        modifier = Modifier
                            .height(20.dp)
                            .clip(RoundedCornerShape(10.dp))
                            .fillMaxWidth(fraction = 0.7f)
                            .background(brush)
                    )
                }

                Spacer(modifier = Modifier.height(4.dp))

                Spacer(
                    modifier = Modifier
                        .height(20.dp)
                        .clip(RoundedCornerShape(10.dp))
                        .fillMaxWidth(fraction = 0.9f)
                        .background(brush)
                )

                Spacer(modifier = Modifier.height(4.dp)) //creates an empty space between

            }
            Spacer(
                modifier = Modifier
                    .height(20.dp)
                    .clip(RoundedCornerShape(10.dp))
                    .padding(end = 4.dp)
                    .fillMaxWidth(fraction = 0.2f)
                    .background(brush)
            )
        }
    }
}


@Composable
@Preview(showBackground = true)
fun ShimmerPreview() {

}