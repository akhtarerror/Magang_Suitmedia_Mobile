package mber.suitmedia.myapplication.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import mber.suitmedia.myapplication.data.User
import mber.suitmedia.myapplication.network.ApiClient
import mber.suitmedia.myapplication.ui.components.UserItem
import mber.suitmedia.myapplication.ui.theme.MyApplicationTheme
import mber.suitmedia.myapplication.ui.theme.OrangeGradientBrush
import mber.suitmedia.myapplication.ui.theme.OrangeGradientHorizontalBrush

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ThirdScreen(
    navController: NavHostController,
    onUserSelected: (String) -> Unit
) {
    val scope = rememberCoroutineScope()
    val listState = rememberLazyListState()

    var users by remember { mutableStateOf<List<User>>(emptyList()) }
    var isLoading by remember { mutableStateOf(false) }
    var isRefreshing by remember { mutableStateOf(false) }
    var currentPage by remember { mutableStateOf(1) }
    var totalPages by remember { mutableStateOf(1) }
    var hasError by remember { mutableStateOf(false) }

    suspend fun loadUsers(page: Int, isRefresh: Boolean = false) {
        if (isRefresh) {
            isRefreshing = true
            currentPage = 1
        } else {
            isLoading = true
        }

        try {
            val response = ApiClient.apiService.getUsers(page)
            if (response.isSuccessful) {
                response.body()?.let { userResponse ->
                    if (isRefresh) {
                        users = userResponse.data
                    } else {
                        users = users + userResponse.data
                    }
                    totalPages = userResponse.total_pages
                    hasError = false
                }
            } else {
                hasError = true
            }
        } catch (e: Exception) {
            hasError = true
        } finally {
            isLoading = false
            isRefreshing = false
        }
    }

    LaunchedEffect(Unit) {
        loadUsers(1, true)
    }

    LaunchedEffect(listState) {
        snapshotFlow { listState.layoutInfo.visibleItemsInfo.lastOrNull()?.index }
            .collect { lastVisibleIndex ->
                if (lastVisibleIndex != null &&
                    lastVisibleIndex >= users.size - 1 &&
                    !isLoading &&
                    !isRefreshing &&
                    currentPage < totalPages) {
                    currentPage++
                    loadUsers(currentPage)
                }
            }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(OrangeGradientBrush)
    ) {
        Scaffold(
            containerColor = Color.Transparent,
            topBar = {
                TopAppBar(
                    title = {
                        Text(
                            "Third Screen",
                            color = Color.White,
                            fontWeight = FontWeight.Bold
                        )
                    },
                    navigationIcon = {
                        IconButton(onClick = { navController.popBackStack() }) {
                            Icon(
                                Icons.Default.ArrowBack,
                                contentDescription = "Back",
                                tint = Color.White
                            )
                        }
                    },
                    colors = TopAppBarDefaults.topAppBarColors(
                        containerColor = Color.Transparent
                    )
                )
            }
        ) { paddingValues ->
            if (users.isEmpty() && !isLoading && !isRefreshing) {
                // Empty state
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(paddingValues),
                    contentAlignment = Alignment.Center
                ) {
                    Card(
                        shape = RoundedCornerShape(16.dp),
                        colors = CardDefaults.cardColors(
                            containerColor = Color.White.copy(alpha = 0.95f)
                        ),
                        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
                    ) {
                        Column(
                            modifier = Modifier.padding(32.dp),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Text(
                                text = if (hasError) "Failed to load users" else "No users found",
                                fontSize = 18.sp,
                                color = Color(0xFF666666),
                                textAlign = TextAlign.Center
                            )
                            if (hasError) {
                                Spacer(modifier = Modifier.height(16.dp))
                                Button(
                                    onClick = {
                                        scope.launch {
                                            loadUsers(1, true)
                                        }
                                    },
                                    shape = RoundedCornerShape(12.dp),
                                    colors = ButtonDefaults.buttonColors(
                                        containerColor = Color.Transparent
                                    )
                                ) {
                                    Box(
                                        modifier = Modifier
                                            .background(OrangeGradientHorizontalBrush, RoundedCornerShape(12.dp))
                                            .padding(horizontal = 16.dp, vertical = 8.dp)
                                    ) {
                                        Text(
                                            "Retry",
                                            color = Color.White,
                                            fontWeight = FontWeight.Bold
                                        )
                                    }
                                }
                            }
                        }
                    }
                }
            } else {
                LazyColumn(
                    state = listState,
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(paddingValues),
                    contentPadding = PaddingValues(16.dp)
                ) {
                    items(users) { user ->
                        UserItem(
                            user = user,
                            onClick = {
                                onUserSelected("${user.first_name} ${user.last_name}")
                                navController.popBackStack()
                            }
                        )
                    }

                    if (isLoading) {
                        item {
                            Box(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(16.dp),
                                contentAlignment = Alignment.Center
                            ) {
                                CircularProgressIndicator(
                                    color = Color.White
                                )
                            }
                        }
                    }
                }
            }
        }
    }

    // Pull to refresh
    if (isRefreshing) {
        LaunchedEffect(Unit) {
            delay(100)
        }
    }
}

@Preview(showBackground = true)
@Composable
fun UserItemPreview() {
    MyApplicationTheme {
        UserItem(
            user = User(
                id = 1,
                email = "john.doe@example.com",
                first_name = "John",
                last_name = "Doe",
                avatar = "https://reqres.in/img/faces/1-image.jpg"
            ),
            onClick = { }
        )
    }
}