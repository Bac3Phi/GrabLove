package dev.uit.grablove.Fragment;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import at.markushi.ui.CircleButton;
import de.hdodenhof.circleimageview.CircleImageView;
import dev.uit.grablove.Constants;
import dev.uit.grablove.Model.Message;
import dev.uit.grablove.Model.UserType;
import dev.uit.grablove.R;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by Administrator on 10/14/2017.
 */

public class Tab1SettingFragment extends Fragment {
    private CircleImageView imAvatar;
    private TextView tvName;

    private FirebaseFirestore db;

    private SharedPreferences pre;

            @Override
            public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
                View rootView = inflater.inflate(R.layout.fragment_tab1_setting, container, false);
  //              RelativeLayout layout =(RelativeLayout) rootView.findViewById(R.id.relativeLayoutTab1);
//                layout.setBackgroundResource(R.drawable.bg_2);

                pre= this.getActivity().getSharedPreferences(Constants.REF_NAME, Context.MODE_PRIVATE);

                imAvatar = (CircleImageView) rootView.findViewById(R.id.circleivTab1);
                tvName = (TextView) rootView.findViewById(R.id.txtNameTab1);

                db = FirebaseFirestore.getInstance();
                db.collection("Users/").document(pre.getString(Constants.USER_KEY, ""))
                       .addSnapshotListener(new EventListener<DocumentSnapshot>() {
                           @Override
                           public void onEvent(DocumentSnapshot documentSnapshot, FirebaseFirestoreException e) {
                               Glide.with(getContext()).load(documentSnapshot.getString(Constants.DB_USER_AVATAR))
                                       .apply(new RequestOptions()
                                                .dontTransform()
                                                .dontAnimate()
                                                .placeholder(R.drawable.loading))
                                       .into(imAvatar);
                               tvName.setText(documentSnapshot.getString(Constants.DB_USER_FULL_NAME));
                           }
                       });

                CircleButton btnSettingFilter = (CircleButton) rootView.findViewById(R.id.btnFilterSettingTab1);
                btnSettingFilter.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent test = new Intent(Tab1SettingFragment.this.getActivity(),fragment_tab1_setting_filter.class);
                        startActivity(test);
                    }
                });
                CircleButton btnSettingProfile = (CircleButton) rootView.findViewById(R.id.btnProfileSettingTab1);
                btnSettingProfile.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent test = new Intent(Tab1SettingFragment.this.getActivity(),fragment_tab1_setting_profile.class);
                        startActivity(test);
                    }
                });

                imAvatar.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(getActivity(),FragmentTab2Profile.class);
                        Bundle bundle = new Bundle();
                        bundle.putString("Avatar",pre.getString(Constants.USER_AVATAR,""));
                        bundle.putString("Name",pre.getString(Constants.USER_NAME,""));
                        bundle.putInt("Age",pre.getInt(Constants.USER_AGE,0));
                        bundle.putString("Description", pre.getString(Constants.USER_DESCRIPTION, "Chưa có mô tả về bản thân"));
                        intent.putExtra("Clicked",bundle);
                        startActivity(intent);
                    }
                });
                return rootView;

    }
}
