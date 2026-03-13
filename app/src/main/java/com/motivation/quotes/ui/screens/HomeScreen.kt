package com.motivation.quotes.ui.screens

import android.content.Context
import android.content.Intent
import androidx.compose.animation.*
import androidx.compose.animation.core.*
import androidx.compose.foundation.*
import androidx.compose.foundation.gestures.detectHorizontalDragGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.motivation.quotes.data.model.QuoteCategory
import com.motivation.quotes.ui.components.AnimatedBackground
import com.motivation.quotes.ui.components.QuoteCard
import com.motivation.quotes.viewmodel.QuoteViewModel
import kotlin.math.abs

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun HomeScreen(
    viewModel: QuoteViewModel,
    onNavigateToFavorites: () -> Unit
) {
    val uiState by viewModel.uiState.collectAsState()
    val context = LocalContext.current
    var dragOffset by remember { mutableStateOf(0f) }

    AnimatedBackground(
        backgroundIndex = uiState.currentBackgroundIndex
    ) {
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
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                // App title
                Text(
                    text = "✨ Daily Motivation",
                    style = MaterialTheme.typography.titleLarge.copy(
                        fontWeight = FontWeight.Bold
                    ),
                    color = Color.White
                )

                Row {
                    // Dark mode toggle
                    IconButton(
                        onClick = { viewModel.toggleDarkMode() },
                        modifier = Modifier
                            .size(40.dp)
                            .clip(CircleShape)
                            .background(Color.White.copy(alpha = 0.2f))
                    ) {
                        Icon(
                            imageVector = if (uiState.isDarkMode) Icons.Default.LightMode else Icons.Default.DarkMode,
                            contentDescription = "Toggle theme",
                            tint = Color.White,
                            modifier = Modifier.size(20.dp)
                        )
                    }

                    Spacer(modifier = Modifier.width(8.dp))

                    // Favorites button
                    IconButton(
                        onClick = onNavigateToFavorites,
                        modifier = Modifier
                            .size(40.dp)
                            .clip(CircleShape)
                            .background(Color.White.copy(alpha = 0.2f))
                    ) {
                        BadgedBox(
                            badge = {
                                if (uiState.favoriteQuotes.isNotEmpty()) {
                                    Badge(
                                        containerColor = Color(0xFFFF4081)
                                    ) {
                                        Text(
                                            text = "${uiState.favoriteQuotes.size}",
                                            color = Color.White,
                                            fontSize = 10.sp
                                        )
                                    }
                                }
                            }
                        ) {
                            Icon(
                                imageVector = Icons.Default.Bookmark,
                                contentDescription = "Favorites",
                                tint = Color.White,
                                modifier = Modifier.size(20.dp)
                            )
                        }
                    }
                }
            }

            // Category filter
            LazyRow(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp),
                contentPadding = PaddingValues(horizontal = 16.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(QuoteCategory.values()) { category ->
                    CategoryChip(
                        category = category,
                        isSelected = uiState.selectedCategory == category,
                        onClick = { viewModel.setCategory(category) }
                    )
                }
            }

            Spacer(modifier = Modifier.weight(0.5f))

            // Quote card with swipe gesture
            AnimatedContent(
                targetState = uiState.currentQuote,
                transitionSpec = {
                    (fadeIn(tween(500)) + slideInHorizontally { width -> width / 4 })
                        .togetherWith(fadeOut(tween(300)) + slideOutHorizontally { width -> -width / 4 })
                },
                label = "quote_transition"
            ) { quote ->
                if (quote != null) {
                    QuoteCard(
                        quote = quote,
                        isFavorite = uiState.isCurrentFavorite,
                        onToggleFavorite = { viewModel.toggleFavorite() },
                        onShare = { shareQuote(context, quote.text, quote.author) },
                        modifier = Modifier
                            .pointerInput(Unit) {
                                detectHorizontalDragGestures(
                                    onDragEnd = {
                                        if (abs(dragOffset) > 100f) {
                                            if (dragOffset < 0) viewModel.getNextQuote()
                                            else viewModel.getPreviousQuote()
                                        }
                                        dragOffset = 0f
                                    }
                                ) { _, dragAmount ->
                                    dragOffset += dragAmount
                                }
                            }
                    )
                }
            }

            Spacer(modifier = Modifier.weight(0.3f))

            // Navigation controls
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 40.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Previous button
                FilledIconButton(
                    onClick = { viewModel.getPreviousQuote() },
                    modifier = Modifier.size(52.dp),
                    colors = IconButtonDefaults.filledIconButtonColors(
                        containerColor = Color.White.copy(alpha = 0.2f),
                        contentColor = Color.White
                    )
                ) {
                    Icon(
                        imageVector = Icons.Default.ArrowBack,
                        contentDescription = "Previous quote",
                        modifier = Modifier.size(24.dp)
                    )
                }

                // Random/Refresh button
                FilledIconButton(
                    onClick = { viewModel.getRandomQuote() },
                    modifier = Modifier.size(64.dp),
                    colors = IconButtonDefaults.filledIconButtonColors(
                        containerColor = Color.White.copy(alpha = 0.3f),
                        contentColor = Color.White
                    )
                ) {
                    Icon(
                        imageVector = Icons.Default.Shuffle,
                        contentDescription = "Random quote",
                        modifier = Modifier.size(32.dp)
                    )
                }

                // Next button
                FilledIconButton(
                    onClick = { viewModel.getNextQuote() },
                    modifier = Modifier.size(52.dp),
                    colors = IconButtonDefaults.filledIconButtonColors(
                        containerColor = Color.White.copy(alpha = 0.2f),
                        contentColor = Color.White
                    )
                ) {
                    Icon(
                        imageVector = Icons.Default.ArrowForward,
                        contentDescription = "Next quote",
                        modifier = Modifier.size(24.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            // Swipe hint
            Text(
                text = "← Swipe to navigate →",
                style = MaterialTheme.typography.labelSmall,
                color = Color.White.copy(alpha = 0.5f),
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(16.dp))
        }
    }
}

@Composable
fun CategoryChip(
    category: QuoteCategory,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .clip(RoundedCornerShape(20.dp))
            .background(
                if (isSelected) Color.White.copy(alpha = 0.35f)
                else Color.White.copy(alpha = 0.15f)
            )
            .clickable(onClick = onClick)
            .padding(horizontal = 16.dp, vertical = 8.dp)
    ) {
        Text(
            text = category.displayName,
            style = MaterialTheme.typography.labelSmall.copy(
                fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal,
                letterSpacing = 0.5.sp
            ),
            color = if (isSelected) Color.White else Color.White.copy(alpha = 0.7f)
        )
    }
}

private fun shareQuote(context: Context, text: String, author: String) {
    val shareIntent = Intent(Intent.ACTION_SEND).apply {
        type = "text/plain"
        putExtra(Intent.EXTRA_TEXT, "\"$text\"\n\n— $author\n\n#DailyMotivation #AnimeQuotes")
    }
    context.startActivity(Intent.createChooser(shareIntent, "Share Quote"))
}
