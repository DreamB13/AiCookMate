package com.sdc.aicookmate

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.motionEventSpy
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.sdc.aicookmate.ui.theme.AiCookMateTheme


@Composable
fun FoodShoppingScreen(navController: NavController) {
    Scaffold(
        bottomBar = { BottomBar(navController) }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .padding(paddingValues)
                .background(Color.White)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth(),
            ) {

                Row (verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .height(90.dp)
                        .background(color = colorResource(R.color.titleColor))){
                    OutlinedTextField(
                        value = "",
                        onValueChange = {},
                        leadingIcon = { Icon(Icons.Default.Search, "검색") },
                        placeholder = { Text("레시피 검색") },
                        modifier = Modifier
                            .weight(0.8f)
                            .background(Color.White, RoundedCornerShape(30.dp)),
                        shape = RoundedCornerShape(30.dp)
                    )

                    Image(
                        painter = painterResource(R.drawable.shoppingcart_btn),
                        contentDescription = "카트에 담기",
                        modifier = Modifier
                            .size(40.dp)
                            .clickable {/**/}
                    )
                }
                Spacer(
                    modifier = Modifier
                        .padding(12.dp)
                )
                Column(
                    modifier = Modifier.fillMaxSize()
                ) {
                    LazyColumn {
                        item { BannerSection() }
                        item { CategorySection() }
                        item { HotDealsSection() }
                        item { PlanSection() }
                    }
                }
            }
        }
    }
}

@Composable
fun BannerSection(
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.Black)

    ) {
        Column {
            Text(
                text = "봄맞이 신선식품 특가",
                style = TextStyle(
                    color = Color.White,
                    fontSize = 18.sp
                )
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = "제철 시즌도 저렴한 40% 특가",
                style = TextStyle(
                    color = Color.White.copy(alpha = 0.8f),
                    fontSize = 14.sp
                )
            )
            Spacer(modifier = Modifier.height(8.dp))

            Box(
                modifier = Modifier
                    .background(
                        Color.White.copy(alpha = 0.2f),
                        RoundedCornerShape(24.dp)
                    )
                    .padding(horizontal = 16.dp, vertical = 8.dp)
                    .clickable {/**/ }
            ) {
                Text(
                    text = "자세히 보기",
                    color = Color.White,
                    fontSize = 12.sp
                )
            }
        }

        Image(
            painter = painterResource(id = R.drawable.ic_sallad1),
            contentDescription = "Banner Image",
            modifier = Modifier
                .size(120.dp)
                .align(Alignment.CenterEnd)
                .clip(RoundedCornerShape(8.dp))
        )
    }
}

@Composable
fun CategorySection() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.White)
            .padding(16.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            CategoryItem("정육", R.drawable.ic_bokuem)
            CategoryItem("채소", R.drawable.ic_gook)
            CategoryItem("생선", R.drawable.ic_japanese)
            CategoryItem("과일", R.drawable.ic_houseparty)
            CategoryItem("김치류", R.drawable.ic_diet)
            CategoryItem("가공식품", R.drawable.ic_fried)
        }
    }
}

@Composable
fun CategoryItem(name: String, iconRes: Int) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(
            modifier = Modifier
                .size(48.dp)
                .background(Color(0xFFF5F5F5), CircleShape),
            contentAlignment = Alignment.Center
        ) {
            Image(
                painter = painterResource(id = iconRes),
                contentDescription = name,
                modifier = Modifier.size(24.dp)
            )
        }
        Spacer(modifier = Modifier.height(4.dp))
        Text(
            text = name,
            fontSize = 12.sp
        )
    }
}

