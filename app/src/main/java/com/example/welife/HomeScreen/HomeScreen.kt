package com.example.welife.HomeScreen

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.delay
import androidx.navigation.NavHostController

@Composable
fun HomeScreen(modifier: Modifier = Modifier, navController: NavHostController) {
    // 1. Track which button is selected
    val selectedItem = remember { mutableStateOf(0) }

    Scaffold(
        bottomBar = {
            // 2. Bottom Navigation Bar with WHITE background
            NavigationBar(
                containerColor = Color.White,
                contentColor = Color.Black
            ) {
                // Button 1: Home
                NavigationBarItem(
                    selected = selectedItem.value == 0,
                    onClick = {
                        selectedItem.value = 0
                    },
                    icon = {
                        Icon(
                            imageVector = Icons.Default.Home,
                            contentDescription = "Home"
                        )
                    },
                    label = {
                        Text("Home")
                    }
                )

                // Button 2: Category
                NavigationBarItem(
                    selected = selectedItem.value == 1,
                    onClick = {
                        selectedItem.value = 1
                    },
                    icon = {
                        Icon(
                            imageVector = Icons.Default.Menu,
                            contentDescription = "Category"
                        )
                    },
                    label = {
                        Text("Category")
                    }
                )

                // Button 3: Cart
                NavigationBarItem(
                    selected = selectedItem.value == 2,
                    onClick = {
                        selectedItem.value = 2
                    },
                    icon = {
                        Icon(
                            imageVector = Icons.Default.ShoppingCart,
                            contentDescription = "Cart"
                        )
                    },
                    label = {
                        Text("Cart")
                    }
                )
            }
        }
    ) { paddingValues ->
        // 3. Show different screen based on selected button
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            when (selectedItem.value) {
                0 -> HomeContent(navController = navController) // FIX 1: Pass navController here
                1 -> CategoryContent(navController = navController)
                2 -> CartContent()
                else -> HomeContent(navController = navController)
            }
        }
    }
}

/* ================= HOME CONTENT ================= */
@Composable
fun HomeContent(navController: NavHostController) { // FIX 2: Add navController parameter
    Scaffold(
        topBar = { TopBar(navController = navController) } // FIX 3: Pass navController here
    ) { padding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.White)
                .padding(padding)
        ) {
            item { TopSearchBar() }
            item { SliderWithCategories() }
            item { PanjabiSection() }
            item { Spacer(modifier = Modifier.height(24.dp)) }
        }
    }
}

/* ================= CATEGORY CONTENT ================= */
@Composable
fun CategoryContent(modifier: Modifier = Modifier, navController: NavHostController) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Add button to Category screen too
        Button(
            onClick = {
                // Add navigation when button is clicked
                navController.navigate("login")
            },
            modifier = Modifier.padding(top = 32.dp)
        ) {
            Text("HI Guest")
        }

        Text(
            text = "Category Screen",
            modifier = Modifier.padding(top = 32.dp)
        )
        Text(
            text = "Browse our products",
            modifier = Modifier.padding(top = 16.dp)
        )
    }
}

/* ================= CART CONTENT ================= */
@Composable
fun CartContent() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Add button to Cart screen too
        Button(
            onClick = {
                // Add your cart logic here
            },
            modifier = Modifier.padding(top = 32.dp)
        ) {
            Text("HI Guest")
        }

        Text(
            text = "🛒 Cart Screen",
            modifier = Modifier.padding(top = 32.dp)
        )
        Text(
            text = "Your cart is empty",
            modifier = Modifier.padding(top = 16.dp)
        )
    }
}

/* ================= TOP BAR ================= */
@Composable
fun TopBar(navController: NavHostController) { // FIX 4: Add navController parameter
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.White)
            .statusBarsPadding()
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(Icons.Default.Menu, null)
        Spacer(modifier = Modifier.width(12.dp))

        Text(
            text = "FABRILIFE",
            fontSize = 22.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.weight(1f)
        )

        // Make "Hi, Guest" clickable
        Text(
            text = "Hi, Guest",
            fontSize = 14.sp,
            modifier = Modifier
                .clickable {
                    // Navigate to login when clicked
                    navController.navigate("login")
                }
                .padding(horizontal = 8.dp, vertical = 4.dp)
        )

        Spacer(modifier = Modifier.width(6.dp))
        Icon(
            imageVector = Icons.Default.AccountCircle,
            contentDescription = "Account",
            modifier = Modifier.clickable {
                // Also make icon clickable
                navController.navigate("login")
            }
        )
    }
}

/* ================= SEARCH BAR ================= */
@Composable
fun TopSearchBar() {
    OutlinedTextField(
        value = "",
        onValueChange = {},
        placeholder = { Text("Search for products") },
        leadingIcon = { Icon(Icons.Default.Search, null) },
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        shape = RoundedCornerShape(14.dp)
    )
}

/* ================= SLIDER + CATEGORY ================= */
@Composable
fun SliderWithCategories() {
    Column {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(320.dp)
        ) {
            HomeSlider()

            CategoryGrid(
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .offset(y = 60.dp)
            )
        }

        // Reserve space so next section starts AFTER category
        Spacer(modifier = Modifier.height(160.dp))
    }
}

