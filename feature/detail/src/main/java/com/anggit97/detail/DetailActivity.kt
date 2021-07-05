package com.anggit97.detail

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.anggit97.core.util.viewBindings
import com.anggit97.detail.databinding.ActivityDetailBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailActivity : AppCompatActivity() {

    private val binding: ActivityDetailBinding by viewBindings(ActivityDetailBinding::inflate)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
    }
}