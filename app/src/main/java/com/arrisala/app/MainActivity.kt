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
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

private val DeepGreen = Color(0xFF103D32)
private val Forest = Color(0xFF1D5948)
private val Gold = Color(0xFFE0BF75)
private val Cream = Color(0xFFF7F3E9)
private val Ink = Color(0xFF26332E)

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
                Column(
                    Modifier.fillMaxWidth().background(Brush.verticalGradient(listOf(Color(0xFF092B25), DeepGreen, Forest))).padding(horizontal = 22.dp, vertical = 28.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text("☾  ✦  ☾", color = Gold, fontSize = 27.sp)
                    Spacer(Modifier.height(8.dp))
                    Text("بِسْمِ اللَّهِ الرَّحْمَٰنِ الرَّحِيمِ", color = Gold, fontSize = 23.sp, textAlign = TextAlign.Center)
                    Spacer(Modifier.height(14.dp))
                    Text("AR-RISALA", color = Color.White, fontSize = 31.sp, fontWeight = FontWeight.Bold, letterSpacing = 2.sp)
                    Text("KNOWLEDGE  •  FAITH  •  GUIDANCE", color = Color(0xFFE8E2D5), fontSize = 11.sp, letterSpacing = 1.sp)
                    Spacer(Modifier.height(12.dp))
                    Text("A little light for the journey of faith", color = Color.White.copy(alpha = 0.88f), fontSize = 13.sp)
                }
                Column(Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(13.dp)) {
                    Text("Assalamu Alaikum", color = DeepGreen, fontSize = 24.sp, fontWeight = FontWeight.Bold)
                    Text("Welcome to AR-RISALA. Explore Quran, authentic Hadith, duas and Islamic learning.", color = Ink, lineHeight = 22.sp)
                    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                        listOf("Home", "Quran", "Hadith", "Dua", "Learn Islam", "About").chunked(2).forEach { pair ->
                            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                                pair.forEach { item ->
                                    Button(
                                        onClick = { page = item },
                                        modifier = Modifier.weight(1f).height(48.dp),
                                        colors = ButtonDefaults.buttonColors(
                                            containerColor = if (page == item) DeepGreen else Color(0xFFE7E0D1),
                                            contentColor = if (page == item) Color.White else DeepGreen
                                        ),
                                        shape = RoundedCornerShape(13.dp),
                                        contentPadding = PaddingValues(horizontal = 8.dp)
                                    ) { Text(item, fontWeight = FontWeight.SemiBold) }
                                }
                            }
                        }
                    }
                    when (page) {
                        "Quran" -> QuranSection()
                        "Hadith" -> HadithSection()
                        "Dua" -> DuaSection()
                        "Learn Islam" -> LearnSection()
                        "About" -> InfoCard("About AR-RISALA", "AR-RISALA is an Islamic learning app created to make Quran guidance, Hadith and Islamic knowledge easier to explore.\n\nOwner & Creator: Abdirahman Jarso (Abjata)\n\nKnowledge • Faith • Guidance.")
                        else -> InfoCard("Begin your journey", "Choose a section above to start reading. May Allah grant us beneficial knowledge, sincerity and good character.")
                    }
                    Text("May Allah bless your search for beneficial knowledge.", color = DeepGreen, textAlign = TextAlign.Center, modifier = Modifier.fillMaxWidth().padding(top = 8.dp))
                    Text("JazakAllahu Khairan", color = Color.Gray, fontSize = 12.sp, modifier = Modifier.align(Alignment.CenterHorizontally))
                }
            }
        }
    }
}

