package com.whiteandc.capture.fragments.list;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.ActivityOptionsCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.whiteandc.capture.DetailActivity;
import com.whiteandc.capture.MonumentsActivity;
import com.whiteandc.capture.R;
import com.whiteandc.capture.data.Monument;
import com.whiteandc.capture.data.MonumentList;
import com.whiteandc.capture.fragments.BasicFragment;


public class FragmentCityList extends BasicFragment implements AdapterView.OnItemClickListener{

	private CityListAdapter adapter = null;
    private ListView list;
    private View rootView;

    @Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
		rootView = inflater.inflate(R.layout.fragment_city_list, container, false);

        ((MonumentsActivity) mActivity).setFullScreen(false);
        ((MonumentsActivity) mActivity).setToolBarVisibility(true);
        ((MonumentsActivity) mActivity).setHomeButtonVisibility(false);

        adapter = new CityListAdapter(this.getActivity());
	    list= (ListView) rootView.findViewById(R.id.city_list);
        list.setAdapter(adapter);
        list.setOnItemClickListener(this);



        ((MonumentsActivity) mActivity).setToolbarTitle(MonumentList.getCityName());
		return rootView;
	}

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Monument monument= MonumentList.getList().get(position);
        View imgTransition= view.findViewById(R.id.item_img);
        switchToDetailActivity(monument.getName(), imgTransition);
    }

    public void switchToDetailActivity(String monumentId, View imgTransition) {
        Intent intent = new Intent(getActivity(), DetailActivity.class);
        ActivityOptionsCompat options = ActivityOptionsCompat.
                makeSceneTransitionAnimation(getActivity(), (View) imgTransition, "img_trans");
        Bundle b= new Bundle();
        b.putString("monumentId", monumentId);
        intent.putExtras(b);
        startActivity(intent, options.toBundle());
    }


    public void searchMonument(String query) {
        adapter.searchMonument(query);
    }
}
