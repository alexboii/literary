package com.example.alex.literary.mainactivity;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.alex.literary.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Alex on 7/31/2016.
 */


class CustomListAdapter extends ArrayAdapter {

        private Context mContext;
        private int id;
        private List<String> items ;

        public CustomListAdapter(Context context, int textViewResourceId , List<String> list )
        {
            super(context, textViewResourceId, list);
            mContext = context;
            id = textViewResourceId;
            items = list ;
        }

        @Override
        public View getView(int position, View v, ViewGroup parent)
        {
            View mView = v ;
            if(mView == null){
                LayoutInflater vi = (LayoutInflater)mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                mView = vi.inflate(id, null);
            }

            TextView text = (TextView) mView.findViewById(R.id.textView);

            if(items.get(position) != null )
            {
                text.setTextColor(Color.BLACK);
                text.setText(items.get(position));
                text.setTextSize(30);

            }

            return mView;
        }

        @Override
        public int getCount() {
            return items.size();
        }

        @Override
        public String getItem(int position) {
            return items.get(position);
        }

        public void setList(ArrayList<String> wordsList) {
            items = wordsList; //mWordsList is the list you have as a field in adapter.
            notifyDataSetChanged();
        }



}
