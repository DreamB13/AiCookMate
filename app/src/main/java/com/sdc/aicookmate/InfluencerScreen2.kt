package com.sdc.aicookmate


import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController


@Composable
fun InfluencerScreen2(navController: NavController) {
    Scaffold(
        bottomBar = { BottomBar(navController) }
    ) { paddingValues ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.White)
                .padding(16.dp)
                .padding(paddingValues)
        ) {

            InfluencerRow(navController)

            OutlinedTextField(
                value = "",
                onValueChange = {},
                leadingIcon = { Icon(Icons.Default.Search, "검색") },
                placeholder = { Text("인플루언서의 레시피를 검색해보세요") },
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(30.dp)
            )

            Spacer(modifier = Modifier.height(10.dp))

//           FoodItems(navController)

        }
    }
}


@Composable
fun InfluencerRow(navController: NavController) {
    val categories = listOf(
        "백종원"
    )

    LazyRow(
        modifier = Modifier.padding(vertical = 8.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(categories) { category ->
            Text(
                text = category,
                modifier = Modifier
                    .padding(horizontal = 12.dp, vertical = 6.dp),
                color = Color.Black,
                fontSize = 14.sp,

                )
        }
    }
}