@Composable
fun QuranSection() {
    Text("THE HOLY QURAN", color = DeepGreen, fontSize = 21.sp, fontWeight = FontWeight.Bold)
    Text("Selected short surahs • Arabic • English meaning • Kiswahili meaning", color = Color.Gray, fontSize = 12.sp)
    InfoCard("1. Al-Fatihah • The Opening", "بِسْمِ اللَّهِ الرَّحْمَٰنِ الرَّحِيمِ\nالْحَمْدُ لِلَّهِ رَبِّ الْعَالَمِينَ\nالرَّحْمَٰنِ الرَّحِيمِ\nمَالِكِ يَوْمِ الدِّينِ\nإِيَّاكَ نَعْبُدُ وَإِيَّاكَ نَسْتَعِينُ\nاهْدِنَا الصِّرَاطَ الْمُسْتَقِيمَ\nصِرَاطَ الَّذِينَ أَنْعَمْتَ عَلَيْهِمْ غَيْرِ الْمَغْضُوبِ عَلَيْهِمْ وَلَا الضَّالِّينَ\n\nEnglish meaning: In the name of Allah, the Most Merciful, the Especially Merciful. All praise is for Allah, Lord of all worlds; the Most Merciful, the Especially Merciful; Master of the Day of Judgment. You alone we worship and You alone we ask for help. Guide us to the straight path—the path of those You have blessed, not those who earned anger, nor those who went astray.\n\nKiswahili: Kwa jina la Allah, Mwingi wa rehema, Mwenye kurehemu. Sifa njema zote ni za Allah, Mola wa walimwengu. Mwingi wa rehema, Mwenye kurehemu. Mfalme wa Siku ya Malipo. Wewe tu tunakuabudu na Wewe tu tunakuomba msaada. Tuongoze njia iliyonyooka.")
    InfoCard("112. Al-Ikhlas • Sincerity", "قُلْ هُوَ اللَّهُ أَحَدٌ\nاللَّهُ الصَّمَدُ\nلَمْ يَلِدْ وَلَمْ يُولَدْ\nوَلَمْ يَكُن لَّهُ كُفُوًا أَحَدٌ\n\nEnglish meaning: Say: He is Allah, the One; Allah, the Eternal Refuge. He neither begets nor is born, and there is none comparable to Him.\n\nKiswahili: Sema: Yeye ndiye Allah, Mmoja. Allah, Mkusudiwa na wote. Hakuzaa wala hakuzaliwa, wala hana yeyote anayefanana naye.")
    InfoCard("113. Al-Falaq • The Daybreak", "قُلْ أَعُوذُ بِرَبِّ الْفَلَقِ\nمِن شَرِّ مَا خَلَقَ\nوَمِن شَرِّ غَاسِقٍ إِذَا وَقَبَ\nوَمِن شَرِّ النَّفَّاثَاتِ فِي الْعُقَدِ\nوَمِن شَرِّ حَاسِدٍ إِذَا حَسَدَ\n\nEnglish meaning: Say: I seek refuge in the Lord of daybreak, from the evil of what He created, from the evil of darkness when it settles, from the evil of those who blow on knots, and from the evil of an envier when they envy.\n\nKiswahili: Sema: Najikinga kwa Mola wa mapambazuko, kutokana na shari ya alivyoviumba, na shari ya giza linapoingia, na shari ya wanaopuliza mafundo, na shari ya hasidi anapohusudu.")
    InfoCard("114. An-Nas • Mankind", "قُلْ أَعُوذُ بِرَبِّ النَّاسِ\nمَلِكِ النَّاسِ\nإِلَٰهِ النَّاسِ\nمِن شَرِّ الْوَسْوَاسِ الْخَنَّاسِ\nالَّذِي يُوَسْوِسُ فِي صُدُورِ النَّاسِ\nمِنَ الْجِنَّةِ وَالنَّاسِ\n\nEnglish meaning: Say: I seek refuge in the Lord of mankind, the King of mankind, the God of mankind, from the evil of the whisperer who withdraws, who whispers into the hearts of mankind, from among jinn and mankind.\n\nKiswahili: Sema: Najikinga kwa Mola wa watu, Mfalme wa watu, Mungu wa watu, kutokana na shari ya mwenye kutia wasiwasi na kujificha, anayenong'ona katika vifua vya watu, awe ni katika majini au watu.")
    Text("These are selected surahs for study, not a complete digital Quran. Please consult a trusted mushaf or qualified teacher to verify recitation and tajwid.", color = Color.Gray, fontSize = 12.sp, lineHeight = 18.sp)
}

