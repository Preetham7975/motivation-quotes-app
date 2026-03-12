package com.motivation.quotes.ui.components

import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color

val backgroundGradients = listOf(
    // Dreamy purple-blue (anime night sky)
    listOf(Color(0xFF1A1A2E), Color(0xFF16213E), Color(0xFF0F3460), Color(0xFF533483)),
    // Sakura pink (cherry blossom)
    listOf(Color(0xFF2D1B4E), Color(0xFF5C2D6E), Color(0xFFB05E8A), Color(0xFFE8A0BF)),
    // Ocean depths (aqua anime)
    listOf(Color(0xFF0D1B2A), Color(0xFF1B4D6E), Color(0xFF2E86AB), Color(0xFF7BC8E0)),
    // Sunset anime sky
    listOf(Color(0xFF2C1654), Color(0xFF8B2FC9), Color(0xFFE15A5A), Color(0xFFFFB347)),
    // Forest magic (Studio Ghibli vibes)
    listOf(Color(0xFF0A2718), Color(0xFF1A5C3E), Color(0xFF2D9B63), Color(0xFF7FD99A)),
    // Starlit purple
    listOf(Color(0xFF0D0221), Color(0xFF190535), Color(0xFF5B1A8A), Color(0xFF9B59B6)),
    // Dawn rose
    listOf(Color(0xFF1A0A2E), Color(0xFF4A1942), Color(0xFFA8456B), Color(0xFFF4A7B9)),
    // Azure twilight
    listOf(Color(0xFF050B2C), Color(0xFF0D2137), Color(0xFF1B4F72), Color(0xFF5DADE2))
)

@Composable
fun AnimatedBackground(
    backgroundIndex: Int,
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit
) {
    val safeIndex = backgroundIndex % backgroundGradients.size
    val gradient = backgroundGradients[safeIndex]

    // Floating orb animation
    val infiniteTransition = rememberInfiniteTransition(label = "bg_anim")
    val animOffset1 by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(
            animation = tween(8000, easing = LinearEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "orb1"
    )
    val animOffset2 by infiniteTransition.animateFloat(
        initialValue = 1f,
        targetValue = 0f,
        animationSpec = infiniteRepeatable(
            animation = tween(12000, easing = LinearEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "orb2"
    )

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(
                brush = Brush.verticalGradient(
                    colors = gradient
                )
            )
            .drawBehind {
                // Animated glowing orbs (anime particle effect)
                val orb1Center = Offset(
                    x = size.width * (0.2f + animOffset1 * 0.6f),
                    y = size.height * (0.1f + animOffset2 * 0.4f)
                )
                val orb2Center = Offset(
                    x = size.width * (0.8f - animOffset2 * 0.4f),
                    y = size.height * (0.6f + animOffset1 * 0.3f)
                )

                // Draw soft glowing circles
                drawCircle(
                    brush = Brush.radialGradient(
                        colors = listOf(
                            Color.White.copy(alpha = 0.15f),
                            Color.Transparent
                        ),
                        center = orb1Center,
                        radius = size.width * 0.4f
                    ),
                    center = orb1Center,
                    radius = size.width * 0.4f
                )
                drawCircle(
                    brush = Brush.radialGradient(
                        colors = listOf(
                            Color.White.copy(alpha = 0.10f),
                            Color.Transparent
                        ),
                        center = orb2Center,
                        radius = size.width * 0.35f
                    ),
                    center = orb2Center,
                    radius = size.width * 0.35f
                )
            }
    ) {
        content()
    }
}
