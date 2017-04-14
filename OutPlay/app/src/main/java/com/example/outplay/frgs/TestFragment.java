package com.example.outplay.frgs;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.outplay.BaiduActivity;
import com.example.outplay.R;
import com.example.outplay.atys.EditActivity;
import com.example.outplay.atys.ExitActivity;
import com.example.outplay.atys.SearchActivity;
import com.example.outplay.atys.ShowTransationActivity;
import com.example.outplay.atys.WelcomeActivity;
import com.zhy.view.CircleMenuLayout;


/**
 * 在FAB被点击后显示出来的
 */
public class TestFragment extends Fragment {
    private CircleMenuLayout mCircleMenuLayout;

    private String[] mItemTexts = new String[] {"二手交易", "编辑", "搜索",
            "我的","地图查询"};

    private int[] mItemImgs = new int[] {R.drawable.icon_transation,R.drawable.icon_edit,
            R.drawable.icon_search,R.drawable.icon2,R.drawable.icon_locat};

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final boolean isuser=getArguments().getBoolean("isuser");

        View view=inflater.inflate(R.layout.fragment_test, container, false);
        mCircleMenuLayout=(CircleMenuLayout)view.findViewById(R.id.id_menulayout);
        mCircleMenuLayout.setMenuItemIconsAndTexts(mItemImgs, mItemTexts);

        mCircleMenuLayout.setOnMenuItemClickListener(new CircleMenuLayout.OnMenuItemClickListener() {

            @Override
            public void itemClick(View view, int pos) {
                switch (pos) {
                    case 0:
                        startActivity(new Intent(getActivity(), ShowTransationActivity.class));
//                        getActivity().onBackPressed();
                        break;
                    case 1:
                        if (isuser) {
                            startActivity(new Intent(getActivity(), EditActivity.class));
//                            getActivity().onBackPressed();
                        } else {
                            startActivity(new Intent(getActivity(), WelcomeActivity.class));
                            getActivity().finish();
                        }
                        break;
                    case 2:
                        startActivity(new Intent(getActivity(), SearchActivity.class));
//                        getActivity().onBackPressed();
                        break;
                    case 3:
                            Intent intent=new Intent(getActivity(),ExitActivity.class);
                            intent.putExtra("isuser",isuser);
                            startActivity(intent);
//                            getActivity().onBackPressed();
                        break;
                    case 4:
                           startActivity(new Intent(getActivity(), BaiduActivity.class));
                        break;
                    default:
                        Toast.makeText(getActivity().getApplicationContext(),
                                "hello",
                                Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void itemCenterClick(View view) {
            }
        });
        return view;
    }
}
