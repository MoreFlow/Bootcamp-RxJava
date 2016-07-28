package pl.temomuko.bootcamprxjava.ui.main;

import pl.temomuko.bootcamprxjava.remote.APIManager;
import rx.Observable;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class MainPresenter {

    private Subscription subscription;
    private MainView view;

    public MainPresenter(MainView view) {
        this.view = view;
    }

    public void loadUsers() {
        subscription = APIManager.getInstance("https://randomuser.me/").getService().getUsers(5)
                .map(userResponse -> userResponse.getUsers())
                .flatMap(userList -> Observable.from(userList))
                .distinct()
                .filter(user -> !user.getName().getFirst().startsWith("Pupa"))
                //.flatMap()
                .doOnNext(user -> user.getPicture().setAvatar(
                        String.format("https://api.adorable.io/avatars/285/%s@%s.png",
                                user.getName().getFirst(),
                                user.getName().getLast())))
                .take(1)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(view::setFirstPerson, view::showError);
    }

    public void onStop() {
        if (subscription != null) {
            subscription.unsubscribe();
        }
    }
}
