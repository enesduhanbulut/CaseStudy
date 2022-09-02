package com.duhan.core.presentation.base

import android.os.Bundle
import android.widget.Toast
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.LifecycleOwner

abstract class BaseActivity<DB : ViewDataBinding, VM : BaseViewModel> : AppCompatActivity() {
    private lateinit var viewModel: VM
    private lateinit var binding: DB

    @get:LayoutRes
    abstract val layoutId: Int

    abstract fun getVM(): VM

    abstract fun bindVM(binding: DB, vm: VM)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, layoutId)
        viewModel = getVM()
        bindVM(binding, viewModel)
        with(viewModel) {
            isInProgress.observe(this as LifecycleOwner) {
                if (it) showProgress()
                else hideProgress()
            }

            errorMessage.observe((this as LifecycleOwner)) {
                Toast.makeText(this@BaseActivity, it, Toast.LENGTH_SHORT).show()
            }
        }
    }

    fun showProgress() = BaseProgress().show(supportFragmentManager, PROGRESS)

    fun hideProgress() =
        supportFragmentManager.fragments.filterIsInstance<BaseProgress>().forEach { it.dismiss() }

    companion object {
        private const val PROGRESS = "InProgress"
    }
}