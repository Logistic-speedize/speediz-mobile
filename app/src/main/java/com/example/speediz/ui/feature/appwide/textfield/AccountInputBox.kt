//package com.example.speediz.ui.feature.appwide.textfield
//
//import androidx.compose.foundation.layout.Column
//import androidx.compose.foundation.layout.PaddingValues
//import androidx.compose.foundation.layout.fillMaxWidth
//import androidx.compose.foundation.layout.height
//import androidx.compose.foundation.layout.padding
//import androidx.compose.material3.OutlinedTextField
//import androidx.compose.material3.Text
//import androidx.compose.material3.TextFieldDefaults
//import androidx.compose.runtime.Composable
//import androidx.compose.ui.Modifier
//import androidx.compose.ui.text.TextStyle
//import androidx.compose.ui.text.font.FontWeight
//import androidx.compose.ui.unit.dp
//import androidx.compose.ui.unit.sp
//
//@Composable
//fun AccountInputBox(
//    modifier: Modifier = Modifier,
//    label: String,
//    value: String = "",
//    onValueChange: (String) -> Unit = {}
//) {
//    Column(modifier = modifier.fillMaxWidth()) {
//
//        // 1. Label positioned top-left
//        Text(
//            text = label,
//            fontSize = 13.sp,
//            fontWeight = FontWeight.SemiBold,
//            modifier = Modifier.padding(bottom = 4.dp)
//        )
//
//        OutlinedTextField(
//            value = value,
//            onValueChange = onValueChange,
//            placeholder = {
//                Text(label, fontSize = 13.sp)
//            },
//            singleLine = true,
//            textStyle = TextStyle(fontSize = 13.sp),
//            modifier = Modifier.fillMaxWidth(),
//            colors = TextFieldDefaults.colors(),
//            contentPadding = PaddingValues(
//                horizontal = 12.dp,
//                vertical = 8.dp
//            )
//        )
//    }
//}