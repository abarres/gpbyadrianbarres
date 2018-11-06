package adrianbarres.geniusplaza.com.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import adrianbarres.geniusplaza.com.R;
import adrianbarres.geniusplaza.com.holders.UserHolder;
import adrianbarres.geniusplaza.com.models.User;
import adrianbarres.geniusplaza.com.utils.PicassoClient;

public class CustomAdapter extends BaseAdapter {

    Context c;
    User userList;
    LayoutInflater inflater;

    public CustomAdapter(Context c, User userList) {
        this.c = c;
        this.userList = userList;
    }

    @Override
    public int getCount() {
        return userList.getUserData().size();
    }

    @Override
    public Object getItem(int position) {
        return userList.getUserData().get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if(inflater==null){
            inflater= (LayoutInflater) c.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }

        if(convertView==null){
            convertView=inflater.inflate(R.layout.user_list_item,parent,false);
        }

        UserHolder holder=new UserHolder(convertView);

        PicassoClient.downloadImage(c,userList.getUserData().get(position).getAvatar(),holder.img);
        holder.name.setText(userList.getUserData().get(position).getFirstName());
        holder.lastName.setText(userList.getUserData().get(position).getLastName());

        return convertView;
    }
}
