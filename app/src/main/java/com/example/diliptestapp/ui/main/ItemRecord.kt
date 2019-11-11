package com.example.diliptestapp.ui.main

import com.example.diliptestapp.R
import com.example.diliptestapp.data.db.entities.User
import com.example.diliptestapp.databinding.ItemRecordBinding
import com.xwray.groupie.databinding.BindableItem


class ItemRecord(private val record: User) : BindableItem<ItemRecordBinding>() {
    override fun getLayout() = R.layout.item_record

    override fun bind(viewBinding: ItemRecordBinding, position: Int) {

        viewBinding.record = record

    }

}