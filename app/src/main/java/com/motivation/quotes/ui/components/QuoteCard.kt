package com.motivation.quotes.ui.components

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.motivation.quotes.data.model.Quote

@Composable
fun QuoteCard(
    quote: Quote,
    isFavorite: Boolean,
    onToggleFavorite: () -> Unit,
    onShare: () -> Unit,
    modifier: Modifier = Modifier
) {
    val favoriteColor by animateColorAsState(
        targetValue = if (isFavorite) Color(0xFFFF4081) else Color.White,
        animationSpec = tween(300),
        label = "favorite_color"
    )

    Box(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 20.dp)
    ) {
        // Glassmorphism card
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(24.dp))
                .background(
                    brush = Brush.verticalGradient(
                        colors = listOf(
                            Color.White.copy(alpha = 0.25f),
                            Color.White.copy(alpha = 0.10f)
                        )
                    )
                )
                .padding(28.dp)
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                // Opening quotation mark (\u201C = ")
                Text(
                    text = "\u201C",
                    fontSize = 60.sp,
                    color = Color.White.copy(alpha = 0.6f),
                    lineHeight = 40.sp,
                    modifier = Modifier
                        .align(Alignment.Start)
                        .offset(y = 10.dp)
                )

                Spacer(modifier = Modifier.height(4.dp))

                // Quote text
                Text(
                    text = quote.text,
                    style = MaterialTheme.typography.headlineMedium.copy(
                        fontStyle = FontStyle.Italic,
                        fontWeight = FontWeight.Light,
                        fontSize = 20.sp,
                        lineHeight = 32.sp
                    ),
                    color = Color.White,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(20.dp))

                // Divider
                Box(
                    modifier = Modifier
                        .width(60.dp)
                        .height(2.dp)
                        .background(
                            brush = Brush.horizontalGradient(
                                colors = listOf(
                                    Color.Transparent,
                                    Color.White.copy(alpha = 0.7f),
                                    Color.Transparent
                                )
                            )
                        )
                )

                Spacer(modifier = Modifier.height(16.dp))

                // Author
                Text(
                    text = "— ${quote.author}",
                    style = MaterialTheme.typography.bodyLarge.copy(
                        fontWeight = FontWeight.Medium,
                        letterSpacing = 1.sp
                    ),
                    color = Color.White.copy(alpha = 0.9f),
                    textAlign = TextAlign.Center
                )

                Spacer(modifier = Modifier.height(4.dp))

                // Category badge
                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(20.dp))
                        .background(Color.White.copy(alpha = 0.2f))
                        .padding(horizontal = 12.dp, vertical = 4.dp)
                ) {
                    Text(
                        text = quote.category,
                        style = MaterialTheme.typography.labelSmall,
                        color = Color.White.copy(alpha = 0.8f),
                        letterSpacing = 1.5.sp
                    )
                }

                Spacer(modifier = Modifier.height(20.dp))

                // Action buttons
                Row(
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    // Favorite button
                    IconButton(
                        onClick = onToggleFavorite,
                        modifier = Modifier
                            .size(48.dp)
                            .clip(RoundedCornerShape(50))
                            .background(Color.White.copy(alpha = 0.2f))
                    ) {
                        Icon(
                            imageVector = if (isFavorite) Icons.Default.Favorite else Icons.Default.FavoriteBorder,
                            contentDescription = if (isFavorite) "Remove from favorites" else "Add to favorites",
                            tint = favoriteColor,
                            modifier = Modifier.size(24.dp)
                        )
                    }

                    Spacer(modifier = Modifier.width(16.dp))

                    // Share button
                    IconButton(
                        onClick = onShare,
                        modifier = Modifier
                            .size(48.dp)
                            .clip(RoundedCornerShape(50))
                            .background(Color.White.copy(alpha = 0.2f))
                    ) {
                        Icon(
                            imageVector = Icons.Default.Share,
                            contentDescription = "Share quote",
                            tint = Color.White,
                            modifier = Modifier.size(24.dp)
                        )
                    }
                }
            }
        }
    }
}
