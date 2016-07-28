package pl.temomuko.bootcamprxjava.ui.main;

import pl.temomuko.bootcamprxjava.remote.UserAPIManager;
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
        subscription = UserAPIManager.getInstance().getService().getUsers(5)
                .map(userResponse -> userResponse.getUsers())
                .flatMap(userList -> Observable.from(userList))
                .distinct()
                .filter(user -> !user.getName().getFirst().startsWith("Pupa"))
                .doOnNext(user -> user.getPicture().setAvatar(
                        String.format("https://api.adorable.io/avatars/285/%s@%s.png",
                                user.getName().getFirst(),
                                user.getName().getLast())))
                .take(1)
//                 probowalem zrobic jeszcze to ze stolica, jest zrobiony caly retrofit do countryAPI (service/manager/model) ale nie udalo mi sie
//                .flatMap(user ->
//                        CountryAPIManager.getInstance().getService().getCountryByCapital(user.getLocation().getCity())
//                            .map(countryResponse -> countryResponse.getName())
//
//                )
//                .onErrorResumeNext(throwable -> null)
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
