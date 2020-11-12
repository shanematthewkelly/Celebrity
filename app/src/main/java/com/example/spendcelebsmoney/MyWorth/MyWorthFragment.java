package com.example.spendcelebsmoney.MyWorth;


import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;
import com.example.spendcelebsmoney.LocalDatabase.User;
import com.example.spendcelebsmoney.LocalDatabase.UserViewModel;
import com.example.spendcelebsmoney.Other.Masthead;
import com.example.spendcelebsmoney.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import org.jetbrains.annotations.NotNull;
import java.util.List;
import java.util.Objects;
import static android.app.Activity.RESULT_OK;


public class MyWorthFragment extends Fragment {

    private UserViewModel userViewModel;
    private Dialog deletePrompt;

    private static final int ADD = 1;
    private static final int EDIT = 2;


    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_my_worth, container, false);

        //instantiate a new Dialog box within the context
        deletePrompt = new Dialog(Objects.requireNonNull(getActivity()));

        FloatingActionButton addUserWorth =  v.findViewById(R.id.floatingActionButton);
        RecyclerView userRecycler = v.findViewById(R.id.userList);

        userRecycler.setLayoutManager(new LinearLayoutManager(getContext()));
        userRecycler.setHasFixedSize(true);

        UserAdapter adapter = new UserAdapter();
        userRecycler.setAdapter(adapter);

        addUserWorth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //starts the create activity
                Intent toCreateUser = new Intent(getActivity(), CreateUser.class);
                startActivityForResult(toCreateUser, ADD);
            }
        });

        //observe the view model
        userViewModel = new ViewModelProvider(this).get(UserViewModel.class);
        userViewModel.getUsers().observe(getViewLifecycleOwner(), new Observer<List<User>>() {
            @Override
            public void onChanged(List<User> users) {
                adapter.setUsers(users);
            }
        });


        //Calling to the adapter
        adapter.setEditButtonClickListener(new UserAdapter.EditButtonClickListener() {
            @Override
            public void editButtonClicked(User user) {
                Intent intent = new Intent(getActivity(), UpdateUser.class);
                intent.putExtra(UpdateUser.ID, user.getId());
                intent.putExtra(UpdateUser.NAME, user.getMyName());
                intent.putExtra(UpdateUser.DESCRIPTION, user.getMyDescription());
//              intent.putExtra(UpdateUser.PHOTO, user.getMyPhoto());
                intent.putExtra(UpdateUser.WORTH, user.getMyWorth());

                startActivityForResult(intent, EDIT);
            }
        });


        //Calling to the adapter to delete user
        adapter.setDeleteButtonClickListener(new UserAdapter.DeleteButtonClickListener() {
            @Override
            public void deleteButtonClicked(User user) {
                userViewModel.deleteUser(user);
            }
        });

        return v;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == ADD && resultCode == RESULT_OK) {


            assert data != null;
            String name = data.getStringExtra(CreateUser.NAME);
            String description = data.getStringExtra(CreateUser.DESCRIPTION);
            String photo = data.getStringExtra(CreateUser.PHOTO);
            int worth = data.getIntExtra(CreateUser.WORTH,1000);


            User user = new User(name, description, photo, worth);
            userViewModel.createUser(user);

            Toast.makeText(getActivity(), "User Saved", Toast.LENGTH_SHORT).show();
        }   else if (requestCode == EDIT && resultCode == RESULT_OK){

            assert data != null;
            int userId = data.getIntExtra(UpdateUser.ID, -1);

            //Error Handler
            if (userId == -1) {
                Toast.makeText(getActivity(), "Card Not Saved.", Toast.LENGTH_SHORT).show();
                return;
            }

            //Rinse and repeat as above
            String name = data.getStringExtra(CreateUser.NAME);
            String description = data.getStringExtra(CreateUser.DESCRIPTION);
            String photo = data.getStringExtra(CreateUser.PHOTO);
            int worth = data.getIntExtra(CreateUser.WORTH,1000);

            User user = new User(name, description, photo, worth);
            user.setId(userId);
            userViewModel.updateUser(user);

            Toast.makeText(getActivity(), "Card Updated", Toast.LENGTH_SHORT).show();

        } else {
            Toast.makeText(getActivity(), "User Not Saved", Toast.LENGTH_SHORT).show();
        }
    }



    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    private void displayModal() {

        //make the background of the dialog transparent
        Objects.requireNonNull(deletePrompt.getWindow()).setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        //show the dialog box
        deletePrompt.show();
        //animation setup
        Animation SmallToBig = AnimationUtils.loadAnimation(getActivity(), R.anim.smalltobig);
        //setting the view to be displayed
        deletePrompt.setContentView(R.layout.delete_prompt);
        ImageView close = deletePrompt.findViewById(R.id.closemusic);

        ImageView deleteImage = deletePrompt.findViewById(R.id.stereo);
        deleteImage.setAnimation(SmallToBig);

        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deletePrompt.dismiss();
            }
        });

        //task that occurs once the button is pressed to delete in the modal/dialogBox
        Button bulkDelete = deletePrompt.findViewById(R.id.musicBtn);
        bulkDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //makes a call to viewModel to delete everything
                userViewModel.bulkDeletion();
                deletePrompt.dismiss();
            }
        });

    }

    @Override
    public void onCreateOptionsMenu(@NotNull Menu menu, @NotNull MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.myworth_menu, menu);
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        if (id == R.id.delete_menu) {
            displayModal();
        }

        if (id == R.id.app_masthead) {

            Intent intent = new Intent(getActivity(), Masthead.class);
            startActivity(intent);
            Objects.requireNonNull(getActivity()).finish();
        }

        return super.onOptionsItemSelected(item);
    }
}
