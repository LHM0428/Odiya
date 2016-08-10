package nhm.com.odiya.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

import nhm.com.odiya.R;

/**
 * Created by Lee on 2016-08-10.
 */
public class ImageAdapter extends BaseAdapter {

    private Context mContext;
    final int couponNum ;

    public ImageAdapter(Context c, int couponNum) {
        this.mContext = c;
        this.couponNum = couponNum;
    }

    @Override
    public int getCount() {
            return 12;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ImageView imageView;
        if(convertView == null){
            imageView = new ImageView(mContext);
            imageView.setLayoutParams(new GridView.LayoutParams(50,50));
        } else{
            imageView = (ImageView) convertView;
        }
        if(couponNum-1 < position)
            imageView.setImageResource(R.drawable.coupon_x);
        else
            imageView.setImageResource(R.drawable.coupon_o);
        return imageView;
    }
}
