package com.example.assignment3

import android.content.Context
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.assignment3.ui.theme.Assignment3Theme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Assignment3Theme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    App(applicationContext)
//                    MainScreen(applicationContext = applicationContext)
//                    Greeting("Android")
                }
            }
        }
    }
}

@Composable
fun App(applicationContext: Context) {
    val navController = rememberNavController()
    NavHost(
        navController = navController,
        startDestination = "home"
    ) {
        composable("home"){
            MainScreen(navController = navController,"")
        }
        composable("home/{data}") {backStackEntry ->
            val data = backStackEntry.arguments?.getString("data")
            MainScreen(navController = navController, data = data)

        }
        composable("detail/{name}/{phone}/{password}"){
            backStackEntry ->
            val name = backStackEntry.arguments?.getString("name")
            val phone = backStackEntry.arguments?.getString("phone")
            val password = backStackEntry.arguments?.getString("password")
            AboutScreen(name = name,phone = phone, password = password,navController = navController)
        }
        composable("media"){
            MediaPlayer(applicationContext = applicationContext,navController)
        }
        composable("pass"){
            DataPass(navController = navController)
        }
    }
}
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(navController: NavController, data: String?) {
    val mContext = LocalContext.current
    var email = remember {
        mutableStateOf("")
    }
    var phone = remember {
        mutableStateOf("")
    }
    var password = remember {
        mutableStateOf("")
    }
    var bool = remember {
        mutableStateOf(false)
    }

    Column(
        Modifier
            .fillMaxSize()
            .padding(all = 20.dp),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        OutlinedTextField(
            value = email.value,
            onValueChange = {
                email.value = it
            },
            modifier = Modifier.padding(vertical = 20.dp),
            placeholder = { Text(text = "Name") },
        )
        OutlinedTextField(
            value = phone.value,
            onValueChange = {
                phone.value = it
            },
            modifier = Modifier.padding(vertical = 20.dp),
            placeholder = { Text(text = "Phone Number") },
        )
        OutlinedTextField(
            value = password.value,
            onValueChange = {
                password.value = it
            },
            modifier = Modifier.padding(vertical = 20.dp),
            placeholder = { Text(text = "Password") },
        )
        Row(verticalAlignment = Alignment.CenterVertically) {
            Checkbox(
                checked = bool.value,
                onCheckedChange = { checked_ ->
                    bool.value = checked_
                },
                colors = CheckboxDefaults.colors(
                    checkedColor = Color.Green.copy(alpha = 0.2f)
                )
            )
            Text(
                modifier = Modifier.padding(start = 1.dp),
                text = "Agree to terms and condition",
                fontSize = 14.sp

            )
        }
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 20.dp, horizontal = 5.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Button(
                onClick = {
                    navController.navigate("detail/${email.value}/${phone.value}/${password.value}")
                },
                enabled = bool.value,
                shape = RoundedCornerShape(8.dp),
            ) {
                Text(text = "Show")
            }
            Button(
                onClick = {
                    navController.navigate("media")
                },
                enabled = bool.value,
                shape = RoundedCornerShape(8.dp),
            ) {
                Text(text = "Media")
            }
            Button(
                onClick = {
                          navController.navigate("pass")
                },
                enabled = bool.value,
                shape = RoundedCornerShape(8.dp),
            ) {
                Text(text = "Data Pass")
            }
        }
        Text(text = "$data")
    }
}
@Composable
fun AboutScreen(name: String?, phone: String?, password: String?,navController: NavController) {

    Column(Modifier.fillMaxSize(), horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.Top) {
        LazyColumn(Modifier.padding(all = 30.dp), horizontalAlignment = Alignment.CenterHorizontally) {
            // Add a single item
            item {
                Text(text = "Email : $name")
            }

            // Add 5 items
            item {
                Text(text = "Phone : $phone")
            }

            // Add another single item
            item {
                Text(text = "Password : $password")
            }
        }
        Button(onClick = {
            navController.navigate("home")
        }) {
            Icon(painter = painterResource(id = R.drawable.baseline_arrow_back_24), contentDescription = "")
            Text(text = "Back")
        }
    }

}

@OptIn(ExperimentalMaterial3Api::class)
@Composable 
fun DataPass(navController: NavController){
    var data = remember {
        mutableStateOf("")
    }
    Column(Modifier.padding(all = 20.dp), horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.Top) {
        
        OutlinedTextField(
            value = data.value,
            onValueChange = {
                data.value = it
            },
            modifier = Modifier.padding(vertical = 20.dp),
            placeholder = { Text(text = "Name") },
        )
        Button(onClick = {
            navController.navigate("home/${data.value}")
        }) {
            Icon(painter = painterResource(id = R.drawable.baseline_arrow_back_24), contentDescription = "")
            Text(text = "Back")
        }
    }
}
@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}
