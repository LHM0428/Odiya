package nhm.com.odiya.cafeFind;

import android.graphics.Rect;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.androidquery.AQuery;
import com.androidquery.callback.AjaxCallback;
import com.androidquery.callback.AjaxStatus;
import com.google.gson.Gson;
import com.nhn.android.maps.NMapActivity;
import com.nhn.android.maps.NMapController;
import com.nhn.android.maps.NMapOverlay;
import com.nhn.android.maps.NMapOverlayItem;
import com.nhn.android.maps.NMapView;
import com.nhn.android.maps.maplib.NGeoPoint;
import com.nhn.android.maps.nmapmodel.NMapError;
import com.nhn.android.maps.overlay.NMapPOIdata;
import com.nhn.android.maps.overlay.NMapPOIitem;
import com.nhn.android.mapviewer.overlay.NMapCalloutCustomOverlay;
import com.nhn.android.mapviewer.overlay.NMapCalloutOverlay;
import com.nhn.android.mapviewer.overlay.NMapOverlayManager;
import com.nhn.android.mapviewer.overlay.NMapPOIdataOverlay;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import nhm.com.odiya.R;
import nhm.com.odiya.utils.GpsInfo;
import nhm.com.odiya.utils.OdiyaData;
import nhm.com.odiya.utils.navermap.NMapCalloutBasicOverlay;
import nhm.com.odiya.utils.navermap.NMapCalloutCustomOverlayView;
import nhm.com.odiya.utils.navermap.NMapPOIflagType;
import nhm.com.odiya.utils.navermap.NMapViewerResourceProvider;

public class CafeFindActivity extends NMapActivity {
    private static final String LOG_TAG = "CafeFind";
    NMapView mMapView;
    NMapController mMapController;
    ImageView img_search;
    Button btn_myLocation;
    private GpsInfo gps;
    NMapOverlayManager mMapOverlayManager;
    NMapViewerResourceProvider mNMapViewerResourceProvider;
    NMapPOIdataOverlay mNMapPOIdataOverlay;
    Map<String, String> map;
    public NGeoPoint searchPoint = null;
    ArrayList<OdiyaData> odiyaList;
    private AQuery aq = new AQuery(this);

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


        map = new HashMap<>();

