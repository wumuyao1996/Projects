package bobulatedapps.genebase;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;


public class MainActivity extends ActionBarActivity {
    public static String currentLine="";
    private Context context;
    private Intent intent;
    private Intent intent2;

    public static String getLine(){
        return currentLine;
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        context = this;

        final Button button = (Button) findViewById(R.id.button);


        final Button button2 = (Button) findViewById(R.id.button2);

        intent= new Intent(context, GeneLookup.class);
        intent2 =new Intent(context, AddGene.class);
        button.setOnClickListener(
                new View.OnClickListener()
                {
                    public void onClick(View view)
                    {
                        startActivity(intent);
                    }
                });
        button2.setOnClickListener(
                new View.OnClickListener()
                {
                    public void onClick(View view)
                    {
                        startActivity(intent2);
                    }
                });
    }



}
