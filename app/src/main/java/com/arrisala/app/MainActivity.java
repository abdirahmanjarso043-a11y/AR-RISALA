package com.arrisala.app;

import android.app.Activity;
import android.os.Bundle;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class MainActivity extends Activity {
    private WebView webView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        webView = new WebView(this);
        WebSettings settings = webView.getSettings();
        settings.setJavaScriptEnabled(true);
        settings.setDomStorageEnabled(true);
        settings.setAllowFileAccessFromFileURLs(true);
        settings.setAllowUniversalAccessFromFileURLs(true);
        webView.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                String script =
                    "(function() {" +
                    "var style=document.createElement('style');" +
                    "style.textContent='body{background-color:#eee7d7!important;background-image:radial-gradient(circle at 18px 18px,rgba(190,157,83,.16) 2px,transparent 2.5px),radial-gradient(circle at 0 0,rgba(16,43,36,.06) 1px,transparent 1.5px),linear-gradient(135deg,#f7f2e6,#e9e1ce)!important;background-size:36px 36px,18px 18px,100% 100%!important;background-attachment:fixed!important}header{background:linear-gradient(145deg,#09251f,#174535 58%,#9a793d)!important;border-bottom:3px solid #c7a65a!important} .panel{background:rgba(255,253,248,.96)!important;border-color:#d8c99f!important} .bilingual-card{padding:12px 0;border-bottom:1px solid #e8dfcc}.bilingual-card:last-child{border-bottom:0}.bilingual-label{font:700 11px Arial,sans-serif;letter-spacing:1px;color:#27604b;margin:8px 0 3px}.bilingual-text{margin:3px 0 8px;line-height:1.55;color:#18251f}';" +
                    "document.head.appendChild(style);" +
                    "var items=[" +
                    "{t:'1. Actions are judged by intentions',e:'Actions are judged by intentions, and each person will have only what they intended.',s:'Hakika matendo huzingatiwa kwa nia, na kila mtu atapata kulingana na alichonuia.',r:'Sahih al-Bukhari 1; Sahih Muslim 1907'}," +
                    "{t:'2. The best among you',e:'The best of you are those who learn the Qur’an and teach it.',s:'Mbora wenu ni yule anayejifunza Qur’ani na akaifundisha.',r:'Sahih al-Bukhari 5027'}," +
                    "{t:'3. Speak good or remain silent',e:'Whoever believes in Allah and the Last Day should speak what is good or remain silent.',s:'Mwenye kumuamini Allah na Siku ya Mwisho aseme yaliyo mema au anyamaze.',r:'Sahih al-Bukhari 6018; Sahih Muslim 47'}," +
                    "{t:'4. Mercy',e:'Those who show mercy will be shown mercy by the Most Merciful. Show mercy to those on earth, and the One above the heavens will show mercy to you.',s:'Wenye kurehemu watarehemewa na Mwingi wa Rehema. Warehemuni walioko ardhini, na Aliye juu ya mbingu atawarehemu.',r:'Jami at-Tirmidhi 1924'}" +
                    "];" +
                    "function addBilingual(){var c=document.getElementById('content');if(!c)return;var h=c.querySelector('h2');if(!h||h.textContent.indexOf('Hadith Library')===-1||document.getElementById('bilingual-hadith'))return;" +
                    "var box=document.createElement('section');box.className='panel';box.id='bilingual-hadith';" +
                    "var html='<h3>Hadith in English & Kiswahili</h3><p class=\'muted\'>A small bilingual reading selection with collection references. The 600-record download below currently stores Arabic source text; translations for the full library are not yet included.</p>';" +
                    "items.forEach(function(x){html+='<div class=\'bilingual-card\'><h3>'+x.t+'</h3><div class=\'bilingual-label\'>ENGLISH</div><p class=\'bilingual-text\'>'+x.e+'</p><div class=\'bilingual-label\'>KISWAHILI</div><p class=\'bilingual-text\'>'+x.s+'</p><p class=\'muted\'>Source: '+x.r+'</p></div>';});" +
                    "box.innerHTML=html;h.parentNode.insertBefore(box,h.nextSibling);" +
                    "}" +
                    "var target=document.getElementById('content');if(target){new MutationObserver(addBilingual).observe(target,{childList:true,subtree:true});}" +
                    "document.querySelectorAll('#nav button').forEach(function(b){b.addEventListener('click',function(){setTimeout(addBilingual,80);});});" +
                    "addBilingual();" +
                    "})();";
                view.evaluateJavascript(script, null);
            }
        });
        setContentView(webView);
        webView.loadUrl("file:///android_asset/index.html");
    }

    @Override
    public void onBackPressed() {
        if (webView != null && webView.canGoBack()) {
            webView.goBack();
        } else {
            super.onBackPressed();
        }
    }
}
