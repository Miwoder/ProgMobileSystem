package pav.fit.bstu.lab9;

import androidx.appcompat.app.AppCompatActivity;

import android.animation.ObjectAnimator;
import android.app.ActivityOptions;
import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;


import androidx.cardview.widget.CardView;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelStoreOwner;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {
    CardViewModel cardViewModel;
    RecyclerView recyclerView;
    CardAdapter cardAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.recyclerview);
        //////////////////////////////////////////////////////
        //////////////////////////////////////////////////////
        recyclerView.addOnItemTouchListener(
                new RecyclerItemClickListener(this, recyclerView ,new RecyclerItemClickListener.OnItemClickListener() {
                    @Override public void onItemClick(View view, int position) {
                        ObjectAnimator.ofFloat(view, "rotation", 0f, 360f).start();
                    }

                    @Override
                    public void onLongItemClick(View view, int position) {
                    }

                })
        );
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        registerForContextMenu(recyclerView);
        cardAdapter = new CardAdapter();
        recyclerView.setAdapter(cardAdapter);

        cardViewModel = new ViewModelProvider(this).get(CardViewModel.class);
        cardViewModel.getCards().observe(this, new Observer<List<ContactCard>>() {
            @Override
            public void onChanged(List<ContactCard> contactCards) {
                cardAdapter.setCards(contactCards);
            }
        });
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        int position = -1;
        try {
            position = ((CardAdapter) recyclerView.getAdapter()).getPosition();
        } catch (Exception e) {
            return super.onContextItemSelected(item);
        }
        switch (item.getItemId()) {
            case R.id.edit:
                editItem(cardAdapter.getItemByPosition(position));
                return true;
            case R.id.delete:
                CardView card =  (CardView) recyclerView.findViewHolderForAdapterPosition(position).itemView;

                /////////////////////////////////////////////////////////////
                /////////////////////////////////////////////////////////////
                card.setForeground(getResources().getDrawable(R.drawable.explosion));
                AnimationDrawable animation =  (AnimationDrawable)card.getForeground();
                animation.start();

                animation.start();
                long totalDuration = 0;
                for(int i = 0; i< animation.getNumberOfFrames();i++){
                    totalDuration += animation.getDuration(i);
                }
                Timer timer = new Timer();
                TimerTask timerTask = new TimerTask(){
                    @Override
                    public void run() {
                        animation.stop();
                    }
                };
                timer.schedule(timerTask, totalDuration);
                checkIfAnimationDone(animation, position);

            default:
                return super.onContextItemSelected(item);
        }
    }

    ///////////////////////////////////////////////////
    //////////////////////////////////////////////////
    private void checkIfAnimationDone(AnimationDrawable anim, int pos){
        final AnimationDrawable a = anim;
        int timeBetweenChecks = 100;
        Handler h = new Handler();
        h.postDelayed(new Runnable(){
            public void run(){
                if (a.getCurrent() != a.getFrame(a.getNumberOfFrames() - 1)){
                    checkIfAnimationDone(a, pos);
                }
                else{
                    cardViewModel.delete(cardAdapter.getItemByPosition(pos));
                }
            }
        }, timeBetweenChecks);
    };



    public void editItem(ContactCard card) {
        Intent intent = new Intent(this, AddCardActivity.class);
        intent.putExtra(AddCardActivity.PAR_ID, card.getId());
        intent.putExtra(AddCardActivity.PAR_NAME, card.getFullName());
        intent.putExtra(AddCardActivity.PAR_WORK, card.getWorkPlace());
        intent.putExtra(AddCardActivity.PAR_PHOTO, card.getPhoto());
        intent.putExtra(AddCardActivity.PAR_EMAIL, card.getEmail());
        intent.putExtra(AddCardActivity.PAR_MOBILE, card.getMobile());
        intent.putExtra(AddCardActivity.PAR_LINK, card.getLink());
        startActivity(intent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.new_item:
                createNewCard();
                return true;

        }
        return super.onOptionsItemSelected(item);
    }

    public void createNewCard() {
        Intent intent = new Intent(this, AddCardActivity.class);
       startActivity(intent);
    }
}