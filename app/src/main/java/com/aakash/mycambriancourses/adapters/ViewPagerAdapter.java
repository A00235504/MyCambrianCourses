package com.aakash.mycambriancourses.adapters;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.aakash.mycambriancourses.ViewPagerFragments.FirstPage;
import com.aakash.mycambriancourses.ViewPagerFragments.SecondPage;

public class ViewPagerAdapter
        extends FragmentPagerAdapter {
String description, title, imageurl, opportunity;
    public ViewPagerAdapter(
            @NonNull FragmentManager fm, String description, String title, String imageurl, String opportunity)
    {
        super(fm);
        this.description = description;
        this.title = title;
        this.imageurl = imageurl;
        this.opportunity = opportunity;
    }

    @NonNull
    @Override
    public Fragment getItem(int position)
    {
        Fragment fragment = null;
        if (position == 0){
            fragment = new FirstPage();
        Bundle bundle = new Bundle();
        bundle.putString("Description", description);
        bundle.putString("Title", title);
            bundle.putString("Imagelink", title);
        fragment.setArguments(bundle);
        }
        else if (position == 1) {
            fragment = new SecondPage();
            Bundle bundle = new Bundle();
            bundle.putString("Opportunity", opportunity);
            bundle.putString("Title", title);
            fragment.setArguments(bundle);
        }
        return fragment;
    }

    @Override
    public int getCount()
    {
        return 2;
    }

    @Override
    public CharSequence getPageTitle(int position)
    {
        String title = null;
        if (position == 0)
            title = "Details";
        else if (position == 1)
            title = "Opportunities";

        return title;
    }
}
