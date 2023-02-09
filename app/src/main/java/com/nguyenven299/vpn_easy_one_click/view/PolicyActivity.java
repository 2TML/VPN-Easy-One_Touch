package com.nguyenven299.vpn_easy_one_click.view;

import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.bumptech.glide.Glide;
import com.nguyenven299.vpn_easy_one_click.BuildConfig;
import com.nguyenven299.vpn_easy_one_click.R;

public class PolicyActivity extends AppCompatActivity {
    private ImageView appImageView;
    private TextView policyContent, appNameTextView;
    private Button closeButton;
    private Boolean policyBool;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_policy);
        Intent intent = getIntent();
        policyBool = intent.getBooleanExtra("Policy", true);
        appImageView = findViewById(R.id.imageApp);
        policyContent = findViewById(R.id.policyConentTextView);
        closeButton = findViewById(R.id.tapAgreeButton);
        appNameTextView = findViewById(R.id.appNameTextView);
        closeButton.setOnClickListener((view) -> {
            this.finish();
        });

        if (policyBool) {
            appNameTextView.setText(this.getString(R.string.app_name)+" Policy");
        } else {
            appNameTextView.setText(this.getString(R.string.app_name)+" Information");
        }

        Window window = getWindow();
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.setStatusBarColor(ContextCompat.getColor(this, R.color.black));
        Glide.with(this).load(R.mipmap.image_app_foreground).into(appImageView);
        if (policyBool) {
            policyContent.setText(Html.fromHtml("<!DOCTYPE html>\n" +
                    "    <html>\n" +
                    "    <head>\n" +
                    "      <meta charset='utf-8'>\n" +
                    "      <meta name='viewport' content='width=device-width'>\n" +
                    "    </head>\n" +
                    "    <body>\n" +
                    "    <strong>Privacy Policy</strong> <p>\n" +
                    "                  NguyenVen built the " + this.getString(R.string.app_name) + " app as\n" +
                    "                  an Ad Supported app. This SERVICE is provided by\n" +
                    "                  NguyenVen at no cost and is intended for use as\n" +
                    "                  is.\n" +
                    "                </p> <p>\n" +
                    "                  This page is used to inform visitors regarding my\n" +
                    "                  policies with the collection, use, and disclosure of Personal\n" +
                    "                  Information if anyone decided to use my Service.\n" +
                    "                </p> <p>\n" +
                    "                  If you choose to use my Service, then you agree to\n" +
                    "                  the collection and use of information in relation to this\n" +
                    "                  policy. The Personal Information that I collect is\n" +
                    "                  used for providing and improving the Service. I will not use or share your information with\n" +
                    "                  anyone except as described in this Privacy Policy.\n" +
                    "                </p> <p>\n" +
                    "                  The terms used in this Privacy Policy have the same meanings\n" +
                    "                  as in our Terms and Conditions, which are accessible at\n" +
                    "                  " + this.getString(R.string.app_name) + " unless otherwise defined in this Privacy Policy.\n " +
                    "                </p> <br></br><p><strong>Information Collection and Use</strong></p> <p>\n" +
                    "                  For a better experience, while using our Service, I\n" +
                    "                  may require you to provide us with certain personally\n" +
                    "                  identifiable information. The information that\n" +
                    "                  I request will be retained on your device and is not collected by me in any way.\n" +
                    "                </p> <div><p>\n" +
                    "                    The app does use third-party services that may collect\n" +
                    "                    information used to identify you.\n" +
                    "                  </p> <p>\n" +
                    "                    Link to the privacy policy of third-party service providers used\n" +
                    "                    by the app\n" +
                    "                  </p> <ul><li><a href=\"https://www.google.com/policies/privacy/\" target=\"_blank\" rel=\"noopener noreferrer\">Google Play Services</a></li><!----><li><a href=\"https://firebase.google.com/policies/analytics\" target=\"_blank\" rel=\"noopener noreferrer\">Google Analytics for Firebase</a></li><!----><!----><!----><!----><!----><!----><!----><!----><!----><!----><!----><!----><!----><!----><!----><!----><!----><!----><!----><!----><!----><!----><!----><!----><!----></ul></div><br></br> <p><strong>Log Data</strong></p> <p>\n" +
                    "                  I want to inform you that whenever you\n" +
                    "                  use my Service, in a case of an error in the app\n" +
                    "                  I collect data and information (through third-party\n" +
                    "                  products) on your phone called Log Data. This Log Data may\n" +
                    "                  include information such as your device Internet Protocol\n" +
                    "                  (“IP”) address, device name, operating system version, the\n" +
                    "                  configuration of the app when utilizing my Service,\n" +
                    "                  the time and date of your use of the Service, and other\n" +
                    "                  statistics.\n" +
                    "                </p><br></br> <p><strong>Cookies</strong></p> <p>\n" +
                    "                  Cookies are files with a small amount of data that are\n" +
                    "                  commonly used as anonymous unique identifiers. These are sent\n" +
                    "                  to your browser from the websites that you visit and are\n" +
                    "                  stored on your device's internal memory.\n" +
                    "                </p> <p>\n" +
                    "                  This Service does not use these “cookies” explicitly. However,\n" +
                    "                  the app may use third-party code and libraries that use\n" +
                    "                  “cookies” to collect information and improve their services.\n" +
                    "                  You have the option to either accept or refuse these cookies\n" +
                    "                  and know when a cookie is being sent to your device. If you\n" +
                    "                  choose to refuse our cookies, you may not be able to use some\n" +
                    "                  portions of this Service.\n" +
                    "                </p> <br></br><p><strong>Service Providers</strong></p> <p>\n" +
                    "                  I may employ third-party companies and\n" +
                    "                  individuals due to the following reasons:\n" +
                    "                </p> <ul><li>To facilitate our Service;</li> <li>To provide the Service on our behalf;</li> <li>To perform Service-related services; or</li> <li>To assist us in analyzing how our Service is used.</li></ul> <p>\n" +
                    "                  I want to inform users of this Service\n" +
                    "                  that these third parties have access to their Personal\n" +
                    "                  Information. The reason is to perform the tasks assigned to\n" +
                    "                  them on our behalf. However, they are obligated not to\n" +
                    "                  disclose or use the information for any other purpose.>\n" +
                    "                </p><br></br> <p><strong>Security</strong></p> <p>\n" +
                    "                  I value your trust in providing us your\n" +
                    "                  Personal Information, thus we are striving to use commercially\n" +
                    "                  acceptable means of protecting it. But remember that no method\n" +
                    "                  of transmission over the internet, or method of electronic\n" +
                    "                  storage is 100% secure and reliable, and I cannot\n" +
                    "                  guarantee its absolute security.\n" +
                    "                </p> <br></br><p><strong>Links to Other Sites</strong></p> <p>\n" +
                    "                  This Service may contain links to other sites. If you click on\n" +
                    "                  a third-party link, you will be directed to that site. Note\n" +
                    "                  that these external sites are not operated by me.\n" +
                    "                  Therefore, I strongly advise you to review the\n" +
                    "                  Privacy Policy of these websites. I have\n" +
                    "                  no control over and assume no responsibility for the content,\n" +
                    "                  privacy policies, or practices of any third-party sites or\n" +
                    "                  services.\n" +
                    "                </p> <br></br><p><strong>Children’s Privacy</strong></p> <div><p>\n" +
                    "                    These Services do not address anyone under the age of 13.\n" +
                    "                    I do not knowingly collect personally\n" +
                    "                    identifiable information from children under 13 years of age. In the case\n" +
                    "                    I discover that a child under 13 has provided\n" +
                    "                    me with personal information, I immediately\n" +
                    "                    delete this from our servers. If you are a parent or guardian\n" +
                    "                    and you are aware that your child has provided us with\n" +
                    "                    personal information, please contact me so that\n" +
                    "                    I will be able to do the necessary actions.<br></br>\n" +
                    "                  </p></div> <!----> <p><strong>Changes to This Privacy Policy</strong></p> <p>\n" +
                    "                  I may update our Privacy Policy from\n" +
                    "                  time to time. Thus, you are advised to review this page\n" +
                    "                  periodically for any changes. I will\n" +
                    "                  notify you of any changes by posting the new Privacy Policy on\n" +
                    "                  this page.\n" +
                    "                </p> <p>This policy is effective as of 2023-02-09</p><br></br> <p><strong>Contact Us</strong></p> <p>\n" +
                    "                  If you have any questions or suggestions about my\n" +
                    "                  Privacy Policy, do not hesitate to contact me at nguyenven992@gmail.com.\n" +
                    "                </p> <p>This privacy policy page was created at <a href=\"https://privacypolicytemplate.net\" target=\"_blank\" rel=\"noopener noreferrer\">privacypolicytemplate.net </a>and modified/generated by <a href=\"https://app-privacy-policy-generator.nisrulz.com/\" target=\"_blank\" rel=\"noopener noreferrer\">App Privacy Policy Generator</a></p><br></br>\n" +
                    "    </body>\n" +
                    "    </html>\n" +
                    "      ", Html.FROM_HTML_MODE_COMPACT));
        } else {
            policyContent.setText(Html.fromHtml("<!DOCTYPE html>\n" +
                    "    <html>\n" +
                    "    <head>\n" +
                    "      <meta charset='utf-8'>\n" +
                    "      <meta name='viewport' content='width=device-width'>\n" +
                    "    </head>\n" +
                    "    <body>\n" +
                    "<strong>" + this.getString(R.string.app_name) + "</strong>\n" +
                    "</p><p>Developer: Ven Tran</p>\n" +
                    "</p><p>Publisher: Ven Tran</p>\n" +
                    "</p><p>Email: nguyenven992@gmail.com</p>\n" +
                    "</p><p>App version: " + BuildConfig.VERSION_NAME + "</p>\n" +
                    "\n" +
                    "</body>\n" +
                    "</html> ", Html.FROM_HTML_MODE_COMPACT));
        }

    }
}