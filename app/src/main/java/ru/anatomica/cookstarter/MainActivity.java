package ru.anatomica.cookstarter;

import android.os.Bundle;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.constraintlayout.widget.ConstraintLayout;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;

public class MainActivity extends AppCompatActivity {

    private HttpClients httpClients;
    private ButtonsCreate buttonsCreate;
    public ConstraintLayout chatLayout;
    public ConstraintLayout chatLayoutInto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        chatLayout = findViewById(R.id.activity_chat);
        chatLayoutInto = findViewById(R.id.activity_chat_layout);
        buttonsCreate = new ButtonsCreate(this, httpClients);
        httpClients = new HttpClients(this, buttonsCreate);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(view ->
                buttonsCreate.createBtn(1)
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                .setAction("Action", null).show()
        );
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        if (chatLayout.getVisibility() == View.VISIBLE) {
            chatLayout.setVisibility(View.INVISIBLE);
            return;
        }
        else {
            shutdown();
        }
    }

    public void onClick(View view) throws InterruptedException {
        switch (view.getId()) {
            case R.id.btn_find:
                break;
            case R.id.btn_choose:
                // httpClient();
                break;
        }
    }

    public void getRestaurants() {
        httpClients.getRestaurants();
    }

    public void shutdown() {
        System.exit(0);
    }
}