package com.example.spendcelebsmoney.LocalDatabase;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import java.util.List;

class Repository {

    private UserDAO userDAO;
    private LiveData<List<User>> allUsers;

    Repository(Application application) {
        Database database = Database.fetchDB(application);
        userDAO = database.userDAO();
        allUsers = userDAO.allUserWorth();
    }

    void createUser(User user) {
        new CreateUser(userDAO).execute(user);
    }

    void updateUser(User user) {
        new UpdateUser(userDAO).execute(user);
    }

    void deleteUser(User user) {
        new DeleteUser(userDAO).execute(user);
    }
    void bulkDeletion() {
        new BulkDelete(userDAO).execute();
    }
    LiveData<List<User>> getAllUsers() {
        return allUsers;
    }

    private static class CreateUser extends AsyncTask<User, Void, Void> {
        private UserDAO userDAO;

        private CreateUser(UserDAO userDAO) {
            this.userDAO = userDAO;
        }

        @Override
        protected Void doInBackground(User... users) {
                userDAO.insertUser((users[0]));
            return null;
        }
    }

    private static class UpdateUser extends AsyncTask<User, Void, Void> {
        private UserDAO userDAO;

        private UpdateUser(UserDAO userDAO) {
            this.userDAO = userDAO;
        }

        @Override
        protected Void doInBackground(User... users) {
            userDAO.updateUser((users[0]));
            return null;
        }
    }

    private static class DeleteUser extends AsyncTask<User, Void, Void> {
        private UserDAO userDAO;

        private DeleteUser(UserDAO userDAO) {
            this.userDAO = userDAO;
        }

        @Override
        protected Void doInBackground(User... users) {
            userDAO.deleteUser((users[0]));
            return null;
        }
    }

    private static class BulkDelete extends AsyncTask<Void, Void, Void> {
        private UserDAO userDAO;

        private BulkDelete(UserDAO userDAO) {
            this.userDAO = userDAO;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            userDAO.bulkDelete();
            return null;
        }
    }

}
