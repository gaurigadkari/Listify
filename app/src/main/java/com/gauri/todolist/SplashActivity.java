package com.gauri.todolist;

import android.content.Intent;
import android.os.Bundle;

import com.daimajia.androidanimations.library.Techniques;
import com.viksaa.sssplash.lib.activity.AwesomeSplash;
import com.viksaa.sssplash.lib.cnst.Flags;
import com.viksaa.sssplash.lib.model.ConfigSplash;

public class SplashActivity extends AwesomeSplash {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        View easySplashScreenView = new EasySplashScreen(SplashActivity.this)
//                .withFullScreen()
//                .withTargetActivity(MainActivity.class)
//                .withSplashTimeOut(4000)
//                .withBackgroundResource(android.R.color.holo_orange_light)
//                .withLogo(R.drawable.list)
//                .withAfterLogoText("Listify")
//                .create();
//        EasySplashScreen config = new EasySplashScreen(SplashActivity.this)
//                .withFullScreen()
//                .withTargetActivity(MainActivity.class)
//                .withSplashTimeOut(4000)
//                .withBackgroundResource(android.R.color.holo_red_light)
//                .withLogo(R.mipmap.ic_new_launcher)
//                .withAfterLogoText("Some more details with custom font");
//        //add custom font
//        Typeface pacificoFont = Typeface.createFromAsset(getAssets(), "Pacifico.ttf");
//        config.getAfterLogoTextView().setTypeface(pacificoFont);
//
//        //change text color
//        config.getHeaderTextView().setTextColor(Color.WHITE);
//
//        //finally create the view
//        View easySplashScreenView = config.create();
//        setContentView(easySplashScreenView);
    }

    @Override
    public void initSplash(ConfigSplash configSplash) {
         /* you don't have to override every property */

        //Customize Circular Reveal
        configSplash.setBackgroundColor(R.color.colorPrimary); //any color you want form colors.xml
        configSplash.setAnimCircularRevealDuration(2000); //int ms
        configSplash.setRevealFlagX(Flags.REVEAL_RIGHT);  //or Flags.REVEAL_LEFT
        configSplash.setRevealFlagY(Flags.REVEAL_BOTTOM); //or Flags.REVEAL_TOP

        //Choose LOGO OR PATH; if you don't provide String value for path it's logo by default

        //Customize Logo
        configSplash.setLogoSplash(R.mipmap.ic_launcher); //or any other drawable
        configSplash.setAnimLogoSplashDuration(2000); //int ms
        configSplash.setAnimLogoSplashTechnique(Techniques.Bounce); //choose one form Techniques (ref: https://github.com/daimajia/AndroidViewAnimations)


//        //Customize Path
//        configSplash.setPathSplash(Constants.DROID_LOGO); //set path String
//        configSplash.setOriginalHeight(400); //in relation to your svg (path) resource
//        configSplash.setOriginalWidth(400); //in relation to your svg (path) resource
//        configSplash.setAnimPathStrokeDrawingDuration(3000);
//        configSplash.setPathSplashStrokeSize(3); //I advise value be <5
//        configSplash.setPathSplashStrokeColor(R.color.colorAccent); //any color you want form colors.xml
//        configSplash.setAnimPathFillingDuration(3000);
//        configSplash.setPathSplashFillColor(R.color.fillColor); //path object filling color


        //Customize Title
        configSplash.setTitleSplash("Listify");
        configSplash.setTitleTextColor(R.color.colorWhite);
        configSplash.setTitleTextSize(36f); //float value
        configSplash.setAnimTitleDuration(3000);
        configSplash.setAnimTitleTechnique(Techniques.FlipInX);
//        configSplash.setTitleFont("fonts/myfont.ttf"); //provide string to your font located in assets/fonts/
    }

    @Override
    public void animationsFinished() {
        startActivity(new Intent(this, MainActivity.class));
    }
}
