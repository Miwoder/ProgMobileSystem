package pav.fit.bstu.lab9;

import android.content.Context;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

@Database(entities = {ContactCard.class}, version = 1)
public abstract class CardDatabase extends RoomDatabase {
    public abstract CardDao cardDao();

    private static CardDatabase instance;

    public static CardDatabase getInstance(Context context){
        if(instance == null){
            instance = Room.databaseBuilder(context.getApplicationContext(), CardDatabase.class, "database")
                    .addCallback(roomCallback)
                    .build();

        }
        return instance;
    }

    private static RoomDatabase.Callback roomCallback = new RoomDatabase.Callback(){
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);
            new DbTask(instance).execute();
        }
    };

    private static class DbTask extends AsyncTask<Void, Void, Void> {
        private CardDao cardDao;

        private DbTask(CardDatabase db){
            cardDao = db.cardDao();


        }

        @Override
        protected Void doInBackground(Void... voids) {
            return null;
        }
    }
}
