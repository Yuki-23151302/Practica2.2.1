package com.example.practica221

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
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
import com.example.practica221.ui.theme.Practica221Theme
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
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

    val intentContext = LocalContext.current
    val snackbarHostState = remember { SnackbarHostState() }
    val coroutineScope = rememberCoroutineScope()

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
                    IconButton(
                        onClick = {
                            Toast.makeText(
                                intentContext,
                                "Botón de regreso",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    ) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Regresar",
                            tint = Color.White
                        )
                    }
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

@Preview(
    showBackground = true,
    locale = "es-rMX"
)
@Composable
fun ContentViewPreview() {
    Practica221Theme {
        ContentView()
    }
}

@Preview(
    showBackground = true,
    widthDp = 800,
    heightDp = 400
)
@Composable
fun ContentViewLandscapePreview() {
    Practica221Theme {
        ContentView()
    }
}