package com.sdc.aicookmate

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldColors
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.boundsInWindow
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Popup
import androidx.navigation.NavController


var ingreidentsSelected =
    mutableStateListOf<String>()
val ingredients = listOf(
    "3분 짜장",
    "가래떡",
    "가지",
    "간 돼지고기",
    "간 마늘",
    "간돼지고기",
    "간마늘",
    "간생강",
    "간장",
    "간장 막걸리",
    "간장게장",
    "간장게장 국물",
    "갈아만든 배음료",
    "갈아만든배",
    "갈은 깨",
    "갈치",
    "감자",
    "감자 작은 거",
    "감자 전분",
    "감자 중간크기",
    "감자전분",
    "건새우",
    "건크렌베리",
    "건표고",
    "건표고버섯",
    "검은깨",
    "검은콩",
    "게맛살",
    "견과류",
    "계란",
    "계란 흰자",
    "계란노른자",
    "계란물",
    "계란후라이",
    "계란흰자",
    "계피가루",
    "고구마",
    "고구마 전분",
    "고구마전분",
    "고기삶아낸 육수",
    "고다치즈",
    "고등어",
    "고로쇠 물",
    "고르곤졸라치즈",
    "고르곤치즈",
    "고사리",
    "고수",
    "고운 고춧가루",
    "고운 소금",
    "고체형 카레",
    "고추",
    "고추가루",
    "고추기름",
    "고추기름(라유)",
    "고추냉이",
    "고추냉이 잎",
    "고추장",
    "고추장 듬뿍",
    "고추장볶음",
    "고추장아찌",
    "고춧가루",
    "고춧가루 듬뿍",
    "고춧기름",
    "고형카레",
    "곤드레나물",
    "골뱅이",
    "곱창김",
    "공심채",
    "과일잼",
    "관자",
    "구운 김",
    "구운 김가루",
    "구운김",
    "국간장",
    "굴",
    "굴 소스",
    "굴소스",
    "굵은 소금",
    "굵은소금",
    "그린 롱 고추",
    "기름",
    "김",
    "김가루",
    "김밥김",
    "김밥용 김",
    "김밥용김",
    "김치",
    "김치 국물",
    "김치국물",
    "김치찌개",
    "김칫국물",
    "까나리 액젓",
    "까나리액젓",
    "까망베르치즈",
    "깐 새우",
    "깐마늘",
    "깨",
    "깨소금",
    "깻가루",
    "깻잎",
    "꽁치통조림",
    "꽃게 액젓",
    "꽃게액젓",
    "꽃빵",
    "꽃소금",
    "꽈리고추",
    "꿀",
    "낙지",
    "남은치킨",
    "냉동 모둠채소",
    "냉동 물만두",
    "냉동 바지락살",
    "냉동 브로콜리",
    "냉동만두",
    "냉동모둠해물",
    "냉동새우",
    "냉동해물",
    "노각",
    "노란 파프리카",
    "노랑파프리카",
    "노른자",
    "노추(간장)",
    "녹말가루",
    "녹인 버터",
    "느타리버섯",
    "다시마",
    "다시팩",
    "다양한버섯",
    "다진 대파",
    "다진 마늘",
    "다진 생강",
    "다진 청양고추",
    "다진고기",
    "다진대파",
    "다진돼지고기",
    "다진마늘",
    "다진생강",
    "다진소고기",
    "다진양파",
    "다진파",
    "단무지",
    "단촛물",
    "달걀",
    "달걀 흰자",
    "달걀물",
    "달걀프라이",
    "달걀흰자",
    "달래",
    "닭",
    "닭 10호",
    "닭 볶음탕용",
    "닭가슴살",
    "닭고기 살",
    "닭고기(가슴살)",
    "닭볶음 닭",
    "닭볶음탕용 닭",
    "당근",
    "당면",
    "당면사리",
    "대추",
    "대파",
    "대파 하얀부분",
    "대파 흰부분",
    "대파흰대",
    "대파흰부분",
    "대패삼겹살",
    "데미그라스 소스",
    "도토리묵",
    "돈가스",
    "돼지 등심",
    "돼지 안심",
    "돼지갈비",
    "돼지고기",
    "돼지고기 (앞다리살)",
    "돼지고기 다짐육",
    "돼지고기 등심",
    "돼지고기 목살",
    "돼지고기 사태",
    "돼지고기 삼겹살",
    "돼지고기 안심",
    "돼지고기 앞다리살",
    "돼지고기 잡채용",
    "돼지고기다짐육",
    "돼지고기목살",
    "돼지고기안심",
    "돼지고기앞다리살",
    "돼지등갈비",
    "돼지등뼈",
    "돼지비계 기름",
    "돼지앞다리살",
    "돼지육수",
    "된장",
    "된장 듬뿍",
    "두반장",
    "두부",
    "두부 1모반",
    "둥근호박",
    "들기름",
    "들기름(올리브유)",
    "들깨가루",
    "들깻가루",
    "등갈비",
    "디포리",
    "디포리육수",
    "딸기",
    "땅콩버터",
    "떡",
    "떡국떡",
    "떡볶이",
    "떡볶이 떡",
    "떡볶이떡",
    "또띠아",
    "뜨거운 물",
    "뜨거운물",
    "라면",
    "라면사리",
    "라면스프",
    "라이스페이퍼",
    "레드와인",
    "레몬",
    "레몬즙",
    "로메인상추",
    "로즈마리",
    "로즈마리 잎",
    "마늘",
    "마늘쫑",
    "마른고추",
    "마른전분",
    "마요네즈",
    "마요네즈 듬뿍",
    "만개한알",
    "만두",
    "말린 사과",
    "맛간장",
    "맛살",
    "맛소금",
    "맛술",
    "매실액",
    "매실즙",
    "매실청",
    "매운청고추",
    "머스타드",
    "머스타드소스",
    "머스터드",
    "머스터드 소스",
    "머스터드소스",
    "메추리알",
    "면",
    "면수",
    "멸치",
    "멸치 다시다팩",
    "멸치 액젓",
    "멸치다시마 육수",
    "멸치다시마육수",
    "멸치다시팩",
    "멸치액젓",
    "멸치육수",
    "명란",
    "명란젓",
    "모닝빵",
    "모듬해물",
    "모시조개",
    "모자렐라 치즈",
    "모짜렐라치즈",
    "모차렐라치즈",
    "목살",
    "목이버섯",
    "무",
    "무 중 사이즈",
    "무 중간크기",
    "무 채",
    "무말랭이",
    "무염버터",
    "무채",
    "무청",
    "묵은지",
    "묵은지(익은김치)",
    "물",
    "물녹말",
    "물만두",
    "물미역",
    "물엿",
    "믈",
    "미나리",
    "미나리효소",
    "미림(맛술)",
    "미역",
    "미역줄기",
    "밀가루",
    "밀가루(중력분)",
    "밀가루중력분",
    "밀떡",
    "바나나",
    "바지락",
    "바질",
    "반건조코다리",
    "반숙 달걀",
    "발사믹",
    "밥",
    "밥새우",
    "방울토마토",
    "배",
    "배음료",
    "배주스",
    "배즙",
    "배추",
    "배추김치",
    "배추잎",
    "백 명란젓",
    "백미",
    "백합조개",
    "버섯",
    "버터",
    "베이컨",
    "베이크드 빈스",
    "베트남고추",
    "병아리콩",
    "병아리콩 통조림",
    "보노스프",
    "보리쌈장",
    "볶은 소고기",
    "볶은 춘장",
    "부추",
    "부추뿌리",
    "부침가루",
    "북어채",
    "불고기",
    "붉은 고추",
    "붉은고추",
    "브로콜리",
    "블루베리",
    "블루베리잼",
    "비엔나 소시지",
    "비엔나소세지",
    "비엔나소시지",
    "빨간 파프리카",
    "빨강파프리카",
    "빵가루",
    "사각어묵",
    "사각치즈",
    "사골곰탕",
    "사과",
    "사과잼",
    "산초",
    "삶은 감자",
    "삶은 계란",
    "삶은 시래기",
    "삶은 완두콩",
    "삶은계란",
    "삶은시래기",
    "삼겹살",
    "상추",
    "새송이버섯",
    "새우",
    "새우만두",
    "새우살",
    "새우젓",
    "새우젓 건더기",
    "샐러드 채소",
    "샐러드채소",
    "샐러리",
    "생강",
    "생강 슬라이스",
    "생강 작은",
    "생강 작은조각",
    "생강가루",
    "생강즙",
    "생강청",
    "생물고등어",
    "생밤",
    "생새우",
    "생수",
    "생연어",
    "생크림",
    "서리태",
    "설탕",
    "셀러리",
    "소갈비",
    "소고기",
    "소고기 불고기용",
    "소고기다시다",
    "소고기다짐육",
    "소고기등심",
    "소고기불고기",
    "소고기안심",
    "소고기홍두깨살",
    "소금",
    "소면",
    "소면 (세면)",
    "소불고기",
    "소불고기감",
    "소세지",
    "소시지",
    "소주",
    "소흥주(술)",
    "송송 썬 쪽파",
    "송송썬 대파",
    "쇠고기",
    "쇠불고기",
    "숙주",
    "순대",
    "순두부",
    "슈가파우더",
    "스테이크소스",
    "스파게티 면",
    "스파게티면",
    "스팸",
    "스프",
    "슬라이스체다치즈",
    "슬라이스치즈",
    "시금치",
    "시나몬가루",
    "시나몬스틱",
    "식빵",
    "식용류",
    "식용유",
    "식용유 1키로 실제로 소모는",
    "식초",
    "신 김치",
    "신김치",
    "쌀",
    "쌀가루",
    "쌀국수",
    "쌀뜨물",
    "쌈 단무지",
    "쌈장",
    "쌈장 듬뿍",
    "썬파",
    "썰어놓은 파",
    "아보카도",
    "아삭이고추",
    "아스파라거스",
    "안초비",
    "알감자",
    "알배기 배추",
    "알배기배추",
    "알배추",
    "알배추 잎",
    "알새우",
    "애호박",
    "액상스프",
    "액젓",
    "얇게 썬 연어",
    "얇게 저민 대파",
    "얇게썬부챗살",
    "양배추",
    "양상추",
    "양상추 푸른잎",
    "양송이버섯",
    "양조 간장",
    "양조간장",
    "양조식초",
    "양파",
    "양파 작은거",
    "양파 작은것",
    "양파 작은크기",
    "양파 중",
    "양파 큰것",
    "양파껍질",
    "양파즙",
    "어린잎채소",
    "어묵",
    "얼음물",
    "에멘탈치즈",
    "연겨자",
    "연두부",
    "연어",
    "오뎅",
    "오디",
    "오이",
    "오이고추",
    "오이채",
    "오일",
    "오징어",
    "오징어 먹물",
    "오징어다리",
    "옥수수 전분",
    "옥수수 통조림",
    "옥수수(캔)",
    "옥수수전분",
    "온수",
    "올리고당",
    "올리브",
    "올리브오일",
    "올리브유",
    "올리브유(퓨어)",
    "완두콩",
    "요거트플레인",
    "우거지",
    "우동사리",
    "우삼겹",
    "우엉",
    "우유",
    "월계수잎",
    "유자",
    "유채",
    "육수",
    "으깬 깨",
    "일반 고춧가루",
    "자른 미역",
    "작은 감자",
    "작은 무",
    "잔멸치",
    "잘 익은 김치",
    "잡곡밥",
    "잡채용 돼지고기",
    "잡채용 소고기",
    "잣",
    "장어",
    "저염 명란",
    "적양배추",
    "적양파",
    "전복",
    "전분",
    "전분가루",
    "전분가루 넘치게",
    "전분물",
    "정종",
    "조개",
    "조갯살",
    "조미 김",
    "조미김",
    "조미볶음",
    "조청",
    "죽순",
    "중 크기 대파",
    "중력분",
    "중새우",
    "중화면",
    "쥐똥고추",
    "즉석밥",
    "진간장",
    "진미채",
    "짜장가루",
    "짜장라면",
    "짜장라면 스프",
    "짜파게티",
    "짜파게티 면",
    "짜파게티 스프",
    "쪽파",
    "쫄면",
    "쫄면 사리",
    "쫄면사리",
    "쯔유",
    "쯔유간장",
    "차돌박이",
    "참기름",
    "참깨",
    "참나물연근샐러드",
    "참치",
    "참치 작은캔",
    "참치 캔",
    "참치액",
    "참치액젓",
    "찹쌀",
    "찹쌀가루",
    "채끝등심",
    "채끝살",
    "채소 믹스",
    "채수",
    "채썬 생 밤",
    "채썬 양파",
    "천연조미료",
    "천연조미료해물육수",
    "천일염",
    "청 고추",
    "청경채",
    "청경채 묶음",
    "청고추",
    "청양 고춧가루",
    "청양고추",
    "청양고춧가루",
    "청양초",
    "청주",
    "청피망",
    "체다슬라이스치즈",
    "체다치즈",
    "체리",
    "초록 파프리카",
    "초장",
    "초콜릿",
    "춘장",
    "치아바타",
    "치즈",
    "치킨",
    "치킨 스톡(액체)",
    "치킨스탁",
    "치킨스톡",
    "칠리소스",
    "카놀라유",
    "카펠리니 면",
    "카펠리니면",
    "칵테일 새우",
    "칵테일새우",
    "칼국수 면",
    "커피우유",
    "컵라면",
    "케일",
    "케찹",
    "케첩",
    "케쳡",
    "코인육수",
    "콜라",
    "콩가루분말",
    "콩기름",
    "콩나물",
    "크래미",
    "타임",
    "탄산수",
    "토마토",
    "토마토 페스토",
    "토마토소스",
    "토마토케첩",
    "토마토페이스트",
    "통깨",
    "통마늘",
    "통삼겹살",
    "통후추",
    "튀김가루",
    "트러플 오일",
    "파",
    "파 흰부분",
    "파마산",
    "파마산치즈",
    "파마산치즈가루",
    "파뿌리",
    "파스타면",
    "파슬리",
    "파슬리 가루",
    "파슬리가루",
    "파슬리가루 토핑",
    "파인애플",
    "파채",
    "파프리카",
    "파프리카(칼라별로반씩)",
    "팔각(향신료)",
    "팥 아이스크림",
    "팽이버섯",
    "페스토",
    "펜네",
    "편 생강",
    "포도씨유",
    "포도주스",
    "포도쥬스",
    "표고버섯",
    "표고버섯불린 물",
    "풋고추",
    "피망",
    "하몽",
    "핫칠리소스/케찹/테리야끼소소/머스터드소스",
    "해감한 바지락",
    "해산물",
    "햄",
    "햇반",
    "현미",
    "현미유",
    "호박",
    "홀 토마토",
    "홀토마토캔",
    "홍게 액젓",
    "홍고추",
    "홍초",
    "홍피망",
    "화이트발사믹식초",
    "화이트와인",
    "황두장",
    "황설탕",
    "황태채",
    "후추",
    "후추가루",
    "후춧가루",
    "흑설탕",
    "흰 대파",
    "흰쌀밥"
)