@Composable
fun HadithSection() {
    Text("AUTHENTIC HADITH", color = DeepGreen, fontSize = 21.sp, fontWeight = FontWeight.Bold)
    Text("Short selections with collection references", color = Color.Gray, fontSize = 12.sp)
    HadithCard("1. Actions are judged by intentions", "The reward of deeds depends upon the intentions, and every person will get the reward according to what he has intended.", "Hakika matendo hulipwa kwa nia, na kila mtu atapata kwa alichonuia.", "Sahih al-Bukhari 1; Sahih Muslim 1907")
    HadithCard("2. Love for your brother", "None of you truly believes until he loves for his brother what he loves for himself.", "Hawi mmoja wenu na imani kamili mpaka ampende ndugu yake kile anachojipendelea mwenyewe.", "Sahih al-Bukhari 13; Sahih Muslim 45")
    HadithCard("3. Speak good or remain silent", "Whoever believes in Allah and the Last Day should speak good or remain silent.", "Mwenye kumuamini Allah na Siku ya Mwisho aseme mema au anyamaze.", "Sahih al-Bukhari 6018; Sahih Muslim 47")
    HadithCard("4. Mercy", "Those who show mercy will be shown mercy by the Most Merciful. Show mercy to those on earth, and the One above the heavens will show mercy to you.", "Wenye kurehemu watarehemewa na Ar-Rahman. Warehemuni walioko ardhini, atakurehemuni Aliye juu ya mbingu.", "Jami' at-Tirmidhi 1924")
    HadithCard("5. Seeking knowledge", "Whoever follows a path in pursuit of knowledge, Allah will make easy for him a path to Paradise.", "Mwenye kufuata njia kutafuta elimu, Allah humfanyia wepesi njia ya Peponi.", "Sahih Muslim 2699")
}

@Composable
fun DuaSection() {
    Text("DAILY DUAS", color = DeepGreen, fontSize = 21.sp, fontWeight = FontWeight.Bold)
    InfoCard("When beginning an action", "بِسْمِ اللَّهِ\nBismillah\nKwa jina la Allah.")
    InfoCard("Seeking beneficial knowledge", "رَبِّ زِدْنِي عِلْمًا\nRabbi zidni ilma.\nMy Lord, increase me in knowledge. (Quran 20:114)\nMola wangu! Nizidishie elimu.")
    InfoCard("For goodness in this life and the next", "رَبَّنَا آتِنَا فِي الدُّنْيَا حَسَنَةً وَفِي الْآخِرَةِ حَسَنَةً وَقِنَا عَذَابَ النَّارِ\nRabbana atina fid-dunya hasanatan wa fil-akhirati hasanatan wa qina adhaban-nar.\nOur Lord, give us good in this world and good in the Hereafter, and protect us from the punishment of the Fire. (Quran 2:201)")
}

@Composable
fun LearnSection() {
    Text("LEARN ISLAM", color = DeepGreen, fontSize = 21.sp, fontWeight = FontWeight.Bold)
    InfoCard("The Five Pillars of Islam", "1. Shahadah — testimony of faith.\n2. Salah — the five daily prayers.\n3. Zakah — obligatory charity for those who meet its conditions.\n4. Sawm — fasting in Ramadan.\n5. Hajj — pilgrimage to Makkah for those able to undertake it.")
    InfoCard("The Six Articles of Faith", "Belief in Allah, His angels, His revealed books, His messengers, the Last Day, and divine decree (Al-Qadar), its good and its difficult aspects.")
    InfoCard("Good character", "Be truthful, keep trusts, show patience, respect parents, care for neighbours, give charity, and treat people with justice and mercy.")
}

@Composable
fun InfoCard(title: String, body: String) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        shape = RoundedCornerShape(18.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(Modifier.padding(17.dp)) {
            Text(title, color = DeepGreen, fontSize = 18.sp, fontWeight = FontWeight.Bold)
            Spacer(Modifier.height(9.dp))
            Text(body, color = Ink, lineHeight = 23.sp, fontSize = 14.sp)
        }
    }
}

@Composable
fun HadithCard(title: String, english: String, kiswahili: String, source: String) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        shape = RoundedCornerShape(18.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(Modifier.padding(17.dp)) {
            Text(title, color = DeepGreen, fontSize = 18.sp, fontWeight = FontWeight.Bold)
            Spacer(Modifier.height(10.dp))
            Text("ENGLISH", color = Forest, fontWeight = FontWeight.Bold, fontSize = 11.sp)
            Text(english, color = Ink, lineHeight = 22.sp)
            Spacer(Modifier.height(9.dp))
            Text("KISWAHILI", color = Forest, fontWeight = FontWeight.Bold, fontSize = 11.sp)
            Text(kiswahili, color = Ink, lineHeight = 22.sp)
            Spacer(Modifier.height(9.dp))
            Text("Source: $source", color = Color.Gray, fontSize = 11.sp)
        }
    }
}
