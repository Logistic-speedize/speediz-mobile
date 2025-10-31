package com.example.speediz.ui.feature.unauthorized.signup.delivery

import androidx.compose.foundation.Image
import androidx.compose.foundation.MarqueeDefaults.Spacing
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.WindowInsetsSides
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.LineHeightStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.room.util.TableInfo
import com.example.speediz.R
import com.example.speediz.ui.navigation.AuthorizedRoute
import com.example.speediz.ui.navigation.UnauthorizedRoute
import com.example.speediz.ui.theme.Spacing
import com.example.speediz.ui.theme.SpeedizTheme
import com.example.speediz.ui.theme.spacing

@Composable
fun ScreenConfirmSignUp(
    onNavigateToSignIn : (String) -> Unit ,
) {
    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .background(
            color = MaterialTheme.colorScheme.background
        )
    ) { innerPadding ->
        Box(
            modifier = Modifier.fillMaxWidth().padding(innerPadding)
        ){
            Column(
                modifier = Modifier
                    .fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ){
                Image(
                    painter = painterResource(id = R.drawable.img_speediz_logo),
                    contentDescription = "Speediz Logo",
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(150.dp)
                        .padding(16.dp),
                )
                Text(
                    text = "Welcome!",
                    style = MaterialTheme.typography.headlineMedium,
                    fontWeight = FontWeight.Bold,
                    fontSize = 52.sp,
                    modifier = Modifier.padding(8.dp).align(alignment = Alignment.CenterHorizontally),
                    color = Color.Black
                )
                Text(
                    text = "You have successfully login/signup\n" +
                            "an account!",
                    style = MaterialTheme.typography.bodyMedium,
                    modifier = Modifier.padding(8.dp).align(alignment = Alignment.CenterHorizontally),
                    textAlign = TextAlign.Center,
                    color = Color.Black
                )
                Spacer(
                    modifier = Modifier.height(24.dp)
                )
                Button(
                    modifier = Modifier
                        .width(200.dp)
                        .background(
                            color = MaterialTheme.colorScheme.primary,
                            shape = RoundedCornerShape(16.dp)
                        )
                    ,
                    onClick = {
                        onNavigateToSignIn(UnauthorizedRoute.SignIn.route)
                    },
                    content = {
                        Text(
                            text = "Back to Sign In",
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.surface
                        )
                    }
                )
            }
        }
    }
}

@Preview ( showBackground = true)
@Composable
fun Preview(){
    SpeedizTheme{
        ScreenConfirmSignUp(
            onNavigateToSignIn = {}
        )
    }
}