package io.github.vooft.pepper.theme

import androidx.compose.material.Typography
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.font.FontFamily

@Composable
fun PepperTypography() = Typography(
    defaultFontFamily = FontFamily.Default
).scaled(all = 0.9f, hFactor = 0.6f)

fun Typography.scaled(
    all: Float = 1.0f,
    hFactor: Float = all,
    subtitleFactor: Float = all,
    bodyFactor: Float = all,
    buttonFactor: Float = all,
    captionFactor: Float = all,
    overlineFactor: Float = all
): Typography {
    return copy(
        h1 = h1.copy(fontSize = h1.fontSize * hFactor, lineHeight = h1.lineHeight * hFactor),
        h2 = h2.copy(fontSize = h2.fontSize * hFactor, lineHeight = h2.lineHeight * hFactor),
        h3 = h3.copy(fontSize = h3.fontSize * hFactor, lineHeight = h3.lineHeight * hFactor),
        h4 = h4.copy(fontSize = h4.fontSize * hFactor, lineHeight = h4.lineHeight * hFactor),
        h5 = h5.copy(fontSize = h5.fontSize * hFactor, lineHeight = h5.lineHeight * hFactor),
        h6 = h6.copy(fontSize = h6.fontSize * hFactor, lineHeight = h6.lineHeight * hFactor),
        subtitle1 = subtitle1.copy(fontSize = subtitle1.fontSize * subtitleFactor, lineHeight = subtitle1.lineHeight * subtitleFactor),
        subtitle2 = subtitle2.copy(fontSize = subtitle2.fontSize * subtitleFactor, lineHeight = subtitle2.lineHeight * subtitleFactor),
        body1 = body1.copy(fontSize = body1.fontSize * bodyFactor, lineHeight = body1.lineHeight * bodyFactor),
        body2 = body2.copy(fontSize = body2.fontSize * bodyFactor, lineHeight = body2.lineHeight * bodyFactor),
        button = button.copy(fontSize = button.fontSize * buttonFactor, lineHeight = button.lineHeight * buttonFactor),
        caption = caption.copy(fontSize = caption.fontSize * captionFactor, lineHeight = caption.lineHeight * captionFactor),
        overline = overline.copy(fontSize = overline.fontSize * overlineFactor, lineHeight = overline.lineHeight * overlineFactor)
    )
}
