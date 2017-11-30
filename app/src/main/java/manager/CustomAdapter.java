package manager;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.lore_depa.mycineforum.R;

import java.util.List;

/**
 * Created by SSBS_SELECT on 27/11/2017.
 */

public class CustomAdapter extends BaseAdapter {

    private Context mContext;
    private List<SASUser> esempioList;

    public CustomAdapter(Context mContext, List<SASUser> esempioList) {
        this.mContext = mContext;
        this.esempioList = esempioList;
    }

    @Override
    public int getCount() {
        return esempioList.size();
    }

    @Override
    public Object getItem(int i) {
        return esempioList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        LayoutInflater layoutInflater = (LayoutInflater) mContext.getSystemService(mContext.LAYOUT_INFLATER_SERVICE);
        view = layoutInflater.inflate(R.layout.list_item, viewGroup, false);

        TextView userNick = view.findViewById(R.id.user_nickname);
        TextView userMail = view.findViewById(R.id.user_mail);

        userNick.setText(esempioList.get(i).getNick());
        userMail.setText(esempioList.get(i).getMail());

        return view;
    }
}
