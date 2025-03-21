package com.sdc.aicookmate


import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.sdc.aicookmate.ui.theme.AiCookMateTheme
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.style.TextAlign

@Composable
fun MainScreen(navController: NavController) {
    val viewModel: RecipeViewModel = viewModel()

    Scaffold(
        bottomBar = { BottomBar(navController) }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .padding(paddingValues)
                .verticalScroll(rememberScrollState())
                .background(Color.White)
        ) {
            Column( //검색창 컬럼
                modifier = Modifier
                    .height(90.dp)
                    .fillMaxWidth()
                    .background(color = colorResource(R.color.titleColor))
            ) { //검색창 컬럼
                FirebaseDropdown(
                    viewModel = viewModel,
                    placeholderText = "레시피를 검색하세요"
                ) { selectedRecipe ->
                    println("선택된 레시피: ${selectedRecipe.title}")
                    navController.navigate("recipeDetail/${selectedRecipe.title}")
                }
            } //검색창컬럼

            Box( //레시피리스트 박스
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { navController.navigate("refigeratorScreen") } // 클릭 시 이동
            ) { //레시피리스트 박스
                Image(
                    painter = painterResource(id = R.drawable.recipelist),
                    contentDescription = "홈 레시피 리스트",
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp)
                        .height(350.dp)
                        .align(Alignment.Center),
                    contentScale = ContentScale.FillBounds
                )
            } //레시피리스트 박스

            Column( //카테고리 컬럼
                modifier = Modifier
                    .fillMaxWidth()
            ) {//카테고리 컬럼
                Text(//텍스트
                    text = "레시피 종류",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = Color.Black,
                    modifier = Modifier
                        .padding(horizontal = 16.dp, vertical = 9.dp)
//                        .background(color = colorResource(R.color.titleColor))
//                        .width(130.dp)
//                        .height(30.dp),
//                        textAlign = TextAlign.Center

                )//텍스트
                CategorySelector(navController)
            }//카테고리 컬럼

            Spacer(modifier = Modifier.height(20.dp))

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 9.dp, horizontal = 16.dp)
            ) {
                Text(
                    text = "실시간 인기 레시피",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = Color.Black,
                    modifier = Modifier
                        .align(Alignment.TopStart)
                )

                Text(
                    "더보기>",
                    fontSize = 14.sp,
                    color = Color.Gray,
                    modifier = Modifier
                        .align(Alignment.TopEnd)
                )

            }

            BestListCard()


        }//최상위 컬럼
    }
}

@Composable
fun CategorySelector(navController: NavController) {
    var selectedCategory by remember { mutableStateOf("한식") }
    Column {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .horizontalScroll(rememberScrollState())
                .padding(horizontal = 16.dp),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {

            CategoryItem(
                iconRes = R.drawable.ic_zzim,
                label = "찜/조림",
                isSelected = selectedCategory == "찜/조림",
                onClick = {
                    selectedCategory = "찜/조림"
                    navController.navigate("zzimScreen") // 찜/조림 화면으로 이동
                }
            )
            CategoryItem(
                iconRes = R.drawable.ic_gook,
                label = "국/탕/찌개",
                isSelected = selectedCategory == "국/탕/찌개",
                onClick = {
                    selectedCategory = "국/탕/찌개"
                    navController.navigate("gookScreen") // 국/탕/찌개 화면으로 이동
                }
            )
            CategoryItem(
                iconRes = R.drawable.ic_bokuem,
                label = "볶음/구이",
                isSelected = selectedCategory == "볶음/구이",
                onClick = {
                    selectedCategory = "볶음/구이"
                    navController.navigate("bokuemScreen") // 볶음/구이 화면으로 이동
                }
            )
            CategoryItem(
                iconRes = R.drawable.ic_rice,
                label = "밥/죽",
                isSelected = selectedCategory == "밥/죽",
                onClick = {
                    selectedCategory = "밥/죽"
                    navController.navigate("riceScreen") // 밥/죽 화면으로 이동
                }
            )
            CategoryItem(
                iconRes = R.drawable.ic_noodle,
                label = "면/만두",
                isSelected = selectedCategory == "면/만두",
                onClick = {
                    selectedCategory = "면/만두"
                    navController.navigate("noodleScreen") // 면/만두 화면으로 이동
                }
            )
            CategoryItem(
                iconRes = R.drawable.ic_quick,
                label = "간편요리",
                isSelected = selectedCategory == "간편요리",
                onClick = {
                    selectedCategory = "간편요리"
                    navController.navigate("quickScreen") // 간편요리 화면으로 이동
                }
            )
            CategoryItem(
                iconRes = R.drawable.ic_chicken,
                label = "야식",
                isSelected = selectedCategory == "야식",
                onClick = {
                    selectedCategory = "야식"
                    navController.navigate("chickenScreen") // 야식 화면으로 이동
                }
            )
            CategoryItem(
                iconRes = R.drawable.ic_diet,
                label = "다이어트",
                isSelected = selectedCategory == "다이어트",
                onClick = {
                    selectedCategory = "다이어트"
                    navController.navigate("dietScreen") // 다이어트 화면으로 이동
                }
            )
            CategoryItem(
                iconRes = R.drawable.ic_influencer,
                label = "인플루언서",
                isSelected = selectedCategory == "인플루언서",
                onClick = {
                    selectedCategory = "인플루언서"
                    navController.navigate("influencerScreen") // 인플루언서 화면으로 이동
                }
            )

        }
    }
}

