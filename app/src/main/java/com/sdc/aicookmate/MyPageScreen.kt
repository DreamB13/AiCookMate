package com.sdc.aicookmate

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.compose.foundation.layout.width
import androidx.compose.material.TabRowDefaults.Divider

data class Recipe(
    val title: String,
    val views: String,
    val viewCount: String,
    val descript: String,
    val imageRes: Int
)

@Composable
fun MyPageScreen(navController: NavController) {
    Scaffold(
        bottomBar = { BottomBar(navController) }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .padding(paddingValues)
                .background(Color.White)
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
        ) {
            ActionButtons(
                buttons = listOf("스크랩 레시피", "구독 목록", "후기 관리"),
                onButtonClick = { /* TODO */ }
            )

            RecentRecipeSection(
                recipes = listOf(
                    Recipe(
                        title = "맛있는 제육볶음",
                        views = "조회수",
                        viewCount = "32회",
                        descript = "",
                        imageRes = R.drawable.recipe1
                    ),
                    Recipe(
                        title = "부드러운 계란탕",
                        views = "조회수",
                        viewCount = "45회",
                        descript = "",
                        imageRes = R.drawable.recipe2
                    ),
                    Recipe(
                        title = "매콤달콤 떡볶이",
                        views = "조회수",
                        viewCount = "28회",
                        descript = "",
                        imageRes = R.drawable.recipe3
                    ),
                    Recipe(
                        title = "매콤한 어묵무침",
                        views = "조회수",
                        viewCount = "56회",
                        descript = "",
                        imageRes = R.drawable.recipe4
                    ),
                    Recipe(
                        title = "쫄깃한 아구찜",
                        views = "조회수",
                        viewCount = "41회",
                        descript = "",
                        imageRes = R.drawable.recipe5
                    )
                )
            )

            Spacer(modifier = Modifier.height(32.dp))


            Text(
                text = "알림",
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier
                    .padding(horizontal = 16.dp),
                color = Color.Black
            )

            Divider(
                color = Color.Gray, // 선의 색상
                thickness = 0.5.dp,   // 선의 두께
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
            )
            NoticeList(
                noticeItems = listOf(
                    "📢 공지사항",
                    "🎉 이벤트",
                    "고객센터",
                    "📝 자주 묻는 질문",
                    "1:1 문의하기"
                )
            )
            Divider(
                color = Color.Gray, // 선의 색상
                thickness = 0.5.dp,   // 선의 두께
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
            )

            Spacer(modifier = Modifier.height(32.dp))

            Text(
                text = "We Contact",
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier
                    .padding(horizontal = 16.dp),
                color = Color.Black
            )

            Divider(
                color = Color.Gray, // 선의 색상
                thickness = 0.5.dp,   // 선의 두께
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
            )

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 9.dp, horizontal = 16.dp)
            ) {
                IconButton(
                    onClick = {},
                    modifier = Modifier
                        .size(45.dp)
                        .weight(1f)
                ) {
                    Image(
                        painter = painterResource(R.drawable.ic_instagram),
                        contentDescription = null,
                        modifier = Modifier.fillMaxSize()
                    )
                }
                IconButton(
                    onClick = {},
                    modifier = Modifier
                        .size(45.dp)
                        .weight(1f)
                ) {
                    Image(
                        painter = painterResource(R.drawable.ic_discord),
                        contentDescription = null,
                        modifier = Modifier.fillMaxSize()
                    )
                }
                IconButton(
                    onClick = {},
                    modifier = Modifier
                        .size(45.dp)
                        .weight(1f)
                ) {
                    Image(
                        painter = painterResource(R.drawable.ic_facebook),
                        contentDescription = null,
                        modifier = Modifier.fillMaxSize()
                    )
                }
            }
        }
    }
}

@Composable
fun ActionButtons(buttons: List<String>, onButtonClick: (String) -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        buttons.forEach { buttonText ->
            Button(
                onClick = { onButtonClick(buttonText) },
                colors = ButtonDefaults.buttonColors(containerColor = colorResource(R.color.titleColor)),
                shape = RoundedCornerShape(10),
                modifier = Modifier
                    .weight(1f)
                    .height(60.dp),
                contentPadding = PaddingValues(0.dp)
            ) {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = buttonText,
                        color = Color.White,
                        fontSize = 14.sp,
                        maxLines = 1
                    )
                }
            }
        }
    }
}

