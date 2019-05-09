package tw.com.yuantsung.guess

import android.app.Activity
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_record.*

class RecordActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_record)
        var count = intent.getIntExtra("COUNT",-1)
        count_textview.setText("次數: ${count.toString()}")

        saveBtn.setOnClickListener { view ->
            val name = nameText.text.toString()
            getSharedPreferences("guess", Context.MODE_PRIVATE)
                .edit()
                .putInt("COUNT",count)
                .putString("name",name)
                .apply()
            var intent = Intent()
            intent.putExtra("name",name)
            setResult(Activity.RESULT_OK,intent)
            finish()
        }
    }
}
