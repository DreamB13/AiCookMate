package com.sdc.aicookmate


import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicText
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.test.isSelected
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController

@Composable
fun RecipeScreen(navController: NavController) {

    val viewModel: RecipeViewModel = viewModel()
    val recipes by viewModel.recipes.collectAsState()
    Scaffold(
        bottomBar = { BottomBar(navController) }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .padding(paddingValues)
                .verticalScroll(rememberScrollState())
                .background(Color.White)
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(90.dp)
                    .background(color = colorResource(R.color.titleColor))
            ) {
                FirebaseDropdown(
                    viewModel = viewModel,
                    placeholderText = "레시피를 검색하세요"
                ) { selectedRecipe ->
                    println("선택된 레시피: ${selectedRecipe.title}")
                    navController.navigate("recipeDetail/${selectedRecipe.title}")
                }
            }

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp)
            ) {
                Spacer(modifier = Modifier.height(3.dp))

                FoodCategories(navController)

                Spacer(modifier = Modifier.height(3.dp))

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 9.dp),
                    horizontalArrangement = Arrangement.End
                ) {
                    RecentButton(
                        text = "최신순",
                        isSelected = true,
                        onClick = { /**/ }
                    )

                    Spacer(modifier = Modifier.width(8.dp))

                    RecentButton(
                        text = "인기순",
                        isSelected = false,
                        onClick = { /**/ }
                    )
                }
                RecipeList(recipes = recipes, navController = navController)

                Spacer(modifier = Modifier.height(6.dp))


            }
        }
    }
}


@Composable
fun FoodCategories(navController: NavController) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        verticalArrangement = Arrangement.SpaceEvenly
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 4.dp, horizontal = 0.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            FoodCategoryItem(navController, title = "찜조림", iconRes = R.drawable.ic_zzim)
            FoodCategoryItem(navController, title = "국/탕/찌개", iconRes = R.drawable.ic_gook)
            FoodCategoryItem(navController, title = "볶음/구이", iconRes = R.drawable.ic_bokuem)
        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 4.dp, horizontal = 0.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            FoodCategoryItem(navController, title = "밥/죽", iconRes = R.drawable.ic_rice)
            FoodCategoryItem(navController, title = "면/만두", iconRes = R.drawable.ic_noodle)
            FoodCategoryItem(navController, title = "간편요리", iconRes = R.drawable.ic_quick)
        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 4.dp, horizontal = 0.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            FoodCategoryItem(navController, title = "야식", iconRes = R.drawable.ic_chicken)
            FoodCategoryItem(navController, title = "다이어트", iconRes = R.drawable.ic_diet)
            FoodCategoryItem(navController, title = "인플루언서", iconRes = R.drawable.ic_influencer)
        }
    }
}


@Composable
fun FoodCategoryItem(navController: NavController, title: String, iconRes: Int) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.width(100.dp)
    ) {
        Box(
            modifier = Modifier
                .size(100.dp)
                .clip(RoundedCornerShape(percent = 20))
                .background(Color.White)
                .clickable {
                    val routes = mapOf(
                        "찜조림" to "zzimScreen",
                        "국/탕/찌개" to "gookScreen",
                        "볶음/구이" to "bokuemScreen",
                        "밥/죽" to "riceScreen",
                        "면/만두" to "noodleScreen",
                        "간편요리" to "quickScreen",
                        "야식" to "chickenScreen",
                        "다이어트" to "dietScreen",
                        "인플루언서" to "influencerScreen"
                    )

                    val route = routes[title]
                    if (route != null) {
                        navController.navigate(route)
                    }
                }
                .border(
                    width = 0.5.dp,
                    color = Color.Black,
                    shape = RoundedCornerShape(percent = 20)
                ),
            contentAlignment = Alignment.Center
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center,
                modifier = Modifier.fillMaxSize()
            ) {
                Box(
                    modifier = Modifier.size(100.dp)
                ) {
                    Image(
                        painter = painterResource(id = iconRes),
                        contentDescription = null,
                        modifier = Modifier
                            .size(80.dp)
                            .align(Alignment.TopCenter),
                        contentScale = ContentScale.Crop
                    )

                    Text(
                        text = title,
                        modifier = Modifier
                            .align(Alignment.BottomCenter)
                            .padding(bottom = 5.dp),
                        style = TextStyle(
                            fontSize = 16.sp,
                            color = Color.Black,
                            fontWeight = FontWeight.Bold
                        )
                    )
                }
            }
        }
    }
}

@Composable
fun FoodItemCard(navController: NavController, foodItem: FoodItem) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(12.dp))
            .clickable { navController.navigate("recipeExplain") }
            .border(0.5.dp, Color.Black, RoundedCornerShape(12.dp))
    ) {

        Image(
            painter = painterResource(id = foodItem.imageRes),
            contentDescription = null,
            modifier = Modifier
                .fillMaxSize()
                .height(150.dp),
            contentScale = ContentScale.Crop
        )
    }


    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.White)
            .padding(5.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column {

                androidx.compose.foundation.text.BasicText(
                    text = foodItem.title,
                    style = TextStyle(
                        fontSize = 16.sp,
                        color = Color.Black
                    )
                )

                Spacer(modifier = Modifier.height(1.dp))

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.ic_time),
                        contentDescription = null,
                        modifier = Modifier.size(16.dp),
                        colorFilter = ColorFilter.tint(Color.Gray)
                    )
                    Box(
                        modifier = Modifier
                            .padding(bottom = 3.dp)
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceBetween,
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            BasicText(
                                text = foodItem.duration,
                                style = TextStyle(
                                    fontSize = 16.sp,
                                    color = Color.Gray,
                                )
                            )
                            DifficultyStars(DifficultyLevel.BEGINNER)
                        }
                    }
                }
            }
        }
    }
}

data class FoodItem(
    val title: String,
    val duration: String,
    val imageRes: Int
)


enum class DifficultyLevel(val text: String) {
    BEGINNER("☆초급"),
    INTERMEDIATE("☆중급"),
    ADVANCED("☆고급")
}

@Composable
fun DifficultyStars(level: DifficultyLevel) {
    Text(
        text = level.text,
        style = TextStyle(
            fontSize = 16.sp,
            color = Color.Gray,
            fontWeight = FontWeight.Normal
        )
    )
}

@Composable
fun RecentButton(
    text: String,
    isSelected: Boolean = false,
    onClick: () -> Unit
) {
    Button(
        onClick = onClick,
        colors = ButtonDefaults.buttonColors(
            containerColor = if (isSelected) Color.Black else Color.White,
            contentColor = if (isSelected) Color.White else Color.Black
        ),
        shape = RoundedCornerShape(8.dp),
        border = BorderStroke(
            width = 1.dp,
            color = if (isSelected) Color.Black else Color.Gray.copy(alpha = 0.3f)
        ),
        contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp),
        modifier = Modifier.height(36.dp)
    ) {
        Text(
            text = text,
            style = TextStyle(
                fontSize = 14.sp,
                fontWeight = FontWeight.Medium
            )
        )
    }
}