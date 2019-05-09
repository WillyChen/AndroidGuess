package tw.com.yuantsung.guess

import android.app.Activity
import android.content.Context
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

    private val REQUEST_RECORD: Int = 100

    var secretNumber = SecretNumber()
    val TAG = MaterialActivity::class.java.simpleName

    override fun onCreate(savedInstanceState: Bundle?) {


        super.onCreate(savedInstanceState)
        Log.d(TAG, "onCreate")
        setContentView(R.layout.activity_material)
        setSupportActionBar(toolbar)
        Log.d(TAG, "secret: ${secretNumber.secret}")
        fab.setOnClickListener { view ->
            replayGame()

        }

        counter.setText(secretNumber.count.toString())
        val getCount = getSharedPreferences("guess", Context.MODE_PRIVATE)
            .getInt("COUNT", -1)
        var getName = getSharedPreferences("guess", Context.MODE_PRIVATE)
            .getString("name", null)
        Log.d(TAG, "name is $getName count is $getCount")
    }

    private fun replayGame() {
        AlertDialog.Builder(this)
            .setTitle("Replay Game")
            .setMessage("Are you sure?")
            .setNegativeButton(getString(R.string.ok), { dialog, which ->
                secretNumber.reset()
                counter.setText(secretNumber.count.toString())
                numberText.setText(null)
            })
            .setNeutralButton("cancel", null)
            .show()
    }


    override fun onStart() {
        super.onStart()
        Log.d(TAG, "onStart")
    }

    override fun onResume() {
        super.onResume()
        Log.d(TAG, "onResume")
    }

    override fun onPause() {
        super.onPause()
        Log.d(TAG, "onPause")

    }

    override fun onRestart() {
        super.onRestart()
        Log.d(TAG, "onRestart")
    }

    override fun onStop() {
        super.onStop()
        Log.d(TAG, "onStop")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d(TAG, "onDestroy")
    }

    fun okBtnAction(view: View) {
        val number = numberText.text.toString().toInt()
        Log.d(TAG, "number: $number}")
        val diff = secretNumber.validate(number)
        var message = getString(R.string.Yes_got)
        when {
            diff < 0 -> message = getString(R.string.smaller)
            diff > 0 -> message = getString(R.string.bigger)
            else -> {
                if (secretNumber.count < 3) {
                    message = getString(R.string.excellent) + number
                }
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
            .setPositiveButton(getString(R.string.ok), { dialog, which ->
                if (diff == 0) {
                    val recordIntent = Intent(this, RecordActivity::class.java)
                    recordIntent.putExtra("COUNT", secretNumber.count)
//                    startActivity(recordIntent)
                    startActivityForResult(recordIntent, REQUEST_RECORD)
                }

            })
            .show()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_RECORD) {
            if (resultCode == Activity.RESULT_OK) {
                val name = data?.getStringExtra("name")
                Log.d(TAG,"$name")
                replayGame()
            }
        }

    }


}