/* ================= SLIDER ================= */
@Composable
fun HomeSlider() {
    val slides = listOf("FREE DELIVERY", "WINTER COLLECTION", "MEGA SALE")
    val pagerState = rememberPagerState { slides.size }

    LaunchedEffect(Unit) {
        while (true) {
            delay(3000)
            pagerState.animateScrollToPage(
                (pagerState.currentPage + 1) % slides.size
            )
        }
    }

    HorizontalPager(
        state = pagerState,
        modifier = Modifier
            .fillMaxWidth()
            .height(320.dp)
            .padding(horizontal = 15.dp)
    ) { page ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.Black),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = slides[page],
                color = Color(0xFFFFC107),
                fontSize = 26.sp,
                fontWeight = FontWeight.Bold
            )
        }
    }
}

/* ================= CATEGORY GRID ================= */
@Composable
fun CategoryGrid(modifier: Modifier = Modifier) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(4),
        userScrollEnabled = false,
        modifier = modifier
            .padding(horizontal = 12.dp)
            .height(220.dp)
    ) {
        item { CategoryCard("T-shirt", Icons.Default.Checkroom, Color(0xFF1F1F1F)) }
        item { CategoryCard("Polo", Icons.Default.ShoppingBag, Color(0xFF1F1F1F)) }
        item { CategoryCard("Kids Polo", Icons.Default.Face, Color(0xFF1F1F1F)) }
        item { CategoryCard("Jeans", Icons.Default.Inventory, Color(0xFF1F1F1F)) }

        item { CategoryCard("Kurti", Icons.Default.Style, Color(0xFF1F1F1F)) }
        item { CategoryCard("New Arrival", Icons.Default.NewReleases, Color(0xFF3E8E5A)) }
        item { CategoryCard("Free Delivery", Icons.Default.LocalShipping, Color(0xFF3F51B5)) }
        item { CategoryCard("Offers", Icons.Default.Percent, Color(0xFFE64A19)) }
    }
}

/* ================= CATEGORY CARD ================= */
@Composable
fun CategoryCard(
    title: String,
    icon: ImageVector,
    bgColor: Color
) {
    Box(
        modifier = Modifier
            .size(90.dp, 110.dp)
            .padding(8.dp)
            .background(bgColor, RoundedCornerShape(22.dp))
            .clickable { }
            .padding(12.dp)
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Icon(
                imageVector = icon,
                contentDescription = title,
                tint = Color(0xFF7DE3F4),
                modifier = Modifier.size(36.dp)
            )

            //  Spacer(modifier = Modifier.weight(1f))

            Text(
                text = title,
                color = Color.White,
                fontSize = 13.sp,
                fontWeight = FontWeight.Medium
            )
        }
    }
}

/* ================= PANJABI SECTION ================= */
@Composable
fun PanjabiSection() {
    Column {
        Text(
            text = "Panjabi",
            fontSize = 22.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(16.dp)
        )

        // 🔥 PANJABI BANNER
        PanjabiBanner()

        Spacer(modifier = Modifier.height(16.dp))

        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            userScrollEnabled = false,
            modifier = Modifier
                .fillMaxWidth()
                .height(700.dp)
                .padding(horizontal = 12.dp)
        ) {
            item { PanjabiCard("Mens Premium Panjabi - Sahar", "৳3150.00", "৳4000.00", "21% off") }
            item { PanjabiCard("Mens Premium Panjabi - Afnahar", "৳1450.00", "৳1700.00", "15% off") }
            item { PanjabiCard("Mens Premium Panjabi - Noor", "৳2550.00", "৳3200.00", "19% off") }
            item { PanjabiCard("Mens Premium Panjabi - Ayaan", "৳1990.00", "৳2750.00", "28% off") }
        }
    }
}

/* ================= PANJABI BANNER ================= */
@Composable
fun PanjabiBanner() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(260.dp)
            .padding(horizontal = 16.dp)
            .background(Color(0xFFE0E0E0), RoundedCornerShape(20.dp)),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = "Panjabi Collection",
            fontSize = 26.sp,
            fontWeight = FontWeight.Bold
        )
    }
}

/* ================= PANJABI CARD ================= */
@Composable
fun PanjabiCard(
    title: String,
    price: String,
    oldPrice: String,
    discount: String
) {
    Box(
        modifier = Modifier
            .padding(8.dp)
            .background(Color.White, RoundedCornerShape(16.dp))
            .clickable { }
    ) {
        Column {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp)
                    .background(
                        Color(0xFFE0E0E0),
                        RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp)
                    )
            )

            Column(modifier = Modifier.padding(12.dp)) {
                Text(title, fontSize = 14.sp, fontWeight = FontWeight.Medium, maxLines = 2)

                Spacer(modifier = Modifier.height(6.dp))

                Box(
                    modifier = Modifier
                        .background(Color.Black, RoundedCornerShape(8.dp))
                        .padding(horizontal = 10.dp, vertical = 4.dp)
                ) {
                    Text("Free Shipping", color = Color.White, fontSize = 12.sp)
                }

                Spacer(modifier = Modifier.height(8.dp))

                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(price, fontWeight = FontWeight.Bold, fontSize = 15.sp)

                    Spacer(modifier = Modifier.width(8.dp))

                    Text(
                        oldPrice,
                        fontSize = 13.sp,
                        color = Color.Gray,
                        textDecoration = TextDecoration.LineThrough
                    )
                }
            }
        }

        Box(
            modifier = Modifier
                .align(Alignment.TopEnd)
                .padding(8.dp)
                .background(Color.Black, RoundedCornerShape(12.dp))
                .padding(horizontal = 10.dp, vertical = 4.dp)
        ) {
            Text(discount, color = Color.White, fontSize = 12.sp, fontWeight = FontWeight.Bold)
        }
    }
}