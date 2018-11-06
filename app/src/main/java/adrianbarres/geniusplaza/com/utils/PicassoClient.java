package adrianbarres.geniusplaza.com.utils;

import android.content.Context;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import adrianbarres.geniusplaza.com.R;

public class PicassoClient {
    public static void downloadImage(Context c, String url, ImageView img)
    {
        if(url != null && url.length()>0)
        {
            Picasso.with(c).load(url).placeholder(R.mipmap.emptyproto).into(img);
        }else
        {
            Picasso.with(c).load(R.mipmap.emptyproto).into(img);
        }
    }
}