//@Preview(showBackground = true)
//@Composable
//fun ShowRefrigeratorScreen() {
//    Refrigerator()
//}

@Composable
fun Refrigerator(navController: NavController) {
    val selectedIngredients = remember { mutableStateListOf<String>() }
    val markedIngredients = remember { mutableStateListOf<String>() }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color.White)
    ) {
        // 상단 여백
        Column(
            modifier = Modifier
                .background(color = colorResource(R.color.titleColor))
                .padding(vertical = 5.dp)
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(70.dp)
                    .background(color = colorResource(R.color.titleColor))
            ) {
                Image(
                    painter = painterResource(id = R.drawable.ic_arrowback),
                    contentDescription = "Back button",
                    modifier = Modifier
                        .padding(start = 9.dp)
                        .align(Alignment.CenterStart)
                        .clickable {
                            navController.navigate("MainScreen")
                        },
                    colorFilter = ColorFilter.tint(Color.White)
                )
                Text(
                    text = "가지고 있는 재료를 보여드릴게요!",
                    fontWeight = FontWeight.Bold,
                    color = Color.White,
                    fontSize = 20.sp,
                    modifier = Modifier
                        .align(Alignment.Center)
//                    .padding(bottom = 5.dp)
                )
            }
            EnhancedSearchDropdown(
                items = ingredients,
                selectedItems = ingreidentsSelected, // 여기서 매개변수 이름을 맞춤
                placeholderText = "재료를 검색하세요"
            ) { selectedItem ->
                println("선택된 항목: $selectedItem")
            }

        }
        // 검색 필드
        Spacer(modifier = Modifier.height(10.dp))

        Column(
            modifier = Modifier
                .padding(horizontal = 20.dp)
                .wrapContentHeight()
        ) {
            Box() {
                Text(
                    "바로 추가",
                    color = Color.White,
                    fontSize = 15.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier
                        .background(color = Color(0xff76D290))
                        .padding(horizontal = 5.dp)
                )
            }
            // 즐겨찾기 박스
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(45.dp)
                    .border(3.dp, Color(0xffF6C8AA), RectangleShape)
                    .background(Color(0xFFFFEED6))
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .horizontalScroll(rememberScrollState())
                        .fillMaxHeight()
                        .padding(end = 10.dp)
                ) {
                    PostIt("감자")
                    PostIt("돼지고기")
                }
            }
        }
        Spacer(modifier = Modifier.height(10.dp))

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .weight(4f) // 남은 공간을 냉장고 이미지가 차지
                .padding(horizontal = 20.dp)
        ) {
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth()
            ) {
                Row(
                    verticalAlignment = Alignment.Bottom,
                    modifier = Modifier.wrapContentHeight()
                ) {
                    Text(
                        "My 냉장고",
                        color = Color.White,
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier
                            .background(
                                color = Color(0xff76D290),
                                RoundedCornerShape(topStart = 10.dp, topEnd = 10.dp)
                            )
                            .padding(horizontal = 20.dp, vertical = 10.dp)
                    )
                    Text(
                        "소스",
                        color = Color.White,
                        fontSize = 15.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier
                            .background(
                                color = Color(0xffD9958F),
                                RoundedCornerShape(topEnd = 10.dp)
                            )
                            .padding(horizontal = 10.dp, vertical = 5.dp)
                    )
                }
                Text(
                    "재료 추가",
                    color = Color.White,
                    fontSize = 15.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier
                        .background(color = Color(0xffE85D3A), RoundedCornerShape(10.dp))
                        .padding(horizontal = 10.dp, vertical = 10.dp)
                        .clickable { navController.navigate("ShowHowAddIngredients") }
                )
            }
            Column(
                modifier = Modifier
                    .border(10.dp, Color(0xffECECEC), RoundedCornerShape(5.dp))
                    .fillMaxSize()
            ) {
                Column(
                    modifier = Modifier
                        .verticalScroll(rememberScrollState())
                        .padding(20.dp)
                ) {
                    val chunkedIngredients = ingreidentsSelected.chunked(3)
                    for (ingredient in chunkedIngredients) {
                        Row(modifier = Modifier.padding(2.dp)) {
                            for (ingredients in ingredient) {
                                PostIt(ingredients)
                            }
                        }
                    }
                }
            }
        }

        Button(
            onClick = { navController.navigate("ShowHowRecommend") },
            shape = RoundedCornerShape(10.dp),
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xffFF9E66)),
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 10.dp, horizontal = 20.dp)
                .weight(1f)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center,
                modifier = Modifier
                    .fillMaxSize()
            ) {
                Image(
                    painter = painterResource(R.drawable.recommend_recipe_btn),
                    contentDescription = "레시피 추천받기",
                    contentScale = ContentScale.FillHeight,
                    modifier = Modifier
                        .fillMaxHeight()
                        .wrapContentWidth()
                )
                Text(
                    "레시피 추천받기",
                    fontSize = 30.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xff582E16)
                )
            }
        }
    }
}