@Composable
fun RecentRecipeSection(recipes: List<Recipe>) {
    val savedRecipes = remember { mutableStateMapOf<String, Boolean>() }

    Column(
        modifier = Modifier
            .fillMaxWidth()
    ) {
        Spacer(modifier = Modifier.height(16.dp))

        Text(
            "최근에 본 레시피",
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(start = 16.dp),
            color = Color.Black
        )
    }


    Divider(
        color = Color.Gray, // 선의 색상
        thickness = 0.5.dp,   // 선의 두께
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)// 전체 너비를 채움
    )

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
            .height(350.dp)
            .verticalScroll(rememberScrollState())
            .background(Color(0xFFFFFFFF))
    ) {
        recipes.forEach { recipe ->
            val isSaved = savedRecipes[recipe.title] ?: false

            RecentRecipeCard(
                title = recipe.title,
                views = recipe.views,
                viewCount = recipe.viewCount,
                descript = recipe.descript,
                imageRes = recipe.imageRes,
                isSaved = isSaved,
                onSaveClick = { newState -> savedRecipes[recipe.title] = newState }
            )
            Spacer(modifier = Modifier.height(8.dp))
        }
    }

    Divider(
        color = Color.Gray, // 선의 색상
        thickness = 0.5.dp,   // 선의 두께
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)// 전체 너비를 채움
    )
}

@Composable
fun NoticeList(noticeItems: List<String>) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
            .background(Color.White)
            .clip(RoundedCornerShape(8.dp))
    ) {

        noticeItems.forEach { item ->
            ClickableMenuItem(text = item)


        }
    }
}

@Composable
fun RecentRecipeCard(
    title: String,
    descript: String,
    views: String,
    viewCount: String,
    imageRes: Int,
    isSaved: Boolean,
    onSaveClick: (Boolean) -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 5.dp, horizontal = 9.dp)
            .height(110.dp)
            .border(
                BorderStroke(
                    width = 2.dp,
                    color = colorResource(R.color.titleColor)
                ),
                shape = RoundedCornerShape(15.dp)
            ),
        shape = RoundedCornerShape(15.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxSize()
        ) {
            Image(
                painter = painterResource(id = imageRes),
                contentDescription = null,
                modifier = Modifier
                    .fillMaxSize()
                    .clip(RoundedCornerShape(15.dp))
                    .weight(0.4f),
                contentScale = ContentScale.Crop
            )

            Spacer(modifier = Modifier.weight(0.02f))

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .weight(0.58f)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(0.4f)
                ) {
                    Box(modifier = Modifier.fillMaxWidth()) {
                        Text(
                            modifier = Modifier.align(Alignment.BottomStart),
                            text = title,
                            color = Color.Black,
                            fontSize = 20.sp,
                            fontWeight = FontWeight.SemiBold
                        )

                        IconButton(
                            modifier = Modifier.align(Alignment.TopEnd),
                            onClick = { onSaveClick(!isSaved) }
                        ) {
                            Icon(
                                painter = painterResource(
                                    if (isSaved) R.drawable.ic_aftersavebutton
                                    else R.drawable.ic_beforesavebutton
                                ),
                                contentDescription = null,
                                tint = Color.Unspecified,
                                modifier = Modifier.size(25.dp)
                            )
                        }
                    }
                }
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(0.55f)
                ) {
                    Text(
                        text = descript,
                        color = Color.Black,
                        maxLines = 2,
                        overflow = TextOverflow.Ellipsis,
                        modifier = Modifier.padding(end = 8.dp)
                    )
                }

                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(0.25f)
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = 5.dp),
                        horizontalArrangement = Arrangement.Start  // 시작 부분부터 배치
                    ) {
                        Row(
                            modifier = Modifier.weight(1f),
                            horizontalArrangement = Arrangement.Start,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(
                                painter = painterResource(id = R.drawable.ic_cookingtime),
                                contentDescription = null,
                                tint = Color.Black,
                                modifier = Modifier.size(20.dp)  // 아이콘 크기 약간 줄임
                            )
                            Spacer(modifier = Modifier.width(5.dp))
                            Text(
                                text = "30분 이내",
                                color = Color.Black,
                                fontSize = 12.sp  // 글자 크기 조정
                            )


                        }
                        Row(
                            modifier = Modifier.weight(1f),
                            horizontalArrangement = Arrangement.Start,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(
                                painter = painterResource(id = R.drawable.ic_difficultlevel),
                                contentDescription = null,
                                tint = Color.Black,
                                modifier = Modifier.size(20.dp)
                            )

                            Text(
                                text = viewCount,
                                color = Color.Black,
                                fontSize = 12.sp
                            )

                        }
                    }
                }
            }
        }
    }
}

@Composable
fun ClickableMenuItem(text: String) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { /* 클릭 이벤트 처리 */ }
            .padding(vertical = 12.dp, horizontal = 16.dp)
    ) {
        Text(
            text = text,
            fontSize = 14.sp,
            modifier = Modifier.align(Alignment.CenterStart),
            color = Color.Black
        )
    }
}

