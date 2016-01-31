package hr.foi.fbrd.sensei.activities;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;

import com.afollestad.materialdialogs.AlertDialogWrapper;
import com.afollestad.materialdialogs.MaterialDialog;
import com.leo.simplearcloader.ArcConfiguration;
import com.leo.simplearcloader.SimpleArcDialog;

import hr.foi.fbrd.sensei.R;
import hr.foi.fbrd.sensei.mvp.view.BaseView;

public abstract class BaseActivity extends AppCompatActivity implements BaseView {

    SimpleArcDialog arcDialog;

    private static BaseActivity instance;

    public static BaseActivity getInstance() {
        return instance;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        instance = this;
        super.onCreate(savedInstanceState);
    }

    @Override
    public void showProgress() {
        if (arcDialog == null) {
            ArcConfiguration config = new ArcConfiguration(this);
            config.setText("Please wait...");
            arcDialog = new SimpleArcDialog(this);
            arcDialog.setConfiguration(config);
            arcDialog.setCanceledOnTouchOutside(false);
        }
        if (!arcDialog.isShowing() || !isFinishing()) {
            arcDialog.show();
        }
    }

    @Override
    public void hideProgress() {
        if (arcDialog != null && arcDialog.isShowing() && !isFinishing()) {
            arcDialog.dismiss();
        }
    }

    @Override
    public void showError(String message) {
        final AlertDialogWrapper.Builder matBuilder = new AlertDialogWrapper.Builder(this);
        matBuilder.setTitle(R.string.app_name);

        if (message != null) {
            matBuilder.setMessage(Html.fromHtml(message));
        } else {
            matBuilder.setMessage("");
        }
        matBuilder.setPositiveButton(android.R.string.ok, null);
        if (!isFinishing()) {
            matBuilder.show();
        }
    }

    @Override
    public void showDialog(String title, String message, MaterialDialog.SingleButtonCallback positiveCallback,
                           MaterialDialog.SingleButtonCallback negativeCallback, String positiveButtonText, String negativeButtonText) {
        MaterialDialog basicDialog = new MaterialDialog.Builder(this)
                .title(title)
                .content(message)
                .positiveText(positiveButtonText)
                .positiveColor(ContextCompat.getColor(this, R.color.colorAccent))
                .onPositive(positiveCallback)
                .onNegative(negativeCallback)
                .negativeText(negativeButtonText)
                .negativeColor(ContextCompat.getColor(this, R.color.colorAccent))
                .build();
        basicDialog.setCanceledOnTouchOutside(false);

        if (!isFinishing()) {
            basicDialog.show();
        }
    }

}