        //검색버튼
        img_search = (ImageView) findViewById(R.id.iv_search);
        img_search.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(final View v, MotionEvent event) {
                EditText et_search = (EditText) findViewById(R.id.et_search);
                //검색어 받기
                final String keyword = et_search.getText().toString();
                Toast.makeText(v.getContext(), keyword + "주변의 오디야를 검색합니다.", Toast.LENGTH_LONG).show();
                String keywordUTF = null;
                try {
                    //검색어 URL로 보내기 위해 UTF-8 변환
                    keywordUTF = URLEncoder.encode(keyword, "UTF-8");
                } catch (Exception e) {
                    e.printStackTrace();
                }
                //검색한 지역에 대한 좌표를 받기 위한 url
                String url = "https://apis.daum.net/local/geo/addr2coord?apikey=8d76f7a19e09506d7ea7b9e7338ebd2a&q=" + keywordUTF + "&output=json";
                //ajax를 통해 검색한 지역에 대한 좌표를 구한다.
                aq.ajax(url, JSONObject.class, new AjaxCallback<JSONObject>() {
                    @Override
                    public void callback(String url, JSONObject object, AjaxStatus status) {
                        JSONObject dataChannel = null;
                        try {
                            dataChannel = (JSONObject) object.get("channel");
                            JSONArray jsonArray = (JSONArray) dataChannel.get("item");
                            JSONObject addressJson = jsonArray.getJSONObject(0);
                            double longitude = (double) addressJson.get("point_x");
                            double latitude = (double) addressJson.get("point_y");
                            searchPoint = new NGeoPoint(longitude, latitude);
                            Log.d(keyword + "의 좌표 : ", searchPoint.toString());

                            //검색한 지역의 주변에 있는 Odiya 찾기
                            String odiyaUTF = URLEncoder.encode("이디야", "UTF-8");
                            String odiyaURL = "https://apis.daum.net/local/v1/search/keyword.json?apikey=8d76f7a19e09506d7ea7b9e7338ebd2a&query=" +
                                    odiyaUTF + "&location=" + searchPoint.getLatitude() + "," + searchPoint.getLongitude() + "&radius=3000";

                            //----------------검색한 위치를 기반으로 주변에 있는 오디야 찾기------------------------------
                            aq.ajax(odiyaURL, JSONObject.class, new AjaxCallback<JSONObject>() {
                                @Override
                                public void callback(String url, JSONObject object, AjaxStatus status) {
                                    odiyaList = new ArrayList<>();
                                    try {
                                        JSONObject dataChannel = (JSONObject) object.get("channel");
                                        JSONArray jsonArray = (JSONArray) dataChannel.get("item");
                                        Gson gson = new Gson();
                                        if(jsonArray!=null && jsonArray.length()>0){
                                            for (int i = 0; i < jsonArray.length(); i++) {
                                                String odiyaJsonStr = jsonArray.getJSONObject(i).toString();
                                                Log.d("odiyaString : ", odiyaJsonStr);
                                                OdiyaData data = gson.fromJson(odiyaJsonStr, OdiyaData.class);
                                                odiyaList.add(data);
                                            }
                                            Log.d("odiyaList : ", odiyaList.get(0).getTitle());
                                            int listSize = odiyaList.size();
                                            NMapPOIdata poiData = new NMapPOIdata(listSize + 1, mNMapViewerResourceProvider);
                                            poiData.beginPOIdata(listSize + 1);
                                            for (int i = 0; i < listSize; i++) {
                                                OdiyaData odiya = odiyaList.get(i);
                                                odiya.setTitle(odiya.getTitle().replace("이디야", "오디야"));
                                                double latitude = Double.parseDouble(odiya.getLatitude());
                                                double longitude = Double.parseDouble(odiya.getLongitude());
                                                NGeoPoint odiyaPoint = new NGeoPoint(longitude, latitude);
                                                String title = "";
                                                if (odiya.getPhone().length() > 0) {
                                                    title = odiya.getTitle() + "\n"
                                                            + "주소 : " + odiya.getNewAddress() + "\n"
                                                            + "연락처 : " + odiya.getPhone();
                                                } else {
                                                    title = odiya.getTitle() + "\n"
                                                            + "주소 : " + odiya.getNewAddress() + "\n";
                                                }

                                                poiData.addPOIitem(odiyaPoint, title, NMapPOIflagType.SPOT, i);
                                            }
                                            //검색한 지역의 위치까지 표시
                                            poiData.addPOIitem(searchPoint, "검색한 위치 - " + keyword, NMapPOIflagType.PIN, listSize + 1);
                                            poiData.endPOIdata();

                                            //POI클릭 시 해당 정보 보여주기
                                            mMapOverlayManager.setOnCalloutOverlayViewListener(new NMapOverlayManager.OnCalloutOverlayViewListener() {
                                                @Override
                                                public View onCreateCalloutOverlayView(NMapOverlay nMapOverlay, NMapOverlayItem nMapOverlayItem, Rect rect) {
                                                    return new NMapCalloutCustomOverlayView(getBaseContext(), nMapOverlay, nMapOverlayItem, rect);
                                                }
                                            });
                                            mNMapPOIdataOverlay = mMapOverlayManager.createPOIdataOverlay(poiData, null);
                                            mNMapPOIdataOverlay.showAllPOIdata(0);
                                        } else {
                                            Toast.makeText(getBaseContext(), "해당 지역 주변에 오디야가 없습니다.",Toast.LENGTH_LONG).show();
                                        }
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }
                                }//callback
                            });//ajax
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }//callback
                });//ajax
                return false;
            }
        });


        //현재 위치 버튼
        btn_myLocation = (Button) findViewById(R.id.btn_myLocation);
        btn_myLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                currentPositionOdiya();
            }//onClick
        });

        // register listener for map state changes
        mMapView.setOnMapStateChangeListener(new NMapView.OnMapStateChangeListener() {
            @Override
            public void onMapInitHandler(NMapView nMapView, NMapError errorInfo) {
                //  NMapView 객체에서 발생하는 상태 변화 및 터치 이벤트에 대한 콜백 인터페이스를 등록합니다.
                //  지도 초기화가 완료되면 아래의 콜백 인터페이스가 호출됩니다.
                // 초기화가 성공하면 지도 보기 모드 및 중심 좌표 등을 설정하고 실패하면 적절한 예외 처리를 수행합니다.
                if (errorInfo == null) { // success
                    currentPositionOdiya();
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

    public void currentPositionOdiya(){
        gps = new GpsInfo(CafeFindActivity.this);

        if (gps.isGetLocation()) {
            double latitude = gps.getLatitude();
            double longitude = gps.getLongitude();
            final NGeoPoint myLocation = new NGeoPoint(longitude, latitude);

            try {
                String odiyaUTF = URLEncoder.encode("이디야", "UTF-8");
                String odiyaURL = "https://apis.daum.net/local/v1/search/keyword.json?apikey=8d76f7a19e09506d7ea7b9e7338ebd2a&query=" +
                        odiyaUTF + "&location=" + myLocation.getLatitude() + "," + myLocation.getLongitude() + "&radius=3000";
                //---------------현재 위치를 기반으로 주변에 있는 오디야 찾기------------------------------
                aq.ajax(odiyaURL, JSONObject.class, new AjaxCallback<JSONObject>() {
                    @Override
                    public void callback(String url, JSONObject object, AjaxStatus status) {
                        odiyaList = new ArrayList<>();
                        try {
                            JSONObject dataChannel = (JSONObject) object.get("channel");
                            JSONArray jsonArray = (JSONArray) dataChannel.get("item");
                            Gson gson = new Gson();
                            if(jsonArray!=null && jsonArray.length()>0){
                                for (int i = 0; i < jsonArray.length(); i++) {
                                    String odiyaJsonStr = jsonArray.getJSONObject(i).toString();
                                    Log.d("odiyaString : ", odiyaJsonStr);
                                    OdiyaData data = gson.fromJson(odiyaJsonStr, OdiyaData.class);
                                    odiyaList.add(data);
                                }
                                Log.d("odiyaList : ", odiyaList.get(0).getTitle());
                                int listSize = odiyaList.size();
                                NMapPOIdata poiData = new NMapPOIdata(listSize + 1, mNMapViewerResourceProvider);
                                poiData.beginPOIdata(listSize + 1);
                                for (int i = 0; i < listSize; i++) {
                                    OdiyaData odiya = odiyaList.get(i);
                                    odiya.setTitle(odiya.getTitle().replace("이디야", "오디야"));
                                    double latitude = Double.parseDouble(odiya.getLatitude());
                                    double longitude = Double.parseDouble(odiya.getLongitude());
                                    NGeoPoint odiyaPoint = new NGeoPoint(longitude, latitude);
                                    String title = "";
                                    if (odiya.getPhone().length() > 0) {
                                        title = odiya.getTitle() + "\n"
                                                + "주소 : " + odiya.getNewAddress() + "\n"
                                                + "연락처 : " + odiya.getPhone();
                                    } else {
                                        title = odiya.getTitle() + "\n"
                                                + "주소 : " + odiya.getNewAddress() + "\n";
                                    }

                                    poiData.addPOIitem(odiyaPoint, title, NMapPOIflagType.SPOT, i);
                                }
                                //현재 위치까지 표시
                                poiData.addPOIitem(myLocation, "현재 위치", NMapPOIflagType.PIN, listSize + 1);
                                poiData.endPOIdata();

                                //POI클릭 시 해당 정보 보여주기
                                mMapOverlayManager.setOnCalloutOverlayViewListener(new NMapOverlayManager.OnCalloutOverlayViewListener() {
                                    @Override
                                    public View onCreateCalloutOverlayView(NMapOverlay nMapOverlay, NMapOverlayItem nMapOverlayItem, Rect rect) {
                                        return new NMapCalloutCustomOverlayView(getBaseContext(), nMapOverlay, nMapOverlayItem, rect);
                                    }
                                });
                                mNMapPOIdataOverlay = mMapOverlayManager.createPOIdataOverlay(poiData, null);
                                mNMapPOIdataOverlay.showAllPOIdata(0);
                            } else {
                                Toast.makeText(getBaseContext(), "현재 위치 주변에 오디야가 없습니다.",Toast.LENGTH_LONG).show();
                            }
                        } catch (JSONException e) {e.printStackTrace();}
                    }//callback
                });//ajax
            } catch (UnsupportedEncodingException e) {e.printStackTrace();}
        } else {
            gps.showSettingsAlert();
        }
    }
}




