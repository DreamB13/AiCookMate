package com.sdc.aicookmate

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.*
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.POST

// --------- Retrofit 데이터 클래스 ---------
data class GptRequest(
    val model: String,
    val messages: List<Message>,
    val max_tokens: Int = 1000
)

data class Message(
    val role: String,   // "system", "user", "assistant"
    val content: String
)

data class GptResponse(
    val id: String,
    val choices: List<Choice>
)

data class Choice(
    val message: Message
)

// --------- Retrofit 인터페이스 ---------
interface SimpleApi {
    @POST("v1/chat/completions")
    fun sendChatCompletion(
        @Header("Authorization") authorization: String,
        @Body request: GptRequest
    ): Call<GptResponse>
}

/**
 * GptScreen
 *
 * NavController만 받던 기존 코드에서,
 * 'ingredients' 파라미터를 추가하고 기본값("")을 주어
 * NavHost 쪽 코드( `composable("GptScreen") { GptScreen(navController) }` )와의
 * 충돌(파라미터 누락 오류)을 해결.
 */
@Composable
fun GptScreen(
    navController: NavController,
    ingredients: String = ""  // 기본값 설정!
) {
    // 스크롤 상태
    val scrollState = rememberLazyListState()

    // GPT 응답 파싱 결과
    var recipeName by remember { mutableStateOf("") }
    var recipeIngredients by remember { mutableStateOf("") }
    var recipeMethod by remember { mutableStateOf("") }

    // UI 상태
    var remainingCount by remember { mutableStateOf(3) }
    var isLoading by remember { mutableStateOf(false) }

    val defaultErrorMessage = "레시피를 가져오는 중 오류가 발생했습니다."

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        Column {
            // 뒤로가기 버튼
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(90.dp)
                    .background(color = colorResource(R.color.titleColor))
            ) {
                Image(
                    painter = painterResource(id = R.drawable.ic_arrowback),
                    contentDescription = "뒤로가기",
                    modifier = Modifier
                        .size(40.dp)
                        .clickable { navController.navigateUp() }
                )

                Text(
                    text = "이런 메뉴 어떠세요?",
                    style = TextStyle(
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold
                    ),
                    modifier = Modifier
                        .padding(8.dp)
                        .align(Alignment.CenterHorizontally)
                )
            }

            // 레시피 박스
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
                    .weight(1f)
                    .border(
                        width = 1.dp,
                        color = Color(0xFF90AA8D),
                        shape = RoundedCornerShape(12.dp)
                    )
            ) {
                if (isLoading) {
                    CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
                } else {
                    Column(modifier = Modifier.fillMaxSize()) {

                        // 첫 줄: 요리명
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(40.dp)
                                .padding(8.dp)
                        ) {
                            Text(recipeName)
                        }

                        // 구분선
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(1.dp)
                                .background(Color(0xFF90AA8D))
                        )

                        // 재료와 방법
                        LazyColumn(
                            state = scrollState,
                            modifier = Modifier.fillMaxSize()
                        ) {
                            item {
                                Text(
                                    "재료: $recipeIngredients",
                                    modifier = Modifier.padding(8.dp)
                                )
                            }
                            item {
                                Text(
                                    "방법: $recipeMethod",
                                    modifier = Modifier.padding(8.dp)
                                )
                            }
                        }
                    }
                }
            }

            // 버튼
            Button(
                onClick = {
                    if (!isLoading && remainingCount > 0) {
                        remainingCount--
                        isLoading = true

                        // GPT 요청 프롬프트 구성

                        val prompt = """
                            제가 가진 재료는 [${ingreidentsSelected.joinToString(", ")}]입니다.
                            주어진 식재료를 가지고 ${titleSelected} 요리 레시피를 제안해 주세요.
                            레시피는 쉽고 빠르게 요리하고 싶어하는 일반인을 대상으로 하며, 식재료 양이나 조리 시간을 그램,분 단위로 반드시 포함하여 친근한 언어로 작성해주세요.
                            레시피는 반드시 주어진 식재료가 포함되는 요리여야 합니다.
                            출력포멧 이외의 필요없는 말은 아무것도 출력하지 마세요

                            출력포맷은 다음과 같습니다.

                            요리명: <요리명>, 재료: <재료> ,요리과정: 1: <요리순서1>,\n 2: <요리순서2>,\n ...

                            
                        """.trimIndent()

                        fetchRecipe(
                            prompt = prompt,
                            onSuccess = { response ->
                                isLoading = false
                                val content = response.choices.getOrNull(0)?.message?.content ?: ""

                                // "요리명:", "재료:", "요리과정:" 기준 파싱
                                recipeName = content
                                    .substringAfter("요리명:")
                                    .substringBefore("재료:")
                                    .trim()

                                recipeIngredients = content
                                    .substringAfter("재료:")
                                    .substringBefore("요리과정:")
                                    .trim()

                                recipeMethod = content
                                    .substringAfter("요리과정:")
                                    .trim()
                            },
                            onError = {
                                isLoading = false
                                recipeName = defaultErrorMessage
                                recipeIngredients = ""
                                recipeMethod = ""
                            }
                        )
                    }
                },
                enabled = (remainingCount > 0 && !isLoading),
                colors = ButtonDefaults.buttonColors(
                    containerColor = colorResource(R.color.titleColor),
                    contentColor = Color.Black
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(80.dp)
                    .padding(horizontal = 12.dp, vertical = 4.dp),
                shape = RoundedCornerShape(8.dp)
            ) {
                Text(text = "레시피 다시 추천 받기 (남은 횟수: $remainingCount)")
            }
        }
    }
}

// ---------- 실제 API 호출 로직 ----------
fun fetchRecipe(
    prompt: String,
    onSuccess: (GptResponse) -> Unit,
    onError: () -> Unit
) {
    val logging = HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.BODY
    }
    val client = OkHttpClient.Builder()
        .addInterceptor(logging)
        .build()

    val retrofit = Retrofit.Builder()
        .baseUrl("https://api.openai.com/")
        .client(client)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    val api = retrofit.create(SimpleApi::class.java)

    val requestBody = GptRequest(
        model = "gpt-3.5-turbo",
        messages = listOf(
            Message("user", prompt)
        ),
        max_tokens = 1000
    )


    val call = api.sendChatCompletion(
        authorization = "Bearer ${BuildConfig.OPENAI_API_KEY}",
        request = requestBody
    )

    call.enqueue(object : Callback<GptResponse> {
        override fun onResponse(call: Call<GptResponse>, response: Response<GptResponse>) {
            if (response.isSuccessful) {
                val body = response.body()
                if (body != null) {
                    onSuccess(body)
                } else {
                    onError()
                }
            } else {
                Log.e("GPT API", "Error: ${response.code()} - ${response.message()}")
                onError()
            }
        }

        override fun onFailure(call: Call<GptResponse>, t: Throwable) {
            Log.e("GPT API", "Failure: ${t.message}")
            onError()
        }
    })
}


