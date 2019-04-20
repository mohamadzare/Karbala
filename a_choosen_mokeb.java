package com.zare.karbala;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;

import Adapter.Adapter_choosen_mokeb;
import Model.Array_class_get;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class a_choosen_mokeb extends AppCompatActivity {

    RecyclerView _Recycle;

    Adapter_choosen_mokeb title;

    RecyclerView.LayoutManager mLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.a_choosen_mokeb);


        _Recycle = (RecyclerView) findViewById(R.id.sh_Recycle);

        _Recycle.setHasFixedSize(true);

        mLayoutManager = new GridLayoutManager(a_choosen_mokeb.this, 2);

        _Recycle.setLayoutManager(mLayoutManager);


        int resId = R.anim.layout_animation_scroll_from_bottom;

        LayoutAnimationController animation = AnimationUtils.loadLayoutAnimation(a_choosen_mokeb.this, resId);

        _Recycle.setLayoutAnimation(animation);

        title = new Adapter_choosen_mokeb(a_choosen_mokeb.this, Array_class_get.array_m_Ostan, "1");

        _Recycle.setAdapter(title);
    }
    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(a_choosen_mokeb.this, a_Circle_list_menu.class));
        finish();
    }
}
