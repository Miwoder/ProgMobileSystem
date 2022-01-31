package com.varets.lab101.db.Repository;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import com.varets.lab101.db.CasesRoomDatabase;
import com.varets.lab101.db.Dao.DaoCases;
import com.varets.lab101.db.Entities.ModelCases;

import java.util.Date;
import java.util.List;

public class CasesRepository {
    public DaoCases daoCases;
    private LiveData<List<ModelCases>> AllCases ;

    public CasesRepository(Application application) {
        CasesRoomDatabase db = CasesRoomDatabase.getCaseDatabase(application);
        this.daoCases = db.DaoCases();
        this.AllCases = daoCases.getAllCases();
    }
    public LiveData<List<ModelCases>> getCases() {
        return this.AllCases ;
    }

    public void addCase(ModelCases modelCases) {
        new InsertCaseAsyncTask(daoCases).execute(modelCases);
    }
    public void updateCase(ModelCases modelCases) {
        new UpdateCaseAsyncTask(daoCases).execute(modelCases);
    }
    public void deleteCase(ModelCases modelCases) {
        new DeleteCaseAsyncTask(daoCases).execute(modelCases);
    }
    private static class InsertCaseAsyncTask extends AsyncTask<ModelCases,Void,Void>{
        private DaoCases daoCases;

        private InsertCaseAsyncTask(DaoCases daoCases){
            this.daoCases = daoCases;
        }
        @Override
        protected Void doInBackground(ModelCases... modelCases) {
            daoCases.addCase(modelCases[0]);
            return null;
        }
    }
    private static class DeleteCaseAsyncTask extends AsyncTask<ModelCases,Void,Void>{
        private DaoCases daoCases;

        private DeleteCaseAsyncTask(DaoCases daoCases){
            this.daoCases = daoCases;
        }
        @Override
        protected Void doInBackground(ModelCases... modelCases) {
            daoCases.deleteCase(modelCases[0]);
            return null;
        }
    }
    private static class UpdateCaseAsyncTask extends AsyncTask<ModelCases,Void,Void>{
        private DaoCases daoCases;

        private UpdateCaseAsyncTask(DaoCases daoCases){
            this.daoCases = daoCases;
        }
        @Override
        protected Void doInBackground(ModelCases... modelCases) {
            daoCases.updateCase(modelCases[0]);
            return null;
        }
    }

}
