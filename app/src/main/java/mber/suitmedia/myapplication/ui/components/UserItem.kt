package mber.suitmedia.myapplication.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import mber.suitmedia.myapplication.data.User
import mber.suitmedia.myapplication.ui.theme.MyApplicationTheme
import mber.suitmedia.myapplication.ui.theme.OrangeGradientBrush

@Composable
fun UserItem(
    user: User,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 6.dp),
        onClick = onClick,
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color.White.copy(alpha = 0.95f)
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 6.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(70.dp)
                    .background(OrangeGradientBrush, CircleShape)
                    .padding(3.dp)
            ) {
                AsyncImage(
                    model = ImageRequest.Builder(LocalContext.current)
                        .data(user.avatar)
                        .crossfade(true)
                        .build(),
                    contentDescription = "User Avatar",
                    modifier = Modifier
                        .fillMaxSize()
                        .clip(CircleShape),
                    contentScale = ContentScale.Crop
                )
            }

            Spacer(modifier = Modifier.width(20.dp))

            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = "${user.first_name} ${user.last_name}",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF333333)
                )

                Spacer(modifier = Modifier.height(6.dp))

                Text(
                    text = user.email,
                    fontSize = 14.sp,
                    color = Color(0xFF666666)
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun UserItemListPreview() {
    MyApplicationTheme {
        Column {
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
            UserItem(
                user = User(
                    id = 2,
                    email = "jane.smith@example.com",
                    first_name = "Jane",
                    last_name = "Smith",
                    avatar = "https://reqres.in/img/faces/2-image.jpg"
                ),
                onClick = { }
            )
            UserItem(
                user = User(
                    id = 3,
                    email = "mike.johnson@example.com",
                    first_name = "Mike",
                    last_name = "Johnson",
                    avatar = "https://reqres.in/img/faces/3-image.jpg"
                ),
                onClick = { }
            )
        }
    }
}
