package com.example.designsystem

import androidx.compose.ui.tooling.preview.Preview

@Preview(name = "1-phone-sw", device = "spec:width=320dp,height=600dp,dpi=420")
@Preview(name = "2-phone-mw", device = "spec:width=360dp,height=640dp,dpi=480")
@Preview(name = "3-phone-compact", device = "spec:width=410dp,height=720dp,dpi=480")
@Preview(name = "4-landscape", device = "spec:width=640dp,height=360dp,dpi=480")
@Preview(name = "5-foldable", device = "spec:width=673dp,height=841dp,dpi=480")
@Preview(name = "6-tablet", device = "spec:width=1280dp,height=800dp,dpi=480")
annotation class DevicePreviews