package FoodCategoriesScreen

import android.net.Uri
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import coil3.compose.rememberAsyncImagePainter
import com.google.firebase.firestore.FieldPath
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import com.sdc.aicookmate.R


@Composable
fun bokuemScreen(navController: NavController) {

    BokuemScreen(navController)
}

@Composable
fun BokuemScreen(navController: NavController) {
    val viewModel: BokuemViewModel = androidx.lifecycle.viewmodel.compose.viewModel()
    val recipes = viewModel.recipes.collectAsState(initial = emptyList()).value
    BokuemRecipeList(recipes = recipes, navController = navController)
}

data class BokuemRecipeData(
    val title: String = "",
    val thumbnail: String = "",
    val servings: String = "",
    val time_required: String = "",
    val difficulty: String = "",
)

class BokuemViewModel : ViewModel() {
    private val firestore = FirebaseFirestore.getInstance()

    // StateFlow로 데이터를 관리
    private val _recipes = MutableStateFlow<List<BokuemRecipeData>>(emptyList())
    val recipes: StateFlow<List<BokuemRecipeData>> = _recipes

    init {
        fetchBokuemRecipes()
    }

    private fun fetchBokuemRecipes() {

        viewModelScope.launch {
            val randomOffset = (0..200).random() // 200개 중 랜덤 오프셋 설정

            firestore.collection("bokuem")
                .orderBy(FieldPath.documentId()) // 정렬 기준 설정
                .startAfter(randomOffset.toString()) // 랜덤 오프셋에서 시작
                .limit(5) // 5개 문서 가져오기
                .get()
                .addOnSuccessListener { result ->
                    val recipeList = result.documents.mapNotNull { document ->
                        document.toObject(BokuemRecipeData::class.java)
                    }
                    _recipes.value = recipeList
                }
                .addOnFailureListener {
                    _recipes.value = emptyList()
                }
        }
    }
}

@Composable
fun BokuemRecipeList(recipes: List<BokuemRecipeData>, navController: NavController) {
    val scrollState = rememberScrollState()
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 4.dp)
            .verticalScroll(scrollState)
            .background(Color.White),
    ) {
        Row {
            Image(
                painter = painterResource(id = R.drawable.ic_arrowback),
                contentDescription = "Back button",
                modifier = Modifier
                    .padding(16.dp)
                    .clickable {
                        navController.popBackStack()
                    })
        }

        recipes.forEach { item ->
            BokuemRecipeItem(item = item) { encodedTitle ->
                // NavController를 사용하여 다음 화면으로 이동
                navController.navigate("recipeDetail/$encodedTitle")
            }
            Spacer(modifier = Modifier.height(8.dp))
        }
    }
}

@Composable
fun BokuemRecipeItem(item: BokuemRecipeData, onClick: (String) -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
            .background(Color.White),

        shape = RoundedCornerShape(12.dp)
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .clickable {
                    onClick(Uri.encode(item.title))
                }
                .background(Color.White),
        ) {
            Row(
                modifier = Modifier.padding(16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Image(
                    painter = rememberAsyncImagePainter(item.thumbnail),
                    contentDescription = null,
                    contentScale = ContentScale.Fit,
                    modifier = Modifier
                        .fillMaxSize()
                        .align(Alignment.CenterVertically)
                        .weight(1f)

                )
                Spacer(modifier = Modifier.weight(0.1f))
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .weight(1f)
                ) {
                    Text(
                        text = item.title,
                        modifier = Modifier.padding(bottom = 12.dp),
                        fontWeight = FontWeight.Bold,
                        color = Color.Black
                    )
                    BokuemInfoRow(iconRes = R.drawable.ic_person, text = item.servings)
                    BokuemInfoRow(iconRes = R.drawable.ic_time, text = item.time_required)
                    BokuemInfoRow(iconRes = R.drawable.ic_star, text = item.difficulty)
                }
            }
        }
    }
}

@Composable
fun BokuemInfoRow(iconRes: Int, text: String) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.padding(vertical = 4.dp)
    ) {
        Image(
            painter = painterResource(id = iconRes),
            contentDescription = null,
            modifier = Modifier.size(16.dp)
        )
        Spacer(modifier = Modifier.width(8.dp))
        Text(text = text, fontWeight = FontWeight.Bold, color = Color.Black)
    }
}
