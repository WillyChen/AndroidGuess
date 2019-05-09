package tw.com.yuantsung.guess

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AlertDialog
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity;

import kotlinx.android.synthetic.main.activity_material.*
import kotlinx.android.synthetic.main.content_material.*

class MaterialActivity : AppCompatActivity() {

    var secretNumber = SecretNumber()
    val TAG = MaterialActivity::class.java.simpleName

    override fun onCreate(savedInstanceState: Bundle?) {


        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_material)
        setSupportActionBar(toolbar)
        Log.d(TAG, "secret: ${secretNumber.secret}")
        fab.setOnClickListener { view ->
            AlertDialog.Builder(this)
                .setTitle("Replay Game")
                .setMessage("Are you sure?")
                .setNegativeButton(getString(R.string.ok),{dialog, which ->
                    secretNumber.reset()
                    counter.setText(secretNumber.count.toString())
                    numberText.setText(null)
                })
                .setNeutralButton("cancel",null)
                .show()

        }
        counter.setText(secretNumber.count.toString())
    }


    fun okBtnAction(view: View) {
        val number = numberText.text.toString().toInt()
        Log.d(TAG, "number: $number}")
        val diff = secretNumber.validate(number)
        var message = getString(R.string.Yes_got)
        when {
            diff < 0 -> message = getString(R.string.bigger)
            diff > 0 -> message = getString(R.string.smaller)
            else -> {
                if (secretNumber.count < 3) {
                    message = getString(R.string.excellent) + number}
            }
        }
//        if (diff < 0) {
//            message = getString(R.string.bigger)
//        } else if (diff > 0) {
//            message = getString(R.string.smaller)
//        } else if (secretNumber.count < 3) {
//            message = getString(R.string.excellent) + number
//        }
        counter.setText(secretNumber.count.toString())
//        Toast.makeText(this,message,Toast.LENGTH_LONG).show()
        AlertDialog.Builder(this)
            .setTitle(getString(R.string.dialog_Title))
            .setMessage(message)
            .setPositiveButton(getString(R.string.ok), {dialog, which ->
                if (diff == 0) {
                    val recordIntent = Intent(this,RecordActivity::class.java)
                    startActivity(recordIntent)
                }
            })
            .show()
    }
}
