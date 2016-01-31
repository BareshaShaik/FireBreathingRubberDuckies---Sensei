package hr.foi.fbrd.sensei.mvp.view;

import com.afollestad.materialdialogs.MaterialDialog;


public interface BaseView {

    void showProgress();

    void hideProgress();

    void showError(String message);

    void showDialog(String title, String message, MaterialDialog.SingleButtonCallback positiveCallback,
                    MaterialDialog.SingleButtonCallback negativeCallback, String positiveButtonText, String negativeButtonText);
}
