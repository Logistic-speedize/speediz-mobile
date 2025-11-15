package com.example.speediz.ui.feature.authorized.delivery.Package

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.speediz.R
import com.example.speediz.ui.theme.SpeedizTheme

@Composable
fun ScreenMap2() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF9FAF6))
            .padding(horizontal = 20.dp, vertical = 24.dp)
    ) {

        // Top Bar
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = painterResource(id = R.drawable.ic_arrow_back),
                contentDescription = "Back",
                modifier = Modifier.size(24.dp),
                colorFilter = ColorFilter.tint(Color.Black)
            )

            Spacer(modifier = Modifier.weight(1f))

            Text(
                text = "Map",
                color = Color.Black,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(end = 24.dp)
            )

            Spacer(modifier = Modifier.weight(1f))
        }

        Spacer(modifier = Modifier.height(36.dp))

        // Search Box
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .shadow(6.dp, RoundedCornerShape(12.dp), clip = false)
                .clip(RoundedCornerShape(12.dp))
                .background(Color.White)
                .border(1.dp, Color(0xFF3A3A3A), RoundedCornerShape(12.dp))
                .padding(horizontal = 16.dp, vertical = 12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = painterResource(id = R.drawable.ic_search),
                contentDescription = "Search",
                modifier = Modifier.size(22.dp),
                colorFilter = ColorFilter.tint(Color.Gray)
            )

            Spacer(modifier = Modifier.width(8.dp))

            Text(
                text = "Search shipment number",
                color = Color.Gray.copy(alpha = 0.9f),
                fontSize = 14.sp
            )
        }

        Spacer(modifier = Modifier.weight(1f))

        // Bottom Sheet
        LocationBottomSheet()
    }
}

@Composable
fun LocationBottomSheet() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(topStart = 28.dp, topEnd = 28.dp))
            .background(Color.White)
            .padding(horizontal = 20.dp, vertical = 24.dp)
    ) {

        Box(
            modifier = Modifier
                .size(width = 60.dp, height = 6.dp)
                .clip(RoundedCornerShape(50))
                .background(Color(0xFFBDB7B7))
                .align(Alignment.CenterHorizontally)
        )

        Spacer(modifier = Modifier.height(24.dp))

        Text(
            text = "Location",
            fontSize = 22.sp,
            fontWeight = FontWeight.Bold,
            color = Color.Black
        )

        Spacer(modifier = Modifier.height(20.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            InfoBox(title = "Pickup Location", value = "Carrol Avenue 64")
            InfoBox(title = "Delivering To", value = "Buerng Salang")
        }

        Spacer(modifier = Modifier.height(20.dp))

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .border(2.dp, Color(0xFFFFD48E), RoundedCornerShape(14.dp))
                .padding(horizontal = 18.dp, vertical = 14.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = painterResource(id = R.drawable.ic_boxes),
                contentDescription = "Package",
                modifier = Modifier.size(40.dp)
            )

            Spacer(modifier = Modifier.width(12.dp))

            Text(
                text = "Package cost",
                fontSize = 16.sp,
                color = Color.Black,
                fontWeight = FontWeight.Medium,
                modifier = Modifier.weight(1f)
            )

            Text(
                text = "$100",
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF7B3FF2)
            )
        }

        Spacer(modifier = Modifier.height(24.dp))

        // ✅ Both Buttons Have Filled Backgrounds
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {

            ActionButton(
                icon = R.drawable.ic_arrow_top_right,
                text = "Get Direction",
                textColor = Color.White,
                borderColor = Color(0xFF007BFF),
                backgroundColor = Color(0xFF007BFF),
                filled = true
            )

            ActionButton(
                icon = R.drawable.ic_navigation,
                text = "Start",
                textColor = Color.White,
                borderColor = Color(0xFFE74C3C),
                backgroundColor = Color(0xFFE74C3C),
                filled = true
            )
        }
    }
}

@Composable
fun InfoBox(title: String, value: String) {
    Column(
        modifier = Modifier
            .width(160.dp)
            .border(2.dp, Color(0xFFFFD48E), RoundedCornerShape(14.dp))
            .padding(horizontal = 14.dp, vertical = 10.dp)
    ) {
        Text(text = title, fontSize = 12.sp, color = Color.Gray)
        Spacer(modifier = Modifier.height(4.dp))
        Text(text = value, fontSize = 15.sp, fontWeight = FontWeight.Bold)
    }
}

@Composable
fun ActionButton(
    icon: Int,
    text: String,
    textColor: Color,
    borderColor: Color,
    filled: Boolean,
    backgroundColor: Color = Color.White
) {
    Row(
        modifier = Modifier
            .width(150.dp)
            .clip(RoundedCornerShape(32.dp))
            .background(if (filled) backgroundColor else Color.White)
            .border(2.dp, borderColor, RoundedCornerShape(32.dp))
            .padding(vertical = 10.dp, horizontal = 14.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {
        Image(
            painter = painterResource(id = icon),
            contentDescription = text,
            modifier = Modifier.size(18.dp),
            colorFilter = ColorFilter.tint(textColor)
        )
        Spacer(modifier = Modifier.width(8.dp))
        Text(text = text, color = textColor, fontSize = 14.sp, fontWeight = FontWeight.Medium)
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewScreenMap2() {
    SpeedizTheme {
        ScreenMap2()
    }
}