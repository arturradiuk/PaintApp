package com.aradiuk.paintapp


import android.content.Context
import android.content.DialogInterface
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.text.InputType
import android.view.Menu
import android.view.MenuItem
import android.view.Window
import android.view.WindowManager
import android.widget.EditText
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import top.defaults.colorpicker.ColorPickerPopup


class DrawActivity : AppCompatActivity() {
    var drawView: DrawView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        getWindow().setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        );

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.drawView = DrawView(this);
        setContentView(drawView);
        drawView!!.requestFocus();
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        this.menu = menu
        menuInflater.inflate(R.menu.draw_menu, menu)
        return true
    }

    private var menu: Menu? = null
    private var isEraserChecked: Boolean = false

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onOptionsItemSelected(item: MenuItem): Boolean {


        return when (item.itemId) {
            R.id.color -> {

                ColorPickerPopup.Builder(this)
                    .initialColor(Color.RED) // Set initial color
                    .enableBrightness(true) // Enable brightness slider or not
                    .enableAlpha(true) // Enable alpha slider or not
                    .okTitle("Choose")
                    .cancelTitle("Cancel")
                    .showIndicator(true)
                    .showValue(true)
                    .build()
                    .show(drawView, object : ColorPickerPopup.ColorPickerObserver() {
                        override fun onColorPicked(color: Int) {
                            drawView?.changeColor(color)
                            var item = menu?.getItem(2)
                            if (isEraserChecked == true) {
                                item?.isChecked = false
                                isEraserChecked = false
                            }

                        }


                    })
                true;
            }
            R.id.bg_color -> {

                ColorPickerPopup.Builder(this)
                    .initialColor(Color.RED) // Set initial color
                    .enableBrightness(true) // Enable brightness slider or not
                    .enableAlpha(true) // Enable alpha slider or not
                    .okTitle("Choose")
                    .cancelTitle("Cancel")
                    .showIndicator(true)
                    .showValue(true)
                    .build()
                    .show(drawView, object : ColorPickerPopup.ColorPickerObserver() {
                        override fun onColorPicked(color: Int) {
                            drawView?.changeBackGroundColor(color)
                            drawView?.invalidate()
                        }

                    })
                true;
            }

            R.id.normal -> {
                drawView?.normal()
                true;
            }

            R.id.blur -> {
                drawView?.blur()
                true;
            }

            R.id.emboss -> {
                drawView?.emboss()
                true;
            }

            R.id.clear -> {
                drawView?.clear()
                true;
            }

            R.id.eraser -> {
                drawView?.eraser()
                item.isChecked = !item.isChecked
                isEraserChecked = item.isChecked
                true;
            }

            R.id.thickness -> {

                showAddItemDialog(this)
                true;
            }


            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun showAddItemDialog(c: Context) {
        val taskEditText = EditText(c)
        var text = String()
        taskEditText.setInputType(InputType.TYPE_CLASS_NUMBER)
        val dialog: android.app.AlertDialog? = android.app.AlertDialog.Builder(c)
            .setTitle("Thickness")
            .setMessage("Please set new thickness")
            .setView(taskEditText)
            .setPositiveButton("Add",
                DialogInterface.OnClickListener { dialog, which ->
                    text = taskEditText.text.toString()
                    drawView?.currentPointRadius = text.toFloat()
                })
            .setNegativeButton("Cancel", null)
            .create()
        if (dialog != null) {
            dialog.show()
        }
    }


}