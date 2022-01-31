package com.varets.lab101;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.varets.lab101.db.Entities.ModelCases;

import java.util.ArrayList;
import java.util.List;

public class CasesAdapter extends RecyclerView.Adapter<CasesAdapter.CaseHolder> {
    private List<ModelCases> cases = new ArrayList<>();
    private OnItemClickListener listener;

    @NonNull
    @Override
    public CaseHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.case_item, parent, false);
        return new CaseHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull CaseHolder holder, int position) {
        ModelCases currentCase = cases.get(position);
        holder.textViewNameCase.setText(currentCase.getNameCase());
        holder.textViewLocationCase.setText(currentCase.getLocationCase());
        holder.textViewDateCase.setText(currentCase.getDateCase());
    }

    @Override
    public int getItemCount() {
        return cases.size();
    }

    public void setCases(List<ModelCases> cases) {
        this.cases = cases;
        notifyDataSetChanged();
    }

    public ModelCases getCaseAt(int position) {
        return cases.get(position);
    }

    class CaseHolder extends RecyclerView.ViewHolder {

        private TextView textViewDateCase;
        private TextView textViewNameCase;
        private TextView textViewLocationCase;

        public CaseHolder(@NonNull View itemView) {
            super(itemView);
            textViewDateCase = itemView.findViewById(R.id.dateCase);
            textViewLocationCase = itemView.findViewById(R.id.locationCase);
            textViewNameCase = itemView.findViewById(R.id.nameCase);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    if (listener != null && position != RecyclerView.NO_POSITION) {
                        listener.onItemClick(cases.get(position));
                    }
                }
            });
        }
    }

    public interface OnItemClickListener {
        void onItemClick(ModelCases modelCases);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }
}
