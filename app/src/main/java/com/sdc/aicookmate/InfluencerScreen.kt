//package com.sdc.aicookmate
//
//import androidx.compose.foundation.BorderStroke
//import androidx.compose.foundation.Image
//import androidx.compose.foundation.background
//import androidx.compose.foundation.border
//import androidx.compose.foundation.layout.Box
//import androidx.compose.foundation.layout.Column
//import androidx.compose.foundation.layout.Row
//import androidx.compose.foundation.layout.Spacer
//import androidx.compose.foundation.layout.fillMaxSize
//import androidx.compose.foundation.layout.fillMaxWidth
//import androidx.compose.foundation.layout.height
//import androidx.compose.foundation.layout.heightIn
//import androidx.compose.foundation.layout.padding
//import androidx.compose.foundation.layout.size
//import androidx.compose.foundation.layout.width
//import androidx.compose.foundation.rememberScrollState
//import androidx.compose.foundation.shape.RoundedCornerShape
//import androidx.compose.foundation.verticalScroll
//import androidx.compose.material.icons.Icons
//import androidx.compose.material.icons.filled.Search
//import androidx.compose.material3.Card
//import androidx.compose.material3.CardDefaults
//import androidx.compose.material3.DropdownMenu
//import androidx.compose.material3.DropdownMenuItem
//import androidx.compose.material3.ExperimentalMaterial3Api
//import androidx.compose.material3.Icon
//import androidx.compose.material3.IconButton
//import androidx.compose.material3.OutlinedTextField
//import androidx.compose.material3.Scaffold
//import androidx.compose.material3.Text
//import androidx.compose.material3.TextFieldDefaults
//import androidx.compose.runtime.Composable
//import androidx.compose.runtime.getValue
//import androidx.compose.runtime.mutableStateMapOf
//import androidx.compose.runtime.mutableStateOf
//import androidx.compose.runtime.remember
//import androidx.compose.runtime.setValue
//import androidx.compose.ui.Alignment
//import androidx.compose.ui.Modifier
//import androidx.compose.ui.draw.clip
//import androidx.compose.ui.focus.FocusRequester
//import androidx.compose.ui.focus.focusRequester
//import androidx.compose.ui.focus.onFocusChanged
//import androidx.compose.ui.graphics.Color
//import androidx.compose.ui.layout.ContentScale
//import androidx.compose.ui.platform.LocalFocusManager
//import androidx.compose.ui.res.colorResource
//import androidx.compose.ui.res.painterResource
//import androidx.compose.ui.text.font.FontWeight
//import androidx.compose.ui.text.style.TextOverflow
//import androidx.compose.ui.tooling.preview.Preview
//import androidx.compose.ui.unit.dp
//import androidx.compose.ui.unit.sp
//import androidx.navigation.NavController
//import com.sdc.aicookmate.model.Influencer
//import androidx.compose.ui.text.input.ImeAction
//
//
//@Composable
//fun InfluencerScreen(navController: NavController) { //
//    Scaffold(
//        bottomBar = { BottomBar(navController) }
//    ) { paddingValues ->
//        Column(
//            modifier = Modifier
//                .padding(paddingValues) //
//                .background(Color(0xFFFCF6E0))
//                .fillMaxSize()
//
//        ) {
//
//            Column(
//                modifier = Modifier
//                    .padding(vertical = 16.dp, horizontal = 8.dp)
//            ) {
//                SmoothGoogleLikeSearchDropdown(
//                    items = listOf(
//                        "백종원",
//                        "김수미",
//                        "류수영",
//                        "이영자"
//                    ),
//                    placeholderText = "인플루언서를 검색해보세요"
//                ) { selectedItem ->
//                    println("선택된 항목: $selectedItem")
//                }
//
//                Column(
//                    modifier = Modifier
//                        .fillMaxWidth()
//                ) {
//                    Text(
//                        "인플루언서",
//                        fontSize = 18.sp,
//                        fontWeight = FontWeight.Bold,
//                        modifier = Modifier
//                            .padding(start = 16.dp, top = 16.dp, bottom = 4.dp),
//                        color = Color.Black
//                    )
//                }
//
//                ScrapInfluencerSection(
//                    influencers = listOf(
//                        Influencer("백종원", "누구나 쉽게 따라할 수 있는 요리가 가장 훌륭한 요리다"),
//                        Influencer("류수영", "누구나 쉽게 따라할 수 있는 요리가 가장 훌륭한 요리다"),
//                        Influencer("이영자", "누구나 쉽게 따라할 수 있는 요리가 가장 훌륭한 요리다"),
//                        Influencer("김수미", "누구나 쉽게 따라할 수 있는 요리가 가장 훌륭한 요리다")
//                    )
//                )
//            }
//        }
//    }
//}
//
//
//@Composable
//fun ScrapInfluencerSection(influencers: List<Influencer>) {
//    val savedRecipes = remember { mutableStateMapOf<String, Boolean>() }
//
//    Column(
//        modifier = Modifier
//            .fillMaxWidth()
//            .padding(16.dp)
//            .background(Color(0xFFFFFFFF))
//    ) {
//
//        influencers.forEach { Influencer ->
//            val isSaved = savedRecipes[Influencer.influencer] ?: false
//
//            ScrapInfluencerCard(
//                name = Influencer.influencer,
//                descript = Influencer.descript,
//                isSaved = isSaved,
//                onSaveClick = { newState -> savedRecipes[Influencer.influencer] = newState }
//            )
//            Spacer(modifier = Modifier.height(8.dp))
//        }
//    }
//}
//
//
//@Composable
//fun ScrapInfluencerCard(
////    imageUrl: String,
//    name: String,
//    descript: String,
//    isSaved: Boolean,
//    onSaveClick: (Boolean) -> Unit
//) {
//    Card(
//        modifier = Modifier
//            .fillMaxWidth()
//            .padding(vertical = 5.dp, horizontal = 15.dp)
//            .height(110.dp)
//            .border(
//                BorderStroke(
//                    width = 2.dp,
//                    color = colorResource(R.color.contentcolorgreen)
//                ),
//                shape = RoundedCornerShape(15.dp)
//            ),
//        shape = RoundedCornerShape(15.dp),
////        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
//        colors = CardDefaults
//            .cardColors(containerColor = Color.White)
//    ) {
//        Row(
//            verticalAlignment = Alignment.CenterVertically,
//            modifier = Modifier.fillMaxSize()
//        ) {
//            Image(
//                painter = painterResource(id = R.drawable.recipe1),
//                contentDescription = null,
//                modifier = Modifier
//                    .fillMaxSize()
//                    .clip(RoundedCornerShape(15.dp))
//                    .weight(0.4f),
//                contentScale = ContentScale.Crop
//            )
//
//            Spacer(modifier = Modifier.weight(0.02f))
//
//            Column(
//                modifier = Modifier
//                    .fillMaxSize()
//                    .weight(0.58f)
//            ) {
//                Column(
//                    modifier = Modifier
//                        .fillMaxWidth()
//                        .weight(0.3f)
//                ) {
//                    Box(
//                        modifier = Modifier
//                            .fillMaxWidth()
//                    ) {
//                        Text(
//                            modifier = Modifier.align(Alignment.BottomStart),
//                            text = name,
//                            color = Color.Black,
//                            fontSize = 20.sp,
//                            fontWeight = FontWeight.SemiBold
//                        )
//
//                        IconButton(modifier = Modifier.align(Alignment.TopEnd),
//                            onClick = { onSaveClick(!isSaved) } // 저장 상태 토글
//                        ) {
//                            Icon(
//                                painter = painterResource(
//                                    if (isSaved) R.drawable.ic_aftersavebutton // 저장됨 아이콘
//                                    else R.drawable.ic_beforesavebutton // 저장 전 아이콘
//                                ),
//                                contentDescription = null,
//                                tint = Color.Unspecified, // 기본 색상 유지
//                                modifier = Modifier.size(25.dp)
//                            )
//                        }
//                    }
//                }
//
//                Column(
//                    modifier = Modifier
//                        .fillMaxWidth()
//                        .weight(0.7f)
//                ) {
//                    Text(
//                        text = descript,
//                        color = Color.Black,
//                        maxLines = 2,
//                        overflow = TextOverflow.Ellipsis,
//                        modifier = Modifier.padding(end = 8.dp)
//                    )
//                }
//
//
//            }
//        }
//    }
//}
//
//@OptIn(ExperimentalMaterial3Api::class)
//@Composable
//fun SmoothGoogleLikeSearchDropdown(
//    items: List<String>,
//    placeholderText: String,
//    onItemSelected: (String) -> Unit
//) {
//    var inputText by remember { mutableStateOf("") }
//    var expanded by remember { mutableStateOf(false) }
//
//    // 필터링된 리스트
//    val filteredItems = remember(inputText) {
//        items.filter { it.contains(inputText, ignoreCase = true) }
//    }
//
//    Column(modifier = Modifier.fillMaxWidth().padding(16.dp)) {
//        // 검색창
//        OutlinedTextField(
//            value = inputText,
//            onValueChange = { newValue ->
//                inputText = newValue
//                expanded = newValue.isNotEmpty() // 입력값이 있을 때만 드롭다운 표시
//            },
//            placeholder = { Text(placeholderText) },
//            leadingIcon = { Icon(Icons.Default.Search, contentDescription = "검색") },
//            modifier = Modifier
//                .fillMaxWidth()
//                .clip(RoundedCornerShape(8.dp)),
//            singleLine = true,
//            colors = TextFieldDefaults.outlinedTextFieldColors(
//                containerColor = Color.White,
//                focusedBorderColor = Color.Gray,
//                unfocusedBorderColor = Color.LightGray
//            )
//        )
//
//        // 드롭다운 메뉴
//        if (expanded) {
//            Box(
//                modifier = Modifier
//                    .fillMaxWidth()
//                    .background(Color.White)
//                    .border(1.dp, Color.LightGray, shape = RoundedCornerShape(8.dp))
//            ) {
//                if (filteredItems.isEmpty()) {
//                    Text(
//                        "검색 결과가 없습니다",
//                        modifier = Modifier
//                            .padding(16.dp)
//                            .align(Alignment.CenterStart),
//                        color = Color.Gray
//                    )
//                } else {
//                    Column(
//                        modifier = Modifier
//                            .fillMaxWidth()
//                            .heightIn(max = 200.dp) // 드롭다운 최대 높이
//                            .verticalScroll(rememberScrollState())
//                    ) {
//                        filteredItems.forEach { item ->
//                            DropdownMenuItem(
//                                text = { Text(text = item) },
//                                onClick = {
//                                    inputText = item // 선택된 항목을 검색창에 반영
//                                    expanded = false // 드롭다운 닫기
//                                    onItemSelected(item) // 선택된 항목 전달
//                                }
//                            )
//                        }
//                    }
//                }
//            }
//        }
//    }
//}