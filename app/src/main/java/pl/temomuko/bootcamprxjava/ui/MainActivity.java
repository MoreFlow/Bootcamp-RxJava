package pl.temomuko.bootcamprxjava.ui;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;
import pl.temomuko.bootcamprxjava.R;
import pl.temomuko.bootcamprxjava.model.User;
import pl.temomuko.bootcamprxjava.remote.APIManager;
import rx.Observable;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.user_image) ImageView userImageView;
    @BindView(R.id.user_name) TextView userTextView;

    private Subscription subscription;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
    }

    @Override
    protected void onStart() {
        super.onStart();
        loadUsers();
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (subscription != null) subscription.unsubscribe();
    }

    public void loadUsers() {
        subscription = APIManager.getInstance().getService().getUsers(5)
                .map(userResponse -> userResponse.getUsers())
                .flatMap(userList -> Observable.from(userList))
                .distinct()
                .filter(user -> !user.getName().getFirst().startsWith("Pupa"))
                .doOnNext(user -> user.getPicture().setAvatar(
                        String.format("https://api.adorable.io/avatars/285/%s@%s.png",
                                user.getName().getFirst(),
                                user.getName().getLast())))
                .take(1)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::setFirstPerson, this::showError);
    }

    public void setFirstPerson(User user) {
        userTextView.setText(String.format("%s%s", user.getName().getFirst(), user.getName().getLast()));
        Picasso.with(this).load(user.getPicture().getAvatar()).into(userImageView);
    }

    public void showError(Throwable throwable) {
        Toast.makeText(MainActivity.this, R.string.connection_error, Toast.LENGTH_SHORT).show();
        userTextView.setText(R.string.connection_error);
        Picasso.with(this).load(android.R.drawable.stat_notify_error).into(userImageView);
    }
}
