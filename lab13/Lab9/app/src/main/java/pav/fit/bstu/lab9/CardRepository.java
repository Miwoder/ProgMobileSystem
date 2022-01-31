package pav.fit.bstu.lab9;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import java.util.List;

public class CardRepository {
    private CardDao cardDao;
    private LiveData<List<ContactCard>> cards;

    public CardRepository(Application application){
        CardDatabase db = CardDatabase.getInstance(application);
        cardDao = db.cardDao();
        cards = cardDao.getAll();
    }

    public void insertCard(ContactCard card){
        new InsertCardAsyncTask(cardDao).execute(card);
    }

    public void updateCard(ContactCard card){
        new UpdateCardAsyncTask(cardDao).execute(card);
    }

    public void deleteCard(ContactCard card){
        new DeleteCardAsyncTask(cardDao).execute(card);
    }

    public LiveData<List<ContactCard>> getAllCards(){
        return cards;
    }

    private static class InsertCardAsyncTask extends AsyncTask<ContactCard, Void, Void> {
        private CardDao cardDao;

        private InsertCardAsyncTask(CardDao cardDao){
            this.cardDao = cardDao;
        }

        @Override
        protected Void doInBackground(ContactCard... cards) {
            cardDao.insert(cards[0]);
            return null;
        }
    }
    private static class UpdateCardAsyncTask extends AsyncTask<ContactCard, Void, Void> {
        private CardDao cardDao;

        private UpdateCardAsyncTask(CardDao cardDao){
            this.cardDao = cardDao;
        }

        @Override
        protected Void doInBackground(ContactCard... cards) {
            cardDao.update(cards[0]);
            return null;
        }
    }

    private static class DeleteCardAsyncTask extends AsyncTask<ContactCard, Void, Void> {
        private CardDao cardDao;

        private DeleteCardAsyncTask(CardDao cardDao){
            this.cardDao = cardDao;
        }

        @Override
        protected Void doInBackground(ContactCard... cards) {
            cardDao.delete(cards[0]);
            return null;
        }
    }





}