@Composable
fun PostIt(text: String) {
    Box(modifier = Modifier.padding(start = 10.dp)) {
        Row(
            modifier = Modifier
                .wrapContentSize()
                .border(1.dp, Color(0xffF6C8AA), RoundedCornerShape(10.dp))
                .background(Color(0xffFFF3E7), shape = RoundedCornerShape(10.dp))
                .padding(horizontal = 10.dp, vertical = 5.dp),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(text, fontSize = 15.sp)
            Spacer(modifier = Modifier.width(10.dp))
            Image(
                painter = painterResource(R.drawable.postit_close_btn),
                contentDescription = "닫기",
                modifier = Modifier
                    .size(12.dp)
                    .clickable { ingreidentsSelected.removeIf { it == text } },
            )
        }
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EnhancedSearchDropdown(
    items: List<String>,
    selectedItems: List<String>, // 이미 선택된 항목 제외
    placeholderText: String,
    onItemSelected: (String) -> Unit
) {
    var inputText by remember { mutableStateOf("") }
    var expanded by remember { mutableStateOf(false) }
    val context = LocalContext.current
    val filteredItems = remember(inputText, items, selectedItems) {
        items.filter { it.contains(inputText, ignoreCase = true) && it !in selectedItems }
            .take(50) // 최대 50개만 표시
    }
    val keyboardController = LocalSoftwareKeyboardController.current // 키보드 제어

    ExposedDropdownMenuBox(
        expanded = expanded,
        onExpandedChange = { expanded = !expanded }
    ) {
        Row (modifier = Modifier.padding(5.dp),
            verticalAlignment = Alignment.CenterVertically){
            OutlinedTextField(
                value = inputText,
                onValueChange = { newValue ->
                    inputText = newValue
                    expanded =
                        newValue.isNotEmpty() && filteredItems.isNotEmpty() // 삭제 시에도 자연스럽게 동작
                },
                placeholder = { Text(placeholderText) },
                leadingIcon = { Icon(Icons.Default.Search, contentDescription = "검색 아이콘") },
                singleLine = true,
                modifier = Modifier
                    .menuAnchor()
                    .weight(6f)
                    .fillMaxWidth(),
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    containerColor = Color.White,
                    focusedBorderColor = Color.Gray,
                    unfocusedBorderColor = Color.LightGray
                ),
                keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Done),
                keyboardActions = KeyboardActions(
                    onDone = {
                        keyboardController?.hide() // 키보드 숨기기
                        expanded = false
                    }
                )
            )



            Text(
                "추가",
                color = Color.White,
                fontSize = 15.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier
                    .background(color = Color(0xffE85D3A), RoundedCornerShape(10.dp))
                    .padding(horizontal = 10.dp, vertical = 10.dp)
                    .wrapContentWidth()
                    .weight(1f)
                    .clickable {
                        if (inputText in ingredients) {
                            if (!ingreidentsSelected.contains(inputText)) {
                                ingreidentsSelected.add(inputText)
                                inputText = ""
                                Toast
                                    .makeText(context, "추가되었어요!", Toast.LENGTH_SHORT)
                                    .show()
                            } else {
                                Toast
                                    .makeText(context, "이미 들어있습니다!", Toast.LENGTH_SHORT)
                                    .show()
                            }
                        } else {
                            Toast
                                .makeText(context, "입력한 재료가 없습니다.", Toast.LENGTH_SHORT)
                                .show()
                        }
                    }
            )
        }

        ExposedDropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false },
            modifier = Modifier
                .fillMaxWidth()
                .heightIn(max = 250.dp) // 드롭다운 높이를 250dp로 제한
        ) {
            if (filteredItems.isEmpty()) {
                DropdownMenuItem(
                    text = { Text("검색 결과가 없습니다", color = Color.Gray) },
                    onClick = { expanded = false }
                )
            } else {
                filteredItems.forEach { item ->
                    DropdownMenuItem(
                        text = { Text(item) },
                        onClick = {
                            inputText = item
                            onItemSelected(item)
                            expanded = false
                            keyboardController?.hide() // 선택 후 키보드 숨기기
                        }
                    )
                }
            }
        }
    }
}
