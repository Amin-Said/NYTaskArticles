package com.am.taskarticles.di

import com.am.taskarticles.viewmodel.MainViewModel
import dagger.Component

@Component(modules = [ApiModule::class])
interface ViewModelComponent {
    fun inject(viewModel: MainViewModel)
}