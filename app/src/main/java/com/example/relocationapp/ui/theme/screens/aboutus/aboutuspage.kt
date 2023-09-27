package com.example.relocationapp.ui.theme.screens.aboutus
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.navigation.NavHostController
import com.example.relocationapp.R
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.rememberPagerState

@OptIn(ExperimentalPagerApi::class)
@Composable
fun AboutUsScreen(navController: NavHostController) {
    val context = LocalContext.current

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.TopCenter
    ) {
        Image(
            painter = painterResource(id = R.drawable.back2),
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxSize()
        )

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "About Us",
                style = TextStyle(
                    fontWeight = FontWeight.Bold,
                    fontSize = 24.sp,
                    color = Color.White
                ),
                modifier = Modifier.padding(bottom = 16.dp)
            )
            Text(
                text = "Welcome to our moving company app! With a commitment to excellence," +
                        " we deliver top-notch moving services tailored to meet your every needs." +
                        " Our experienced team ensures an easy and convenient relocation experience." +
                        " From great planning to careful execution, we handle every item care." +
                        " Trust us to make your move a breeze, so you can focus on settling into your new space with ease.Leave the heavy lifting to us.",
                style = TextStyle(fontSize = 16.sp, color = Color.White)
            )
        }
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = 300.dp), // Adjusted padding to create space for the slideshow
            contentAlignment = Alignment.TopCenter
        ) {
            val pagerState = rememberPagerState(initialPage = 0)
            HorizontalPager(
                state = pagerState,
                count = 5,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(300.dp)
                    .background(Color.Gray)
            ) { page ->
                Image(
                    painter = painterResource(id = getSlideImage(page)),
                    contentDescription = null,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.fillMaxSize()
                )
            }

            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(bottom = 16.dp),
                contentAlignment = Alignment.BottomCenter
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    val darkGreen = Color(0xFF006400)
                    Text(
                        text =  "For more information contact us below",
                        style = TextStyle(fontSize = 16.sp, color = darkGreen)
                    )
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.clickable {
                            val intent =
                                Intent(Intent.ACTION_CALL, Uri.parse("tel:" + "+254711228685"))
                            if (ContextCompat.checkSelfPermission(
                                    context,
                                    android.Manifest.permission.CALL_PHONE
                                ) != PackageManager.PERMISSION_GRANTED
                            ) {
                                ActivityCompat.requestPermissions(
                                    context as Activity,
                                    arrayOf(android.Manifest.permission.CALL_PHONE),
                                    1
                                )
                            } else {
                                context.startActivity(intent)
                            }
                        }
                    ) {
                        Icon(
                            imageVector = Icons.Default.Phone,
                            contentDescription = "Phone",
                            modifier = Modifier.size(24.dp),
                            tint = Color.White
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(text = "+254711228685", color = Color.White)
                    }
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.clickable {
                            val emailIntent = Intent(
                                Intent.ACTION_SENDTO,
                                Uri.fromParts("mailto", "rickyrichie001@gmail.com", null)
                            )
                            emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Subject")
                            emailIntent.putExtra(Intent.EXTRA_TEXT, "Body")
                            context.startActivity(
                                Intent.createChooser(
                                    emailIntent,
                                    "Send email..."
                                )
                            )
                        }
                    ) {
                        Icon(
                            imageVector = Icons.Default.Email,
                            contentDescription = "Email",
                            modifier = Modifier.size(24.dp),
                            tint = Color.White
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = "rickyrichie001@gmail.com",
                            color = Color.White
                        )
                    }
                }
            }
        }
    }
}
fun getSlideImage(page: Int): Int {
    val slideImages = listOf(
        R.drawable.slide1,
        R.drawable.slide2,
        R.drawable.slide3,
        R.drawable.slide4,
        R.drawable.slide5
    )
    return slideImages.getOrElse(page) { R.drawable.slide1 }
}
