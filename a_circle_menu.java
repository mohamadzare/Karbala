package com.zare.karbala;

import android.animation.ValueAnimator;
import android.content.Context;
import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.animation.LinearInterpolator;
import android.widget.TextView;
import android.widget.Toast;

import com.hanks.htextview.HTextView;
import com.hanks.htextview.HTextViewType;
import com.kapil.circularlayoutmanager.CircularLayoutManager;
import com.kapil.circularlayoutmanager.OnItemClickListener;
import com.kapil.circularlayoutmanager.ScrollWheel;

import java.util.ArrayList;
import java.util.List;

import Adapter.OnRecyclerItemClickListener;
import Adapter.RecyclerItemDecoration;
import Adapter.RecyclerViewAdapter;
import Model.FontManager;
import Model.Model_circle;
import spencerstudios.com.bungeelib.Bungee;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class a_circle_menu extends AppCompatActivity {

    private RecyclerView recyclerView;
    private ScrollWheel scrollWheel;

    private FloatingActionButton addItemButton;
    private FloatingActionButton scrollWheelToggleButton;

    private List<Model_circle> list;

    TextView hTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.a_circle_menu);

//        hTextView = (TextView) findViewById(R.id.text);
//
//
//        final ValueAnimator animator = ValueAnimator.ofFloat(0.0f, 0.5f);
//        animator.setRepeatCount(ValueAnimator.INFINITE);
//        animator.setInterpolator(new LinearInterpolator());
//        animator.setDuration(5000L);
//        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
//            @Override
//            public void onAnimationUpdate(ValueAnimator animation) {
//                final float progress = (float) animation.getAnimatedValue();
//                final float width = hTextView.getWidth();
//                final float translationX = width * progress;
//                hTextView.setTranslationX(translationX);
//                //second.setTranslationX(translationX - width);
//            }
//        });
//        animator.start();

        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);

      //   scrollWheel = (ScrollWheel) findViewById(R.id.scroll_wheel);


        setViews();
    }

    private void initializeList() {
        list = new ArrayList<>();
        Model_circle model = new Model_circle();
        model.setEvent("پرداخت نذورات");
        model.setTimings("");
        list.add(model);

        Model_circle model2 = new Model_circle();
        model2.setEvent("یاداشت روزانه");
        model2.setTimings("");
        list.add(model2);

        Model_circle model3 = new Model_circle();
        model3.setEvent("ارتباط با مسئولین");
        model3.setTimings("");
        list.add(model3);

        Model_circle model4 = new Model_circle();
        model4.setEvent("اخبار");
        model4.setTimings("");
        list.add(model4);

        Model_circle model5 = new Model_circle();
        model5.setEvent("تصاویر و فیلم");
        model5.setTimings("");
        list.add(model5);

        Model_circle model6 = new Model_circle();
        model6.setEvent("زیارت مجازی");
        model6.setTimings("");
        list.add(model6);

        Model_circle model7 = new Model_circle();
        model7.setEvent("موکب یاب");
        model7.setTimings("");
        list.add(model7);

        Model_circle model8 = new Model_circle();
        model8.setEvent("قرآن خوان");
        model8.setTimings("");
        list.add(model8);


        Model_circle model9 = new Model_circle();
        model9.setEvent("دعا و حدیث");
        model9.setTimings("");
        list.add(model9);


        Model_circle model10 = new Model_circle();
        model10.setEvent("ثبت نام نیروع های متبرع");
        model10.setTimings("");
        list.add(model10);


        Model_circle model11 = new Model_circle();
        model11.setEvent("معرفی ستادها");
        model11.setTimings("");
        list.add(model11);

        Model_circle model12 = new Model_circle();
        model12.setEvent("خدمات");
        model12.setTimings("");
        list.add(model12);

        Model_circle model13 = new Model_circle();
        model13.setEvent("توضیحات");
        model13.setTimings("");
        list.add(model13);

        Model_circle model14 = new Model_circle();
        model14.setEvent("ارتباط با ما");
        model14.setTimings("");
        list.add(model14);
//        list = new ArrayList<>();
//        String event = "Event ", timing = "12:00am - 12:00pm";
//
//        for (int i = 0; i < 10; i++) {
//            Model_circle model = new Model_circle();
//            model.setEvent(event + (i + 1));
//            model.setTimings(timing);
//
//            list.add(model);
//        }
    }


    private void setViews() {
        initializeList();
        recyclerView.setAdapter(new RecyclerViewAdapter(a_circle_menu.this, list));
        recyclerView.addItemDecoration(new RecyclerItemDecoration());
        recyclerView.setLayoutManager(new CircularLayoutManager(a_circle_menu.this, 200, -10));
        recyclerView.addOnItemTouchListener(new OnRecyclerItemClickListener(a_circle_menu.this,
                new OnRecyclerItemClickListener.OnItemClickListener() {
                    @Override
                    public void OnItemClick(RecyclerView parent, int childIndex) {

                        if (((TextView) parent.getChildAt(childIndex)
                                .findViewById(R.id.event)).getText().toString().equalsIgnoreCase("اخبار")) {
                            startActivity(new Intent(a_circle_menu.this, a_news.class));
                            finish();
                        } else if (((TextView) parent.getChildAt(childIndex)
                                .findViewById(R.id.event)).getText().toString().equalsIgnoreCase("موکب یاب")) {

                            startActivity(new Intent(a_circle_menu.this, a_mokeb.class));
                            finish();
                        } else if (((TextView) parent.getChildAt(childIndex)
                                .findViewById(R.id.event)).getText().toString().equalsIgnoreCase("پرداخت نذورات")) {

//                            startActivity(new Intent(a_circle_menu.this,a_mokeb.class));
//                            finish();
                        } else if (((TextView) parent.getChildAt(childIndex)
                                .findViewById(R.id.event)).getText().toString().equalsIgnoreCase("یاداشت روزانه")) {

//                            startActivity(new Intent(a_circle_menu.this,a_mokeb.class));
//                            finish();
                        } else if (((TextView) parent.getChildAt(childIndex)
                                .findViewById(R.id.event)).getText().toString().equalsIgnoreCase("ارتباط با مسئولین")) {

//                            startActivity(new Intent(a_circle_menu.this,a_mokeb.class));
//                            finish();
                        } else if (((TextView) parent.getChildAt(childIndex)
                                .findViewById(R.id.event)).getText().toString().equalsIgnoreCase("تصاویر و فیلم")) {
                            startActivity(new Intent(a_circle_menu.this, a_gallery.class));
                            finish();
                        } else if (((TextView) parent.getChildAt(childIndex)
                                .findViewById(R.id.event)).getText().toString().equalsIgnoreCase("زیارت مجازی")) {

                            startActivity(new Intent(a_circle_menu.this, a_ziarate_majazi.class));
                            finish();
                            Bungee.slideRight(a_circle_menu.this);
                        } else if (((TextView) parent.getChildAt(childIndex)
                                .findViewById(R.id.event)).getText().toString().equalsIgnoreCase("قرآن خوان")) {

//                            startActivity(new Intent(a_circle_menu.this,a_mokeb.class));
//                            finish();
                        } else if (((TextView) parent.getChildAt(childIndex)
                                .findViewById(R.id.event)).getText().toString().equalsIgnoreCase("دعا و حدیث")) {
                            startActivity(new Intent(a_circle_menu.this, a_hadis.class));
                            finish();
                            Bungee.slideRight(a_circle_menu.this);
                        } else if (((TextView) parent.getChildAt(childIndex)
                                .findViewById(R.id.event)).getText().toString().equalsIgnoreCase("ثبت نام نیروع های متبرع")) {

                            startActivity(new Intent(a_circle_menu.this, a_register_guys_motabare.class));
                            finish();
                            Bungee.slideRight(a_circle_menu.this);

                        } else if (((TextView) parent.getChildAt(childIndex)
                                .findViewById(R.id.event)).getText().toString().equalsIgnoreCase("معرفی ستادها")) {

                            startActivity(new Intent(a_circle_menu.this, a_moarefi_setad.class));
                            finish();
                            Bungee.slideRight(a_circle_menu.this);
                        } else if (((TextView) parent.getChildAt(childIndex)
                                .findViewById(R.id.event)).getText().toString().equalsIgnoreCase("خدمات")) {

//                            startActivity(new Intent(a_circle_menu.this,a_mokeb.class));
//                            finish();
                        } else if (((TextView) parent.getChildAt(childIndex)
                                .findViewById(R.id.event)).getText().toString().equalsIgnoreCase("توضیحات")) {

                            startActivity(new Intent(a_circle_menu.this, a_tozihat.class));
                            finish();
                            Bungee.slideRight(a_circle_menu.this);
                        } else if (((TextView) parent.getChildAt(childIndex)
                                .findViewById(R.id.event)).getText().toString().equalsIgnoreCase("ارتباط با ما")) {

                            startActivity(new Intent(a_circle_menu.this, a_about.class));
                            finish();
                            Bungee.slideRight(a_circle_menu.this);
                        }
                    }
                }));

//        scrollWheel.setRecyclerView(recyclerView);
//        scrollWheel.setScrollWheelEnabled(false);
//        scrollWheel.setHighlightTouchAreaEnabled(false);
//        // scrollWheel.setConsumeTouchOutsideTouchAreaEnabled(false);
//        scrollWheel.setTouchAreaThickness(50);
//        scrollWheel.setOnItemClickListener(new OnItemClickListener() {
//            @Override
//            public void onItemClick(ScrollWheel scrollWheel, int childIndex) {
//
//            }
//
//            @Override
//            public void onItemLongClick(ScrollWheel scrollWheel, int childIndex) {
//
//            }
//        });

    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

}
