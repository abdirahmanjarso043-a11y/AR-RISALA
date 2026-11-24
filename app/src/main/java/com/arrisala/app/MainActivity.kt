package com.arrisala.app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

private val DeepGreen = Color(0xFF103D32)
private val Gold = Color(0xFFD6B46A)
private val Cream = Color(0xFFF7F5EF)

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent { RisalaApp() }
    }
}

@Composable
fun RisalaApp() {
    var page by remember { mutableStateOf("Home") }
    MaterialTheme(colorScheme = lightColorScheme(primary = DeepGreen, secondary = Gold, background = Cream, surface = Color.White)) {
        Scaffold(containerColor = Cream) { padding ->
            Column(Modifier.fillMaxSize().padding(padding).verticalScroll(rememberScrollState())) {
                Column(Modifier.fillMaxWidth().background(DeepGreen).padding(24.dp), horizontalAlignment = Alignment.CenterHorizontally) {
                    Text("بِسْمِ اللَّهِ الرَّحْمَٰنِ الرَّحِيمِ", color = Gold, fontSize = 23.sp)
                    Spacer(Modifier.height(12.dp))
                    Text("AR-RISALA", color = Color.White, fontSize = 30.sp, fontWeight = FontWeight.Bold)
                    Text("Knowledge • Faith • Guidance", color = Color(0xFFE8E2D5), fontSize = 14.sp)
                }
                Column(Modifier.padding(18.dp), verticalArrangement = Arrangement.spacedBy(12.dp)) {
                    Text("Assalamu Alaikum", color = DeepGreen, fontSize = 24.sp, fontWeight = FontWeight.Bold)
                    Text("Welcome. Explore Islamic learning with care and authentic references.", color = Color.DarkGray)
                    listOf("Home", "Quran", "Hadith", "Learn Islam", "About").chunked(2).forEach { pair ->
                        Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                            pair.forEach { item ->
                                Button(onClick = { page = item }, modifier = Modifier.weight(1f).height(52.dp),
                                    colors = ButtonDefaults.buttonColors(containerColor = if (page == item) DeepGreen else Color(0xFFE6E1D5), contentColor = if (page == item) Color.White else DeepGreen),
                                    shape = RoundedCornerShape(14.dp)) { Text(item, fontWeight = FontWeight.SemiBold) }
                            }
                            if (pair.size == 1) Spacer(Modifier.weight(1f))
                        }
                    }
                    when (page) {
                        "Quran" -> InfoCard("The Holy Quran", "Read, reflect and learn from the words of Allah. Quran text and translations will be added with verified sources in a future update.")
                        "Hadith" -> {
                            HadithCard("Hadith 1 — Actions are judged by intentions", "Actions are judged by intentions.", "Matendo huzingatiwa kwa nia.", "Sahih al-Bukhari 1; Sahih Muslim 1907")
                            HadithCard("Hadith 2 — Good character", "The best among you are those who have the best character.", "Mbora wenu ni yule mwenye tabia njema zaidi.", "Sahih al-Bukhari 3559; Sahih Muslim 2321")
                        }
                        "Learn Islam" -> InfoCard("Learn Islam", "Explore the foundations of faith, prayer, good character, the life of Prophet Muhammad (peace be upon him), and everyday Islamic manners. Content will be expanded with references.")
                        "About" -> InfoCard("About AR-RISALA", "AR-RISALA is an Islamic learning app created to make Quran guidance, Hadith and Islamic knowledge easier to explore. Knowledge • Faith • Guidance.")
                        else -> InfoCard("Begin your journey", "Choose Quran, Hadith or Learn Islam above. May Allah grant us beneficial knowledge.")
                    }
                    Text("May Allah bless your search for beneficial knowledge.", color = DeepGreen, textAlign = TextAlign.Center, modifier = Modifier.fillMaxWidth().padding(top = 10.dp))
                    Text("JazakAllahu Khairan", color = Color.Gray, fontSize = 12.sp, modifier = Modifier.align(Alignment.CenterHorizontally))
                }
            }
        }
    }
}

@Composable
fun InfoCard(title: String, body: String) {
    Card(colors = CardDefaults.cardColors(containerColor = Color.White), shape = RoundedCornerShape(18.dp)) {
        Column(Modifier.padding(18.dp)) {
            Text(title, color = DeepGreen, fontSize = 20.sp, fontWeight = FontWeight.Bold)
            Spacer(Modifier.height(8.dp))
            Text(body, color = Color.DarkGray, lineHeight = 23.sp)
        }
    }
}

@Composable
fun HadithCard(title: String, english: String, kiswahili: String, source: String) {
    Card(colors = CardDefaults.cardColors(containerColor = Color.White), shape = RoundedCornerShape(18.dp)) {
        Column(Modifier.padding(18.dp)) {
            Text(title, color = DeepGreen, fontSize = 18.sp, fontWeight = FontWeight.Bold)
            Spacer(Modifier.height(12.dp))
            Text("English", color = Gold, fontWeight = FontWeight.Bold)
            Text(english, color = Color.DarkGray, lineHeight = 22.sp)
            Spacer(Modifier.height(10.dp))
            Text("Kiswahili", color = Gold, fontWeight = FontWeight.Bold)
            Text(kiswahili, color = Color.DarkGray, lineHeight = 22.sp)
            Spacer(Modifier.height(10.dp))
            Text("Source: $source", color = Color.Gray, fontSize = 12.sp)
        }
    }
}