@Composable
fun CategoryItem(
    iconRes: Int,
    label: String,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .clickable { onClick() }
            .background(
                color = if (isSelected) Color(0xFFE3F2FD) else Color.White,
                shape = RoundedCornerShape(12.dp)
            )
            .border(
                width = if (isSelected) 2.dp else 0.dp,
                color = if (isSelected) Color.Blue else Color.Black,
                shape = RoundedCornerShape(12.dp)
            )
            .size(80.dp)
            .padding(8.dp)
    ) {
        Image(
            painter = painterResource(id = iconRes),
            contentDescription = label,
            modifier = Modifier.size(40.dp)
        )

        Spacer(modifier = Modifier.height(4.dp))

        Text(
            text = label,
            fontSize = 12.sp,
            fontWeight = FontWeight.Bold,
            color = Color.Black
        )
    }
}


@Composable
fun BestListCard() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .horizontalScroll(rememberScrollState())
            .padding(horizontal = 16.dp)
            .height(210.dp),
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        BestCard(imageRes = R.drawable.recipe1, description = "5분 뚝딱 콩나물 불고기 간단 레시피")
        BestCard(imageRes = R.drawable.recipe2, description = "아침에 간단하게 계란국")
        BestCard(imageRes = R.drawable.recipe3, description = "백종원 떡볶이 황금 레시피")
        BestCard(imageRes = R.drawable.recipe4, description = "매콤한 어묵볶음 레시피")
    }
}


@Composable
fun BestCard(imageRes: Int, description: String) {
    Column(
        modifier = Modifier
            .width(190.dp)
            .height(190.dp)

    ) {
        Image(
            painter = painterResource(id = imageRes),
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .fillMaxWidth()
                .height(140.dp)
                .clip(RoundedCornerShape(13.dp))
        )
        Text(
            text = description,
            fontSize = 13.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(2.dp),
            color = Color.Black
        )
    }
}

@Composable
fun BottomBar(navController: NavController) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .padding(5.dp)
            .background(Color.White)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Spacer(modifier = Modifier.weight(1f))
            Image(
                painter = painterResource(R.drawable.home_btn),
                contentDescription = "메인 홈 아이콘",
                modifier = Modifier
                    .size(40.dp)
                    .weight(1f)
                    .clickable { navController.navigate("main") }
            )
            Spacer(modifier = Modifier.weight(1f))
            Image(
                painter = painterResource(R.drawable.shopping_btn),
                contentDescription = "쇼핑 아이콘",
                modifier = Modifier
                    .size(40.dp)
                    .weight(1f)
                    .clickable { navController.navigate("shopping") }
            )
            Spacer(modifier = Modifier.weight(1f))
            Image(
                painter = painterResource(R.drawable.recipe_book),
                contentDescription = "레시피 아이콘",
                modifier = Modifier
                    .size(40.dp)
                    .weight(1f)
                    .clickable { navController.navigate("Recipe") }
            )
            Spacer(modifier = Modifier.weight(1f))
            Image(
                painter = painterResource(R.drawable.mypage_btn),
                contentDescription = "마이페이지 아이콘",
                modifier = Modifier
                    .size(40.dp)
                    .weight(1f)
                    .clickable { navController.navigate("myPage") }
            )
            Spacer(modifier = Modifier.weight(1f))
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FirebaseDropdown(
    viewModel: RecipeViewModel,
    placeholderText: String,
    onItemSelected: (RecipeData) -> Unit
) {
    var inputText by remember { mutableStateOf("") }
    var expanded by remember { mutableStateOf(false) }
    val searchResults by viewModel.searchResults.collectAsState(initial = emptyList())
    val isLoading by viewModel.isLoading.collectAsState(initial = false) // 로딩 상태 추가

    // 입력 텍스트와 검색 결과에 따라 드롭다운 상태 업데이트
    LaunchedEffect(inputText, searchResults) {
        expanded = inputText.isNotEmpty() && searchResults.isNotEmpty()
    }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        OutlinedTextField(
            value = inputText,
            onValueChange = { newValue ->
                inputText = newValue // 텍스트 필드 값 갱신
                viewModel.fetchRecipes2(newValue) // 검색 쿼리 호출
            },
            placeholder = { Text(placeholderText) },
            leadingIcon = { Icon(Icons.Default.Search, contentDescription = "검색") },
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(30.dp)),
            singleLine = true,
            colors = TextFieldDefaults.outlinedTextFieldColors(
                containerColor = Color.White, // 배경색 고정
                focusedBorderColor = Color.LightGray, // 선택 상태 테두리 색상
                unfocusedBorderColor = Color.LightGray, // 비선택 상태 테두리 색상
                focusedLabelColor = Color.Transparent, // 선택 상태 라벨 색상
                unfocusedLabelColor = Color.Transparent // 비선택 상태 라벨 색상
            )
        )

        // 로딩 상태 표시
        if (isLoading) {
            CircularProgressIndicator(
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .padding(8.dp),
                color = Color.Gray
            )
        }

        // 드롭다운 표시
        if (expanded) {
            DropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false },
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color.White)
            ) {
                searchResults.forEach { recipe ->
                    DropdownMenuItem(
                        text = { Text(text = recipe.title) },
                        onClick = {
                            inputText = recipe.title
                            expanded = false
                            onItemSelected(recipe)
                        }
                    )
                }
            }
        }
    }
}
