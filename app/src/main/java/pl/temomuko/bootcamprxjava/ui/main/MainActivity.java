package pl.temomuko.bootcamprxjava.ui.main;

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

public class MainActivity extends AppCompatActivity implements MainView {

    @BindView(R.id.user_name) TextView userTextView;
    @BindView(R.id.user_image) ImageView userImageView;

    private MainPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        presenter = new MainPresenter(this);
    }

    @Override
    protected void onStart() {
        super.onStart();
        presenter.loadUsers();
    }

    @Override
    protected void onStop() {
        super.onStop();
        presenter.onStop();
    }

    public void setFirstPerson(User user) {
        userTextView.setText(String.format("%s %s", user.getName().getFirst(), user.getName().getLast()));
        Picasso.with(this).load(user.getPicture().getAvatar()).into(userImageView);
    }

    public void showError(Throwable throwable) {
        Toast.makeText(MainActivity.this, R.string.connection_error, Toast.LENGTH_SHORT).show();
        userTextView.setText(R.string.connection_error);
        Picasso.with(this).load(android.R.drawable.stat_notify_error).into(userImageView);
    }
}
