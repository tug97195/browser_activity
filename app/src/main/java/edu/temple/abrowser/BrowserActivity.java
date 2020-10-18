package edu.temple.abrowser;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.os.Bundle;

public class BrowserActivity extends AppCompatActivity implements PageControlFragment.PageControlInterface, PageViewerFragment.PageViewerInterface {


    FragmentManager fm;

    PageControlFragment pageControlFragment;

    PageViewerFragment pageViewerFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        fm = getSupportFragmentManager();

        Fragment tmpFragment;

        // If fragment already added (activity restarted) then hold reference
        // otherwise add new fragment. Only one instance of fragment is ever present
        if ((tmpFragment = fm.findFragmentById(R.id.page_control)) instanceof PageControlFragment)
            pageControlFragment = (PageControlFragment) tmpFragment;
        else {
            pageControlFragment = new PageControlFragment();
            fm.beginTransaction()
                    .add(R.id.page_control, pageControlFragment)
                    .commit();
        }

        // If fragment already added (activity restarted) then hold reference
        // otherwise add new fragment. Only one instance of fragment is ever present
        if ((tmpFragment = fm.findFragmentById(R.id.page_viewer)) instanceof PageViewerFragment)
            pageViewerFragment = (PageViewerFragment) tmpFragment;
        else {
            pageViewerFragment = new PageViewerFragment();
            fm.beginTransaction()
                    .add(R.id.page_viewer, pageViewerFragment)
                    .commit();
        }

    }

    /**
     * Update WebPage whenever PageControlFragment sends new Url
     * @param url to load
     */
    @Override
    public void go(String url) {
        pageViewerFragment.go(url);
    }

    /**
     * Go back to previous page when user presses Back in PageControlFragment
     */
    @Override
    public void back() {
        pageViewerFragment.back();
    }

    /**
     * Go back to next page when user presses Forward in PageControlFragment
     */
    @Override
    public void forward() {
        pageViewerFragment.forward();
    }

    /**
     * Update displayed Url in PageControlFragment when Webpage Url changes
     * @param url to display
     */
    @Override
    public void updateUrl(String url) {
        pageControlFragment.updateUrl(url);
    }
}