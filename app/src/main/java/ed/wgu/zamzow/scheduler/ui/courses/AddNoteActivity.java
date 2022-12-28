package ed.wgu.zamzow.scheduler.ui.courses;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import ed.wgu.zamzow.scheduler.R;
import ed.wgu.zamzow.scheduler.objects.Class;

public class AddNoteActivity extends AppCompatActivity {

    private Class selectedClass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_note);


    }
}