package pav.fit.bstu.lab9;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class CardAdapter extends RecyclerView.Adapter<CardAdapter.CardViewHolder> {
    List<ContactCard> col = new ArrayList<>();




    private int position;

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }


    @NonNull
    @Override
    public CardViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_item, parent, false);
        CardViewHolder pvh = new CardViewHolder(v);
        return pvh;
    }

    @Override
    public void onBindViewHolder(@NonNull CardViewHolder holder, int position) {
        holder.textViewName.setText(col.get(position).getFullName());
        holder.textViewWork.setText(col.get(position).getWorkPlace());
        holder.textViewEmail.setText(col.get(position).getEmail());
        holder.textViewMobile.setText(col.get(position).getMobile());
        holder.textViewLink.setText(col.get(position).getLink());
        byte[] decodedMessage = android.util.Base64.decode(col.get(position).getPhoto(), Base64.DEFAULT);
        Bitmap bm = BitmapFactory.decodeByteArray(decodedMessage, 0, decodedMessage.length);
        holder.imageView.setImageBitmap(bm);
        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                setPosition(holder.getPosition());
                return false;
            }
        });
    }

    public String getItemIdByPos(int pos){
        return col.get(pos).getId();
    }

    @Override
    public int getItemCount() {
        return col.size();
    }

    public void setCards(List<ContactCard> col){
        this.col = col;
        notifyDataSetChanged();
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    public ContactCard getItemByPosition(int pos){
        return col.get(pos);
    }



    public static class CardViewHolder extends RecyclerView.ViewHolder implements View.OnCreateContextMenuListener {
        CardView cv;
        ImageView imageView;
        TextView textViewName;
        TextView textViewWork;
        TextView textViewEmail;
        TextView textViewMobile;
        TextView textViewLink;



        @Override
        public void onCreateContextMenu(ContextMenu contextMenu, View view, ContextMenu.ContextMenuInfo contextMenuInfo){
            contextMenu.add(Menu.NONE, R.id.edit,
                    Menu.NONE, R.string.edit);
            contextMenu.add(Menu.NONE, R.id.delete,
                    Menu.NONE, R.string.delete);

        }

        CardViewHolder(View itemView) {
            super(itemView);
            cv = (CardView) itemView.findViewById(R.id.card_item);
            imageView = itemView.findViewById(R.id.image);
            textViewName = itemView.findViewById(R.id.name);
            textViewWork = itemView.findViewById(R.id.work);
            textViewEmail = itemView.findViewById(R.id.email);
            textViewMobile = itemView.findViewById(R.id.mobile);
            textViewLink = itemView.findViewById(R.id.link);
            itemView.setOnCreateContextMenuListener(this);
        }


    }
}