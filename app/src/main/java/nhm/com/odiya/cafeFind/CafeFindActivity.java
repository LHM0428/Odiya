package nhm.com.odiya.cafeFind;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.nhn.android.maps.NMapActivity;
import com.nhn.android.maps.NMapController;
import com.nhn.android.maps.NMapView;
import com.nhn.android.maps.maplib.NGeoPoint;
import com.nhn.android.maps.nmapmodel.NMapError;
import com.nhn.android.maps.overlay.NMapPOIdata;
import com.nhn.android.mapviewer.overlay.NMapCalloutOverlay;
import com.nhn.android.mapviewer.overlay.NMapOverlayManager;
import com.nhn.android.mapviewer.overlay.NMapPOIdataOverlay;

import nhm.com.odiya.R;
import nhm.com.odiya.spring.HttpClient;
import nhm.com.odiya.utils.GpsInfo;
import nhm.com.odiya.utils.navermap.NMapPOIflagType;
import nhm.com.odiya.utils.navermap.NMapViewerResourceProvider;

public class CafeFindActivity extends NMapActivity{
    private static final String LOG_TAG = "CafeFind";
    NMapView mMapView;
    NMapController mMapController;
    ImageView img_search;
    Button btn_myLocation;
    private GpsInfo gps;
    NMapOverlayManager mMapOverlayManager;
    NMapViewerResourceProvider mNMapViewerResourceProvider;
    NMapPOIdataOverlay mNMapPOIdataOverlay;
    NMapCalloutOverlay mNMapCalloutOverlay;

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
        //Scailing, 확대
        mMapView.setScalingFactor(2f);
        // use built in zoom controls
        mMapView.setBuiltInZoomControls(true, null);
        //create resource provider
        mNMapViewerResourceProvider = new NMapViewerResourceProvider(this);
        //create overlay manager
        mMapOverlayManager = new NMapOverlayManager(this, mMapView, mNMapViewerResourceProvider);
        //오버레이 아이템


        //검색버튼
        img_search = (ImageView) findViewById(R.id.iv_search);
        img_search.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                EditText et_search = (EditText) findViewById(R.id.et_search);
                String keyword = et_search.getText().toString();
                Toast.makeText(v.getContext(), keyword, Toast.LENGTH_LONG).show();
                NetworkTask networkTask = new NetworkTask();
                networkTask.execute("");
                return false;
            }
        });

        //현재 위치 버튼튼
       btn_myLocation = (Button) findViewById(R.id.btn_myLocation);
        btn_myLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gps = new GpsInfo(CafeFindActivity.this);

                if(gps.isGetLocation()){
                    double latitude = gps.getLatitude();
                    double longitude = gps.getLongitude();
                    NGeoPoint myLocation = new NGeoPoint(longitude,latitude);

                 Toast.makeText(getBaseContext(),"당신의 위치 - \n위도: " + latitude + "\n경도: " + longitude,Toast.LENGTH_LONG).show();
                    mMapController.animateTo(myLocation);
                    mMapController.setZoomLevel(14);
                    //Overay 표시
                    NMapPOIdata poiData = new NMapPOIdata(10, mNMapViewerResourceProvider);
                    poiData.beginPOIdata(10);
                    poiData.addPOIitem(myLocation, "내 위치",NMapPOIflagType.PIN,0);
                    poiData.endPOIdata();
                    mNMapPOIdataOverlay = mMapOverlayManager.createPOIdataOverlay(poiData, null);
                    mNMapPOIdataOverlay.showAllPOIdata(0);
                } else {
                    gps.showSettingsAlert();
                }
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
                   // mMapController.setMapCenter(new NGeoPoint(127.3006806,36.6036003), 11);
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
    }

    public class NetworkTask extends AsyncTask {
        /**
         * doInBackground 실행되기 이전에 동작한다.
         */
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(Object[] params) {
            /**
             * 본 작업을 쓰레드로 처리해준다.
             */
            // HTTP 요청 준비 작업
            HttpClient.Builder http = new HttpClient.Builder("POST", "http://192.168.10.100:8989/android");

            // HTTP 요청 전송
            HttpClient post = http.create();
            post.request();

            // 응답 상태코드 가져오기
            int statusCode = post.getHttpStatusCode();

            // 응답 본문 가져오기
            String body = post.getBody();

            return body;
        }

        /**
         * doInBackground 종료되면 동작한다.
         * s : doInBackground가 리턴한 값이 들어온다.
         */
        protected void onPostExecute(String s) {
            Log.d("HTTP_RESULT", s);
        }
    }
}