@Composable
fun HotDealsSection() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.White)
            .padding(16.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier.fillMaxWidth()
                .padding(10.dp)
        ) {
            Column {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(
                        text = "♥",
                        color = Color.Red,
                        fontSize = 16.sp
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(
                        text = "기획전",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
                Text(
                    "특별한 기획전을 확인하세요",
                    fontSize = 14.sp,
                )
            }

            Text(
                text = "더보기 ›",
                color = Color.Gray,
                fontSize = 14.sp,
                modifier = Modifier.clickable {/**/ }
            )
        }

        Spacer(modifier = Modifier.height(16.dp))


        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            HotDealItem(
                modifier = Modifier.weight(1f),
                name = "닭볶음탕 밀키트",
                description = "신선한 국내산",
                price = "13,900원",
                imageRes = R.drawable.ic_recipe8
            )
            HotDealItem(
                modifier = Modifier.weight(1f),
                name = "백종원 떡볶이 밀키트",
                description = "맛있는 국내산",
                price = "8,900원",
                imageRes = R.drawable.recipe3
            )
        }

        Spacer(modifier = Modifier.height(16.dp))


        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            HotDealItem(
                modifier = Modifier.weight(1f),
                name = "동그랑땡",
                description = "신선한 국내산",
                price = "12,900원",
                imageRes = R.drawable.ic_sallad3
            )
            HotDealItem(
                modifier = Modifier.weight(1f),
                name = "두부찌개",
                description = "맛있는 국내산",
                price = "15,900원",
                imageRes = R.drawable.ic_sallad2
            )
        }
    }
}

@Composable
fun HotDealItem(
    modifier: Modifier = Modifier,
    name: String,
    description: String,
    price: String,
    imageRes: Int
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.Start
    ) {
        Image(
            painter = painterResource(id = imageRes),
            contentDescription = name,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .fillMaxSize()
                .aspectRatio(1f)
                .clip(RoundedCornerShape(12.dp))
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = name, fontSize = 14.sp,
            modifier = Modifier.fillMaxWidth()
        )
        Text(
            text = description,
            fontSize = 12.sp,
            color = Color.Gray,
            modifier = Modifier.fillMaxWidth()
        )
        Text(
            text = price,
            fontSize = 14.sp,
            modifier = Modifier.fillMaxWidth()

        )
    }
}

@Composable
fun PlanSection() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.White)
            .padding(16.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier.fillMaxWidth()
        ) {
            Column {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(
                        text = "♥",
                        color = Color.Red,
                        fontSize = 16.sp
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(
                        text = "기획전",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
                Text(
                    "특별한 기획전을 확인하세요",
                    fontSize = 14.sp,
                )
            }

            Text(
                text = "더보기 ›",
                color = Color.Gray,
                fontSize = 14.sp,
                modifier = Modifier.clickable {/**/ }
            )
        }

        Spacer(modifier = Modifier.height(16.dp))


        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            PlanDealItem(
                modifier = Modifier.weight(1f),
                name = "동그랑땡",
                description = "신선한 국내산",
                price = "12,900원",
                imageRes = R.drawable.ic_sallad2
            )
            PlanDealItem(
                modifier = Modifier.weight(1f),
                name = "두부찌개",
                description = "맛있는 국내산",
                price = "15,900원",
                imageRes = R.drawable.ic_sallad2
            )
        }

        Spacer(modifier = Modifier.height(16.dp))


        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            PlanDealItem(
                modifier = Modifier.weight(1f),
                name = "동그랑땡",
                description = "신선한 국내산",
                price = "12,900원",
                imageRes = R.drawable.ic_sallad3
            )
            PlanDealItem(
                modifier = Modifier.weight(1f),
                name = "두부찌개",
                description = "맛있는 국내산",
                price = "15,900원",
                imageRes = R.drawable.ic_sallad2
            )
        }
    }
}

@Composable
fun PlanDealItem(
    modifier: Modifier = Modifier,
    name: String,
    description: String,
    price: String,
    imageRes: Int
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.Start
    ) {
        Image(
            painter = painterResource(id = imageRes),
            contentDescription = name,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .fillMaxSize()
                .aspectRatio(1f)
                .clip(RoundedCornerShape(12.dp))
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = name, fontSize = 14.sp,
            modifier = Modifier.fillMaxWidth()
        )
        Text(
            text = description,
            fontSize = 12.sp,
            color = Color.Gray,
            modifier = Modifier.fillMaxWidth()
        )
        Text(
            text = price,
            fontSize = 14.sp,
            modifier = Modifier.fillMaxWidth()

        )
    }
}