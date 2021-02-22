package com.nhatran241.ezlib.custom.dialog;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;


import com.nhatran241.ezlib.R;

import java.util.Objects;

public class PolicyDialog extends Dialog {
    private ImageView vAppIcon;
    private TextView vTvPrivacy,vTvInformation,vTvLogData,vTvCookies,vTvService,vTvSecurity,vTvLinkToOtherSide,vTvChildren,vTvChanges,vTvContactUs;
    private CardView vCvAgree;

    public PolicyDialog(@NonNull Context context) {
        super(context);
        init(context);
    }


    private void init(Context context) {
        setCancelable(false);
        View view = LayoutInflater.from(context).inflate(R.layout.library_dialog_policy, null);
        Objects.requireNonNull(getWindow()).setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        setContentView(view);
        vAppIcon = view.findViewById(R.id.iv_icon_launcher);
        vTvPrivacy = view.findViewById(R.id.tv_privacy);
        vTvInformation = view.findViewById(R.id.tv_information_collection_and_use);
        vTvLogData = view.findViewById(R.id.tv_log_data);
        vTvCookies = view.findViewById(R.id.tv_cookies);
        vTvService = view.findViewById(R.id.tv_service_providers);
        vTvSecurity = view.findViewById(R.id.tv_security);
        vTvLinkToOtherSide = view.findViewById(R.id.tv_link_to_other_sites);
        vTvChildren = view.findViewById(R.id.tv_children);
        vTvChanges = view.findViewById(R.id.tv_changes);
        vTvContactUs = view.findViewById(R.id.tv_contact);
        vCvAgree = view.findViewById(R.id.cv_agree);

            }

    @SuppressLint("SetTextI18n")
    public void show(int icon,String mStoreName, String mAppName, String mChangeDate, String mEmail, IPolicyDialogListener iPolicyDialogListener) {
        vAppIcon.setImageResource(icon);
        vTvPrivacy.setText(mStoreName+" built the "+mAppName+" app as a Free app. This SERVICE is provided by "+mStoreName+" at no cost and is intended for use as is.\n" +
                "\n" +
                "This page is used to inform visitors regarding my policies with the collection, use, and disclosure of Personal Information if anyone decided to use my Service.\n" +
                "\n" +
                "If you choose to use my Service, then you agree to the collection and use of information in relation to this policy. The Personal Information that I collect is used for providing and improving the Service. I will not use or share your information with anyone except as described in this Privacy Policy.\n" +
                "\n" +
                "The terms used in this Privacy Policy have the same meanings as in our Terms and Conditions, which is accessible at " +mAppName+" unless otherwise defined in this Privacy Policy.");
        vTvInformation.setText("For a better experience, while using our Service, I may require you to provide us with certain personally identifiable information, including but not limited to "+mStoreName+". The information that I request will be retained on your device and is not collected by me in any way.\n" +
                "\n" +
                "The app does use third party services that may collect information used to identify you.\n" +
                "\n" +
                "Link to privacy policy of third party service providers used by the app" +
                "+AdMob\n" +
                "+Google Analytics for Firebase");
        vTvLogData.setText("I want to inform you that whenever you use my Service, in a case of an error in the app I collect data and information (through third party products) on your phone called Log Data. This Log Data may include information such as your device Internet Protocol (“IP”) address, device name, operating system version, the configuration of the app when utilizing my Service, the time and date of your use of the Service, and other statistics.");
        vTvCookies.setText("Cookies are files with a small amount of data that are commonly used as anonymous unique identifiers. These are sent to your browser from the websites that you visit and are stored on your device's internal memory.\n" +
                "\n" +
                "This Service does not use these “cookies” explicitly. However, the app may use third party code and libraries that use “cookies” to collect information and improve their services. You have the option to either accept or refuse these cookies and know when a cookie is being sent to your device. If you choose to refuse our cookies, you may not be able to use some portions of this Service.");
        vTvService.setText("I may employ third-party companies and individuals due to the following reasons:\n" +
                "\n" +
                "To facilitate our Service;\n" +
                "To provide the Service on our behalf;\n" +
                "To perform Service-related services; or\n" +
                "To assist us in analyzing how our Service is used.\n" +
                "I want to inform users of this Service that these third parties have access to your Personal Information. The reason is to perform the tasks assigned to them on our behalf. However, they are obligated not to disclose or use the information for any other purpose." );
        vTvSecurity.setText("I value your trust in providing us your Personal Information, thus we are striving to use commercially acceptable means of protecting it. But remember that no method of transmission over the internet, or method of electronic storage is 100% secure and reliable, and I cannot guarantee its absolute security.");
        vTvLinkToOtherSide.setText("This Service may contain links to other sites. If you click on a third-party link, you will be directed to that site. Note that these external sites are not operated by me. Therefore, I strongly advise you to review the Privacy Policy of these websites. I have no control over and assume no responsibility for the content, privacy policies, or practices of any third-party sites or services.");
        vTvChildren.setText("These Services do not address anyone under the age of 13. I do not knowingly collect personally identifiable information from children under 13 years of age. In the case I discover that a child under 13 has provided me with personal information, I immediately delete this from our servers. If you are a parent or guardian and you are aware that your child has provided us with personal information, please contact me so that I will be able to do necessary actions.");
        vTvChanges.setText("I may update our Privacy Policy from time to time. Thus, you are advised to review this page periodically for any changes. I will notify you of any changes by posting the new Privacy Policy on this page.\n" +
                "\n" +
                "This policy is effective as of"+mChangeDate);
        vTvContactUs.setText("If you have any questions or suggestions about my Privacy Policy, do not hesitate to contact me at "+mEmail+".");
        vCvAgree.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                iPolicyDialogListener.onAgreeClick();
                dismiss();
            }
        });
        super.show();
    }

    public PolicyDialog(@NonNull Context context, int themeResId) {
        super(context, themeResId);
        init(context);
    }

    protected PolicyDialog(@NonNull Context context, boolean cancelable, @Nullable OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
        init(context);
    }

    public interface IPolicyDialogListener{
        void onAgreeClick();
    }
}
