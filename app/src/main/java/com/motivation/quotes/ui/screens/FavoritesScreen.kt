package com.motivation.quotes.ui.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.*
import androidx.compose.runtime.*
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
import com.motivation.quotes.ui.components.AnimatedBackground
import com.motivation.quotes.viewmodel.QuoteViewModel

@Composable
fun FavoritesScreen(
    viewModel: QuoteViewModel,
    onNavigateBack: () -> Unit
) {
    val uiState by viewModel.uiState.collectAsState()

    AnimatedBackground(backgroundIndex = 5) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .statusBarsPadding()
                .navigationBarsPadding()
        ) {
            // Top bar
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(
                    onClick = onNavigateBack,
                    modifier = Modifier
                        .size(40.dp)
                        .clip(CircleShape)
                        .background(Color.White.copy(alpha = 0.2f))
                ) {
                    Icon(
                        imageVector = Icons.Default.ArrowBack,
                        contentDescription = "Back",
                        tint = Color.White
                    )
                }

                Spacer(modifier = Modifier.width(12.dp))

                Text(
                    text = "⭐ Favorite Quotes",
                    style = MaterialTheme.typography.titleLarge.copy(
                        fontWeight = FontWeight.Bold
                    ),
                    color = Color.White
                )
            }

            if (uiState.favoriteQuotes.isEmpty()) {
                // Empty state
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {
                        Icon(
                            imageVector = Icons.Default.FavoriteBorder,
                            contentDescription = null,
                            tint = Color.White.copy(alpha = 0.5f),
                            modifier = Modifier.size(80.dp)
                        )
                        Spacer(modifier = Modifier.height(16.dp))
                        Text(
                            text = "No favorites yet",
                            style = MaterialTheme.typography.headlineMedium,
                            color = Color.White.copy(alpha = 0.7f)
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            text = "Tap ♥ on any quote to save it here",
                            style = MaterialTheme.typography.bodyMedium,
                            color = Color.White.copy(alpha = 0.5f),
                            textAlign = TextAlign.Center
                        )
                    }
                }
            } else {
                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    contentPadding = PaddingValues(16.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    items(
                        items = uiState.favoriteQuotes,
                        key = { it.id }
                    ) { quote ->
                        AnimatedVisibility(
                            visible = true,
                            enter = fadeIn(),
                            exit = fadeOut()
                        ) {
                            FavoriteQuoteItem(
                                quote = quote,
                                onRemoveFavorite = {
                                    viewModel.removeFavorite(quote)
                                }
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun FavoriteQuoteItem(
    quote: Quote,
    onRemoveFavorite: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(16.dp))
            .background(
                brush = Brush.verticalGradient(
                    colors = listOf(
                        Color.White.copy(alpha = 0.20f),
                        Color.White.copy(alpha = 0.10f)
                    )
                )
            )
            .padding(16.dp)
    ) {
        Column {
            Text(
                text = "\"${quote.text}\"",
                style = MaterialTheme.typography.bodyLarge.copy(
                    fontStyle = FontStyle.Italic,
                    lineHeight = 24.sp
                ),
                color = Color.White,
            )
            Spacer(modifier = Modifier.height(8.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column {
                    Text(
                        text = "— ${quote.author}",
                        style = MaterialTheme.typography.bodyMedium.copy(
                            fontWeight = FontWeight.Medium
                        ),
                        color = Color.White.copy(alpha = 0.8f)
                    )
                    Text(
                        text = quote.category,
                        style = MaterialTheme.typography.labelSmall,
                        color = Color.White.copy(alpha = 0.5f),
                        letterSpacing = 1.sp
                    )
                }

                IconButton(
                    onClick = onRemoveFavorite,
                    modifier = Modifier
                        .size(36.dp)
                        .clip(CircleShape)
                        .background(Color(0xFFFF4081).copy(alpha = 0.3f))
                ) {
                    Icon(
                        imageVector = Icons.Default.Favorite,
                        contentDescription = "Remove favorite",
                        tint = Color(0xFFFF4081),
                        modifier = Modifier.size(18.dp)
                    )
                }
            }
        }
    }
}
