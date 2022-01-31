package pav.fit.bstu.lab9;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.List;

public class CardViewModel extends AndroidViewModel {

    private CardRepository repository;
    private LiveData<List<ContactCard>> cards;

    public CardViewModel(@NonNull Application application){
        super(application);
        repository = new CardRepository(application);
        cards = repository.getAllCards();
    }

    public void insert(ContactCard card){
        repository.insertCard(card);
    }

    public void update(ContactCard card){
        repository.updateCard(card);
    }

    public void delete(ContactCard card){
        repository.deleteCard(card);
    }

    public LiveData<List<ContactCard>> getCards(){
        return cards;
    }
}
