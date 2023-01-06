package com.example.composedemo.features_note.presentation.main.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.composedemo.R
import com.example.composedemo.features_note.domain.model.Packages

@Composable
fun HomeItem(
    packages: Packages,
    onEvent: (HomeListEvent) -> Unit,
    modifier: Modifier = Modifier,
) {

    Card(
        elevation = 8.dp,
        modifier = modifier,
    ) {
        Row(
            modifier = Modifier
                .padding(8.dp)
                .fillMaxWidth()
                .padding(4.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            Image(
                painter = painterResource(id = R.drawable.portrait_profile),
                contentDescription = "profile",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .size(60.dp)
                    .clip(CircleShape)
                    .padding(4.dp)
            )

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
                    Text(
                        text = packages.title,
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold
                    )

                    Spacer(modifier = Modifier.width(8.dp))

                    IconButton(onClick = {
                        onEvent(HomeListEvent.OnDeletePackageClick(packages))
                    }) {
                        Icon(imageVector = Icons.Default.Delete, contentDescription = "delete")
                    }
                }

                Spacer(modifier = Modifier.width(2.dp))

                packages.description?.let {
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = it,
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.Gray
                    )
                }
            }
            Text(
                text = "$ " + packages.price,
                fontSize = 20.sp,
                modifier = Modifier.padding(end = 4.dp)
            )
        }
    }
}
