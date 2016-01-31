package hr.foi.fbrd.sensei.activities;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.canelmas.let.AskPermission;
import com.canelmas.let.Let;
import com.dmacan.input.entity.Input;
import com.dmacan.input.manager.InputManager;
import com.easyandroidanimations.library.Animation;
import com.easyandroidanimations.library.SlideInAnimation;
import com.eftimoff.androipathview.PathView;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import hr.foi.fbrd.sensei.R;
import hr.foi.fbrd.sensei.helpers.FactoryHelper;
import hr.foi.fbrd.sensei.helpers.Preferences;
import hr.foi.fbrd.sensei.mvp.presenter.LoginPresenter;
import hr.foi.fbrd.sensei.mvp.view.DeviceView;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class SplashActivity extends BaseActivity implements DeviceView {

    @Bind(R.id.imgLogo)
    ImageView imgLogo;
    @Bind(R.id.pathView)
    PathView pathView;
    @Bind(R.id.btnLoginFacebook)
    Button btnLoginFacebook;

    LoginPresenter presenter;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        ButterKnife.bind(this);
        presenter = FactoryHelper.getPresenter(this);
                pathView.getPathAnimator()
                .duration(1500)
                .listenerEnd(this::checkIsUserLoggedIn)
                .start();
        Glide.with(this)
                .load(R.drawable.sensei_v1)
                .into(imgLogo);
        for (Input input : InputManager.getAllAvailableInputs(this, true)) {
            Log.i("DAM", input.toString());
        }
    }

    @AskPermission({
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_COARSE_LOCATION,
    })
    @OnClick(R.id.btnLoginFacebook)
    protected void onBtnLoginClick() {
        presenter.facebookLogin();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        Let.handle(this, requestCode, permissions, grantResults);
    }

    private void checkIsUserLoggedIn() {
        Preferences.isUserStored()
                .subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(data -> {
                    if (data) {
                        proceed();
                    } else {
                        new SlideInAnimation(btnLoginFacebook)
                                .setDirection(Animation.DIRECTION_DOWN)
                                .animate();
                    }
                });
    }

    @Override
    protected void onResume() {
        super.onResume();
        presenter.onViewResume();
    }

    @Override
    protected void onPause() {
        presenter.onViewPause();
        super.onPause();
    }

    public void onFailure(String message) {
        hideProgress();
        Toast.makeText(SplashActivity.this, message, Toast.LENGTH_SHORT).show();
        Log.e("ERROR", message);
    }

    public void onSuccess(String message) {
        presenter.pairDevices();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        FactoryHelper.getCallbackManager().onActivityResult(requestCode, resultCode, data);
    }

    private void proceed() {
        finish();
        startActivity(new Intent(this, MainActivity.class));
    }

    @Override
    public void onDeviceSuccess() {
        hideProgress();
        proceed();
    }

    @Override
    public void onDeviceFailure(String message) {
        hideProgress();
        Toast.makeText(SplashActivity.this, message, Toast.LENGTH_SHORT).show();
        Log.e("ERROR", message);
    }
}
