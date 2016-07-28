package pl.temomuko.bootcamprxjava.ui.main;

import pl.temomuko.bootcamprxjava.model.User;

public interface MainView {
    void setFirstPerson(User user);
    void showError(Throwable throwable);
}
