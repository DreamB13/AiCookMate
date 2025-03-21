package com.sdc.aicookmate

import android.content.pm.PackageManager
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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.google.mlkit.vision.common.InputImage
import com.google.mlkit.vision.text.TextRecognition
import com.google.mlkit.vision.text.korean.KoreanTextRecognizerOptions
import java.io.IOException

@Preview(showBackground = true)
@Composable
fun PreviewScanReceiptImageScreen() {
    // Fake NavController for preview purposes
    val fakeNavController = rememberNavController()

    ScanReceiptImage(navController = fakeNavController)
}


@Composable
fun ScanReceiptImage(navController: NavController) {
    var imageUri by remember { mutableStateOf<Uri?>(null) }
    val context = LocalContext.current
    val pickMedia =
        rememberLauncherForActivityResult(ActivityResultContracts.PickVisualMedia()) { uri ->
            if (uri != null) {
                imageUri = uri
            }
        }
    var receiptText by remember { mutableStateOf("") }

    // Camera Launcher 설정
    val permissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission()
    ) { isGranted ->
        if (isGranted) {
            Toast.makeText(context, "카메라 권한 허용됨", Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(context, "카메라 권한이 필요합니다.", Toast.LENGTH_SHORT).show()
        }
    }

    val cameraLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.TakePicture(),
        onResult = { success ->
            if (success) {
                Toast.makeText(context, "사진 저장 완료", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(context, "사진 촬영 실패", Toast.LENGTH_SHORT).show()
            }
        }
    )

    LaunchedEffect(imageUri) {
        if (imageUri != null) {
            val recognizer =
                TextRecognition.getClient(KoreanTextRecognizerOptions.Builder().build())
            try {
                val image = InputImage.fromFilePath(context, imageUri!!)
                recognizer.process(image)
                    .addOnSuccessListener { result ->
                        receiptText = result.text
                    }
                    .addOnFailureListener { e ->
                    }
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }
    }
    val foundIngredients = ingredients.filter { it in receiptText }.distinct().toMutableList()
    val scrollState = rememberScrollState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color.White)
    ) {
        Spacer(modifier = Modifier.height(10.dp))

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(10.dp)
                .weight(1f)
        ) {
            Box(
                modifier = Modifier
                    .weight(3f)
                    .padding(horizontal = 10.dp)
                    .background(color = Color(0xffECECEC))
            ) {
                Column {
                    Text(
                        "영수증",
                        fontSize = 30.sp,
                        fontWeight = FontWeight.Bold,
                        textAlign = TextAlign.Center,
                        modifier = Modifier
                            .padding(3.dp)
                            .fillMaxWidth()
                    )

                    Column(
                        modifier = Modifier
                            .padding(vertical = 10.dp)
                            .verticalScroll(scrollState)
                            .fillMaxWidth()
                    ) {
                        for (ingredient in foundIngredients) {
                            ListOfIngredientsUI(ingredient)
                        }
                    }
                }
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                ) {
                    Button(
                        onClick = {
                            val newIngredients =
                                foundIngredients.filter { it !in ingreidentsSelected }
                            ingreidentsSelected.addAll(newIngredients.distinct())
                            navController.navigate("refigeratorScreen")
                            Toast.makeText(context, "재료를 추가했어요!", Toast.LENGTH_SHORT).show()
                        },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = colorResource(id = R.color.titleColor) // XML 색상 적용
                        ),
                        shape = RoundedCornerShape(12.dp),
                        contentPadding = PaddingValues(0.dp),
                        modifier = Modifier
                            .size(100.dp)
                            .align(Alignment.BottomEnd) // 버튼을 오른쪽 아래로 배치
                            .padding(10.dp)
                    ) {
                        Text(
                            "확인",
                            fontSize = 24.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.Black
                        )
                    }
                }
            }
        }
        Spacer(modifier = Modifier.padding(vertical = 20.dp))

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 10.dp)
                .weight(0.7f)

        ) {
            Row {
                Button(
                    onClick = {
                        if (ContextCompat.checkSelfPermission(
                                context,
                                android.Manifest.permission.CAMERA
                            ) ==
                            PackageManager.PERMISSION_GRANTED
                        ) {
                            // 권한이 허용된 경우 카메라 실행
                            val uri = createImageUri(context)
                            cameraLauncher.launch(uri)
                            imageUri = uri
                        } else {
                            // 권한 요청
                            permissionLauncher.launch(android.Manifest.permission.CAMERA)
                        }
                    },
                    shape = RoundedCornerShape(12.dp),
                    contentPadding = PaddingValues(0.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = colorResource(id = R.color.titleColor) // XML 색상 적용
                    ),

                    modifier = Modifier
                        .size(70.dp)
                )
                {
                    Image(
                        painter = painterResource(id = R.drawable.ic_plus),
                        contentDescription = "사진 버튼 이미지",
                        modifier = Modifier.size(40.dp)
                    )
                }
                Column(modifier = Modifier.padding(horizontal = 10.dp, vertical = 3.dp)) {
                    Text(
                        "카메라 촬영",
                        fontSize = 24.sp,
                        modifier = Modifier,
                        fontWeight = FontWeight.Bold
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text("냉장고를 촬영하고 사진을 넣어보세요", fontSize = 12.sp)
                }
            }

            Spacer(modifier = Modifier.height(25.dp))
            Row {
                Button(
                    onClick = {
                        pickMedia.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
                    },
                    shape = RoundedCornerShape(12.dp),
                    contentPadding = PaddingValues(0.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = colorResource(id = R.color.titleColor) // XML 색상 적용
                    ),

                    modifier = Modifier
                        .size(70.dp)
                )
                {
                    Image(
                        painter = painterResource(id = R.drawable.ic_plus),
                        contentDescription = "사진 버튼 이미지",
                        modifier = Modifier.size(40.dp)
                    )
                }
                Column(modifier = Modifier.padding(horizontal = 10.dp, vertical = 3.dp)) {
                    Text(
                        "사진 넣기",
                        fontSize = 24.sp,
                        modifier = Modifier,
                        fontWeight = FontWeight.Bold
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text("갤러리에 있는 사진을 등록해보세요", fontSize = 12.sp)
                }
            }

            Spacer(modifier = Modifier.height(25.dp))
            Row {
                Button(
                    onClick = {
                        navController.navigate("refigeratorScreen")
                    },
                    shape = RoundedCornerShape(12.dp),
                    contentPadding = PaddingValues(0.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = colorResource(id = R.color.titleColor) // XML 색상 적용
                    ),

                    modifier = Modifier
                        .size(70.dp)
                )
                {
                    Image(
                        painter = painterResource(id = R.drawable.ic_plus),
                        contentDescription = "뒤로가기",
                        modifier = Modifier.size(40.dp)
                    )//냉장고로 돌아가기
                }
                Column(modifier = Modifier.padding(horizontal = 5.dp, vertical = 3.dp)) {
                    Text(
                        "뒤로가기",
                        fontSize = 24.sp,
                        modifier = Modifier,
                        fontWeight = FontWeight.Bold
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text("돌아가서 레시피를 추천받아보세요", fontSize = 12.sp)
                }
            }
        }
    }
}


@Composable
fun ListOfIngredientsUI(text: String) {
    Box(modifier = Modifier.height(6.dp))
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.LightGray)
            .height(1.dp)
    )
    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 5.dp)
    ) {
        Text(text)
        Image(
            painter = painterResource(R.drawable.postit_close_btn),
            contentDescription = "닫기 버튼",
        )
    }
}