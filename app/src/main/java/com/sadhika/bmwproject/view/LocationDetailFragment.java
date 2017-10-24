package com.sadhika.bmwproject.view;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.sadhika.bmwproject.R;
import com.sadhika.bmwproject.model.pojos.LocationInfo;
import com.sadhika.bmwproject.utils.Utils;

import butterknife.BindView;
import butterknife.ButterKnife;


public class LocationDetailFragment extends Fragment implements OnMapReadyCallback {

    private static final String ARG_LOCATIONINFO = "location.info";
    private static final int ZOOM_LEVEL = 15;

    @BindView(R.id.textview_arrival_time)
    TextView mArrivalTime;

    @BindView(R.id.textview_location)
    TextView mLocationName;

    @BindView(R.id.textview_address)
    TextView mAddress;

    @BindView(R.id.textview_lattitule)
    TextView mLattitude;

    @BindView(R.id.textview_longitude)
    TextView mLongitude;

    SupportMapFragment mMapFragment;

    private LocationInfo mLocInfo;

    private OnInteraction mListener;

    public LocationDetailFragment() {}

    public static LocationDetailFragment newInstance(Parcelable locationInfo) {
        LocationDetailFragment fragment = new LocationDetailFragment();
        Bundle args = new Bundle();
        args.putParcelable(ARG_LOCATIONINFO, locationInfo);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mLocInfo = getArguments().getParcelable(ARG_LOCATIONINFO);
        }
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_location_detail, container, false);
        view.setClickable(true);
        ButterKnife.bind(this, view);
        mMapFragment = (SupportMapFragment) getChildFragmentManager()
                .findFragmentById(R.id.location_map);

        ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle(mLocInfo.getName());

        initializeViews();
        return view;
    }

    private void initializeViews() {
        mArrivalTime.setText(String.format(getString(R.string.tag_arrival_time), Utils.getRemainingTimeToArrive(mLocInfo.getArrivalTime())));
        mLocationName.setText(String.format(getString(R.string.tag_location), mLocInfo.getName()));
        mAddress.setText(String.format(getString(R.string.tag_address), mLocInfo.getAddress()));
        mLattitude.setText(String.format(getString(R.string.tag_latitude), mLocInfo.getLatitude()));
        mLongitude.setText(String.format(getString(R.string.tag_longitude), mLocInfo.getLongitude()));
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnInteraction) {
            mListener = (OnInteraction) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mMapFragment.getMapAsync(this);

    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        LatLng latlng = new LatLng(mLocInfo.getLatitude(), mLocInfo.getLongitude());
        googleMap.addMarker(new MarkerOptions().position(latlng).title(mLocInfo.getName()));

        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latlng, ZOOM_LEVEL));

    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
    }

    public interface OnInteraction {
    }
}
