package adrianbarres.geniusplaza.com.holders;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import adrianbarres.geniusplaza.com.R;

public class UserHolder {

    public TextView name;
    public TextView lastName;
    public ImageView img;

    public UserHolder(View v) {
        name= (TextView) v.findViewById(R.id.name);
        lastName= (TextView) v.findViewById(R.id.lastName);
        img= (ImageView) v.findViewById(R.id.avatar);
    }
}