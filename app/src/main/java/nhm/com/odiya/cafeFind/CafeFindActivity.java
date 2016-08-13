package nhm.com.odiya.cafeFind;

import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.nhn.android.maps.NMapActivity;
import com.nhn.android.maps.NMapController;
import com.nhn.android.maps.NMapView;
import com.nhn.android.maps.maplib.NGeoPoint;
import com.nhn.android.maps.nmapmodel.NMapError;


import nhm.com.odiya.R;

public class CafeFindActivity extends NMapActivity {
    private static final String  LOG_TAG = "CafeFind";
    NMapView mMapView;
    NMapController mMapController;
    ImageView img_search;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.cafefind_activity);
        // create map view
        mMapView = (NMapView) findViewById(R.id.mapView);
        // set Client ID for Open MapViewer Library
        mMapView.setClientId("VzHHuMNj_4DlOZFsyWJm");

        // initialize map view
        mMapView.setClickable(true);

        // use map controller to zoom in/out, pan and set map center, zoom level etc.
        mMapController = mMapView.getMapController();

        // use built in zoom controls
        mMapView.setBuiltInZoomControls(true, null);


        img_search = (ImageView) findViewById(R.id.iv_search);
        img_search.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                EditText et_search = (EditText) findViewById(R.id.et_search);
                String keyword = et_search.getText().toString();
                Toast.makeText(v.getContext(), keyword, Toast.LENGTH_LONG).show();
                return false;
            }
        });


        // register listener for map state changes
        mMapView.setOnMapStateChangeListener(new NMapView.OnMapStateChangeListener() {
            @Override
            public void onMapInitHandler(NMapView nMapView, NMapError errorInfo) {
            //  NMapView 객체에서 발생하는 상태 변화 및 터치 이벤트에 대한 콜백 인터페이스를 등록합니다.
            //  지도 초기화가 완료되면 아래의 콜백 인터페이스가 호출됩니다.
            // 초기화가 성공하면 지도 보기 모드 및 중심 좌표 등을 설정하고 실패하면 적절한 예외 처리를 수행합니다.
                if (errorInfo == null) { // success
                    mMapController.setMapCenter(new NGeoPoint(126.978371, 37.5666091), 11);
                } else { // fail
                    Log.e(LOG_TAG, "onMapInitHandler: error=" + errorInfo.toString());
                }

            }

            @Override
            public void onMapCenterChange(NMapView nMapView, NGeoPoint nGeoPoint) {

            }

            @Override
            public void onMapCenterChangeFine(NMapView nMapView) {

            }

            @Override
            public void onZoomLevelChange(NMapView nMapView, int i) {

            }

            @Override
            public void onAnimationStateChange(NMapView nMapView, int i, int i1) {

            }
        });
        mMapView.setOnMapViewTouchEventListener(new NMapView.OnMapViewTouchEventListener() {
            @Override
            public void onLongPress(NMapView nMapView, MotionEvent motionEvent) {

            }

            @Override
            public void onLongPressCanceled(NMapView nMapView) {

            }

            @Override
            public void onTouchDown(NMapView nMapView, MotionEvent motionEvent) {

            }

            @Override
            public void onTouchUp(NMapView nMapView, MotionEvent motionEvent) {

            }

            @Override
            public void onScroll(NMapView nMapView, MotionEvent motionEvent, MotionEvent motionEvent1) {

            }

            @Override
            public void onSingleTapUp(NMapView nMapView, MotionEvent motionEvent) {

            }
        });
    }

}

