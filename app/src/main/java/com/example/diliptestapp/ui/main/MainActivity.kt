package com.example.diliptestapp.ui.main

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.GridLayoutManager
import com.example.diliptestapp.R
import com.example.diliptestapp.data.db.entities.User
import com.example.diliptestapp.ui.RecordViewModel
import com.example.diliptestapp.ui.RecordViewModelFactory
import com.example.diliptestapp.util.Coroutines
import com.example.diliptestapp.util.hide
import com.example.diliptestapp.util.show
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.ViewHolder
import kotlinx.android.synthetic.main.activity_main.*
import org.kodein.di.KodeinAware
import org.kodein.di.android.kodein
import org.kodein.di.generic.instance


class MainActivity : AppCompatActivity(), KodeinAware {

    override val kodein by kodein()
    private lateinit var viewModel: RecordViewModel
    private val factory: RecordViewModelFactory by instance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        viewModel = ViewModelProviders.of(this, factory).get(RecordViewModel::class.java)
        bindUI()
        fab.setOnClickListener(View.OnClickListener {
            viewModel.addUser(fab)
        })

    }

    private fun bindUI() = Coroutines.main {
        progress_bar.show()
        viewModel.records.await().observe(this, Observer {
            //  toast(it.size.toString())
            progress_bar.hide()
            initRecyclerView(it.toRecordItem())

        })
    }

    private fun initRecyclerView(mItemRecord: List<ItemRecord>) {

        val mAdapter = GroupAdapter<ViewHolder>().apply {
            addAll(mItemRecord)

        }

        recyclerview.apply {
            layoutManager = GridLayoutManager(context, 2)
            setHasFixedSize(true)
            adapter = mAdapter
        }


    }

    private fun List<User>.toRecordItem(): List<ItemRecord> {
        return this.map {
            ItemRecord(it)
        }
    }
}
