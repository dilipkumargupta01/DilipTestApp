package com.example.diliptestapp.ui.zoom


import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProviders
import com.example.diliptestapp.R
import com.example.diliptestapp.ui.RecordViewModel
import com.example.diliptestapp.ui.RecordViewModelFactory
import com.example.diliptestapp.util.PicassoTrustAll
import com.ortiz.touchview.TouchImageView
import kotlinx.android.synthetic.main.activity_zoom.*
import org.kodein.di.KodeinAware
import org.kodein.di.android.kodein
import org.kodein.di.generic.instance


class ZoomActivity : AppCompatActivity(), KodeinAware {
    lateinit var imageURL: String
    var id: Int = -1
    private var touch: TouchImageView? = null
    override val kodein by kodein()
    private lateinit var viewModel: RecordViewModel
    private val factory: RecordViewModelFactory by instance()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_zoom)
        viewModel = ViewModelProviders.of(this, factory).get(RecordViewModel::class.java)
        getData()


    }

    fun getData() {
        if (intent != null) {
            if (intent.getStringExtra("imageUrl") != null) {
                imageURL = intent.getStringExtra("imageUrl")
            } else {
                imageURL = ""
            }


            id = intent.getIntExtra("id", -1)

            touch = findViewById<TouchImageView>(R.id.iv_user)

            if (touch != null) {
                PicassoTrustAll.getInstance(this)
                    .load(imageURL)
                    .placeholder(R.mipmap.ic_launcher)
                    .into(iv_user)
            }

        }

        fab.setOnClickListener(View.OnClickListener {
            viewModel.deleteUser(fab, id)
        })
    }

}
