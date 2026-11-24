package com.example.practica221

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.CameraAlt
import androidx.compose.material.icons.filled.ExitToApp
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Share
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material.icons.outlined.Send
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.practica221.ui.screens.ProfileActivity
import com.example.practica221.ui.screens.SecondActivity
import com.example.practica221.ui.theme.Practica221Theme
import kotlinx.coroutines.launch
import kotlinx.coroutines.delay
import androidx.lifecycle.lifecycleScope
import androidx.compose.material3.rememberDrawerState

class MainActivity : ComponentActivity() {
    private var keepSplash = true

    override fun onCreate(savedInstanceState: Bundle?) {
        val splashScreen = installSplashScreen()

        splashScreen.setKeepOnScreenCondition { keepSplash }
        lifecycleScope.launch {
            delay(3000)
            keepSplash = false
        }

        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            Practica221Theme {
                ContentView()
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ContentView() {
    var counter by rememberSaveable { mutableIntStateOf(0) }
    var isFavorite by rememberSaveable { mutableStateOf(false) }

    val intentContext = LocalContext.current
    val snackbarHostState = remember { SnackbarHostState() }
    val coroutineScope = rememberCoroutineScope()

    // Configuración para el menú lateral (Navigation Drawer)
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)

    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            ModalDrawerSheet {
                Text(
                    text = "Menú de Navegación",
                    modifier = Modifier.padding(16.dp),
                    fontSize = 20.sp
                )
                HorizontalDivider()
                NavigationDrawerItem(
                    label = { Text("Inicio") },
                    selected = false,
                    onClick = {
                        coroutineScope.launch {
                            drawerState.close()
                        }
                    }
                )
                NavigationDrawerItem(
                    label = { Text("Perfil") },
                    selected = false,
                    onClick = {
                        coroutineScope.launch {
                            drawerState.close()
                        }
                        intentContext.startActivity(
                            Intent(intentContext, ProfileActivity::class.java)
                        )
                    }
                )
            }
        }
    ) {
        Scaffold(
            modifier = Modifier.fillMaxSize(),
            topBar = {
                TopAppBar(
                    title = {
                        Text(
                            text = "Práctica 2.2.1",
                            color = Color.White
                        )
                    },
                    colors = TopAppBarDefaults.topAppBarColors(
                        containerColor = MaterialTheme.colorScheme.secondary,
                        titleContentColor = Color.White
                    ),
                    navigationIcon = {
                        // Botón para abrir el menú lateral (Drawer)
                        IconButton(
                            onClick = {
                                coroutineScope.launch {
                                    drawerState.open()
                                }
                            }
                        ) {
                            Icon(
                                imageVector = Icons.Default.Menu,
                                contentDescription = "Abrir menú",
                                tint = Color.White
                            )
                        }
                    },
                    actions = {
                        // Ícono de Favorito interactivo
                        IconButton(
                            onClick = {
                                isFavorite = !isFavorite
                                val msg = if (isFavorite) "Agregado a favoritos" else "Eliminado de favoritos"
                                Toast.makeText(intentContext, msg, Toast.LENGTH_SHORT).show()
                            }
                        ) {
                            Icon(
                                imageVector = if (isFavorite) Icons.Default.Favorite else Icons.Outlined.FavoriteBorder,
                                contentDescription = "Marcar como favorito",
                                tint = Color.White
                            )
                        }

                        // Ícono de Compartir que redirige a SecondActivity
                        IconButton(
                            onClick = {
                                val intent = Intent(intentContext, SecondActivity::class.java).apply {
                                    putExtra("EXTRA_MESSAGE", "¡Hola desde MainActivity!")
                                }
                                intentContext.startActivity(intent)
                            }
                        ) {
                            Icon(
                                imageVector = Icons.Default.Share,
                                contentDescription = "Compartir",
                                tint = Color.White
                            )
                        }

                        // Menú desplegable con WhatsApp, Instagram y Cámara
                        DropDownMenu(intentContext)
                    }
                )
            },
            bottomBar = {
                BottomAppBar(
                    containerColor = MaterialTheme.colorScheme.secondary,
                    contentColor = MaterialTheme.colorScheme.primary
                ) {
                    Text(
                        modifier = Modifier.fillMaxWidth(),
                        textAlign = TextAlign.Center,
                        text = "Práctica 2.2.1",
                        color = Color.White,
                        fontSize = 18.sp
                    )
                }
            },
            snackbarHost = {
                SnackbarHost(hostState = snackbarHostState)
            },
            floatingActionButton = {
                FloatingActionButton(
                    onClick = {
                        coroutineScope.launch {
                            snackbarHostState.showSnackbar(
                                message = "I am a Snackbar! 🙁",
                                actionLabel = "Do something"
                            )
                        }
                    }
                ) {
                    Text(
                        text = "+",
                        fontSize = 24.sp
                    )
                }
            }
        ) { innerPadding ->
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.Red)
                    .padding(innerPadding),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                item {
                    Image(
                        painter = painterResource(
                            id = R.drawable.android1920x1080
                        ),
                        contentDescription = "Ilustración de Android",
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(200.dp)
                    )

                    androidx.compose.foundation.layout.Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 10.dp),
                        horizontalArrangement = Arrangement.Start
                    ) {
                        Image(
                            painter = painterResource(
                                id = R.drawable.ic_launcher_foreground
                            ),
                            contentDescription = "Like",
                            modifier = Modifier
                                .size(60.dp)
                                .clickable {
                                    counter++
                                }
                                .padding(start = 12.dp)
                        )

                        Text(
                            text = counter.toString(),
                            fontSize = 20.sp,
                            color = Color.White,
                            modifier = Modifier.padding(start = 8.dp)
                        )
                    }
                }

                item {
                    Text(
                        text = stringResource(R.string.materia),
                        fontSize = 24.sp,
                        color = Color.White,
                        modifier = Modifier.fillMaxWidth(),
                        textAlign = TextAlign.Center
                    )
                }

                item {
                    Text(
                        text = stringResource(R.string.nombre),
                        fontSize = 20.sp,
                        color = Color.White,
                        modifier = Modifier.fillMaxWidth(),
                        textAlign = TextAlign.Center
                    )
                }

                item {
                    Text(
                        text = stringResource(R.string.apellido),
                        fontSize = 20.sp,
                        color = Color.White,
                        modifier = Modifier.fillMaxWidth(),
                        textAlign = TextAlign.Center
                    )

                    LazyRow(
                        horizontalArrangement = Arrangement.SpaceBetween,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(8.dp)
                    ) {
                        item {
                            Image(
                                modifier = Modifier
                                    .size(200.dp)
                                    .height(100.dp)
                                    .padding(5.dp),
                                painter = painterResource(
                                    id = R.drawable.mobile_dev1
                                ),
                                contentDescription = "Mobile Apps 1"
                            )
                        }
                        item {
                            Image(
                                modifier = Modifier
                                    .size(200.dp)
                                    .height(100.dp)
                                    .padding(5.dp),
                                painter = painterResource(
                                    id = R.drawable.mobile_dev2
                                ),
                                contentDescription = "Mobile Apps 2"
                            )
                        }
                        item {
                            Image(
                                modifier = Modifier
                                    .size(200.dp)
                                    .height(100.dp)
                                    .padding(5.dp),
                                painter = painterResource(
                                    id = R.drawable.mobile_dev3
                                ),
                                contentDescription = "Mobile Apps 3"
                            )
                        }
                        item {
                            Image(
                                modifier = Modifier
                                    .size(200.dp)
                                    .height(100.dp)
                                    .padding(5.dp),
                                painter = painterResource(
                                    id = R.drawable.mobile_dev4
                                ),
                                contentDescription = "Mobile Apps 4"
                            )
                        }
                        item {
                            Image(
                                modifier = Modifier
                                    .size(200.dp)
                                    .height(100.dp)
                                    .padding(5.dp),
                                painter = painterResource(
                                    id = R.drawable.mobile_dev5
                                ),
                                contentDescription = "Mobile Apps 5"
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun DropDownMenu(context: Context) {
    var expanded by remember { mutableStateOf(false) }
    Box {
        IconButton(onClick = { expanded = true }) {
            Icon(
                imageVector = Icons.Default.MoreVert,
                contentDescription = stringResource(R.string.options_menu),
                tint = Color.White
            )
        }

        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            DropdownMenuItem(
                text = { Text("Abrir WhatsApp") },
                onClick = {
                    expanded = false
                    openWhatsApp(context)
                },
                leadingIcon = {
                    Icon(Icons.Outlined.Send, contentDescription = null)
                }
            )

            DropdownMenuItem(
                text = { Text("Abrir Instagram") },
                onClick = {
                    expanded = false
                    openInstagram(context)
                },
                leadingIcon = {
                    Icon(Icons.Default.Person, contentDescription = null)
                }
            )

            DropdownMenuItem(
                text = { Text("Abrir Cámara") },
                onClick = {
                    expanded = false
                    openCamera(context)
                },
                leadingIcon = {
                    Icon(Icons.Default.CameraAlt, contentDescription = null)
                }
            )
        }
    }
}

fun openWhatsApp(context: Context) {
    try {
        val intent = context.packageManager.getLaunchIntentForPackage("com.whatsapp")
        if (intent != null) {
            context.startActivity(intent)
        } else {
            Toast.makeText(context, "WhatsApp no está instalado", Toast.LENGTH_SHORT).show()
        }
    } catch (e: Exception) {
        Toast.makeText(context, "Error al abrir WhatsApp", Toast.LENGTH_SHORT).show()
    }
}

fun openInstagram(context: Context) {
    try {
        val intent = context.packageManager.getLaunchIntentForPackage("com.instagram.android")
        if (intent != null) {
            context.startActivity(intent)
        } else {
            Toast.makeText(context, "Instagram no está instalado", Toast.LENGTH_SHORT).show()
        }
    } catch (e: Exception) {
        Toast.makeText(context, "Error al abrir Instagram", Toast.LENGTH_SHORT).show()
    }
}

fun openCamera(context: Context) {
    val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
    try {
        context.startActivity(intent)
    } catch (e: Exception) {
        Toast.makeText(context, "No se pudo abrir la cámara", Toast.LENGTH_SHORT).show()
    }
}

@Preview(showBackground = true, locale = "es-rMX")
@Composable
fun ContentViewPreview() {
    Practica221Theme {
        ContentView()
    }
}